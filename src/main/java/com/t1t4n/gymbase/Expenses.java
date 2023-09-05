package com.t1t4n.gymbase;

import java.util.Date;

public class Expenses {
    String name;
    String comment;
    Date date;
    double value;

    public Expenses(String name, String comment, Date date, double value) {
        this.name = name;
        this.comment = comment;
        this.date = date;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
