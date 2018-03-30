package com.cj.reocrd.contract;

import com.cj.reocrd.api.ApiCallback;
import com.cj.reocrd.base.BaseModel;
import com.cj.reocrd.base.BasePresenter;
import com.cj.reocrd.base.BaseView;


/**
 * Created by Lyndon.Li on 2018/3/20.
 */

public interface IndexContract {

    interface View extends BaseView {
        //返回获取的数据

    }

    abstract static class Presenter extends BasePresenter<View> {
        public abstract  void loginRequest();
        public abstract  void registerRequest(String por,String phone,String password,String code);
        public abstract void getCode(String por,String  phoneName,String type);

    }
}
