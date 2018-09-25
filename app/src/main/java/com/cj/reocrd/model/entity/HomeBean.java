package com.cj.reocrd.model.entity;

import java.util.List;

/**
 * Created by Lyndon.Li on 2018/3/29.
 * HomeBean 主要用于存放 list 集合数据，方便解析后台接口返回数据
 */

public class HomeBean {

    private List<GoodsBean> mlist;  // 商品列表
    private List<BannerData> blist; // 首页banner 列表
    private List<GoodsType> tlist; // 商品全部分类
    private List<OrderBean> olist; // 订单列表
    private List<AddressBean> addlist; //地址列表
    private List<GoodsCommentBean> clist; // 商品详情页评论
    private List<Team> fs;

    private List<GoodsBean> shhh; //实惠好货
    private List<GoodsBean> mdss; //摩登时尚
    private List<GoodsBean> lmsj; //联盟商家
    private List<GoodsBean> pzsh; //品质生活
    private List<GoodsBean> chwl; //
    private List<InvoiceInfo> list; //

    public List<GoodsBean> getLmsj() {
        return lmsj;
    }

    public void setLmsj(List<GoodsBean> lmsj) {
        this.lmsj = lmsj;
    }

    public List<InvoiceInfo> getList() {
        return list;
    }

    public void setList(List<InvoiceInfo> list) {
        this.list = list;
    }

    public List<GoodsBean> getShhh() {
        return shhh;
    }

    public void setShhh(List<GoodsBean> shhh) {
        this.shhh = shhh;
    }

    public List<GoodsBean> getMdss() {
        return mdss;
    }

    public void setMdss(List<GoodsBean> mdss) {
        this.mdss = mdss;
    }

    public List<GoodsBean> getPzsh() {
        return pzsh;
    }

    public void setPzsh(List<GoodsBean> pzsh) {
        this.pzsh = pzsh;
    }

    public List<GoodsBean> getChwl() {
        return chwl;
    }

    public void setChwl(List<GoodsBean> chwl) {
        this.chwl = chwl;
    }

    public List<Team> getFs() {
        return fs;
    }

    public void setFs(List<Team> fs) {
        this.fs = fs;
    }

    private AppInfo AppInfo; // app info

    public void setAppInfo(AppInfo appInfo) {
        AppInfo = appInfo;
    }

    public AppInfo getAppInfo() {
        return AppInfo;
    }

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
