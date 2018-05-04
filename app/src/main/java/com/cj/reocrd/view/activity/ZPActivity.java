package com.cj.reocrd.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.cj.reocrd.R;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.contract.MyContract;
import com.cj.reocrd.model.entity.Zp;
import com.cj.reocrd.presenter.MyPrresenter;
import com.cj.reocrd.utils.CollectionUtils;
import com.cj.reocrd.utils.ConstantsUtils;
import com.cj.reocrd.utils.LogUtil;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.view.view.LuckPan.LuckPanLayout;
import com.cj.reocrd.view.view.LuckPan.LuckyPanView;
import com.cj.reocrd.view.view.LuckPan.RotatePan;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/4/26.
 */

public class ZPActivity extends BaseActivity<MyPrresenter> implements MyContract.View,LuckPanLayout.AnimationEndListener {

    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.id_lucky_turntable)
    ImageView idLuckyTurntable;
    @BindView(R.id.zp_money)
    TextView zpMoney;

    Zp zp;
    int type;
    @BindView(R.id.title_rl)
    RelativeLayout titleRl;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.id_start_btn)
    ImageView idStartBtn;
    @BindView(R.id.rotatePan)
    RotatePan mRotatePan;
    @BindView(R.id.id_start)
    ImageView idStart;
    @BindView(R.id.luckpan_layout)
    LuckPanLayout luckPanLayout;

    private Animation mStartAnimation;
    private Animation mEndAnimation;
    private boolean isRunning;
    private int level;


    private ImageView goBtn;
    private ImageView yunIv;
    private String[] strs,moneys;
    private boolean isCan = false;
    private  int totalMaeny = 0;


    @Override
    public void onSuccess(Object data) {
        ApiResponse response = (ApiResponse) data;
        switch (type) {
            case 1:
                if (UrlConstants.SUCCESE_CODE.equals(response.getStatusCode())) {
                    zp = (Zp) response.getResults();
                    if("1".equals(zp.getCan())){
                        isCan = true;
                        totalMaeny = Integer.parseInt(zp.getTotalmoney());
                        zpMoney.setText("当前奖池金额："+zp.getTotalmoney()+"元");
                        type = 2;
                        moneys = new String[6];
                        moneys[0] = zp.getZero()+"元";
                        moneys[1] = zp.getOne()+"元";
                        moneys[2] = zp.getTwo()+"元";
                        moneys[3] = zp.getThree()+"元";
                        moneys[4] = zp.getFour()+"元";
                        moneys[5] = zp.getFive()+"元";
                        mRotatePan.setMoneys(moneys);
                        mPresenter.lotteryGetResult(UrlConstants.UrLType.LOTTERY_GET_RESULT, uid);
                    }
                } else {
                    isCan = false;
                    ToastUtil.showToastS(this, response.getMessage());
                }
                break;
            case 2:
                if (UrlConstants.SUCCESE_CODE.equals(response.getStatusCode())) {
                    zp = (Zp) response.getResults();
                    if (zp != null && !TextUtils.isEmpty(zp.getLevel())) {
                        level = Integer.parseInt(zp.getLevel());
                        isCan = true;
                    }
                } else {
                    isCan = false;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onFailureMessage(String msg) {

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_zp;
    }

    @Override
    public void initData() {
        super.initData();
        type = 1;
        mPresenter.lotteryLevel(UrlConstants.UrLType.LOTTERY_LEVEL, uid);
        strs = getResources().getStringArray(R.array.names);
    }

    @Override
    public void initView() {
        titleCenter.setText("大转盘");

        mStartAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_anim);
        mStartAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
    }

    @OnClick({ R.id.title_left,R.id.zp_guize, R.id.id_start})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.zp_guize:
                new  MaterialDialog.Builder(this)
                        .title("游戏规则")
                        .content("每天上午下午各抽奖一次")
                        .positiveText("确定")
                        .show();
                break;
            case R.id.id_start:
                if(isCan){
                    luckPanLayout.rotate(level,100);
                    luckPanLayout.setAnimationEndListener(this);
                }else{
                    ToastUtil.showShort("暂时不能抽奖");
                }
//                if (!mLuckyPanView.isStart())
//                {
//                    mLuckyPanView.luckyStart(1);
//                } else
//                {
//                    if (!mLuckyPanView.isShouldEnd())
//
//                    {
//                        mLuckyPanView.luckyEnd();
//                    }
//                }
                break;
            default:
                break;
        }
    }

    @Override
    public void endAnimation(int position) {
        ToastUtil.showShort(strs[position]);
    }
}
