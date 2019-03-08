package com.wust.homework.util;

import org.apache.logging.log4j.util.Strings;
import org.springframework.util.StringUtils;

public class UGCcheck {

    public static boolean isEmptyOrNull(String... args){
        for (String param:args){
            if(StringUtils.isEmpty(param)){
                return true;
            }
        }
        return false;
    }
}
