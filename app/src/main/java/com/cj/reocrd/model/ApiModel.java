package com.cj.reocrd.model;


import com.cj.reocrd.api.ApiCallback;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.ApiService;
import com.cj.reocrd.api.ApiStore;
import com.cj.reocrd.model.entity.GirlData;

import retrofit2.Callback;

/**
 * Created by Lyndon.Li on 2018/3/17.
 */

public class ApiModel  {

    public static volatile ApiModel apiModel = null;

    private ApiModel(){
    }

    public static ApiModel getInstance(){
        if(null == apiModel ){
            synchronized (ApiModel.class){
                if( null == apiModel){
                    apiModel = new ApiModel();
                }
            }
        }
        return apiModel;
    }



    public void getListData(int size, int page,Callback<GirlData> apiCallback) {
        ApiStore.getInstance().getApiService().getListData(size, page).enqueue(apiCallback);
    }
}
