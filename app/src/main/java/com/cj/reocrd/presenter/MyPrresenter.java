package com.cj.reocrd.presenter;

import com.cj.reocrd.api.ApiCallback;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.contract.HomeContract;
import com.cj.reocrd.contract.MyContract;
import com.cj.reocrd.model.ApiModel;
import com.cj.reocrd.model.entity.UserBean;

import java.io.File;
import java.util.HashMap;

import okhttp3.MultipartBody;
import retrofit2.Call;

/**
 * Created by Administrator on 2018/3/30.
 */

public class MyPrresenter extends MyContract.Presenter {
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void getMYHome(String por, String uid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", uid);
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
    public void getUserInfo(String por, String uid) {

    }

    @Override
    public void updatePhoto(String uid, MultipartBody.Part filePart) {
        ApiModel.getInstance().uploadPhoto(uid, filePart, UserBean.class, new ApiCallback<String>() {
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
    public void updateName(String por, String uid, String name) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("name", name);
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
    public void updateSex(String por, String uid, String sex) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("sex", sex);
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
    public void updatePhone(String por, String uid, String phone, String code) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", uid);   //
        map.put("phone", phone);   //
        map.put("code", code); //
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
}
