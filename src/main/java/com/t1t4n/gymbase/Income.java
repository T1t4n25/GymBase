package com.t1t4n.gymbase;

import java.util.Date;

public class Income {
    int value;
    String name;
    Date date;

    public Income(int value, String name, Date date) {
        this.value = value;
        this.name = name;
        this.date = date;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
