package com.cj.reocrd.base;


import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.utils.SPUtils;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.util.NIMUtil;
import com.zhy.autolayout.config.AutoLayoutConifg;

/**
 * MultiDexApplication防止方法数过多
 */
public class BaseApplication extends MultiDexApplication {
    private static BaseApplication baseApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
        //屏幕适配
        AutoLayoutConifg.getInstance().useDeviceSize();
//        ZXingLibrary.initDisplayOpinion(this);
        // SDK初始化（启动后台服务，若已经存在用户登录信息， SDK 将完成自动登录）
        NIMClient.init(this, loginInfo(), null);
        if (NIMUtil.isMainProcess(this)) {
            // 注意：以下操作必须在主进程中进行
            // 1、UI相关初始化操作
            NimUIKit.init(this);
            // 2、相关Service调用
        }
    }

    public static Context getAppContext() {
        return baseApplication;
    }

    // 如果已经存在用户登录信息，返回LoginInfo，否则返回null即可
    private LoginInfo loginInfo() {
        // 从本地读取上次登录成功时保存的用户登录信息
        String account = (String) SPUtils.get(this, SPUtils.SpKey.IM_ACCID, "");
        String token = (String) SPUtils.get(this, SPUtils.SpKey.IM_TOKEN, "");
        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(token)) {
            return new LoginInfo(account, token);
        } else {
            return null;
        }
    }
}
