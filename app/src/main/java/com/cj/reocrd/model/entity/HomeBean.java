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
    private List<OrderBean>  olist ; // 订单列表
    private List<AddressBean>    addlist; //地址列表
    private List<GoodsCommentBean>  clist; // 商品详情页评论

    public List<GoodsCommentBean> getClist() {
        return clist;
    }

    public void setClist(List<GoodsCommentBean> clist) {
        this.clist = clist;
    }

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

    public List<OrderBean> getOlist() {
        return olist;
    }

    public void setOlist(List<OrderBean> olist) {
        this.olist = olist;
    }


    public List<AddressBean> getAddlist() {
        return addlist;
    }

    public void setAddlist(List<AddressBean> addlist) {
        this.addlist = addlist;
    }
}
