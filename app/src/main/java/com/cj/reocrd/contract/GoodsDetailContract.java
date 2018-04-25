package com.cj.reocrd.contract;

import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.base.BasePresenter;
import com.cj.reocrd.base.BaseView;
import com.cj.reocrd.model.entity.GoodsCommentBean;
import com.cj.reocrd.model.entity.GoodsType;

import java.util.List;


/**
 * Created by Lyndon.Li on 2018/4/3
 * 商品
 */

public interface GoodsDetailContract {

    interface View extends BaseView {
        //返回获取的数据
        void  acticonToSubmitOrder(ApiResponse apiResponse);
        void  setCollectImg(boolean stuats);
        void showComment(List<GoodsCommentBean> goodsCommentBeanList);
    }

    abstract static class Presenter extends BasePresenter<View> {
        public abstract void getGoodsDetail(String goodID);
        public abstract void addToCart(String uid,String sid,int num,String goodsID);
        public abstract void orderByDetail(String uid,String mid,String sid,int num);
        public abstract void collectGoods(String uid,String mid);
        public abstract void collectDelete(String uid,String mid);
        public abstract void collectList(String uid,int pagesize,int pageno);
        public abstract void collectBrowse(String uid, int pagesize, int pageno);
        public abstract void myWallet(String uid);
        public abstract void getGoodsDetailComment(String mid, int pagesize, int pageno);


    }
}
