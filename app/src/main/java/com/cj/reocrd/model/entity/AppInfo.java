package com.cj.reocrd.model.entity;

/**
 */

public class AppInfo {

    /**
     * packageName : 安卓第二版
     * apkUrl : static/upload/app/android/a98340ba17684c609d27e3f2fb259c90.apk
     * versionCode : 2
     * versionName : 安卓第二版
     * detailDesc : <p>安卓第二版安卓第二版安卓第二版安卓第二版安卓第二版</p>
     * isupdate  ： 1 // 强制更新， 2.不强制更新
     * apkSize : 5.78MB
     */

    private String packageName;
    private String apkUrl;
    private String versionCode;
    private String versionName;
    private String detailDesc;
    private String apkSize;
    private String isupdate;

    public String getIsupdate() {
        return isupdate;
    }

    public void setIsupdate(String isupdate) {
        this.isupdate = isupdate;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getDetailDesc() {
        return detailDesc;
    }

    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc;
    }

    public String getApkSize() {
        return apkSize;
    }

    public void setApkSize(String apkSize) {
        this.apkSize = apkSize;
    }
}
