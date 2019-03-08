package com.wust.homework.model;

import lombok.Data;

import java.util.Date;

@Data
public class Good {
    private int id;
    private String itemName;
    private double itemPrice;
    private String pictureUrl;
    private int isBuy;
    private int itemNum;
    private String buyTime;
    private String summary;
    private String detail;
    private double buyPrice;



}
