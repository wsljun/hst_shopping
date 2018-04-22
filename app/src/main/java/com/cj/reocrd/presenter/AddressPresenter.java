package com.cj.reocrd.presenter;

import com.cj.reocrd.api.ApiCallback;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.contract.AddressContract;
import com.cj.reocrd.contract.CartContract;
import com.cj.reocrd.model.ApiModel;
import com.cj.reocrd.model.entity.AddressBean;
import com.cj.reocrd.model.entity.HomeBean;
import com.cj.reocrd.utils.CollectionUtils;

import java.util.List;

import okhttp3.Address;
import retrofit2.Call;


/**
 * Created by Lyndon.Li on 2018/4/09.
 */

public class AddressPresenter extends AddressContract.Presenter {

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void getAddressList(String uid) {
        baseMap.clear();
        baseMap.put("uid",uid);
        ApiModel.getInstance().getData(UrlConstants.UrLType.URL_ADDR_LIST, baseMap, HomeBean.class, new ApiCallback() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if(null != apiResponse && isViewAttached()){
                    if(UrlConstants.SUCCESE_CODE.equals(apiResponse.getStatusCode())){
                        HomeBean homeBean = (HomeBean) apiResponse.getResults();
                        mView.showAddressList(homeBean.getAddlist());
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
    public void addAddress(String uid, String rid, String consignee, String phone, String address, String postcode) {
        baseMap.clear();
        baseMap.put("uid",uid);
        baseMap.put("rid",rid);
        baseMap.put("consignee",consignee);
        baseMap.put("phone",phone);
        baseMap.put("address",address);
        baseMap.put("postcode",postcode);
        ApiModel.getInstance().getData(UrlConstants.UrLType.URL_ADD_ADDR, baseMap, null, new ApiCallback() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if(null != apiResponse && isViewAttached()){
                    if(UrlConstants.SUCCESE_CODE.equals(apiResponse.getStatusCode())){
                        mView.updateAddressList();
                        // onFailureMessage  toast 展示
                        mView.onFailureMessage("addAddress,"+apiResponse.getMessage());
                        // todo 新增成功后会返回详细的地址信息
                        //{"consignee":"李均","address":"湖南省长沙市芙蓉区大望路12号","phone":"18811373790","message":"操作成功","add_id":"853e5be5-9e4a-4171-97a8-e050b8a1538f","statusCode":"1"}
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
    public void updateAddress(String aid, String rid, String consignee, String phone, String address, String postcode) {
        baseMap.clear();
        baseMap.put("aid",aid);
        baseMap.put("rid",rid);
        baseMap.put("consignee",consignee);
        baseMap.put("phone",phone);
        baseMap.put("address",address);
        baseMap.put("postcode",postcode);
        ApiModel.getInstance().getData(UrlConstants.UrLType.URL_UPDATE_ADDR, baseMap, null, new ApiCallback() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if(null != apiResponse && isViewAttached()){
                    if(UrlConstants.SUCCESE_CODE.equals(apiResponse.getStatusCode())){
                        mView.onFailureMessage("updateAddress,"+apiResponse.getMessage());
                        //{"consignee":"李均","address":"湖南省长沙市芙蓉区大望路12号","phone":"18811373790","message":"操作成功","add_id":"853e5be5-9e4a-4171-97a8-e050b8a1538f","statusCode":"1"}
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
    public void setDefaultAddress(String aid) {
        baseMap.clear();
        baseMap.put("address_id",aid);
        baseMap.put("uid", BaseActivity.uid);
        ApiModel.getInstance().getData(UrlConstants.UrLType.URL_DEFAULT_ADDR, baseMap, null, new ApiCallback() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if(null != apiResponse && isViewAttached()){
                    if(UrlConstants.SUCCESE_CODE.equals(apiResponse.getStatusCode())){
                        mView.updateAddressList();
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
    public void delAddress(String aid) {
        baseMap.clear();
        baseMap.put("aid",aid);
        ApiModel.getInstance().getData(UrlConstants.UrLType.URL_DEL_ADDR, baseMap, null, new ApiCallback() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if(null != apiResponse && isViewAttached()){
                    if(UrlConstants.SUCCESE_CODE.equals(apiResponse.getStatusCode())){
                        mView.updateAddressList();
                        mView.onFailureMessage("删除"+apiResponse.getMessage());
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
    public void selectAddressMap(String parentid, String layer) {
        baseMap.clear();
        baseMap.put("parentid",parentid);
        baseMap.put("layer",layer);
        ApiModel.getInstance().getData(UrlConstants.UrLType.URL_Select_ADDR_MAP, baseMap, AddressBean.class, new ApiCallback() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if(null != apiResponse && isViewAttached()){
//                    if(UrlConstants.SUCCESE_CODE.equals(apiResponse.getStatusCode())){
                        mView.onSuccess(apiResponse.getResults());
//                    }
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
