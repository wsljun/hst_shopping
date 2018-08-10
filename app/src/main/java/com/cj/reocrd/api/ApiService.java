package com.cj.reocrd.api;


import android.util.ArrayMap;

import com.cj.reocrd.model.entity.GirlData;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * API 定义
 */
public interface ApiService {
    /**
     * 图片URL   http://gank.io/api/data/福利/20/1
     *
     * @param size
     * @param page
     * @return
     */
    @GET("data/福利/{size}/{page}")
    Call<GirlData> getListData(@Path("size") int size, @Path("page") int page);

    //普通请求
    @FormUrlEncoded
    @POST(UrlConstants.URL)
    Call<String> getData(@Field("data") String data);

    //普通请求
    @FormUrlEncoded
    @POST(UrlConstants.URL)
    Call<ApiResponse> getDataTest(@Query("data") String data);

    //带有文件or 图片
    @Multipart
    @POST(UrlConstants.UrLType.UPDATE_PHOTO)
    Call<String> getUpdatePortraitInfo(@Query("uid") String uid, @Part MultipartBody.Part filePrta);

    @Multipart
    @POST(UrlConstants.UrLType.FRIEDNS_SEND)
    Call<String> uploadPic(@Query("uid") String uid, @Query("detail") String detail, @Part List<MultipartBody.Part> parts);

}
