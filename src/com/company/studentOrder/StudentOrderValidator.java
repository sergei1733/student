package com.company.studentOrder;

import com.company.studentOrder.domain.*;
import com.company.studentOrder.mail.MailSender;
import com.company.studentOrder.validator.ChildrenValidator;
import com.company.studentOrder.validator.CityRegisterValidator;
import com.company.studentOrder.validator.StudentValidator;
import com.company.studentOrder.validator.WeddingValidator;

import java.util.LinkedList;
import java.util.List;

public class StudentOrderValidator {
    private CityRegisterValidator citiregisterVal;
    private WeddingValidator weddingVal;
    private ChildrenValidator childrenVal;
    private StudentValidator studentVal;
    private MailSender mailSender;

    public StudentOrderValidator() {
        citiregisterVal = new CityRegisterValidator();
        weddingVal = new WeddingValidator();
        childrenVal = new ChildrenValidator();
        studentVal = new StudentValidator();
        mailSender = new MailSender();
    }

    public static void main(String[] args) {

        StudentOrderValidator sov = new StudentOrderValidator();
        sov.checkAll();
    }

    public void checkAll() {
        List<StudentOrder> soList = readStudentOrders();
        for (StudentOrder so : soList) {
            checkOneOrder(so);
        }
    }

    public void checkOneOrder(StudentOrder so) {
        AnswerCityRegister citiAnswer = checkCityRegister(so);
        //AnswerChildren childAnswer = checkChildren(so);
        //AnswerStudent studentAnswer = checkStudent(so);
        //AnswerWedding wedAnswer = checkWedding(so);

        //sendMail(so);
    }

    public List<StudentOrder> readStudentOrders() {
        List<StudentOrder> soList = new LinkedList<>();
        for (int c = 0; c < 5; c++) {
                StudentOrder so = SaveStudentOrder.buildStudentOrder(c);
                soList.add(so);
            }
        return soList;
        }

        public AnswerCityRegister checkCityRegister (StudentOrder so){

            return citiregisterVal.checkCityRegister(so);
        }

        public AnswerWedding checkWedding (StudentOrder so){
            return weddingVal.checkWedding(so);
        }

        public AnswerChildren checkChildren (StudentOrder so){
            return childrenVal.checkChildren(so);
        }

        public AnswerStudent checkStudent (StudentOrder so){
            return studentVal.checkStudent(so);
        }

        public void sendMail (StudentOrder so){
            mailSender.sendMail(so);
        }
    }
