package com.jnu.youownme.dataprocessor;

public enum Reason {
    MARRY, BIRTHDAY, NULL;

    @Override
    public String toString() {
        String s="";
        switch (this){
            case MARRY:
                s = "结婚";
                break;
            case BIRTHDAY:
                s = "生日";
                break;
            default:
                break;
        }
        return s;
    }

    static public Reason getReason(String str){
        Reason reason = NULL;
        switch (str){
            case "结婚":
                reason = MARRY;
                break;
            case "生日":
                reason = BIRTHDAY;
                break;
            default:
                break;
        }
        return reason;
    }
}
