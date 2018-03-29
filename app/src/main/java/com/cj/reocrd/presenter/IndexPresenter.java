package com.cj.reocrd.presenter;

import com.cj.reocrd.api.ApiCallback;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.contract.IndexContract;
import com.cj.reocrd.model.ApiModel;
import com.cj.reocrd.model.entity.GirlData;

import java.util.HashMap;
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
    public void getCode(String por,String  phoneName,String type) {
        // TODO 获取验证码
        HashMap<String,Object> map = new HashMap<>();
        map.put("phone",phoneName);   //
        map.put("type",type); // 设备唯一码
        // TODO test
        ApiModel.getInstance().getData(por, map, String.class, new ApiCallback<String>() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if(null != apiResponse){
                    if(isViewAttached()){
                        mView.onFailureMessage(apiResponse.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }

        });


    }
}
