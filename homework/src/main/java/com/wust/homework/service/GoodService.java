package com.wust.homework.service;

import com.wust.homework.common.GlobalConst;
import com.wust.homework.mapper.GoodMapper;
import com.wust.homework.model.Good;
import com.wust.homework.util.DateUtil;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@Service
public class GoodService {

    @Resource
    private GoodMapper goodMapper;
    public List<Good> findAllGoods(){
        return goodMapper.findAllGood();
    }


    public List<Good> findBuyGoods(){
        return goodMapper.findBuyGood();
    }

    public Map<String,Object> account(){
        Map<String,Object> data=new HashMap<>();
        float sum=0;
        List<Good> goodList=goodMapper.calculateAccount();
        for (Good good:goodList){
            Good goodDetail=goodMapper.selectGoodById(good.getId());
            good.setPictureUrl(goodDetail.getPictureUrl());
            good.setItemName(goodDetail.getItemName());
            sum+=good.getItemNum()*good.getItemPrice();
        }
        data.put("price",sum);
        data.put("goodList",goodList);
        return data;
    }

    public List<Good> cart(){
        List<Good> list=new ArrayList<>();
        list=goodMapper.selectCartGoods();
        for (Good good: list){
            Good detailGood =goodMapper.selectGoodById(good.getId());
            good.setItemName(detailGood.getItemName());
            good.setItemPrice(detailGood.getItemPrice());
        }
        return list;
    }

    public void saveImg(MultipartFile[] file)  {
        MultipartFile file1 = file[0];
        String name=file1.getOriginalFilename();
        System.out.println("file name"+name);

        String savePath = System.getProperty("user.dir");

        File filePath = new File(savePath+"\\src\\main\\resources\\static\\static\\img\\"+name);

        try {
            file1.transferTo(filePath);
        } catch (IOException e) {

        }


        goodMapper.saveTempImageUrl("http://47.92.116.118:8083/icon/"+name);

    }

    public int saveGood(Object form) throws IOException {

        HashMap<String,String> formData= (HashMap) form;

        String goodName=formData.get("imageName");
        String summary=formData.get("summary");
        String detail=formData.get("detail");
        String imageurl=formData.get("imageUrl");

        Object thisrice=formData.get("goodPrice");
        Double goodPrice=Double.parseDouble(thisrice.toString());

        Object thisId=formData.get("goodId");
        if(thisId==null){
            Good good = new Good();
            good.setItemName(goodName);
            good.setSummary(summary);
            good.setDetail(detail);
            good.setItemPrice(goodPrice);
            if(formData.get("isLocal").equals("1")) {
                good.setPictureUrl(goodMapper.selectLastImageUrl());
            }else {
                good.setPictureUrl(imageurl);
            }
            goodMapper.addGood(good);
            return goodMapper.selectLastGoodId();
        }else {
            int goodId = Integer.parseInt(thisId.toString());
            Good modifyGood = goodMapper.selectGoodById(goodId);
                modifyGood.setId(goodId);
                modifyGood.setItemName(goodName);
                modifyGood.setSummary(summary);
                modifyGood.setDetail(detail);
                modifyGood.setItemPrice(goodPrice);
                if(formData.get("isLocal").equals("1")) {
                    modifyGood.setPictureUrl(goodMapper.selectLastImageUrl());
                }else {
                    modifyGood.setPictureUrl(imageurl);
                }
                goodMapper.updateGood(modifyGood);
                return goodId;
        }

    }


    public Good findOneGood(int goodId) {
        Double goodPrice=goodMapper.selectHaveBuyGoods(goodId);
        //如果是未购买
        Good  good=goodMapper.selectGoodById(goodId);
        System.out.println("findOneGood ==="+good.getPictureUrl());
        if(goodPrice!=null){
            good.setIsBuy(GlobalConst.HAVEBUY.getCode());
            good.setBuyPrice(goodPrice);
        }else {
            good.setIsBuy(GlobalConst.NOTBUY.getCode());
        }
        return good;
    }

    public int  deleteGood(int goodId) {
        return  goodMapper.deleteGood(goodId);
    }


    public List<Good> findGoodWithState(){
        List<Good> allGoods=findAllGoods();
        List<Good> buyGoods=findBuyGoods();
        Map<String,Object> data=new HashMap<>();
        List <Good> goodList=new ArrayList<>();

        for(Good allgood: allGoods) {
            allgood.setItemNum(0);
            allgood.setIsBuy(GlobalConst.NOTBUY.getCode());
            for (Good buygood : buyGoods) {
                if(allgood.getId()==buygood.getId()) {
                    System.out.println("===== buyGood"+buygood.getItemNum());
                    allgood.setItemNum(buygood.getItemNum()+allgood.getItemNum());
                    System.out.println("====all num"+allgood.getItemNum());
                    allgood.setIsBuy(GlobalConst.HAVEBUY.getCode());
                }
            }
            goodList.add(allgood);
        }

        return goodList;
    }

    public void addToCart(Map<String, String> bodyjsonParam) {
        Good good=new Good();
        good.setId(Integer.parseInt(bodyjsonParam.get("goodId")));
        good.setItemNum(Integer.parseInt(bodyjsonParam.get("goodNum")));
        System.out.println("============Price"+bodyjsonParam.get("goodPrice"));
        good.setItemPrice(Double.parseDouble(bodyjsonParam.get("goodPrice")));
        goodMapper.addGoodToCart(good);
    }

    public void closeCart(Map<String, String> bodyjsonParam) {
        String idList=bodyjsonParam.get("idList");
        String [] lists=idList.split(",");
        for(String id : lists){
            List<Good> cartGood=goodMapper.selectCartGoodById(Integer.parseInt(id));
            for(Good good:cartGood){
                Good completegood=goodMapper.selectGoodById(Integer.parseInt(id));
                completegood.setId(Integer.parseInt(id));
                completegood.setItemNum(good.getItemNum());
                completegood.setBuyTime(DateUtil.formDate(new Date()));
                goodMapper.addToUserGood(completegood);
            }
            goodMapper.deleteCartGood(Integer.parseInt(id));
        }

    }

    public Good findRecentGood() {
        Good good=goodMapper.selectGoodById(goodMapper.selectLastGoodId());
        good.setId(goodMapper.selectLastGoodId());
        return good;
    }
}
