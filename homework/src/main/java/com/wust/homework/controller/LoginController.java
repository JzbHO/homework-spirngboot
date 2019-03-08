package com.wust.homework.controller;

import com.alibaba.fastjson.JSONObject;
import com.wust.homework.common.ERROECODE;
import com.wust.homework.common.UserType;
import com.wust.homework.model.Good;
import com.wust.homework.service.GoodService;
import com.wust.homework.util.Md5Util;
import com.wust.homework.util.ReponseUtil;
import com.wust.homework.util.Response;
import com.wust.homework.util.UGCcheck;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class LoginController {

    @Resource
    private GoodService goodService;

    @GetMapping("/index")
    @ResponseBody
    public Response index(){
        List<Good> goodsInfo=goodService.findAllGoods();
        Map<String,Object> data=new HashMap<>();
        data.put("good",goodsInfo);
        return ReponseUtil.makeResponse(data,ERROECODE.SUCCESSS.getMsg()
                ,ERROECODE.SUCCESSS.getCode());
    }

    @PostMapping(value = "api/login",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Response login(@RequestParam Map<String, String> bodyjsonParam){

        log.info("user login in the time {}",bodyjsonParam);
        String username=bodyjsonParam.get("username");
        String password=bodyjsonParam.get("password");
        if(UGCcheck.isEmptyOrNull(username,password)){
            return ReponseUtil.makeResponse(null, "用户名和密码都不能为空"
                    ,ERROECODE.LOGIN_FAIL.getCode());
        }
        Map<String,Object> data=new HashMap<>();
        log.info("user login in the time {}",new Date());
        if(username.equals("buyer")&&password.equals(Md5Util.string2MD5("reyub"))){
            data.put("userType", UserType.BUYER.getCode());
            return ReponseUtil.makeResponse(data, ERROECODE.SUCCESSS.getMsg()
                    ,ERROECODE.SUCCESSS.getCode());
        }else if (username.equals("seller")&&password.equals(Md5Util.string2MD5("relles"))){
            data.put("userType", UserType.SELLER.getCode());
            return ReponseUtil.makeResponse(data, ERROECODE.SUCCESSS.getMsg()
                    ,ERROECODE.SUCCESSS.getCode());
        }else {
            return ReponseUtil.makeResponse(null, ERROECODE.LOGIN_FAIL.getMsg()
                    ,ERROECODE.LOGIN_FAIL.getCode());
        }
    }




}
