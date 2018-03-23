package com.cj.reocrd.base;

import android.content.Context;

/**
 * 描述：
 */
public interface BaseView {
    /**
     * 显示正在加载进度框
     */
//    void showLoading();
    /**
     * 隐藏正在加载进度框
     */
//    void hideLoading();
    /**
     * 当数据请求成功后，调用此接口显示数据
     * @param data 数据源
     */
    void onSuccess(Object data);
    /**
     * 当数据请求失败后，调用此接口提示
     * @param msg 失败原因
     */
    void onFailureMessage(String msg);
    /**
     * 当数据请求异常，调用此接口提示
     */
//    void showErrorMessage();
    /**
     * 获取上下文
     * @return 上下文
     */
    Context getContext();
}
