package com.cj.reocrd.presenter;

import com.cj.reocrd.api.ApiCallback;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.contract.CartContract;
import com.cj.reocrd.contract.GoodsContract;
import com.cj.reocrd.model.ApiModel;
import com.cj.reocrd.model.entity.AddressBean;
import com.cj.reocrd.model.entity.HomeBean;
import com.cj.reocrd.utils.CollectionUtils;

import retrofit2.Call;


/**
 */

public class CartPresenter extends CartContract.Presenter {

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void getCartData(String uid) {
        baseMap.clear();
        baseMap.put("uid",uid);
        ApiModel.getInstance().getData(UrlConstants.UrLType.URL_CART_DATA, baseMap, HomeBean.class, new ApiCallback() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if(null != apiResponse && isViewAttached()){
                    if(UrlConstants.SUCCESE_CODE.equals(apiResponse.getStatusCode())){
                        HomeBean homeBean = (HomeBean) apiResponse.getResults();
//                        if(!CollectionUtils.isNullOrEmpty(homeBean.getMlist())){
                            mView.showCartData(homeBean.getMlist());
//                        }
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
    public void delCartGoods(String goodsID) {
        baseMap.clear();
        baseMap.put("bid",goodsID);
        ApiModel.getInstance().getData(UrlConstants.UrLType.URL_DEL_CART, baseMap, null, new ApiCallback() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if(null != apiResponse && isViewAttached()){
                    if(UrlConstants.SUCCESE_CODE.equals(apiResponse.getStatusCode())){
                            mView.updateCartData(); // 刷新list
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
    public void addCartGoodsNum(String cartID, int num) {
        baseMap.clear();
        baseMap.put("bid",cartID);
        baseMap.put("num",num);
        ApiModel.getInstance().getData(UrlConstants.UrLType.URL_ADD_CART_GOODSNUM, baseMap, null, new ApiCallback() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if(null != apiResponse && isViewAttached()){
                    mView.updateCartData(); // 刷新list
//                    mView.onSuccess(apiResponse.getMessage());
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

    public void cartSubmitOrder(String cartID, String  uid) {
        baseMap.clear();
        baseMap.put("bid",cartID);
        baseMap.put("uid",uid);
        ApiModel.getInstance().getData(UrlConstants.UrLType.URL_ORDER_FROM_CART, baseMap, AddressBean.class, new ApiCallback() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if(null != apiResponse && isViewAttached()){
                    if(UrlConstants.SUCCESE_CODE.equals(apiResponse.getStatusCode())){
                        // {"oid":"a3796d4a-32c6-4755-b614-fcf690c3cffb","message":"操作成功","statusCode":"1"}
                        AddressBean addressBean = (AddressBean) apiResponse.getResults();
                        mView.toSubmitOrder(addressBean);
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
