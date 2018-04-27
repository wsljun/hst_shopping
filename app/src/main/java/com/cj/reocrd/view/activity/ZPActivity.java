package com.cj.reocrd.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
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

import com.cj.reocrd.R;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.contract.MyContract;
import com.cj.reocrd.model.entity.Zp;
import com.cj.reocrd.presenter.MyPrresenter;
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
    RotatePan rotatePan;
    @BindView(R.id.id_start)
    ImageView idStart;
    @BindView(R.id.luckpan_layout)
    LuckPanLayout luckPanLayout;

    @BindView(R.id.id_luckypan)
    LuckyPanView mLuckyPanView;

    private Animation mStartAnimation;
    private Animation mEndAnimation;
    private boolean isRunning;
    private int mItemCount;


    private ImageView goBtn;
    private ImageView yunIv;
    private String[] strs;


    @Override
    public void onSuccess(Object data) {
        ApiResponse response = (ApiResponse) data;
        switch (type) {
            case 1:
                if (UrlConstants.SUCCESE_CODE.equals(response.getStatusCode())) {
                    zp = (Zp) response.getResults();
                } else {
                    ToastUtil.showToastS(this, response.getMessage());
                }
                break;
            case 2:
                if (UrlConstants.SUCCESE_CODE.equals(response.getStatusCode())) {
                    zp = (Zp) response.getResults();
                    if (zp != null && !TextUtils.isEmpty(zp.getLevel())) {
                        mItemCount = Integer.parseInt(zp.getLevel());
                        endAnimation();
                    }
                } else {
                    stopAnimation();
                    ToastUtil.showToastS(this, response.getMessage());
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


    // 结束动画，慢慢停止转动，抽中的奖品定格在指针指向的位置
    private void endAnimation() {
        float toDegreeMin = 360 / mItemCount * (5 - 0.5f) + 1;
        Random random = new Random();
        int randomInt = random.nextInt(360 / mItemCount - 1);
        float toDegree = toDegreeMin + randomInt + 360 * 5; //5周 + 偏移量
        // 按中心点旋转 toDegree度
        // 参数：旋转的开始角度、旋转的结束角度、X轴的伸缩模式、X坐标的伸缩值、Y轴的伸缩模式、Y坐标的伸缩值
        mEndAnimation = new RotateAnimation(0, toDegree, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mEndAnimation.setDuration(3000); // 设置旋转时间
        mEndAnimation.setRepeatCount(0); // 设置重复次数
        mEndAnimation.setFillAfter(true);// 动画执行完后是否停留在执行完的状态
        mEndAnimation.setInterpolator(new DecelerateInterpolator()); // 动画播放的速度
        mEndAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isRunning = false;
                LogUtil.e("---->>", toDegreeMin + "-" + randomInt + "-" + mItemCount);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        idLuckyTurntable.startAnimation(mEndAnimation);
        mStartAnimation.cancel();
    }

    //停止动画（异常情况，没有奖品）
    private void stopAnimation() {
        //转盘停止回到初始状态
        if (isRunning) {
            mStartAnimation.cancel();
            idLuckyTurntable.clearAnimation();
            isRunning = false;
        }
    }

    @OnClick({R.id.id_start_btn, R.id.zp_guize, R.id.id_start})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_start_btn:
                if (!isRunning) {
                    isRunning = true;
                    mStartAnimation.reset();
                    idLuckyTurntable.startAnimation(mStartAnimation);
                    if (mEndAnimation != null) {
                        mEndAnimation.cancel();
                    }
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            //网络获取抽奖结果
                            type = 2;
                            mPresenter.lotteryGetResult(UrlConstants.UrLType.LOTTERY_GET_RESULT, uid);
                        }
                    }, 2000);
                }
                break;
            case R.id.zp_guize:
                break;
            case R.id.id_start:
                luckPanLayout.rotate(2,100);
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
        ToastUtil.showShort("Position = "+position+","+strs[position]);
    }
}
