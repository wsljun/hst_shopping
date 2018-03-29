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
    }

    @Override
    public void getListDataTest(int size, int page) {
//        ApiModel.getInstance().getListData(size,page ,new Callback<GirlData>() {
//            @Override
//            public void onResponse(Call<GirlData> call, Response<GirlData> response) {
//                if(response.code() == 200){
//                    GirlData girlData = parse(response.body(),FirstBean.class); //(List<FirstBean>) response.body().getResults();
////                     FirstBean girlDataList = (FirstBean) response.body().getResults();
//                    if(isViewAttached()){
//                        mView.onSuccess(girlData.getResults());
//                    }else{
//                        mView.onFailureMessage("mView == null");
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GirlData> call, Throwable t) {
//
//            }
//        });
    }

    @Override
    public void getHomeData(int pageSize, int pageno) {
        // ToDO  map 可以提取到Base中去
        HashMap<String,Object> map = new HashMap<>();
        map.put("pagesize",pageSize);
        map.put("pageno",pageno);
        ApiModel.getInstance().getData(UrlConstants.UrLType.GET_HOME_DATA, map
                , HomeBean.class, new ApiCallback<HomeBean>() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
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
                if(isViewAttached()){
                    mView.onFailureMessage(t.toString());
                }
            }
        });
    }


}
