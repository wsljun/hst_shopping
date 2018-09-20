package com.cj.reocrd.presenter;

import android.text.TextUtils;

import com.cj.reocrd.api.ApiCallback;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.contract.OrderContract;
import com.cj.reocrd.contract.SubmitOrderContract;
import com.cj.reocrd.model.ApiModel;
import com.cj.reocrd.model.entity.AddressBean;
import com.cj.reocrd.model.entity.HomeBean;
import com.cj.reocrd.model.entity.OrderDetail;
import com.cj.reocrd.utils.ToastUtil;

import retrofit2.Call;


/**
 */

public class SubmitOrderPresenter extends SubmitOrderContract.Presenter {

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void getOrderDetail(String oid) {
        baseMap.clear();
        baseMap.put("oid",oid);
        ApiModel.getInstance().getData(UrlConstants.UrLType.URL_ORDER_DETAIL, baseMap, OrderDetail.class, new ApiCallback() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if(null != apiResponse && isViewAttached()){
                    OrderDetail orderDetail = (OrderDetail) apiResponse.getResults();
                    mView.updateOrderInfo(orderDetail);
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
    public void updateOrderAddress(String oid, String aid) {
        if(TextUtils.isEmpty(oid)||TextUtils.isEmpty(aid)){
            return;
        }
        baseMap.clear();
        baseMap.put("oid",oid);
        baseMap.put("aid",aid);
        ApiModel.getInstance().getData(UrlConstants.UrLType.URL_UPDATE_ORDER_ADDR, baseMap, AddressBean.class, new ApiCallback() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if(null != apiResponse && isViewAttached()){
                    AddressBean addressBean = (AddressBean) apiResponse.getResults();
//                    mView.updateOrderInfo(orderDetail);
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
    public void updateExpTimr(String oid, String expresstime) {
        if(TextUtils.isEmpty(oid)||TextUtils.isEmpty(expresstime)){
            return;
        }
        baseMap.clear();
        baseMap.put("oid",oid);
        baseMap.put("expresstime",expresstime);
        ApiModel.getInstance().getData(UrlConstants.UrLType.URL_UPDATE_EXPRESSTIME, baseMap, AddressBean.class, new ApiCallback() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if(null != apiResponse && isViewAttached()){
                    // TODO: 2018/9/19 exptime
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
