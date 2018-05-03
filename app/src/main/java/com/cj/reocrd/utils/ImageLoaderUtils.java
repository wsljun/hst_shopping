package com.cj.reocrd.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.cj.reocrd.R;

import java.io.File;


/**
 * Description : 图片加载工具类 使用glide框架封装
 */
public class ImageLoaderUtils {

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

}
