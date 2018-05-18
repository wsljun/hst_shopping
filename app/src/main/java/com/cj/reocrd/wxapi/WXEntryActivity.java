package com.cj.reocrd.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cj.reocrd.R;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.base.BaseApplication;
import com.cj.reocrd.utils.LogUtil;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.view.fragment.MineFragment;
import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

/**
 * Created by Administrator on 2018/5/7.
 */

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler{
    @Override
    public int getLayoutId() {
        return R.layout.entry;
    }

    @Override
    public void initData() {
        MineFragment.wxapi.handleIntent(getIntent(), this);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        String result = null;
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK: {
                result = "分享成功";
            }
            break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "分享取消";
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "分享被拒绝";
                break;
            default:
                result = "分享返回";
                break;
        }

        ToastUtil.showShort(result);
        this.finish();
    }
}

