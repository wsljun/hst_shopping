package com.cj.reocrd.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.cj.reocrd.api.UrlConstants;

/**
 */

public class UpdateUtil {
    public  static final String DOWNLOAD_FILE_NAME = "hst.apk";
    private static final String TAG = "UpdateUtil";

    /**
         * 获取版本名称
         */
        public static String getVerName(Context context){
            String verName = null;
            try {
                verName = context.getPackageManager().getPackageInfo(
                        context.getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return verName;
        }
        /**
         * 获取版本号
         */
        public static int getVerCode(Context context){
            int verName = 0;
            try {
                verName = context.getPackageManager().getPackageInfo(
                        context.getPackageName(), 0).versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return verName;
        }

        //下载文件
        public static void downloadFile(String url,Context context){
            long downloadId = 0;
            url = UrlConstants.BASE_URL+url;
            DownloadManager manager =(DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(downloadId);
            query.setFilterByStatus(DownloadManager.STATUS_RUNNING);//正在下载
            Cursor c = manager.query(query);
            if (c.moveToNext()) {
                //正在下载中，不重新下载
                LogUtil.d(TAG, "downloadFile: 正在下载");
            } else {
                Log.d(TAG, "downloadFile: 下载地址:"+url);
                //创建下载请求 "http://down.huixueyuan.com/publish_cs/android/TiFenWang.apk"
                DownloadManager.Request down = new DownloadManager.Request(Uri.parse(url));
                //设置允许使用的网络类型，这里是移动网络和wifi都可以
                down.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
                //显示在下载界面，即下载后的文件在系统下载管理里显示
                down.setVisibleInDownloadsUi(true);
                //设置下载标题
                down.setTitle("好膳堂");
                //显示Notification
                down.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                //设置下载后文件存放的位置，在SDCard/Android/data/你的应用的包名/files/目录下面
                down.setDestinationInExternalFilesDir(context, null, DOWNLOAD_FILE_NAME);
                //将下载请求放入队列,返回值为downloadId
                downloadId = manager.enqueue(down);
                Log.d(TAG, "downloadId:" + downloadId);
            }
        }



}
