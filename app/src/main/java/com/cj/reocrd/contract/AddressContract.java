package com.cj.reocrd.contract;

import com.cj.reocrd.base.BasePresenter;
import com.cj.reocrd.base.BaseView;
import com.cj.reocrd.model.entity.AddressBean;
import com.cj.reocrd.model.entity.GoodsBean;

import java.util.List;


/**
 * 全部商品
 */

public interface AddressContract {

    interface View extends BaseView {
        //返回获取的数据
        void showAddressList(List<AddressBean> beans);
        void updateAddressList();
    }

    abstract static class Presenter extends BasePresenter<View> {
        public abstract void getAddressList(String uid);
        public abstract void addAddress(String uid,String rid,String consignee,String phone,String address,String postcode);
        public abstract void updateAddress(String aid,String rid,String consignee,String phone,String address,String postcode);
        public abstract void setDefaultAddress(String aid);
        public abstract void delAddress(String aid);
        public abstract void selectAddressMap(String parentid,String layer);

    }
}
