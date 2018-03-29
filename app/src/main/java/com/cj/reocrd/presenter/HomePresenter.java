package com.cj.reocrd.presenter;

import com.cj.reocrd.contract.HomeContract;
import com.cj.reocrd.model.ApiModel;
import com.cj.reocrd.model.entity.FirstBean;
import com.cj.reocrd.model.entity.GirlData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
                    GirlData girlData = parse(response.body(),FirstBean.class); //(List<FirstBean>) response.body().getResults();
//                     FirstBean girlDataList = (FirstBean) response.body().getResults();
                    if(isViewAttached()){
                        mView.onSuccess(girlData.getResults());
                    }else{
                        mView.onFailureMessage("mView == null");
                    }
                }
            }

            @Override
            public void onFailure(Call<GirlData> call, Throwable t) {

            }
        });
    }


    public static  <T>GirlData parse(GirlData request, Class<T> clz){
//        if(jsonStr != null && request.getResults().trim().length() >0 ){
            Gson gson = new Gson();
//            GirlData request = gson.fromJson(jsonStr,GirlData.class);
            if(request != null && request.getResults() != null){
                Object data = null;
                if(request.getResults() instanceof List){
                    List<T> list =gson.fromJson(gson.toJson(request.getResults()),
                            new TypeToken<List<T>>() {}.getType());
                    data = list;
                    request.setDataList(list);
                }else{
                    T t = gson.fromJson((String) request.getResults(), clz);
                    data = t;
                    request.setResults(data);
                }
//                request.setResults(data);
                return request;
            }
            return request;
//        }
//        return null;
    }

}
