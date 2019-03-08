package com.wust.homework.common;

public enum GlobalConst {
    HAVEBUY(0,"已购买"),NOTBUY(1,"未购买"),
    HAVESELLE(0,"已售出"),NOSELLE(1,"未售出");

    private int code;
    private String msg;

    private GlobalConst(int code,String msg){
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
