package com.company.studentOrder.validator;

import com.company.studentOrder.domain.AnswerChildren;
import com.company.studentOrder.domain.StudentOrder;

public class ChildrenValidator {
    public AnswerChildren checkChildren(StudentOrder so){
        System.out.println("Children check is running");
        return new AnswerChildren();
    }
}
