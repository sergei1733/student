package com.company.studentOrder.domain;

import java.time.LocalDate;

public class Child extends Person{
    private String certificateNumber;
    private LocalDate issueDate;
    private RegisterOffice issueDepartment;

    public Child(String sorName, String givenName, String patronymic, LocalDate date0fBirth) {
        super(sorName, givenName, patronymic, date0fBirth);
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public RegisterOffice getIssueDepartment() {
        return issueDepartment;
    }

    public void setIssueDepartment(RegisterOffice issueDepartment) {
        this.issueDepartment = issueDepartment;
    }
}
