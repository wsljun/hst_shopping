package com.cj.reocrd.presenter;

import com.cj.reocrd.api.ApiCallback;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.contract.HomeContract;
import com.cj.reocrd.model.ApiModel;
import com.cj.reocrd.model.entity.HomeBean;

import java.util.HashMap;

import retrofit2.Call;

/**
 * Created by Lyndon.Li on 2018/3/20.
 *  login register
 */

public class HomePresenter extends HomeContract.Presenter {

    @Override
    public void onStart() {
        super.onStart();
        //TODO 获取
    }

    @Override
    public void getListDataTest(int size, int page) {

    }

    @Override
    public void getHomeData(int pageSize, int pageno) {
        showLoading();
        // ToDO  map 可以提取到Base中去
        HashMap<String,Object> map = new HashMap<>();
        map.put("pagesize",pageSize);
        map.put("pageno",pageno);
        ApiModel.getInstance().getData(UrlConstants.UrLType.GET_HOME_DATA, map
                , HomeBean.class, new ApiCallback<HomeBean>() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                hideLoading();
                if(null != apiResponse && isViewAttached()){
                    if(UrlConstants.SUCCESE_CODE.equals(apiResponse.getStatusCode())){
                        HomeBean homeBean = (HomeBean) apiResponse.getResults();
                        mView.onRefreshHomeData(homeBean);
                    }else{
                        mView.onFailureMessage(apiResponse.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                hideLoading();
                if(isViewAttached()){
                    mView.onFailureMessage(t.toString());
                }
            }
        });
    }

    @Override
    public void checkUpdate(String version) {
        baseMap.clear();
        baseMap.put("type","1"); // 1,android .2.ios
        baseMap.put("version",version);
        ApiModel.getInstance().getData(UrlConstants.UrLType.CHECK_APP_UPDATE, baseMap
                , HomeBean.class, new ApiCallback<HomeBean>() {
                    @Override
                    public void onSuccess(ApiResponse apiResponse) {
                        if(null != apiResponse && isViewAttached()){
                            // "statusCode":“1”,     //1.暂无新版本 2 请更新App
                            if("2".equals(apiResponse.getStatusCode())){
                                HomeBean homeBean = (HomeBean) apiResponse.getResults();
                                mView.onSuccess(homeBean.getAppInfo());
                            }else{
//                                mView.onFailureMessage(apiResponse.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        if(isViewAttached()){
                            mView.onFailureMessage(t.toString());
                        }
                    }
                });
    }

    /**
     * @param search  搜索内容
     * @param sortlabel  排序
     * @param label    标签
     * @param pageSize
     * @param pageno
     */
    public void getSearchData(String search,String sortlabel,String label,int pageSize, int pageno) {
        baseMap.clear();
        baseMap.put("search",search);
        baseMap.put("price",sortlabel);
        baseMap.put("label",label);
        baseMap.put("pagesize",pageSize);
        baseMap.put("pageno",pageno);
        ApiModel.getInstance().getData(UrlConstants.UrLType.URL_SEARCH, baseMap
                , HomeBean.class, new ApiCallback<HomeBean>() {
                    @Override
                    public void onSuccess(ApiResponse apiResponse) {
                        if(null != apiResponse && isViewAttached()){
                            if(UrlConstants.SUCCESE_CODE.equals(apiResponse.getStatusCode())){
                                HomeBean homeBean = (HomeBean) apiResponse.getResults();
                                mView.onSuccess(homeBean);
                            }else{
                                mView.onFailureMessage(apiResponse.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        if(isViewAttached()){
                            mView.onFailureMessage(t.toString());
                        }
                    }
                });
    }



}
