package com.cj.reocrd.contract;

import com.cj.reocrd.base.BasePresenter;
import com.cj.reocrd.base.BaseView;
import com.cj.reocrd.model.entity.GoodsBean;
import com.cj.reocrd.model.entity.GoodsType;

import java.util.List;


/**
 * Created by Lyndon.Li on 2018/4/09
 * 全部商品
 */

public interface CartContract {

    interface View extends BaseView {
        //返回获取的数据
        void showCartData(List<GoodsBean> goodsBeanList);
        void updateCartData();
    }

    abstract static class Presenter extends BasePresenter<View> {
        public abstract void getCartData(String uid);
        public abstract void delCartGoods(String goodsID);
        public abstract void addCartGoodsNum(String goodsID,int num);
    }
}
