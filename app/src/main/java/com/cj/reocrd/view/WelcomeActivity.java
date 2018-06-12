package com.cj.reocrd.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.utils.SPUtils;
import com.cj.reocrd.utils.Utils;
import com.cj.reocrd.view.activity.MainActivity;
import com.cj.reocrd.view.adapter.GuidePageAdapter;
import com.netease.nimlib.sdk.NimIntent;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/30.
 */

public class WelcomeActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private final String TAG = "WelcomeActivity";
    @BindView(R.id.guide_vp)
    ViewPager guideVp;
    @BindView(R.id.guide_ll_point)
    LinearLayout guideLlPoint;
    @BindView(R.id.guide_ib_start)
    TextView guideIbStart;
    private boolean isCancle = false;

    private int[] imageIdArray;//图片资源的数组
    private List<View> viewList;//图片资源的集合
    //实例化原点View
    private ImageView iv_point;
    private ImageView[] ivPointArray;


    private MyCount mc;//倒计时类
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        mc = new MyCount(3000, 1000);
        mc.start();
//        initViewPager();
//        initPoint();
    }

    /**
     * 加载图片ViewPager
     */
    private void initViewPager() {
        //实例化图片资源
        imageIdArray = new int[]{R.mipmap.qidong1, R.mipmap.qidong2, R.mipmap.qidong3};
        viewList = new ArrayList<>();
        //获取一个Layout参数，设置为全屏
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        //循环创建View并加入到集合中
        int len = imageIdArray.length;
        for (int i = 0; i < len; i++) {
            //new ImageView并设置全屏和图片资源
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(params);
            imageView.setBackgroundResource(imageIdArray[i]);

            //将ImageView加入到集合中
            viewList.add(imageView);
        }
        //View集合初始化好后，设置Adapter
        guideVp.setAdapter(new GuidePageAdapter(viewList));
        //设置滑动监听
        guideVp.setOnPageChangeListener(this);
    }

    /**
     * 加载底部圆点
     */
    private void initPoint() {
        //根据ViewPager的item数量实例化数组
        ivPointArray = new ImageView[viewList.size()];
        //循环新建底部圆点ImageView，将生成的ImageView保存到数组中
        int size = viewList.size();
        for (int i = 0; i < size; i++) {
            iv_point = new ImageView(this);
            iv_point.setLayoutParams(new ViewGroup.LayoutParams(20, 20));
            iv_point.setPadding(50, 0, 50, 0);//left,top,right,bottom
            ivPointArray[i] = iv_point;
            //第一个页面需要设置为选中状态，这里采用两张不同的图片
            if (i == 0) {
                iv_point.setBackgroundResource(R.mipmap.page_press);
            } else {
                iv_point.setBackgroundResource(R.mipmap.page_normal);
            }
            //将数组中的ImageView加入到ViewGroup
            guideLlPoint.addView(ivPointArray[i]);
        }
    }

    @OnClick(R.id.guide_ib_start)
    public void onViewClicked() {
        //获取手机的imei 存到sp
        BaseActivity.pid = Utils.getIMEI();
        // 检查userid是否存在
        String userid = (String) SPUtils.get(this, UrlConstants.key.USERID, "");
        //添加到base里，全局用
        if (TextUtils.isEmpty(userid)) {
            startActivity(new Intent(this, IndexActivity.class));
        } else {
            BaseActivity.uid = userid;
            startActivity(new Intent(this, MainActivity.class));
        }
        finish();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
//循环设置当前页的标记图
        int length = imageIdArray.length;
        for (int i = 0; i < length; i++) {
            ivPointArray[position].setBackgroundResource(R.mipmap.page_press);
            if (position != i) {
                ivPointArray[i].setBackgroundResource(R.mipmap.page_normal);
            }
        }

        //判断是否是最后一页，若是则显示按钮
        if (position == imageIdArray.length - 1) {
            guideIbStart.setVisibility(View.VISIBLE);
        } else {
            guideIbStart.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 定义一个倒计时的内部类
     */
    class MyCount extends CountDownTimer {
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            //获取手机的imei 存到sp
            BaseActivity.pid = Utils.getIMEI();
            // 检查userid是否存在
            String userid = (String) SPUtils.get(WelcomeActivity.this, UrlConstants.key.USERID, "");
            String account = (String) SPUtils.get(WelcomeActivity.this, SPUtils.SpKey.IM_ACCID, "");
            String token = (String) SPUtils.get(WelcomeActivity.this, SPUtils.SpKey.IM_TOKEN, "");
            //添加到base里，全局用
            if (TextUtils.isEmpty(userid)||TextUtils.isEmpty(account)||TextUtils.isEmpty(token)) {
                startActivity(new Intent(WelcomeActivity.this, IndexActivity.class));
            } else {
                BaseActivity.uid = userid;
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            }
            finish();
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }
    }

}
