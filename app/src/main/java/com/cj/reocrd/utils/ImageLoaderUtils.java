package com.cj.reocrd.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.cj.reocrd.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Hashtable;


/**
 * Description : 图片加载工具类 使用glide框架封装
 */
public class ImageLoaderUtils {

    public static final String TAG = "ImageLoaderUtils";

    public static void display(Context context, ImageView imageView, String url, int placeholder, int error) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(placeholder)
                .circleCrop()
                .error(error)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .encodeQuality(90);

        Glide.with(context)
                .load(url)
                .apply(requestOptions)
                .into(imageView);
    }

    public static void display(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .centerCrop()
                .placeholder(R.mipmap.ic_image_loading)
                .error(R.mipmap.ic_empty_picture);
        Glide.with(context)
                .load(url)
                .apply(requestOptions)
                .into(imageView);
    }

    public static void display(Context context, ImageView imageView, File url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(R.mipmap.ic_image_loading)
                .error(R.mipmap.ic_empty_picture);

        Glide.with(context)
                .load(url)
                .apply(requestOptions)
                .into(imageView);
    }

    public static void displayRound(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.mipmap.tuihuo)
                .centerCrop()
                .transform(new GlideRoundTransformUtil(context));
        Glide.with(context)
                .load(url)
                .apply(requestOptions)
                .into(imageView);
    }


    /**
     * Bitmap转换成byte[]并且进行压缩,压缩到不大于maxkb
     *
     * @param bitmap
     * @param maxkb
     * @return
     */
    public static byte[] bitmap2Bytes(Bitmap bitmap, int maxkb) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        int options = 100;
        while (output.toByteArray().length > maxkb && options != 10) {
            output.reset(); //清空output
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, output);//这里压缩options%，把压缩后的数据存放到output中
            options -= 10;
        }
//        bitmap = BitmapFactory.decodeByteArray(output.toByteArray(),0,output.toByteArray().length);
        return output.toByteArray();
    }

    /**
     * 根据一个网络连接(String)获取bitmap图像
     *
     * @param imageUri
     * @return
     */
    static Bitmap bitmap;

    public static Bitmap getbitmap(String imageUri) {
        // 显示网络上的图片
        try {
            URL myFileUrl = new URL(imageUri);
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.setRequestMethod("GET"); //设置请求方法
            conn.setConnectTimeout(60*1000); //设置连接服务器超时时间
            conn.setReadTimeout(60*1000);  //设置读取数据超时时间
            conn.connect(); //开始连接
            int responseCode = conn.getResponseCode(); //得到服务器的响应码
            if (responseCode == 200) {
                //访问成功
                InputStream is = conn.getInputStream(); //获得服务器返回的流数据
                bitmap = BitmapFactory.decodeStream(is); //根据流数据 创建一个bitmap对象
                is.close();
                Log.d(TAG, "responseCode 200 = ：" + bitmap.getByteCount());
                return bitmap;

            } else {
                //访问失败
                Log.d(TAG, "访问失败===responseCode：" + responseCode);
            }
            Log.v(TAG, "image download finished." + imageUri);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            bitmap = null;
        } catch (IOException e) {
            e.printStackTrace();
            Log.v(TAG, "getbitmap bmp fail---");
            bitmap = null;
        }
        return bitmap;
    }


    public static File saveImage(Bitmap bmp) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "hst");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName =  "hst.jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "saveImage: file.path= "+file.getAbsolutePath());
          return  file;
    }




    public static void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "hst");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = "hst.jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
    }



}
