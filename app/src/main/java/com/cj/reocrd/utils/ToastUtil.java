package com.cj.reocrd.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.cj.reocrd.base.BaseApplication;


/**
 * Toast统一管理类
 */
public class ToastUtil {


    private static Toast toast;
    private static Toast toast2;

    private static Toast initToast(CharSequence message, int duration) {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getAppContext(), message, duration);
        } else {
            toast.setText(message);
            toast.setDuration(duration);
        }
        return toast;
    }

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort(CharSequence message) {
        initToast(message, Toast.LENGTH_SHORT).show();
    }


    /**
     * 短时间显示Toast
     *
     * @param strResId
     */
    public static void showShort(int strResId) {
//		Toast.makeText(context, strResId, Toast.LENGTH_SHORT).show();
        initToast(BaseApplication.getAppContext().getResources().getText(strResId), Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong(CharSequence message) {
        initToast(message, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param strResId
     */
    public static void showLong(int strResId) {
        initToast(BaseApplication.getAppContext().getResources().getText(strResId), Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param message
     * @param duration
     */
    public static void show(CharSequence message, int duration) {
        initToast(message, duration).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param strResId
     * @param duration
     */
    public static void show(Context context, int strResId, int duration) {
        initToast(context.getResources().getText(strResId), duration).show();
    }

//    /**
//     * 显示有image的toast
//     *
//     * @param tvStr
//     * @param imageResource
//     * @return
//     */
//    public static Toast showToastWithImg(final String tvStr, final int imageResource) {
//        if (toast2 == null) {
//            toast2 = new Toast(BaseApplication.getAppContext());
//        }
//        View view = LayoutInflater.from(BaseApplication.getAppContext()).inflate(R.layout.toast_custom, null);
//        TextView tv = (TextView) view.findViewById(R.id.toast_custom_tv);
//        tv.setText(TextUtils.isEmpty(tvStr) ? "" : tvStr);
//        ImageView iv = (ImageView) view.findViewById(R.id.toast_custom_iv);
//        if (imageResource > 0) {
//            iv.setVisibility(View.VISIBLE);
//            iv.setImageResource(imageResource);
//        } else {
//            iv.setVisibility(View.GONE);
//        }
//        toast2.setView(view);
//        toast2.setGravity(Gravity.CENTER, 0, 0);
//        toast2.show();
//        return toast2;
//
//    }


    /**
     * 显示一个小提示框(int)
     *
     * @param pContext
     * @param message
     */
    public static void showToastS(final Context pContext, final int message) {
        showToast(pContext, message, Toast.LENGTH_SHORT);
    }

    /**
     * 显示一个小提示框(CharSequence)
     *
     * @param pContext
     * @param message
     */
    public static void showToastS(final Context pContext, final CharSequence message) {
        showToast(pContext, message, Toast.LENGTH_SHORT);
    }

    /**
     * 显示一个小提示框(int)
     *
     * @param pContext
     * @param message
     */
    public static void showToastL(final Context pContext, final int message) {
        showToast(pContext, message, Toast.LENGTH_LONG);
    }

    /**
     * 显示一个小提示框(CharSequence)
     *
     * @param pContext
     * @param message
     */
    public static void showToastL(final Context pContext, final CharSequence message) {
//        Looper.prepare();
        showToast(pContext, message, Toast.LENGTH_LONG);
//        Looper.loop();
    }

    /**
     * 将int的参数转为String的函数
     */
    public static void showToast(final Context pContext, final int message, final int duration) {
        showToast(pContext, pContext.getString(message), duration);
    }

    /**
     * 显示一个小提示框 主封装
     *
     * @param pContext
     * @param message
     * @param duration
     */
    public static void showToast(final Context pContext, CharSequence message, int duration) {
        Toast toast = Toast.makeText(pContext, message, duration);
        toast.show();
    }

    public static void shortToastInBackgroundThread(final Activity pContext, final CharSequence message){
        pContext.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showToast(pContext.getApplicationContext(), message, Toast.LENGTH_SHORT);
            }
        });
    }









}
