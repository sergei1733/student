package com.company.dao;

import com.company.config.Config;
import com.company.studentOrder.domain.*;
import com.company.studentOrder.exception.DaoException;
import org.w3c.dom.DOMException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class StudentOrderDaoImpl implements StudentOrderDao {

    private static final String INSERT_ORDER =
            "INSERT INTO jc_student_order(" +
                    " student_order_status, student_order_date, h_sur_name, " +
                    " h_given_name, h_patronymic, h_date_of_birth, h_passport_seria, " +
                    " h_passport_number, h_passport_date, h_passport_office_id, h_post_index, " +
                    " h_street_code, h_building, h_extension, h_apartment, h_university_id, h_student_number, " +
                    " w_sur_name, w_given_name, w_patronymic, w_date_of_birth, w_passport_seria, " +
                    " w_passport_number, w_passport_date, w_passport_office_id, w_post_index, " +
                    " w_street_code, w_building, w_extension, w_apartment, w_university_id, w_student_number, " +
                    " certificate_id, register_office_id, marriage_date)" +
                    " VALUES (?, ?, ?, " +
                    " ?, ?, ?, ?, " +
                    " ?, ?, ?, ?, " +
                    " ?, ?, ?, ?, ?, ?, " +
                    " ?, ?, ?, ?, ?, " +
                    " ?, ?, ?, ?, " +
                    " ?, ?, ?, ?, ?, ?, " +
                    " ?, ?, ?);";
    private static final String INSERT_CHILD =
            "INSERT INTO jc_student_child(" +
                    " student_order_id, " +
                    "c_sur_name, c_given_name, c_patronymic, " +
                    "c_date_of_birth, c_certificate_number, " +
                    "c_certificate_date, c_register_office_id, " +
                    "c_post_index, c_street_code, c_building, " +
                    "c_extension, c_apartment)" +
                    "VALUES ( ?, ?, ?, ?, ?, ?," +
                    "?, ?, ?, ?, ?, ?, ?);";
    private static final String SELECT_ORDERS =
            "select * from jc_student_order where student_order_status = 0 ORDER BY student_order_date";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                Config.getProperties(Config.DB_URL),
                Config.getProperties(Config.DB_LOGIN),
                Config.getProperties(Config.DB_PASSWORD));
    }

    @Override
    public long saveStudentOrder(StudentOrder so) throws DaoException {
        long result = -1L;
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(INSERT_ORDER,new String [] {"student_order_id"})) {
           con.setAutoCommit(false);//берем транзакцию в свои руки
           try {
               //header
               stmt.setInt(1, StudentOrderStatus.START.ordinal());
               stmt.setTimestamp(2, java.sql.Timestamp.valueOf(LocalDateTime.now()));

               //husband and wife
               setParamsForAdult(stmt, 3, so.getHusband());//заполнение для мужа
               setParamsForAdult(stmt, 18, so.getWife());// заполнение для жены

               //marriage
               stmt.setString(33, so.getMarriageCertificateId());
               stmt.setLong(34, so.getMarriageOffice().getOfficeId());
               stmt.setDate(35, java.sql.Date.valueOf(so.getMarriageDate()));

               stmt.executeUpdate(); //мщдификация донных

               ResultSet gkRs = stmt.getGeneratedKeys();

               if (gkRs.next()) {
                   result = gkRs.getLong(1);
               }
               gkRs.close();

               saveChildren(con, so, result);

               con.commit(); //сохраняем изменения
           }catch (SQLException ex){
               con.rollback(); //отменяем изменения
               throw ex;
           }

        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
        return result;

    }

    @Override
    public List<StudentOrder> getStudentOrders() throws DaoException {
        List<StudentOrder> result = new LinkedList<>();
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_ORDERS)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                StudentOrder so = new StudentOrder();
                fillStudentOrder(rs, so);
                fillMarriage(rs,so);
                Adult husband = fillAdult(rs,"h_");
                Adult wife = fillAdult(rs,"w_");
                so.setHusband(husband);
                so.setWife(wife);
                result.add(so);
            }
            rs.close();
        }catch (SQLException ex) {
            throw new DaoException(ex);

        }
        return result;
    }

    private Adult fillAdult(ResultSet rs, String pref) throws SQLException {
        Adult adult = new Adult();
        adult.setSorName(rs.getString(pref+"sur_name"));
        adult.setGivenName(rs.getString(pref+"given_name"));
        adult.setPatronymic(rs.getString(pref+"patronymic"));
        adult.setDate0fBirth(rs.getDate(pref+"date_of_birth").toLocalDate());
        adult.setPassportSeria(rs.getString(pref+"passport_seria"));
        adult.setPassportNumber(rs.getString(pref+"passport_number"));
        adult.setIssueDate(rs.getDate(pref+"passport_date").toLocalDate());

        PassportOffice po = new PassportOffice(rs.getLong(pref+"passport_office_id"),"","");
        adult.setIssueDepartment(po);
        Address adr = new Address();
        Street st = new Street(rs.getLong(pref+"street_code"),"");
        adr.setStreet(st);
        adr.setPostCode(rs.getString(pref+"post_index"));
        adr.setBuilding(rs.getString(pref+"building"));
        adr.setExtension(rs.getString(pref+"extension"));
        adr.setApartment(rs.getString(pref+"apartment"));

        adult.setAddress(adr);

        University uni = new University(rs.getLong(pref+"university_id"),"");
        adult.setUnivesity(uni);
        adult.setStudentId(rs.getString(pref+"student_number"));


        return adult;
    }

    private void fillStudentOrder(ResultSet rs, StudentOrder so) throws SQLException {
        so.setStudentOrderId(rs.getLong("student_order_id"));
        so.setStudentOrderDate(rs.getTimestamp("student_order_date").toLocalDateTime());
        so.setStudentOrderStatus(StudentOrderStatus.fromValue(rs.getInt("student_order_status")));
    }
    private void fillMarriage(ResultSet rs, StudentOrder so) throws SQLException {
        so.setMarriageCertificateId(rs.getString("certificate_id"));
        so.setMarriageDate(rs.getDate("marriage_date").toLocalDate());
        long roId = rs.getLong("register_office_id");
        RegisterOffice ro = new RegisterOffice(roId,"","");
        so.setMarriageOffice(ro);

       // so.setMarriageDate(rs.getDate(""));
    }

    private void saveChildren(Connection con, StudentOrder so, Long soId) throws SQLException {
        try(PreparedStatement stmt = con.prepareStatement(INSERT_CHILD)) {
            for (Child child : so.getChildren()) {
                stmt.setLong(1,soId);
                setParaForChild(stmt,child);
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    private void setParamsForAdult(PreparedStatement stmt, int start, Adult adult) throws SQLException {
        setParamsForPerson(stmt, start, adult);
        stmt.setString(start+4,adult.getPassportSeria());
        stmt.setString(start+5,adult.getPassportNumber());
        stmt.setDate(start+6, Date.valueOf(adult.getIssueDate()));
        stmt.setLong(start+7, adult.getIssueDepartment().getOfficeId());
        setParamsForAddress(stmt, start+8, adult);
        stmt.setLong(start+13,adult.getUnivesity().getUniversityId());
        stmt.setString(start+14,adult.getStudentId());

    }

    private void setParaForChild(PreparedStatement stmt, Child child) throws SQLException{
        setParamsForPerson(stmt,2,child);
        stmt.setString(6, child.getCertificateNumber());
        stmt.setDate(7,Date.valueOf(child.getIssueDate()));
        stmt.setLong(8,child.getIssueDepartment().getOfficeId());
        setParamsForAddress(stmt,9,child);
    }

    private void setParamsForPerson(PreparedStatement stmt, int start, Person person) throws SQLException {
        stmt.setString(start, person.getSorName());
        stmt.setString(start +1, person.getGivenName());
        stmt.setString(start +2, person.getPatronymic());
        stmt.setDate(start +3, Date.valueOf(person.getDate0fBirth()));
    }

    private void setParamsForAddress(PreparedStatement stmt, int start, Person person) throws SQLException {
        Address h_address = person.getAddress();
        stmt.setString(start,h_address.getPostCode());
        stmt.setLong(start +1, h_address.getStreet().getStreetCode());
        stmt.setString(start +2, h_address.getBuilding());
        stmt.setString(start +3, h_address.getExtension());
        stmt.setString(start +4, h_address.getApartment());
    }
}
