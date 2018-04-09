package com.cj.reocrd.presenter;

import com.cj.reocrd.api.ApiCallback;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.contract.GoodsContract;
import com.cj.reocrd.model.ApiModel;
import com.cj.reocrd.model.entity.HomeBean;
import com.cj.reocrd.utils.CollectionUtils;

import retrofit2.Call;

/**
 * Created by Lyndon.Li on 2018/3/23.
 *  login register
 */

public class GoodsPresenter extends GoodsContract.Presenter {

    @Override
    public void onStart() {
        super.onStart();
        //初次进入 tid 传空
//       getGoodsType("fasdfasdfsaf");
    }

    public void getGoodsType(String time) {
        time = "fasdfasdfsaf";
        baseMap.put("time",time); //time:fasdfasdfsaf
//        baseMap.put("pagesize","20");
//        baseMap.put("pageno","0");
        ApiModel.getInstance().getData(UrlConstants.UrLType.URL_ALL_GOODS_TYPE, baseMap, HomeBean.class
                , new ApiCallback() {
                    @Override
                    public void onSuccess(ApiResponse apiResponse) {
                        if(null != apiResponse && isViewAttached()){
                            if(UrlConstants.SUCCESE_CODE.equals(apiResponse.getStatusCode())){
                                HomeBean homeBean = (HomeBean) apiResponse.getResults();
                                mView.saveGoodsType(homeBean.getTlist());
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

    @Override
    public void getGoodsData(String tid, int pageno, int pagesize) {
        baseMap.clear();
        baseMap.put("tid",tid);
        baseMap.put("pagesize",pagesize);
        baseMap.put("pageno",pageno);
        ApiModel.getInstance().getData(UrlConstants.UrLType.URL_ALL_GOODS, baseMap, HomeBean.class, new ApiCallback() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if(null != apiResponse && isViewAttached()){
                    if(UrlConstants.SUCCESE_CODE.equals(apiResponse.getStatusCode())){
                        HomeBean homeBean = (HomeBean) apiResponse.getResults();
                        if(CollectionUtils.isNullOrEmpty(homeBean.getMlist())){
                            mView.onFailureMessage("暂时没有数据");
                        }else{
                            mView.onSuccess(homeBean);
                        }
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
