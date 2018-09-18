package com.cj.reocrd.model.entity;

import com.cj.reocrd.utils.Utils;

/**
 * Created by Lyndon.Li on 2018/3/29.
 * 商品信息 ，除购物车外
 * {
 　　"id":"ef82663a-6c06-4aeb-9fb7-58606b96fcdd",
 　　"name":"商品001 ",
 　　"imgurl":"static/upload/attimg/4a50f3c9be2e4f14a8f97fa485062eef.jpg",
 　　"price":"10000",
 　　"blocknum":"10"
 }
 *
 */

public class GoodsBean {
    private String id ;
    private String name ;
    private String imgurl; // 图片地址
    private String price ;   // 价格
    private String blocknum; // 售出数量
    private String bid;// 商品所在购物车id
    private String mid; // 商品id ,同 id ;此字段主要在获取购物车商品时使用
    private String sid; // 规格id
    private String stock; // 当前规格库存
    private String specnum;// 规格数量
    private String unit; // 单位
    private String buynum; // 购买数量
    private String createtime;
    private boolean isShowDate ; // 是否显示时间，浏览记录页使用
    private boolean  isChecked;  // 是否被选中，购物车计算价格使用
    private String oldprice;//老价格

    public String getOldprice() {
        return oldprice;
    }

    public void setOldprice(String oldprice) {
        oldprice = Utils.strDivide(oldprice);
        this.oldprice = oldprice;
    }

    public void setIsChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean getIsChecked(){
        return isChecked;
    }


    public boolean getIsShowDate() {
        return isShowDate;
    }

    public void setShowDate(boolean showDate) {
        isShowDate = showDate;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getCreatetime() {
        return createtime;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public void setPrice(String price) {
        price = Utils.strDivide(price);
        this.price = price;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getSpecnum() {
        return specnum;
    }

    public void setSpecnum(String specnum) {
        this.specnum = specnum;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getBuynum() {
        return buynum;
    }

    public void setBuynum(String buynum) {
        this.buynum = buynum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgUrl) {
        this.imgurl = imgUrl;
    }

    public String getPrice() {
        return price;
    }


    public String getBlocknum() {
        return blocknum;
    }

    public void setBlocknum(String blockNum) {
        this.blocknum = blockNum;
    }
}
