package com.jnu.youownme.dataprocessor;

public enum Reason {
    MARRY, BIRTHDAY, HOUSE, EXAM;

    @Override
    public String toString() {
        String s="";
        switch (this){
            case MARRY:
                s = "结婚大喜";
                break;
            case BIRTHDAY:
                s = "生辰诞日";
                break;
            case EXAM:
                s = "金榜题名";
                break;
            case HOUSE:
                s = "新造华堂";
                break;
            default:
                break;
        }
        return s;
    }

    static public Reason getReason(String str){
        Reason reason = BIRTHDAY;
        switch (str){
            case "结婚大喜":
                reason = MARRY;
                break;
            case "生辰诞日":
                reason = BIRTHDAY;
                break;
            case "新造华堂":
                reason = HOUSE;
                break;
            case "金榜题名":
                reason = EXAM;
                break;
            default:
                break;
        }
        return reason;
    }
}