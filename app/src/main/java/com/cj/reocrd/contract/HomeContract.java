package com.cj.reocrd.contract;

import com.cj.reocrd.base.BasePresenter;
import com.cj.reocrd.base.BaseView;
import com.cj.reocrd.model.entity.HomeBean;


/**
 * Created by Lyndon.Li on 2018/3/20.
 */

public interface HomeContract {

    interface View extends BaseView {
        /**
         * @param homeBean 包含 轮播图 和 首页商品信息
         */
        //返回获取的数据
        public abstract void onRefreshHomeData(HomeBean homeBean);
    }

    abstract static class Presenter extends BasePresenter<View> {
        public abstract  void getListDataTest(int size,int page);
        public abstract  void getHomeData(int pageSize,int pageno);
    }
}
