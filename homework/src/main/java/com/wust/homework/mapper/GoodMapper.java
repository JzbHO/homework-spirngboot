package com.wust.homework.mapper;

import com.wust.homework.model.Good;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GoodMapper {
    @Select("SELECT id , item_name as itemName , item_price as itemPrice , picture_url as pictureUrl FROM goods ")
    List<Good> findAllGood();

    @Select("SELECT good_id as id ,good_num as itemNum FROM user_goods ")
    List<Good> findBuyGood();

    @Select("SELECT  good_id as id , good_price as itemPrice ,good_num as itemNum ,time as buyTime FROM user_goods")
    List<Good> calculateAccount();

    @Select("SELECT  item_name as itemName , item_price as itemPrice , picture_url as pictureUrl,detail as detail ,summary as summary FROM goods where id=#{id}")
    Good selectGoodById(@Param("id") int id);

    @Select("select good_id as id,good_num as itemNum,good_price as itemPrice from cart")
    List<Good> selectCartGoods();

    @Insert({"insert into goods(item_name,item_price,picture_url,summary,detail) values (#{itemName},#{itemPrice},#{pictureUrl},#{summary},#{detail})"})
    int addGood(Good good);

    @Select("select good_price as buyPrice from user_goods where good_id = #{id} limit 1")
    Double selectHaveBuyGoods(@Param("id") int id);

    @Delete("delete from goods where id=#{goodId}")
    int deleteGood(@Param("goodId") int goodId);

    @Insert("insert into cart (good_id,good_num,good_price) values (#{id},#{itemNum},#{itemPrice}) ")
    void addGoodToCart(Good good);

    @Delete("delete from cart where good_id=#{goodId}")
    void deleteCartGood(int goodId);

    @Insert("insert into user_goods (good_id,good_num,time,good_price) values (#{id},#{itemNum},#{buyTime},#{itemPrice})")
    void addToUserGood(Good selectGoodById);

    @Select("select good_num as itemNum from cart where good_id=#{parseInt} ")
    List<Good> selectCartGoodById(int parseInt);

    @Update("update goods set item_name=#{itemName},item_price=#{itemPrice} , picture_url=#{pictureUrl}," +
            "summary=#{summary},detail=#{detail} where id=#{id}")
    void updateGood(Good modifyGood);

    @Select("select max(id) from goods")
    int selectLastGoodId();

    @Select("select imageUrl from imageurl  order by id desc limit 1")
    String selectLastImageUrl();

    @Select("insert into imageurl (imageUrl) value (#{imageUrl})")
    String saveTempImageUrl(@Param("imageUrl") String imageUrl);

}
