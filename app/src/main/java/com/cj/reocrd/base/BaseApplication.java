package com.cj.reocrd.base;


import android.content.Context;
import android.support.multidex.MultiDexApplication;

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
    }
    public static Context getAppContext() {
        return baseApplication;
    }
}
