package com.wust.homework.util;

import lombok.Data;

import java.util.Map;

@Data
public class Response {
    private Map<String,Object>data;
    private String msg;
    private int code;
}
