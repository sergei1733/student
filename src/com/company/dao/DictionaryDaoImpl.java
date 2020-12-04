package com.company.dao;

import com.company.config.Config;
import com.company.studentOrder.domain.CountryArea;
import com.company.studentOrder.domain.PassportOffice;
import com.company.studentOrder.domain.RegisterOffice;
import com.company.studentOrder.domain.Street;
import com.company.studentOrder.exception.DaoException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DictionaryDaoImpl implements DictionaryDao {
    private static final String GET_STREET = "select street_code, street_name FROM jc_street where upper(street_name) like upper(?)";
    private static final String GET_PASSPORT = "select * FROM jc_passport_office WHERE p_office_area_id = ?";
    private static final String GET_REGISTER = "select * FROM jc_register_office WHERE r_office_area_id = ?";
    private static final String GET_AREA = "select * FROM jc_country_struct WHERE area_id like ? and area_id<> ?";

    private Connection getConnection() throws SQLException {
        Connection con = DriverManager.getConnection(
                Config.getProperties(Config.DB_URL),
                Config.getProperties(Config.DB_LOGIN),
                Config.getProperties(Config.DB_PASSWORD));

        return con;
    }

    public List<Street> findStreet(String pattern) throws DaoException {
        List<Street> result = new LinkedList<>();

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_STREET);) {
            stmt.setString(1, "%"+pattern+"%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Street str = new Street(rs.getLong("Street_code"), rs.getString("street_name"));
                result.add(str);
            }
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
        return result;
    }

    @Override
    public List<PassportOffice> findPassportOffice(String areaId) throws DaoException {
        List<PassportOffice> result = new LinkedList<>();

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_PASSPORT);) {
            stmt.setString(1, areaId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PassportOffice str = new PassportOffice(
                        rs.getLong("p_office_id"),
                        rs.getString("p_office_area_id"),
                        rs.getString("p_office_name"));
                result.add(str);
            }
            return result;
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public List<RegisterOffice> findregisterOffice(String areaId) throws DaoException {
        List<RegisterOffice> result = new LinkedList<>();

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_REGISTER);) {
            stmt.setString(1, areaId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                RegisterOffice str = new RegisterOffice(
                        rs.getLong("r_office_id"),
                        rs.getString("r_office_area_id"),
                        rs.getString("r_office_name"));
                result.add(str);
            }
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
        return result;
    }

    @Override
    public List<CountryArea> findAreas(String areaId) throws DaoException {
        List<CountryArea> result = new LinkedList<>();

        try (Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(GET_AREA);) {

            String param1=bildParam(areaId);
            String param2=areaId;
            stmt.setString(1, param1);
            stmt.setString(2, param2);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                CountryArea str = new CountryArea(
                        rs.getString("area_id"),
                        rs.getString("area_name"));
                result.add(str);
            }
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
        return result;

    }

    private String bildParam(String areaId)throws SQLException {
        if (areaId==null || areaId.trim().isEmpty()){
            return "__0000000000";
        } else if (areaId.endsWith("0000000000")){
            return areaId.substring(0,2)+"___0000000";
        }else if (areaId.endsWith("0000000")){
            return areaId.substring(0,5)+"___0000";
        }else if (areaId.endsWith("0000")) {
            return areaId.substring(0, 8) + "____";
        }
        throw new SQLException("Invalid parameter 'areaId': "+areaId);

    }
}
