package com.cj.reocrd.api;

import android.util.Log;

import com.cj.reocrd.base.BaseApplication;
import com.cj.reocrd.utils.NetWorkUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


/**
 * Created by Lyndon.Li on 2018/3/17.
 *
 */

public class ApiStore {

    private Retrofit retrofit = null;
    private ApiService apiService = null;
    public static volatile ApiStore apiStore;
    private OkHttpClient okHttpClient = null;

    public static ApiStore getInstance(){
        if(null == apiStore ){
            synchronized (ApiStore.class){
                if( null == apiStore){
                    apiStore = new ApiStore();
                }
            }
        }
        return apiStore;
    }
    //构造方法私有
    private ApiStore() {
    }

    public ApiService getApiService() {
        if(null == apiService ){
            apiService = getRetrofit().create(ApiService.class);
        }
        return apiService;
    }

    private Retrofit getRetrofit() {
        if(null == retrofit){
            retrofit = new Retrofit.Builder()
                    .client(getOkHttpClient())
                    .addConverterFactory(ScalarsConverterFactory.create())
//                    .addConverterFactory(GsonConverterFactory.create(getGson()))
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(UrlConstants.BASE_URL)
                    .build();
        }
        return retrofit;
    }

    private OkHttpClient getOkHttpClient(){
        if(null == okHttpClient){
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            File cacheFile = new File(BaseApplication.getAppContext().getCacheDir(), "cache");
            Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb

            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS).
                            readTimeout(60, TimeUnit.SECONDS).
                            writeTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .addInterceptor(headerInterceptor)
                    .addNetworkInterceptor(new HttpCacheInterceptor())
                    .cache(cache)
                    .build();
        }
        return okHttpClient;
    }

    /**
     * java bean 转化json数据  序列化
     * 解析反序列化
     * @return
     **/
    public Gson getGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .serializeNulls()//允许序列化反序列化为null
                .create();
    }


    //增加头部信息
    Interceptor headerInterceptor =new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request build = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .build();
            return chain.proceed(build);
        }
    };

    class HttpCacheInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetWorkUtils.isNetConnected(BaseApplication.getAppContext())) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                Log.d("Okhttp", "no network");
            }

            Response originalResponse = chain.proceed(request);
            if (NetWorkUtils.isNetConnected(BaseApplication.getAppContext())) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                        .removeHeader("Pragma")
                        .build();
            }
        }
    }

}
