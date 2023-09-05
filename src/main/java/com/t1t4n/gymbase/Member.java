package com.t1t4n.gymbase;

import java.util.Date;

public class Member {
    int id;
    String name;
    String subState;
    String subType;
    Double subValue;
    Date subStartDate;
    Date deadlineDate;
    Date joinDate;
    String number;


    //full constructor for data importing from database


    public Member(int id, String name, String subState, String subType, Double subValue, Date deadlineDate, Date joinDate, String number) {
        this.id = id;
        this.name = name;
        this.subState = subState;
        this.subType = subType;
        this.subValue = subValue;
        this.deadlineDate = deadlineDate;
        this.joinDate = joinDate;
        this.number = number;
    }

    //sub constructor for adding members without paying
    public Member(int id, String name, String number, String subType, String subState, Date joinDate) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.subType = subType;
        this.subState = subState;
        this.joinDate = joinDate;
    }

    //sub constructor for adding the money and the deadline based on months
    public Member(Double subValue, Date deadlineDate) {
        this.subValue = subValue;
        this.deadlineDate = deadlineDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSubValue() {
        return subValue;
    }

    public void setSubValue(Double subValue) {
        this.subValue = subValue;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getSubState() {
        return subState;
    }

    public void setSubState(String subState) {
        this.subState = subState;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public Date getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(Date deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public Date getSubStartDate() {
        return subStartDate;
    }

    public void setSubStartDate(Date subStartDate) {
        this.subStartDate = subStartDate;
    }
}
