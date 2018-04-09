package com.cj.reocrd.contract;

import com.cj.reocrd.base.BasePresenter;
import com.cj.reocrd.base.BaseView;
import com.cj.reocrd.model.entity.GoodsType;

import java.util.List;


/**
 * Created by Lyndon.Li on 2018/3/23
 * 全部商品
 */

public interface GoodsContract {

    interface View extends BaseView {
        //返回获取的数据
       public abstract void saveGoodsType(List<GoodsType> list);
    }

    abstract static class Presenter extends BasePresenter<View> {
        public abstract void getGoodsData(String tid, int pageno, int pagesize);
    }
}
