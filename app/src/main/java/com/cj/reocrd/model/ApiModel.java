package com.cj.reocrd.model;


import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cj.reocrd.api.ApiCallback;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.ApiStore;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.model.entity.GirlData;
import com.cj.reocrd.model.entity.HomeBean;
import com.cj.reocrd.utils.DESedeUtils;
import com.cj.reocrd.utils.LogUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Lyndon.Li on 2018/3/17.
 *
 */

public class ApiModel  {
    private static final String TAG = "ApiModel";

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

//    // 封装cipher秘文
//    String data = "{\"phone\":\"" + phone + "\",\"type\":\"" + type + "\"}";
//    String imei = "123222212121";
//    String cipher = getDesede(data, imei);
//		System.out.println("密文-->>" + getDesede(data, imei));

    /**
     * @param por  url type 必传
     *  pid  设备唯一码 ，app 开启时生成，固定值，可不传
     * @param dMap
     * @param clz  class 将 返回结果进行拆分除去 stutasCode && message 剩下的部分 单独封装为一个 javabean
     * @param <T>
     * @return
     */
    public   <T>ApiResponse getData(String por,HashMap<String,Object> dMap, final Class<T> clz,ApiCallback apiCallback) {
        ApiResponse apiResponse = new ApiResponse();
        String  cipher =  DESedeUtils.getDesede(toJsonStr(dMap), UrlConstants.PID);
        LogUtil.d(TAG, "getData:cipher；"+cipher);
        HashMap<String,Object> map = new HashMap<>();
        map.put("por",por);   // 请求接口
        map.put("pid",UrlConstants.PID); // 设备唯一码
        map.put("cipher",cipher); // c参数密文
        String data = toJsonStr(map);
        ApiStore.getInstance().getApiService().getData(data).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
//                System.out.println("onResponse: String 密文= "+response.body());
                String result = DESedeUtils.getdeCrypt((response.body()),UrlConstants.PID);
//                System.out.println( "onResponse: String 解密= "+result);
                if(!TextUtils.isEmpty(result)){
                    //todo  apicallback
                    apiCallback.onSuccess(parseFastJson(result,clz));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                apiCallback.onFailure(call,t);
            }
        });

        return apiResponse;
    }

    public static String toJsonStr(Object object){
        String str_json = JSONObject.toJSONString(object);
        LogUtil.d(TAG, "toJsonStr: "+str_json);
        return str_json;
    }

    /**
     * @param jsonStr  接口返回数据 解密后 的 json 字符串
     * @param clz class 将 返回结果进行拆分除去 stutasCode && message 剩下的部分 单独封装为一个 javabean
     * @param <T>  javabean
     * @return  ApiResponse<> t : obj
     */
    public static <T> ApiResponse parseFastJson(String jsonStr, Class<T> clz){
        String code = "2"; // 1
        String msg = "请求错误，请稍后重试！";
        ApiResponse apiResponse = null; // new ApiResponse("2","请求错误，请稍后重试！") ;
        if(jsonStr != null && jsonStr.trim().length() >0 ){
            JSONObject jsonObject = JSON.parseObject(jsonStr);
            if (jsonObject.containsKey("statusCode")){
                code = jsonObject.getString("statusCode");
            }
            if (jsonObject.containsKey("message")){
                msg = jsonObject.getString("message");
            }
            apiResponse = new ApiResponse(code,msg);
            jsonObject.remove("statusCode");
            jsonObject.remove("message");
            if(jsonObject.isEmpty()){
                return  apiResponse;
            }else{
                T t = JSONObject.parseObject(JSONObject.toJSONString(jsonObject), clz);
                apiResponse.setResults(t);
                return  apiResponse;
                // TODO  obj or list<obj>
               /* *String jsonText = JSONObject.toJSONString(jsonObject);
                request = JSONObject.parseObject(jsonText,ApiResponse.class);
                if(jsonText != null && jsonText != null){
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
                }*/
            }
        }
        return  apiResponse;
    }


    // gson 暂时不用 解析存在bug
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
    }


}
