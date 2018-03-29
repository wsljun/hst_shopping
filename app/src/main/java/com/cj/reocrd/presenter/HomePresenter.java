package com.cj.reocrd.presenter;

import android.net.Uri;

import com.cj.reocrd.api.ApiCallback;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.contract.HomeContract;
import com.cj.reocrd.model.ApiModel;
import com.cj.reocrd.model.entity.FirstBean;
import com.cj.reocrd.model.entity.GirlData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Lyndon.Li on 2018/3/20.
 *  login register
 */

public class HomePresenter extends HomeContract.Presenter {

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void getListDataTest(int size, int page) {
//        ApiModel.getInstance().getListData(size,page ,new Callback<GirlData>() {
//            @Override
//            public void onResponse(Call<GirlData> call, Response<GirlData> response) {
//                if(response.code() == 200){
//                    GirlData girlData = parse(response.body(),FirstBean.class); //(List<FirstBean>) response.body().getResults();
////                     FirstBean girlDataList = (FirstBean) response.body().getResults();
//                    if(isViewAttached()){
//                        mView.onSuccess(girlData.getResults());
//                    }else{
//                        mView.onFailureMessage("mView == null");
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GirlData> call, Throwable t) {
//
//            }
//        });
        getCodeData("101","15311780968","1");
    }

    @Override
    public void getCodeData(String por, String phone, String type) {

    }

}
