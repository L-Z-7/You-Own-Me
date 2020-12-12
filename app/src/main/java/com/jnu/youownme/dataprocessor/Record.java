package com.jnu.youownme.dataprocessor;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

public class Record implements Serializable {
    Type type;
    Date date;
    double money;
    Reason reason;
    String name;

    public Record(Type type, Date date, double money, Reason reason, String name) {
        this.type = type;
        this.date = date;
        this.money = money;
        this.reason = reason;
        this.name = name;
    }

    public Record() {
        this.type = Type.RECEIVE;
        this.date = DataBank.getSelectedDate();
        this.money = 0;
        this.reason = Reason.BIRTHDAY;
        this.name = "匿名";
    }

    public void assign(Record record){
        this.type = record.getType();
        this.date = record.getDate();
        this.money = record.getMoney();
        this.reason = record.getReason();
        this.name = record.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Record record = (Record) o;
        return this.toString().equals(record.toString());
    }

    @NonNull
    @Override
    public String toString() {
        return type.toString()+" "+date+" "+name+" "+money+" "+reason.toString();
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public Reason getReason() {
        return reason;
    }

    public void setReason(Reason reason) {
        this.reason = reason;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
