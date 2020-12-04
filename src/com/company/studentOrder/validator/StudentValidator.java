package com.company.studentOrder.validator;

import com.company.studentOrder.domain.AnswerStudent;
import com.company.studentOrder.domain.StudentOrder;

public class StudentValidator {
    public AnswerStudent checkStudent(StudentOrder so){
        System.out.println("Студенты проверяются");
        return new AnswerStudent();
    }
}
