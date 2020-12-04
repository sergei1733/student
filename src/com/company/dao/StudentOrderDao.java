package com.company.dao;

import com.company.studentOrder.domain.StudentOrder;
import com.company.studentOrder.exception.DaoException;

import java.util.List;

public interface StudentOrderDao {
    long saveStudentOrder(StudentOrder so) throws DaoException;
    List<StudentOrder> getStudentOrders() throws DaoException;
}
