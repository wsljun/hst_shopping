package com.cj.reocrd.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.contract.GoodsDetailContract;
import com.cj.reocrd.model.entity.GoodsCommentBean;
import com.cj.reocrd.model.entity.HomeBean;
import com.cj.reocrd.model.entity.Wallet;
import com.cj.reocrd.presenter.GoodsDetailPresenter;
import com.cj.reocrd.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/18.
 */

public class WalletActivity extends BaseActivity<GoodsDetailPresenter> implements GoodsDetailContract.View {
    public static final int WALLET_GET_REQUEST = 318;
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.wallet_balance)
    TextView walletBalance;
    @BindView(R.id.wallet_useableblance)
    TextView walletUseableblance;
    @BindView(R.id.wallet_freeze)
    TextView walletFreeze;
    @BindView(R.id.wallet_score)
    TextView walletScore;
    @BindView(R.id.wallet_stock)
    TextView walletStock;
    @BindView(R.id.wallet_get)
    TextView walletGet;
    @BindView(R.id.wallet_time)
    TextView walletTime;

    int blance;
    int useableblance;
    int freeze;
    int score;
    int stock;

    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet;
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter.myWallet(uid);
    }

    @Override
    public void initView() {
        titleCenter.setText(getString(R.string.wallet));
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    public void onSuccess(Object data) {
        ApiResponse response = (ApiResponse) data;
        if (UrlConstants.SUCCESE_CODE.equals(response.getStatusCode())) {
            Wallet wallet = (Wallet) response.getResults();
            if (wallet != null) {
                blance = Integer.parseInt(wallet.getBalance()) / 100;
                useableblance = 1000;//Integer.parseInt(wallet.getUseableblance()) / 100;
                freeze = Integer.parseInt(wallet.getFreeze()) / 100;
                score = Integer.parseInt(wallet.getScore());
                stock = Integer.parseInt(wallet.getStock());
                walletUseableblance.setText(blance + "");
                walletBalance.setText(useableblance + "");
                walletFreeze.setText(freeze + "");
                walletScore.setText(score + "");
                walletStock.setText(stock + "");
            }
        } else {
            ToastUtil.showToastS(this, response.getMessage());
        }
    }

    @Override
    public void onFailureMessage(String msg) {
        ToastUtil.showToastS(this, msg);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void acticonToSubmitOrder(ApiResponse apiResponse) {

    }

    @Override
    public void setCollectImg(boolean stuats) {

    }

    @Override
    public void showComment(List<GoodsCommentBean> goodsCommentBeanList) {

    }

    @OnClick({R.id.title_left, R.id.wallet_get})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.wallet_get:
                if (useableblance > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("useableblance", useableblance);
                    startActivityForResult(WalletGetActivity.class, bundle, WalletGetActivity.WALLET_GET_REQUEST);
                } else {
                    return;
                }
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == WalletGetActivity.WALLET_GET_REQUEST && resultCode == RESULT_OK) {
            mPresenter.myWallet(uid);
        }
    }
}
