package com.cj.reocrd.presenter;

import com.cj.reocrd.api.ApiCallback;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.contract.IndexContract;
import com.cj.reocrd.model.ApiModel;
import com.cj.reocrd.model.entity.UserBean;

import java.util.HashMap;

import retrofit2.Call;

/**
 * Created by Lyndon.Li on 2018/3/20.
 * login register
 */

public class IndexPresenter extends IndexContract.Presenter {

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void loginRequest(String por, String phone, String password) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("password", password);
        ApiModel.getInstance().getData(por, map, UserBean.class, new ApiCallback<String>() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (null != apiResponse && isViewAttached()) {
                    mView.onSuccess(apiResponse);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mView.onFailureMessage(t.toString());
            }

        });
    }

    @Override
    public void loginRequestCode(String por, String phone, String code) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("code", code);
        ApiModel.getInstance().getData(por, map, UserBean.class, new ApiCallback<String>() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (null != apiResponse && isViewAttached()) {
                    mView.onSuccess(apiResponse);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mView.onFailureMessage(t.toString());
            }

        });
    }

    @Override
    public void registerRequest(String por, String phone, String password, String code, String recommend, String username) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("password", password);
        map.put("code", code);
        map.put("recommend", recommend);
        map.put("username", username);
        ApiModel.getInstance().getData(por, map, UserBean.class, new ApiCallback<String>() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (null != apiResponse && isViewAttached()) {
                    mView.onSuccess(apiResponse);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mView.onFailureMessage(t.toString());
            }
        });
    }


    @Override
    public void getCode(String por, String phoneName, String type) {
        // TODO 获取验证码
        HashMap<String, Object> map = new HashMap<>();
        map.put("phone", phoneName);   //
        map.put("type", type); //
        // TODO test
        ApiModel.getInstance().getData(por, map, UserBean.class, new ApiCallback<String>() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (null != apiResponse && isViewAttached()) {
                    mView.onSuccess(apiResponse);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mView.onFailureMessage(t.toString());
            }

        });
    }

    @Override
    public void checkRecommend(String por, String recommend) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("recommend", recommend);   //
        ApiModel.getInstance().getData(por, map, UserBean.class, new ApiCallback<String>() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (null != apiResponse && isViewAttached()) {
                    mView.onSuccess(apiResponse);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mView.onFailureMessage(t.toString());
            }

        });
    }

    @Override
    public void updatePwd(String por, String phone, String password, String code) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("password", password);
        map.put("code", code);
        ApiModel.getInstance().getData(por, map, UserBean.class, new ApiCallback<String>() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (null != apiResponse && isViewAttached()) {
                    mView.onSuccess(apiResponse);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mView.onFailureMessage(t.toString());
            }
        });
    }


}
