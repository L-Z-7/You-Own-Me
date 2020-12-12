package com.jnu.youownme.dataprocessor;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

public class Date implements Serializable {
    private int year, month, day;

    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public Date(String str){
        String[] date = str.split("\\.");
        this.year = Integer.parseInt(date[0]);
        this.month = Integer.parseInt(date[1]) - 1;
        this.day = Integer.parseInt(date[2]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Date date = (Date) o;
        return year == date.year &&
                month == date.month &&
                day == date.day;
    }

    @NonNull
    @Override
    public String toString() {
        return year + "." + (month+1) + "." + day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
