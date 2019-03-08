package com.wust.homework.common;

import lombok.Data;

public enum ERROECODE {

    SUCCESSS(0,"请求成功"),LOGIN_FAIL(1,"账号密码错误");

    private int code;
    private String msg;

    private ERROECODE(int code,String msg){
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
