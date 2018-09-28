package com.cj.reocrd.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseFragment;
import com.cj.reocrd.contract.GoodsDetailContract;
import com.cj.reocrd.model.entity.GoodsCommentBean;
import com.cj.reocrd.model.entity.Wallet;
import com.cj.reocrd.presenter.GoodsDetailPresenter;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.utils.Utils;
import com.cj.reocrd.view.activity.WalletGetActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.cj.reocrd.base.BaseActivity.uid;

/**
 * Created by Administrator on 2018/3/18.
 */

public class WalletFragment extends BaseFragment<GoodsDetailPresenter> implements GoodsDetailContract.View {
    @BindView(R.id.wallet_balance)
    TextView walletBalance;
    @BindView(R.id.wallet_useableblance)
    TextView walletUseableblance;
    @BindView(R.id.wallet_freeze)
    TextView walletFreeze;
    @BindView(R.id.wallet_time)
    TextView walletTime;
    @BindView(R.id.tv_wallet_income)
    TextView walletIncome;
    @BindView(R.id.tv_wallet_score)
    TextView walletScore;
    @BindView(R.id.tv_wallet_stock)
    TextView walletStock;

    Double useableblance, sh;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_wallet;
    }

    @Override
    public void initView() {
        mPresenter.myWallet(uid);
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSuccess(Object data) {
        ApiResponse response = (ApiResponse) data;
        if (UrlConstants.SUCCESE_CODE.equals(response.getStatusCode())) {
            Wallet wallet = (Wallet) response.getResults();
            if (wallet != null) {
                useableblance = Utils.formatDouble2(Double.valueOf(wallet.getUseableblance()) / 100);
                sh = Utils.formatDouble2(Double.valueOf(wallet.getSupply()) / 100);
                walletUseableblance.setText(Utils.strDivide(wallet.getUseableblance()));
                walletBalance.setText(Utils.strDivide(wallet.getBalance()));
                walletFreeze.setText(Utils.strDivide(wallet.getFreeze()));
                walletIncome.setText(Utils.strDivide(wallet.getSupply()));
                walletScore.setText(Utils.strDivide(wallet.getScore()));
                walletStock.setText(Utils.strDivide(wallet.getStock()));
            }
        } else {
            ToastUtil.showToastS(mActivity, response.getMessage());
        }
    }

    @Override
    public void onFailureMessage(String msg) {
        ToastUtil.showToastS(mActivity, msg);
    }

    @Override
    public Context getContext() {
        return mActivity;
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

    @OnClick({R.id.wallet_get})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.wallet_get:
                Bundle bundle = new Bundle();
                bundle.putDouble("useableblance", useableblance);
                bundle.putDouble("sh", sh);
                if (null != useableblance && useableblance > 0) {
                    startActivity(WalletGetActivity.class,bundle);
                } else if (null != sh && sh > 0) {
                    startActivity(WalletGetActivity.class,bundle);
                } else {
                    ToastUtil.showShort("金额不足无法提现！");
                }

                break;
            default:
                break;

        }
    }
}
