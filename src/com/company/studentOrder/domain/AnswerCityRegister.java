package com.company.studentOrder.domain;

import java.util.ArrayList;
import java.util.List;

public class AnswerCityRegister {
    private List<AnswerCityRegisterItem> items;

    public void addItem(AnswerCityRegisterItem item){
        if (this.items == null){
            this.items = new ArrayList<>(10);
        }
        items.add(item);
    }

    public List<AnswerCityRegisterItem> getItems() {
        return items;
    }
}
