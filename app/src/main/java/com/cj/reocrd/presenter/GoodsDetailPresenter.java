package com.cj.reocrd.presenter;

import com.cj.reocrd.api.ApiCallback;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.contract.GoodsDetailContract;
import com.cj.reocrd.model.ApiModel;
import com.cj.reocrd.model.entity.GoodsDetailsBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by Lyndon.Li on 2018/4/3.
 */

public class GoodsDetailPresenter extends GoodsDetailContract.Presenter {

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void getGoodsDetail(String goodsID) {
        baseMap.clear();
        baseMap.put("id",goodsID);
        baseMap.put("pagesize","20");
        baseMap.put("pageno","0");
        ApiModel.getInstance().getData(UrlConstants.UrLType.URL_GOODS_DETAIL, baseMap, GoodsDetailsBean.class, new ApiCallback() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if(null != apiResponse && isViewAttached()){
                    if(UrlConstants.SUCCESE_CODE.equals(apiResponse.getStatusCode())){
                        GoodsDetailsBean goodsDetailsBean = (GoodsDetailsBean) apiResponse.getResults();
                        mView.onSuccess(goodsDetailsBean);
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
