package com.cj.reocrd.model;


import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cj.reocrd.api.ApiCallback;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.ApiStore;
import com.cj.reocrd.model.entity.GirlData;
import com.cj.reocrd.net.NetWorkUtil;
import com.cj.reocrd.utils.DESedeUtils;
import com.cj.reocrd.utils.LogUtil;
import com.cj.reocrd.utils.ToastUtil;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cj.reocrd.base.BaseActivity.pid;


/**
 * Created by Lyndon.Li on 2018/3/17.
 */

public class ApiModel {
    private static final String TAG = "ApiModel";

    public static volatile ApiModel apiModel = null;

    private ApiModel() {
    }

    public static ApiModel getInstance() {
        if (null == apiModel) {
            synchronized (ApiModel.class) {
                if (null == apiModel) {
                    apiModel = new ApiModel();
                }
            }
        }
        return apiModel;
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
    public <T> ApiResponse getData(String por, HashMap<String, Object> dMap, final Class<T> clz, ApiCallback apiCallback) {
        ApiResponse apiResponse = new ApiResponse();
        String cipher = DESedeUtils.getDesede(toJsonStr(dMap), pid);
//        LogUtil.d(TAG, "data:cipher；" + cipher);
        HashMap<String, Object> map = new HashMap<>();
        map.put ("por", por);   // 请求接口
        map.put("pid", pid); // 设备唯一码
        map.put("cipher", cipher); // c参数密文
        String data = toJsonStr(map);
        ApiStore.getInstance().getApiService().getData(data).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String result = DESedeUtils.getdeCrypt((response.body()), pid);
                LogUtil.d( TAG,"onResponse: result 解密= "+result);
                if (!TextUtils.isEmpty(result)) {
                    //todo  apicallback
                    try {
                        apiCallback.onSuccess(parseFastJson(result, clz));
                    } catch (Exception e) {
                        e.printStackTrace();
                        ToastUtil.showShort("返回数据错误");
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                LogUtil.d( TAG,"onResponse: result Throwable= "+t.toString());
                apiCallback.onFailure(call,t);
            }
        });

        return apiResponse;
    }

    public <T> ApiResponse uploadPhoto(String uid, MultipartBody.Part filePrta, final Class<T> clz, ApiCallback apiCallback) {
        ApiResponse apiResponse = new ApiResponse();
        ApiStore.getInstance().getApiService().getUpdatePortraitInfo(uid, filePrta).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!TextUtils.isEmpty(response.toString())) {
                    try {
                        apiCallback.onSuccess(parseFastJson(response.body().toString(), clz));
                    } catch (Exception e) {
                        e.printStackTrace();
                        ToastUtil.showShort("返回数据错误");
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                apiCallback.onFailure(call, t);
            }
        });

        return apiResponse;
    }

    public <T> ApiResponse uploadPic(String uid, String detail, List<MultipartBody.Part> parts, final Class<T> clz, ApiCallback apiCallback) {
        ApiResponse apiResponse = new ApiResponse();
        ApiStore.getInstance().getApiService().uploadPic(uid, detail, parts).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!TextUtils.isEmpty(response.toString())) {
                    try {
                        apiCallback.onSuccess(parseFastJson(response.body().toString(), clz));
                    } catch (Exception e) {
                        e.printStackTrace();
                        ToastUtil.showShort("返回数据错误");
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                apiCallback.onFailure(call, t);
            }
        });

        return apiResponse;
    }

    public static String toJsonStr(Object object) {
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
    public static <T> ApiResponse parseFastJson(String jsonStr, Class<T> clz) throws Exception{
        String code = "2"; // 1
        String msg = "请求错误，请稍后重试！";
        ApiResponse apiResponse = null; // new ApiResponse("2","请求错误，请稍后重试！") ;
        if(jsonStr != null && jsonStr.trim().length() >0 ){
            JSONObject jsonObject = JSON.parseObject(jsonStr);
              if (jsonObject.containsKey("statusCode")){
                code = jsonObject.getString("statusCode");
                jsonObject.remove("statusCode");
            }
            if (jsonObject.containsKey("message")){
                msg = jsonObject.getString("message");
                jsonObject.remove("message");
            }
            apiResponse = new ApiResponse(code,msg);
            if(jsonObject.isEmpty()){
                return  apiResponse;
            }else{
                T t = JSONObject.parseObject(JSONObject.toJSONString(jsonObject), clz);
                apiResponse.setResults(t);
                return apiResponse;
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
}
