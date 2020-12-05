package com.company.studentOrder;

import com.company.dao.DictionaryDaoImpl;
import com.company.dao.StudentOrderDao;
import com.company.dao.StudentOrderDaoImpl;
import com.company.studentOrder.domain.*;
import com.company.studentOrder.exception.DaoException;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class SaveStudentOrder {
    public static void main(String[] args) throws Exception {
  //      List<Street> d = new DictionaryDaoImpl().findStreet("про");
  //      for (Street s : d) {
  //          System.out.println(s.getStreetName());
  //      }
  //      List<PassportOffice> po = new DictionaryDaoImpl().findPassportOffice("010020000000");
  //      for (PassportOffice p : po) {
  //          System.out.println(p.getOfficeName());
  //      }
  //      List<RegisterOffice> ro = new DictionaryDaoImpl().findregisterOffice("010010000000");
  //      for (RegisterOffice r : ro) {
  //          System.out.println(r.getOfficeName());
  //      }
        /*
        List<CountryArea> ca1 = new DictionaryDaoImpl().findAreas("");
        for (CountryArea c : ca1) {
            System.out.println(c.getAreaId() + ";" + c.getAreaName());
        }
        System.out.println("--------->");
        List<CountryArea> ca2 = new DictionaryDaoImpl().findAreas("020000000000");
        for (CountryArea c : ca2) {
            System.out.println(c.getAreaId() + ";" + c.getAreaName());
        }
        System.out.println("--------->");
        List<CountryArea> ca3 = new DictionaryDaoImpl().findAreas("020010000000");
        for (CountryArea c : ca3) {
            System.out.println(c.getAreaId() + ";" + c.getAreaName());
        }
        System.out.println("--------->");
        List<CountryArea> ca4 = new DictionaryDaoImpl().findAreas("020010010000");
        for (CountryArea c : ca4) {
            System.out.println(c.getAreaId() + ";" + c.getAreaName());
        }

         */
        //StudentOrder s = buildStudentOrder(10);
        StudentOrderDao dao = new StudentOrderDaoImpl();
        //Long id = dao.saveStudentOrder(s);
        //System.out.println(id);

        List<StudentOrder> soList = dao.getStudentOrders();
        for (StudentOrder so : soList){
            System.out.println(so.getStudentOrderId());
        }
        //Class.forName("org.postgresql.Driver");
        //Connection con = DriverManager.getConnection(
        //         "jdbc:postgresql://localhost:5432/jc_student",
        //         "postgres", "postgres");
        //
        //Statement stmt = con.createStatement();
        //ResultSet rs = stmt.executeQuery("select * from jc_street");
        //while(rs.next()){
        //    System.out.println(rs.getLong(1)+" : "+ rs.getString(2));
        //}

        //buildStudentOrder(so);
        //StudentOrder so = new StudentOrder();


        //long ans = saveStudentOrder(so);
        //System.out.println(ans);
    }

    static long saveStudentOrder(StudentOrder studentOrder) {
        long answer = 190;
        System.out.println("saveStudentOrder: ");
        return answer;
    }

    public static StudentOrder buildStudentOrder(long id) {
        StudentOrder so = new StudentOrder();
        so.setStudentOrderId(id);
        so.setMarriageCertificateId("" + (123456000 + id));
        so.setMarriageDate(LocalDate.of(2016, 7, 4));
        RegisterOffice ro = new RegisterOffice(1l, "", "");
        so.setMarriageOffice(ro);

        Street street = new Street(1l, "First street");
        Address address = new Address("123456", street, "12", "", "142");
        //муж
        Adult husband = new Adult("Васильев", "Андрей", "Петрович", LocalDate.of(1991, 1, 1));
        husband.setPassportSeria("" + (1000 + id));
        husband.setPassportNumber("" + (10000 + id));
        husband.setIssueDate(LocalDate.of(2017, 9, 15));
        PassportOffice po1 = new PassportOffice(1l, "", "");
        husband.setIssueDepartment(po1);
        husband.setStudentId("" + (10000 + id));
        husband.setAddress(address);
        husband.setUnivesity(new University(2L, ""));
        husband.setStudentId("HH12345");
        //жена
        Adult wife = new Adult("Петрова", "Вероника", "Алексеевна", LocalDate.of(1998, 3, 12));
        wife.setPassportSeria("" + (2000 + id));
        wife.setPassportNumber("" + (20000 + id));
        wife.setIssueDate(LocalDate.of(2015, 9, 15));
        PassportOffice po2 = new PassportOffice(2l, "", "");
        wife.setIssueDepartment(po2);
        wife.setStudentId("" + (20000 + id));
        wife.setAddress(address);
        wife.setUnivesity(new University(1L, ""));
        wife.setStudentId("WW12345");
        //ребенок
        Child child1 = new Child("Петрова", "Ирина", "Викторовна", LocalDate.of(2018, 6, 29));
        child1.setCertificateNumber("" + (30000 + id));
        child1.setIssueDate(LocalDate.of(2018, 7, 19));
        RegisterOffice ro2 = new RegisterOffice(2l, "", "");
        child1.setIssueDepartment(ro2);
        child1.setAddress(address);
        //ребенок
        Child child2 = new Child("Петров", "Евгений", "Викторовна", LocalDate.of(2018, 6, 29));
        child2.setCertificateNumber("" + (40000 + id));
        child2.setIssueDate(LocalDate.of(2018, 7, 19));
        RegisterOffice ro3 = new RegisterOffice(3l, "", "");
        child2.setIssueDepartment(ro3);
        child2.setAddress(address);

        so.setHusband(husband);
        so.setWife(wife);
        so.addChild(child1);
        so.addChild(child2);

        return so;
    }


}
