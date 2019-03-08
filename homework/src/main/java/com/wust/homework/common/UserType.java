package com.wust.homework.common;

public enum UserType {

    BUYER(0,"用户"),SELLER(1,"商家");

    private int code;
    private String msg;

    private UserType(int code,String msg){
        this.code=code;
        this.msg=msg;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
