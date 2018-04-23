package com.cj.reocrd.utils;


import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.cj.reocrd.view.activity.AddressActivity;

/**
 * Created by Lyndon.Li on 2018/3/20.
 * Activity 启动，包相关
 * TODO
 */

public class ActivityUtils {


    public static  int getWidth(Activity context){
        WindowManager manager = context.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    public static  int getHeight(Activity context){
        WindowManager manager = context.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }



}
