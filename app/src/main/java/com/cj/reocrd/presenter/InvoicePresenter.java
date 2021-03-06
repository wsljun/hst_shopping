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
    public void getInvoiceList(String uid, String pageno, String isapply) {
        if(TextUtils.isEmpty(uid)||TextUtils.isEmpty(pageno)){
            return;
        }
        baseMap.clear();
        baseMap.put("uid",uid);
        baseMap.put("pageno",pageno);
        baseMap.put("isapply",isapply);
        ApiModel.getInstance().getData(UrlConstants.UrLType.URL_INVOICE_LIST, baseMap, HomeBean.class, new ApiCallback() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if(null != apiResponse && isViewAttached()){
                    HomeBean homeBean = (HomeBean) apiResponse.getResults();
//                    if(!CollectionUtils.isNullOrEmpty(homeBean.getList())){
                        mView.onSuccess(homeBean.getList());
//                    }else{
//                        mView.onFailureMessage("已全部加载！");
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

    @Override
    public void subimitInvoiceInfo(String uid, String oid, String head, String corp, String code,
                                   String userinfo, String username, String userphone, String usereamil) {
        if(TextUtils.isEmpty(uid)||TextUtils.isEmpty(oid)){
            return;
        }
        baseMap.clear();
        baseMap.put("uid",uid);
        baseMap.put("oid",oid);
        baseMap.put("head",head);
        baseMap.put("corp",corp);
        baseMap.put("code",code);
        baseMap.put("userinfo",userinfo);
        baseMap.put("username",username);
        baseMap.put("userphone",userphone);
        baseMap.put("usereamil",usereamil);
        ApiModel.getInstance().getData(UrlConstants.UrLType.URL_SUBMIT_INVOICE, baseMap, InvoiceInfo.class, new ApiCallback() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if(null != apiResponse && isViewAttached()){
                    if(UrlConstants.SUCCESE_CODE.contains(apiResponse.getStatusCode())){
                        mView.onSuccess(apiResponse);
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
