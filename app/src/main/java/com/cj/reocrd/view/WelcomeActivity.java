package com.cj.reocrd.view;

import android.text.TextUtils;

import com.cj.reocrd.R;
import com.cj.reocrd.api.ApiCallback;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.model.ApiModel;
import com.cj.reocrd.model.entity.HomeBean;
import com.cj.reocrd.utils.LogUtil;
import com.cj.reocrd.utils.SPUtils;
import com.cj.reocrd.utils.UpdateUtil;
import com.cj.reocrd.utils.Utils;
import com.cj.reocrd.view.activity.MainActivity;
import com.zhy.autolayout.utils.L;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2018/3/30.
 */

public class WelcomeActivity extends BaseActivity {
    private final String TAG = "WelcomeActivity";
    private  boolean  isCancle = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initData() {
        super.initData();
        //获取手机的imei 存到sp
        BaseActivity.pid = Utils.getIMEI();
        // 检查userid是否存在
        String userid = (String) SPUtils.get(this, UrlConstants.key.USERID, "");
        //添加到base里，全局用
        if (TextUtils.isEmpty(userid)) {
            startActivity(IndexActivity.class);
        } else {
            BaseActivity.uid = userid;
            startActivity(MainActivity.class);
        }
        finish();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initPresenter() {

    }

}
