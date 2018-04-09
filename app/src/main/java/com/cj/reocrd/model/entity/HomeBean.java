package com.cj.reocrd.model.entity;

import java.util.List;

/**
 * Created by Lyndon.Li on 2018/3/29.
 *  HomeBean 主要用于存放 list 集合数据，方便解析后台接口返回数据
 */

public class HomeBean {

    private List<GoodsBean> mlist;  // 商品列表
    private List<BannerData> blist; // 首页banner 列表
    private List<GoodsType>  tlist ; // 商品全部分类

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


    public List<GoodsType> getTlist() {
        return tlist;
    }

    public void setTlist(List<GoodsType> tlist) {
        this.tlist = tlist;
    }
}
