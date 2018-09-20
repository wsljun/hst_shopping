package com.cj.reocrd.contract;

import com.cj.reocrd.base.BasePresenter;
import com.cj.reocrd.base.BaseView;
import com.cj.reocrd.model.entity.AddressBean;
import com.cj.reocrd.model.entity.GoodsBean;

import java.util.List;


/**
 * todo 发票 Contract
 */

public interface InvoiceContract {

    interface View extends BaseView {
        //返回获取的数据

    }

    abstract static class Presenter extends BasePresenter<View> {
        public  abstract void getInvoiceList(String uid,String pageno);
         public abstract void subimitInvoiceInfo(String uid,String oid,String head,String corp,String code,
                                           String userinfo,String username,String userphone,String usereamil);
    }
}
