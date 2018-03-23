package com.cj.reocrd.base;

import android.content.Context;


/**
 * 描述:基类presenter
 */
public abstract class BasePresenter<T>{
    public Context mContext;
    public T mView;

    public void setVM(T v) {
        this.mView = v;
        this.onStart();
    }
    public void onStart(){

    };

    /**
     * 断开view，一般在onDestroy中调用
     */
    public void onDestroy() {
        this.mView = null;
    }
    /**
     * 是否与View建立连接
     * 每次调用业务请求的时候都要出先调用方法检查是否与View建立连接
     */
    public boolean isViewAttached(){
        return mView != null;
    }

}
