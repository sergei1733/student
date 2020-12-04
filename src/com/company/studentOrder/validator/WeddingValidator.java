package com.company.studentOrder.validator;

import com.company.studentOrder.domain.AnswerWedding;
import com.company.studentOrder.domain.StudentOrder;

public class WeddingValidator {
     public AnswerWedding checkWedding(StudentOrder so){
        System.out.println("wedding запущен");
        return new AnswerWedding();
    }
}
