package com.cj.reocrd.model.entity;

/**
 * Created by Lyndon.Li on 2018/3/29.
 * 商品信息
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
    private String pirce ;   // 价格
    private String blocknum; // 售出数量

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

    public String getPirce() {
        return pirce;
    }

    public void setPirce(String pirce) {
        this.pirce = pirce;
    }

    public String getBlocknum() {
        return blocknum;
    }

    public void setBlocknum(String blockNum) {
        this.blocknum = blockNum;
    }
}
