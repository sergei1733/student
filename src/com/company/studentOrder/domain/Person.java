package com.company.studentOrder.domain;

import java.time.LocalDate;

public abstract class Person {
    private String sorName;
    private String givenName;
    private String patronymic;
    private LocalDate date0fBirth;
    private Address address;

    public Person() {

    }

    public Person(String sorName, String givenName, String patronymic, LocalDate date0fBirth) {
        this.sorName = sorName;
        this.givenName = givenName;
        this.patronymic = patronymic;
        this.date0fBirth = date0fBirth;
    }

    public String getSorName() {
        return sorName;
    }

    public void setSorName(String sorName) {
        this.sorName = sorName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public LocalDate getDate0fBirth() {
        return date0fBirth;
    }

    public void setDate0fBirth(LocalDate date0fBirth) {
        this.date0fBirth = date0fBirth;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
