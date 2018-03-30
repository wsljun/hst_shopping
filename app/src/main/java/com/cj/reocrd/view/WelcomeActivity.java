package com.cj.reocrd.view;

import android.text.TextUtils;

import com.cj.reocrd.R;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.utils.LogUtil;
import com.cj.reocrd.utils.SPUtils;
import com.cj.reocrd.utils.Utils;
import com.cj.reocrd.view.activity.MainActivity;
import com.zhy.autolayout.utils.L;

import retrofit2.http.Url;

/**
 * Created by Administrator on 2018/3/30.
 */

public class WelcomeActivity extends BaseActivity {
    private final String TAG = "WelcomeActivity";
    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initData() {
        super.initData();
        //获取手机的imei 存到sp
        SPUtils.put(this, UrlConstants.key.PID, Utils.getIMEI(this));
        LogUtil.e(TAG, Utils.getIMEI(this));
        // 检查userid是否存在
        String userid = (String) SPUtils.get(this, UrlConstants.key.USERID, "");
        LogUtil.e(TAG, userid);
        if (TextUtils.isEmpty(userid)) {
            startActivity(IndexActivity.class);
        } else {
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
