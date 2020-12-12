package com.jnu.youownme.dataprocessor;

public enum Type {
    RECEIVE, RENDER, NULL;

    @Override
    public String toString() {
        String s="";
        switch (this){
            case RECEIVE:
                s = "收礼";
                break;
            case RENDER:
                s = "随礼";
                break;
            default:
                break;
        }
        return s;
    }

    static public Type getType(String str){
        Type type = NULL;
        switch (str){
            case "收礼":
                type = RECEIVE;
                break;
            case "随礼":
                type = RENDER;
                break;
            default:
                break;
        }
        return type;
    }
}
