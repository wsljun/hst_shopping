package com.cj.reocrd.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.utils.SPUtils;
import com.cj.reocrd.view.IndexActivity;
import com.cj.reocrd.view.WelcomeActivity;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.NimIntent;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/6/12.
 */

public class MsgToPActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        ArrayList<IMMessage> messages = (ArrayList<IMMessage>) getIntent().getSerializableExtra(NimIntent.EXTRA_NOTIFY_CONTENT);
        if (messages != null && messages.size() > 0) {
            String userid = (String) SPUtils.get(this, UrlConstants.key.USERID, "");
            String account = (String) SPUtils.get(this, SPUtils.SpKey.IM_ACCID, "");
            String token = (String) SPUtils.get(this, SPUtils.SpKey.IM_TOKEN, "");
            //添加到base里，全局用
            if (TextUtils.isEmpty(userid) || TextUtils.isEmpty(account) || TextUtils.isEmpty(token)) {
                startActivity(new Intent(this, IndexActivity.class));
            } else {
                NimUIKit.startP2PSession(this, messages.get(0).getSessionId());
            }
        }
        finish();
    }
}
