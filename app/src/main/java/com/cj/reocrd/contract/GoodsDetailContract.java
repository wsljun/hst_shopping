package com.cj.reocrd.contract;

import com.cj.reocrd.base.BasePresenter;
import com.cj.reocrd.base.BaseView;
import com.cj.reocrd.model.entity.GoodsType;

import java.util.List;


/**
 * Created by Lyndon.Li on 2018/4/3
 * 商品
 */

public interface GoodsDetailContract {

    interface View extends BaseView {
        //返回获取的数据
    }

    abstract static class Presenter extends BasePresenter<View> {
        public abstract void getGoodsDetail(String goodID);
    }
}
