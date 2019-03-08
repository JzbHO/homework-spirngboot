package com.wust.homework.util;

import java.util.Map;

public class ReponseUtil {
    public static Response makeResponse(Map data, String msg, int code){
        Response response=new Response();
        response.setData(data);
        response.setMsg(msg);
        response.setCode(code);
        return response;
    }



}
