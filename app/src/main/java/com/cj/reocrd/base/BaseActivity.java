package com.cj.reocrd.base;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.Size;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.alipay.sdk.app.EnvUtils;
import com.cj.reocrd.R;
import com.cj.reocrd.net.NetChangeObserver;
import com.cj.reocrd.net.NetWorkUtil;
import com.cj.reocrd.net.NetworkStateReceiver;
import com.cj.reocrd.utils.AndroidBug54971Workaround;
import com.cj.reocrd.utils.TUtil;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.view.dialog.LoadingDialog;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhy.autolayout.AutoLayoutActivity;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2018/3/13.
 */

public abstract class BaseActivity <T extends  BasePresenter >extends AutoLayoutActivity implements
        NetChangeObserver{

    private static final String TAG = "BaseActivity" ;
    public T mPresenter;
    public Context mContext;
    public Unbinder unbinder;
    public static String uid = "";
    public static String pid = "";

    FrameLayout frameLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doBeforeSetcontentView();
//        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX); //todo 设置沙箱环境,正式上线时必须注销掉
        setContentView(R.layout.activity_base);
        frameLayout = findViewById(R.id.activity_base_fl);
        mContext = this.getApplicationContext();
        View view = LayoutInflater.from(mContext).inflate(getLayoutId(),null);
        frameLayout.addView(view);
        ButterKnife.bind(this);
        unbinder = ButterKnife.bind(this);
        mPresenter = TUtil.getT(this, 0);
        if(mPresenter!=null){
            mPresenter.mContext=this;
        }
        if (savedInstanceState != null) {
            uid = savedInstanceState.getString("uid");
            pid = savedInstanceState.getString("pid");
        }
//        initFragment(savedInstanceState);
        initPresenter();
        init();
        initPresenter();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("uid", uid);
        outState.putString("pid", pid);
        super.onSaveInstanceState(outState);
    }

    // todo 需要时重写即可
    public void initFragment(Bundle savedInstanceState) {

    };

    public abstract int getLayoutId();

    /**
     * 设置layout前配置
     */
    private void doBeforeSetcontentView() {

        // 把actvity放到application栈中管理
        AppManager.getAppManager().addActivity(this);
        // 无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        translucentStatusBar();
//        initState();
    }
    private void translucentStatusBar() {
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //全屏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_VISIBLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.BLACK);
            getWindow().setStatusBarColor(Color.BLACK); //TRANSPARENT
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_VISIBLE
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }


    private final void init() {
        initData();
        initView();
    }
    public void initData() {

    }

    public abstract void initView();
    /**
     * 沉浸式状态栏
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setNavigationBarColor(Color.BLACK);
        }
    }

    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract void initPresenter();

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 开启浮动加载进度条
     */
    public void startProgressDialog() {
//        LoadingDialog.showDialogForLoading(this);
    }

    /**
     //     * 网络访问错误提醒
     //     */
    public static void showNetErrorTip() {
        ToastUtil.showShort("网络连接出错");
    }
    public void showNetErrorTip(String error) {
//        ToastUitl.showToastWithImg(error,R.drawable.ic_wifi_off);
    }
    @Override
    protected void onResume() {
        super.onResume();
        //防统计之类的
        NetworkStateReceiver.registerObserver(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        NetWorkUtil.isNetworkConnect = NetWorkUtil.isNetworkAvailable(mContext);
        NetworkStateReceiver.removeRegisterObserver(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null){
            mPresenter.onDestroy();
        }
        unbinder.unbind();
        AppManager.getAppManager().finishActivity(this);
    }

    @Override
    public void onConnect(NetWorkUtil.NetType type) {

    }

    @Override
    public void onDisConnect() {
        showNetErrorTip();
    }
}
