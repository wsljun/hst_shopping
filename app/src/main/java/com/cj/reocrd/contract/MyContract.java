package com.cj.reocrd.contract;

import com.cj.reocrd.base.BasePresenter;
import com.cj.reocrd.base.BaseView;

import java.io.File;

import okhttp3.MultipartBody;

/**
 * Created by Administrator on 2018/3/30.
 */

public interface MyContract {
    interface View extends BaseView {
        //返回获取的数据

    }

    abstract static class Presenter extends BasePresenter<MyContract.View> {
        public abstract void getMYHome(String por,String uid);

        public abstract void getUserInfo(String por,String uid);

        public abstract void updatePhoto(String uid, MultipartBody.Part filePart);

        public abstract void updateName(String por,String uid, String name);

        public abstract void updateSex(String por,String uid, String sex);

        public abstract void updatePhone(String por,String uid, String phone, String code);

        public abstract void getCode(String por,String  phoneName,String type);
    }
}
