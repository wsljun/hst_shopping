package com.cj.reocrd.model.entity;

import java.util.List;

/**
 * Created by Lyndon.Li on 2018/3/29.
 * s首页数据 url 201
 * {
 　　"statusCode":"1",
 　　"message":"获取成功",
 　　"blist":[
 　　　　{
 　　　　　　"imgurl":"static/upload/attimg/b36dce1cf19e4bbebd0b49703cfaeeec.jpg",
 　　　　　　"url":"http://www.360doc.com/content/15/0111/21/19291760_439977810.shtml"
 　　　　},
 　　　　{
 　　　　　　"imgurl":"static/upload/attimg/4969abb50c074179a80ed0ab3fc4a73f.jpg",
 　　　　　　"url":"http://www.baidu.com"
 　　　　},
 　　　　{
 　　　　　　"imgurl":"static/upload/attimg/565577896999484187e01caf08d33391.jpg",
 　　　　　　"url":"http://www.baidu.com"
 　　　　}
 　　],
 　　"mlist":[

 　　]
 }
 */

public class HomeBean {

    private List<GoodsBean> mlist;  // 商品列表
    private List<BannerData> blist;

    public List<GoodsBean> getMlist() {
        return mlist;
    }

    public void setMlist(List<GoodsBean> mlist) {
        this.mlist = mlist;
    }

    public List<BannerData> getBlist() {
        return blist;
    }

    public void setBlist(List<BannerData> blist) {
        this.blist = blist;
    }
}
