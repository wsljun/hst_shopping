package com.cj.reocrd.presenter;

import android.text.TextUtils;

import com.cj.reocrd.api.ApiCallback;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.contract.InvoiceContract;
import com.cj.reocrd.model.ApiModel;
import com.cj.reocrd.model.entity.AddressBean;
import com.cj.reocrd.model.entity.HomeBean;
import com.cj.reocrd.model.entity.InvoiceInfo;
import com.cj.reocrd.utils.CollectionUtils;

import java.util.List;

import retrofit2.Call;


/**
 * todo
 */

public class InvoicePresenter extends InvoiceContract.Presenter {

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void getInvoiceList(String uid, String pageno) {
        if(TextUtils.isEmpty(uid)||TextUtils.isEmpty(pageno)){
            return;
        }
        baseMap.clear();
        baseMap.put("uid",uid);
        baseMap.put("pageno",pageno);
        ApiModel.getInstance().getData(UrlConstants.UrLType.URL_INVOICE_LIST, baseMap, HomeBean.class, new ApiCallback() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if(null != apiResponse && isViewAttached()){
                    HomeBean homeBean = (HomeBean) apiResponse.getResults();
                    if(!CollectionUtils.isNullOrEmpty(homeBean.getList())){
                        mView.onSuccess(homeBean.getList());
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
    public void subimitInvoiceInfo(String uid, String oid, String head, String corp, String code,
                                   String userinfo, String username, String userphone, String usereamil) {
        if(TextUtils.isEmpty(uid)||TextUtils.isEmpty(oid)){
            return;
        }
        baseMap.clear();
        baseMap.put("uid",uid);
        baseMap.put("head",head);
        baseMap.put("corp",corp);
        baseMap.put("code",code);
        baseMap.put("userinfo",userinfo);
        baseMap.put("username",username);
        baseMap.put("userphone",userphone);
        baseMap.put("usereamil",usereamil);
        ApiModel.getInstance().getData(UrlConstants.UrLType.URL_INVOICE_LIST, baseMap, InvoiceInfo.class, new ApiCallback() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if(null != apiResponse && isViewAttached()){

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
