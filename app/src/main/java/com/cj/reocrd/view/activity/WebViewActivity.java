package com.cj.reocrd.view.activity;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.utils.ConstantsUtils;

import butterknife.BindView;

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.title_center)
    TextView tvCenter;
    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.title_left)
    TextView textViewLeft;
    private String webViewUrl;
    public final static String BUNDLE_WEBVIEW_URL = "web_url";
    public final static String BUNDLE_WEBVIEW_TYPE = "type";
    public final static int TYPE_ABOUT = 1;
    public final static int TYPE_HELP = 2;
    public final static int TYPE_COMMENT = 3;
    public final static int TYPE_GOODS_DETAILS = 4;

    @Override
    public int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    public void initData() {
        //声明WebSettings子类
        WebSettings webSettings = webView.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);


//设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

//缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(false); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

//其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
    }

    @Override
    public void initView() {
        textViewLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        int type = getIntent().getIntExtra(BUNDLE_WEBVIEW_TYPE,0);
        switch (type){
            case TYPE_ABOUT:
                tvCenter.setText("关于我们");
                webView.loadUrl(UrlConstants.URL_ABOUT);
                break;
            case TYPE_HELP:
                tvCenter.setText("帮助中心");
                webView.loadUrl(UrlConstants.URL_HELP);
                break;
            case TYPE_GOODS_DETAILS:
                tvCenter.setText("商品详情");
                String id = getIntent().getStringExtra(BUNDLE_WEBVIEW_URL);
                webView.loadUrl(UrlConstants.URL_GOODS_DETAIL+id);
                break;
            case TYPE_COMMENT :
                break;
            default:
                break;
        }
    }

    @Override
    public void initPresenter() {

    }



}
