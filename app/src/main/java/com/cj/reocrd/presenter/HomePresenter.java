package com.cj.reocrd.presenter;

import com.cj.reocrd.contract.HomeContract;
import com.cj.reocrd.contract.IndexContract;
import com.cj.reocrd.model.ApiModel;
import com.cj.reocrd.model.entity.FirstBean;
import com.cj.reocrd.model.entity.GirlData;

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
        ApiModel.getInstance().getListData(size,page ,new Callback<GirlData>() {
            @Override
            public void onResponse(Call<GirlData> call, Response<GirlData> response) {
                if(response.code() == 200){
                    List<FirstBean> girlDataList = (List<FirstBean>) response.body().getResults();
                    mView.onSuccess(girlDataList);
                }
            }

            @Override
            public void onFailure(Call<GirlData> call, Throwable t) {

            }
        });
    }

}
