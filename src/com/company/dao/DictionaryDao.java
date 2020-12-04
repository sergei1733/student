package com.company.dao;

import com.company.studentOrder.domain.CountryArea;
import com.company.studentOrder.domain.PassportOffice;
import com.company.studentOrder.domain.RegisterOffice;
import com.company.studentOrder.domain.Street;
import com.company.studentOrder.exception.DaoException;

import java.util.List;

public interface DictionaryDao {
    List<Street> findStreet(String pattern)throws DaoException;
    List<PassportOffice> findPassportOffice(String areaId)throws DaoException;
    List<RegisterOffice> findregisterOffice(String areaId)throws DaoException;
    List<CountryArea> findAreas(String areaId)throws DaoException;

}
