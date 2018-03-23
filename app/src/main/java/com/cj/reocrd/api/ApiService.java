package com.cj.reocrd.api;


import com.cj.reocrd.model.entity.GirlData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Lyndon.Li on 2018/3/17.
 *  API 定义
 */
public interface ApiService {
    /**
     * 图片URL   http://gank.io/api/data/福利/20/1
     * @param size
     * @param page
     * @return
     */
    @GET("data/福利/{size}/{page}")
    Call<GirlData> getListData(@Path("size") int size, @Path("page") int page);
}
