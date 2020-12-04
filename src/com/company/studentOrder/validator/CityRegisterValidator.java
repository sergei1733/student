package com.company.studentOrder.validator;

import com.company.studentOrder.domain.*;
import com.company.studentOrder.exception.CityRegisterException;
import com.company.studentOrder.exception.TransportException;

import java.util.List;

public class CityRegisterValidator {
    public static final String IN_CODE = "NO_GRN";
    public String hostName;
    String password;
    protected int port;
    private String login;

    private CityRegisterChecker personChecker;

    public CityRegisterValidator() {

        personChecker = new FakeCityRegisterChecker();
    }

    public AnswerCityRegister checkCityRegister(StudentOrder so) {
        var ans = new AnswerCityRegister();
        ans.addItem(checkPerson(so.getHusband()));
        ans.addItem(checkPerson(so.getWife()));
        List<Child> children = so.getChildren();
        for (Child child : children) {
            ans.addItem(checkPerson(child));
        }

        return ans;
    }
    private AnswerCityRegisterItem checkPerson(Person person){
        AnswerCityRegisterItem.CityStatus status = null;
        AnswerCityRegisterItem.CityError error = null;
        try {
            CityRegisterResponse tmp = personChecker.checkPerson(person);
            status = tmp.isExisting()?
                    AnswerCityRegisterItem.CityStatus.YES:
                    AnswerCityRegisterItem.CityStatus.NO;
        } catch (CityRegisterException ex){
            ex.printStackTrace(System.out);
            status = AnswerCityRegisterItem.CityStatus.ERROR;
            error = new AnswerCityRegisterItem.CityError(ex.getCode(),ex.getMessage());
        }catch (TransportException ex){
            ex.printStackTrace(System.out);
            status = AnswerCityRegisterItem.CityStatus.ERROR;
            error = new AnswerCityRegisterItem.CityError(IN_CODE,ex.getMessage());
        }
        AnswerCityRegisterItem ans =
                new AnswerCityRegisterItem(status,person,error);
        return ans;
    }
}
