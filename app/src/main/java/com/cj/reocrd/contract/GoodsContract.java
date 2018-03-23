package com.cj.reocrd.contract;

import com.cj.reocrd.base.BasePresenter;
import com.cj.reocrd.base.BaseView;


/**
 * Created by Lyndon.Li on 2018/3/23
 * 全部商品
 */

public interface GoodsContract {

    interface View extends BaseView {
        //返回获取的数据

    }

    abstract static class Presenter extends BasePresenter<View> {
        public abstract  void getGoodsTest(int size,int page);
    }
}
