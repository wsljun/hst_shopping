package com.cj.reocrd.contract;

import com.cj.reocrd.base.BasePresenter;
import com.cj.reocrd.base.BaseView;


/**
 */

public interface IndexContract {

    interface View extends BaseView {
        //返回获取的数据

    }

    abstract static class Presenter extends BasePresenter<View> {
        public abstract void loginRequest(String por, String phone, String password);

        public abstract void loginRequestCode(String por, String phone, String code);

        public abstract void registerRequest(String por, String phone, String password, String code, String recommend, String username);

        public abstract void getCode(String por, String phoneName, String type);

        public abstract void checkRecommend(String por,String recommend);

        public abstract void updatePwd(String por, String phone, String password, String code);
    }
}
