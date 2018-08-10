package com.cj.reocrd.model.entity;

/**
 * {  // 首页轮播图信息
 　　　　　　"imgurl":"static/upload/attimg/565577896999484187e01caf08d33391.jpg",
 　　　　　　"url":"http://www.baidu.com"
 　　　　}
 */

public class BannerData {

    private String imgurl; // 展示图片url
    private String url;  // 连接地址

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
