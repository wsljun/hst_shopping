package com.cj.reocrd.presenter;

import com.cj.reocrd.contract.IndexContract;
import com.cj.reocrd.model.ApiModel;
import com.cj.reocrd.model.entity.GirlData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Lyndon.Li on 2018/3/20.
 *  login register
 */

public class IndexPresenter extends IndexContract.Presenter {

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void loginRequest() {

    }

    @Override
    public void registerRequest() {

    }

    @Override
    public void getCode(int phoneName) {
        // TODO 获取验证码

    }
}
