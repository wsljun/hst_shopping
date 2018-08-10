package com.cj.reocrd.receiver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;

import com.cj.reocrd.utils.LogUtil;
import com.cj.reocrd.utils.UpdateUtil;

import java.io.File;

/**
 */

public class DownloadCompleteReceiver extends BroadcastReceiver {
    private DownloadManager manager;

    /**
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
            //通过downloadId去查询下载的文件名
            long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            LogUtil.d("Update", "广播downloadId:" + downloadId);
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(downloadId);
            Cursor myDownload = manager.query(query);
           Uri apkUri = null;
            if (myDownload.moveToFirst()) {
                int fileUriIdx = myDownload.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);
                String fileUri = myDownload.getString(fileUriIdx);
//                install(fileUri,context); // todo test install
                 String fileName = null;
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    if (fileUri != null) {
                        File file= new File(
                                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                                , UpdateUtil.DOWNLOAD_FILE_NAME); //fileUri: file:///storage/emulated/0/Android/data/com.cj.reocrd/files/hst.apk
                        //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
//                        File file= new File(fileUri);
                        apkUri = FileProvider.getUriForFile(context, "com.cj.reocrd.fileprovider", file);
//                        fileName = Uri.parse(fileUri).getPath();  //apkUri.getPath= /download/Download/hst.apk
//                        apkUri = Uri.parse(fileUri);
                        Log.e("down", "onReceive: apkUri.getPath= "+apkUri.getPath());
                        Log.e("down", "onReceive: apkUri.getEncodedPath= "+apkUri.getEncodedPath());
                    }
                } else {
                    //Android 7.0以上的方式：请求获取写入权限，这一步报错
                    //过时的方式：DownloadManager.COLUMN_LOCAL_FILENAME
                    int fileNameIdx = myDownload.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
                    fileName = myDownload.getString(fileNameIdx);
                    apkUri = Uri.fromFile(new File(fileName));
                }
//                int fileNameIdx = myDownload.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
//                String fileName = myDownload.getString(fileNameIdx);
                if(null != apkUri){
                    installAPK(apkUri, context);
                }
            }
        }
    }

    //安装APK
    private void installAPK(Uri apkUri, Context context) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }


    private  void install(String fileName,Context context){
        if (fileName != null) {
            if (fileName.endsWith(".apk")) {
                if(Build.VERSION.SDK_INT>=24) {//判读版本是否在7.0以上
                    File file= new File(fileName);
                    Uri apkUri = FileProvider.getUriForFile(context, "com.cj.reocrd.fileprovider", file);//在AndroidManifest中的android:authorities值
                    Intent install = new Intent(Intent.ACTION_VIEW);
                    install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
                    install.setDataAndType(apkUri, "application/vnd.android.package-archive");
                    context.startActivity(install);
                } else{
                    Intent install = new Intent(Intent.ACTION_VIEW);
                    install.setDataAndType(Uri.fromFile(new File(fileName)), "application/vnd.android.package-archive");
                    install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(install);
                }
            }
        }
    }
}
