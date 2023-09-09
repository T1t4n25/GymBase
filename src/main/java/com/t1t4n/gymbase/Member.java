package com.t1t4n.gymbase;

import java.util.Date;

public class Member {
    int id;
    String name;
    String subState;
    String subType;
    int subValue;
    Date subStartDate;
    Date deadlineDate;
    Date joinDate;
    Date lastPayDate;
    String number;
    int joinDuration;

    //full constructor for data importing from database


    public Member(int id, String name, String subState, String subType, int subValue, Date deadlineDate, Date joinDate, String number, Date lastPayDate) {
        this.id = id;
        this.name = name;
        this.subState = subState;
        this.subType = subType;
        this.subValue = subValue;
        this.deadlineDate = deadlineDate;
        this.joinDate = joinDate;
        this.number = number;
        this.lastPayDate = lastPayDate;
    }

    public Member(int id, String name, String subState, String subType, int subValue, Date date, Date lastpay, Date deadlineDate) {
        this.id = id;
        this.name = name;
        this.subState = subState;
        this.subType = subType;
        this.subValue = subValue;
        this.joinDate = date;
        this.lastPayDate = lastpay;
        this.deadlineDate =deadlineDate;
    }

    public Member(String name, String subType, int joinDuration, boolean duration) {
        this.name = name;
        this.subType = subType;
        this.joinDuration = joinDuration;
    }

    public Member(String name, String number, int subValue) {
        this.name = name;
        this.subValue = subValue;
        this.number = number;
    }

    public Member(String name, int subValue, Date deadlineDate, String number) {
        this.name = name;
        this.subValue = subValue;
        this.deadlineDate = deadlineDate;
        this.number = number;
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

    public int getSubValue() {
        return subValue;
    }

    public void setSubValue(int subValue) {
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

    public Date getLastPayDate() {
        return lastPayDate;
    }

    public void setLastPayDate(Date lastPayDate) {
        this.lastPayDate = lastPayDate;
    }

    public String getJoinDuration() {
        return String.valueOf(joinDuration) + " يوم";
    }

    public void setJoinDuration(int joinDuration) {
        this.joinDuration = joinDuration;
    }
}
