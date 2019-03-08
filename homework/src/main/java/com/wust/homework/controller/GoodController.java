package com.wust.homework.controller;

import com.wust.homework.common.ERROECODE;
import com.wust.homework.common.GlobalConst;
import com.wust.homework.common.UserType;
import com.wust.homework.model.Good;
import com.wust.homework.service.GoodService;
import com.wust.homework.util.Md5Util;
import com.wust.homework.util.ReponseUtil;
import com.wust.homework.util.Response;
import com.wust.homework.util.UGCcheck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.net.www.content.image.png;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Slf4j
@Controller
public class GoodController {

    @Resource
    private HttpServletRequest request;

    @Resource
    private GoodService goodService;

    @PostMapping(value = "api/buyergoods",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Response findBuyerGoods(@RequestParam Map<String, String> bodyjsonParam){
            Map<String,Object> data=new HashMap<>();
            log.info("good list {}",goodService.findGoodWithState());

            data.put("goodList",goodService.findGoodWithState());

            return ReponseUtil.makeResponse(data, ERROECODE.SUCCESSS.getMsg()
                    ,ERROECODE.SUCCESSS.getCode());
        }

    @PostMapping(value = "/api/account",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Response account(@RequestParam Map<String, String> bodyjsonParam){
        Map<String,Object> data=goodService.account();
        log.info("account data {}",data);
        return ReponseUtil.makeResponse(data, ERROECODE.SUCCESSS.getMsg()
                ,ERROECODE.SUCCESSS.getCode());

    }

    @PostMapping(value = "/api/cart")
    @ResponseBody
    public Response sellerGood(@RequestParam Map<String, String> bodyjsonParam){
        Map<String,Object> data=new HashMap<>();
        data.put("goodList",goodService.cart());
        log.info("account data {}",data);
        return ReponseUtil.makeResponse(data, ERROECODE.SUCCESSS.getMsg()
                ,ERROECODE.SUCCESSS.getCode());

    }



    @PostMapping(value = "/api/uploadGood")
    @ResponseBody
    public Response uploadGood(@RequestPart ("form") Object form) throws IOException {
       log.info("insert success {}"+form);
       int goodId=goodService.saveGood(form);
        Map<String,String>data=new HashMap<>();
        data.put("thisId", String.valueOf(goodId));

        return  ReponseUtil.makeResponse(data, ERROECODE.SUCCESSS.getMsg()
                ,ERROECODE.SUCCESSS.getCode());

    }

    @PostMapping(value = "/api/saveImg")
    @ResponseBody
    public Response uploadImage(@RequestPart("file") MultipartFile[] file) throws IOException {
        goodService.saveImg(file);
        return  ReponseUtil.makeResponse(null, ERROECODE.SUCCESSS.getMsg()
                ,ERROECODE.SUCCESSS.getCode());

    }

    @PostMapping(value = "/api/showGood")
    @ResponseBody
    public Response showGood(@RequestParam Map<String, String> bodyjsonParam) throws InterruptedException {
        long sum=0;
        for(long i=0;i<999999999/2;i++){
            sum+=0;
        }
        Map<String,Object>data=new HashMap<>();
        String goodId=bodyjsonParam.get("goodId");
        Good good=null;
        log.info("bodyjson {}",bodyjsonParam);
        if(goodId.equals("undefined")){
            good=goodService.findRecentGood();
        } else {
            good = goodService.findOneGood(Integer.parseInt(goodId));

        }
        data.put("good", good);
        return  ReponseUtil.makeResponse(data, ERROECODE.SUCCESSS.getMsg()
                ,ERROECODE.SUCCESSS.getCode());

    }

    @PostMapping(value = "/api/delete")
    @ResponseBody
    public Response deleteGood(@RequestParam Map<String, String> bodyjsonParam){
        goodService.deleteGood(Integer.parseInt(bodyjsonParam.get("goodId")));
        Map<String,Object>data=new HashMap<>();
        data.put("goodList",goodService.findGoodWithState());
        return  ReponseUtil.makeResponse(data, ERROECODE.SUCCESSS.getMsg()
                ,ERROECODE.SUCCESSS.getCode());
    }

    @PostMapping(value = "/api/addToCart")
    @ResponseBody
    public Response addToCart(@RequestParam Map<String, String> bodyjsonParam){
        goodService.addToCart(bodyjsonParam);
        return  ReponseUtil.makeResponse(null, ERROECODE.SUCCESSS.getMsg()
                ,ERROECODE.SUCCESSS.getCode());
    }

    @PostMapping(value = "/api/closeCart")
    @ResponseBody
    public Response closeCart(@RequestParam Map<String, String> bodyjsonParam){
        goodService.closeCart(bodyjsonParam);
        log.info("close cart {}  ",bodyjsonParam);
        return  ReponseUtil.makeResponse(null, ERROECODE.SUCCESSS.getMsg()
                ,ERROECODE.SUCCESSS.getCode());
    }

    /**
     * 通过url请求返回图像的字节流
     */
    @RequestMapping("/icon/{cateogry}")
    public void getIcon(@PathVariable("cateogry") String cateogry,
                        HttpServletRequest request,
                        HttpServletResponse response) throws IOException {



        String savePath = System.getProperty("user.dir");

        File file = new File(savePath+"\\src\\main\\resources\\static\\static\\img\\"+cateogry);



        FileInputStream inputStream = new FileInputStream(file);
        byte[] data = new byte[(int)file.length()];
        int length = inputStream.read(data);
        inputStream.close();

        response.setContentType("image/png");
        response.setContentType("image/jpg");


        OutputStream stream = response.getOutputStream();
        stream.write(data);
        stream.flush();
        stream.close();
    }






}
