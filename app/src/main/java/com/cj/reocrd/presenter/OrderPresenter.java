package com.cj.reocrd.presenter;

import android.text.TextUtils;

import com.cj.reocrd.api.ApiCallback;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.contract.CartContract;
import com.cj.reocrd.contract.OrderContract;
import com.cj.reocrd.model.ApiModel;
import com.cj.reocrd.model.entity.HomeBean;
import com.cj.reocrd.model.entity.OrderBean;
import com.cj.reocrd.utils.CollectionUtils;
import com.cj.reocrd.utils.ToastUtil;

import java.util.List;

import retrofit2.Call;


/**
 * Created by Lyndon.Li on 2018/4/09.
 */

public class OrderPresenter extends OrderContract.Presenter {

    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     * @param pagesize
     * @param pageno
     * @param uid
     * @param status 订单状态(1.未付款   2 待发货  3待确认，4待评价 5完成  6退款中 7 退款完成 8自行取消)
     *                不传 status 时 获取全部订单
     */
    @Override
    public void getOrderList(String pagesize, String pageno, String uid, String status) {
        baseMap.clear();
        baseMap.put("pagesize",pagesize);
        baseMap.put("pageno",pageno);
        baseMap.put("uid",uid);
        if(!TextUtils.isEmpty(status)){
            baseMap.put("status",status);
        }
        ApiModel.getInstance().getData(UrlConstants.UrLType.URL_ORDER_LIST, baseMap, HomeBean.class, new ApiCallback() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if(null != apiResponse && isViewAttached()){
                    HomeBean homeBean = (HomeBean) apiResponse.getResults();
                    mView.showOrderList(homeBean.getOlist());
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
    public void getOrderDetail(String oid) {
        baseMap.clear();
        baseMap.put("oid",oid);
        ApiModel.getInstance().getData(UrlConstants.UrLType.URL_ORDER_DETAIL, baseMap, HomeBean.class, new ApiCallback() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if(null != apiResponse && isViewAttached()){
//                    HomeBean homeBean = (HomeBean) apiResponse.getResults();
//                    mView.showOrderList(homeBean.getOlist());
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
    public void cancelOrder(String oid) {
        baseMap.clear();
        baseMap.put("oid",oid);
        ApiModel.getInstance().getData(UrlConstants.UrLType.URL_CANCEL_ORDER, baseMap, HomeBean.class, new ApiCallback() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if(null != apiResponse && isViewAttached()){
                    if(UrlConstants.SUCCESE_CODE.equals(apiResponse.getStatusCode())){
                        //
                        mView.updateOrderList();
                        ToastUtil.showShort(apiResponse.getMessage());
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
