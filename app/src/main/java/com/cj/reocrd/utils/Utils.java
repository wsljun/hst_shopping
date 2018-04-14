package com.cj.reocrd.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.cj.reocrd.api.UrlConstants;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.TELEPHONY_SERVICE;

public class Utils {
    /**
     * 验证手机号码1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}
     *
     * @param mobileNumber
     * @return
     */
    private static final Pattern regex = Pattern.compile("^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}$");

    public static boolean checkMobileNumber(String mobileNumber) {
        boolean flag = false;
        try {
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 获取手机imei，这个需要获取权限
     * https://blog.csdn.net/nugongahou110/article/details/47003257
     * 改成获取Pseudo ID，也是15位
     */
    public static String getIMEI() {
        return "35" +
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +

                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +

                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +

                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +

                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +

                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +

                Build.USER.length() % 10; //13 位
    }

}
