package com.cj.reocrd.contract;

import com.cj.reocrd.base.BasePresenter;
import com.cj.reocrd.base.BaseView;
import com.cj.reocrd.model.entity.GoodsBean;
import com.cj.reocrd.model.entity.OrderBean;

import java.util.List;


/**
 * Created by Lyndon.Li on 2018/4/09
 * 全部商品
 */

public interface OrderContract {

    interface View extends BaseView {
        //返回获取的数据
        void showOrderList(List<OrderBean> goodsBeanList);
    }

    abstract static class Presenter extends BasePresenter<View> {
        public abstract void getOrderList(String pagesize,String pageno,String uid,String status);
        public abstract void getOrderDetail(String oid);
        public abstract void cancelOrder(String oid);

    }
}
