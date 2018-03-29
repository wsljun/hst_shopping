package com.cj.reocrd.model;


import android.util.ArrayMap;

import com.alibaba.fastjson.JSONObject;
import com.cj.reocrd.api.ApiCallback;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.ApiService;
import com.cj.reocrd.api.ApiStore;
import com.cj.reocrd.model.entity.GirlData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;

import dalvik.annotation.TestTarget;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    public <T>ApiResponse getData(String por, String pid,String cip,final Class<T> clz) {
        ApiResponse apiResponse = new ApiResponse();
        ArrayMap<String,Object> map = new ArrayMap<>();
        map.put("por",por);   // 请求接口
        map.put("pid",pid); // 设备唯一码
        map.put("cipher",cip); // c参数密文
        ApiStore.getInstance().getApiService().getData(map).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                //todo  bug
                parse(response.body(),clz);
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

            }
        });

        return apiResponse;
    }


    public static  <T>ApiResponse parse(ApiResponse request, Class<T> clz){
//        if(jsonStr != null && request.getResults().trim().length() >0 ){
        Gson gson = new Gson();
//            GirlData request = gson.fromJson(jsonStr,GirlData.class);
        if(request != null && request.getDataList() != null){
            Object data = null;
            if(request.getDataList() instanceof List){
                List<T> list =gson.fromJson(gson.toJson(request.getDataList()),
                        new TypeToken<List<T>>() {}.getType());
                data = list;
                request.setDataList(list);
            }else{
                T t = gson.fromJson(gson.toJson(request.getDataList()), clz);
                data = t;
            }
            return request;
        }
        return request;
//        }
//        return null;
    }


    public static <T> ApiResponse parseFastJson(String jsonStr, Class<T> clz){
        if(jsonStr != null && jsonStr.trim().length() >0 ){
            ApiResponse request = JSONObject.parseObject(jsonStr,ApiResponse.class);
            if(request != null && request.getResults() != null){
                Object data = null;
                if(request.getResults() instanceof List){
                    List<T> list = JSONObject.parseArray(JSONObject.toJSONString(request.getResults()), clz);
                    data = list;
                }else{
                    T t = JSONObject.parseObject(JSONObject.toJSONString(request.getResults()), clz);
                    data = t;
                }
                request.setResults(data);
                return request;
            }
            return request;
        }
        return null;
    }



}
