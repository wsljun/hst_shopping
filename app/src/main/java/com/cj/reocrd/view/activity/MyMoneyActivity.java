package com.cj.reocrd.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.contract.GoodsDetailContract;
import com.cj.reocrd.contract.MyContract;
import com.cj.reocrd.model.entity.GoodsCommentBean;
import com.cj.reocrd.model.entity.UserBean;
import com.cj.reocrd.model.entity.Wallet;
import com.cj.reocrd.presenter.GoodsDetailPresenter;
import com.cj.reocrd.presenter.MyPrresenter;
import com.cj.reocrd.utils.ImageLoaderUtils;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/5/22.
 */

public class MyMoneyActivity extends BaseActivity<GoodsDetailPresenter> implements GoodsDetailContract.View {
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.mymoney_icon)
    ImageView mymoneyIcon;
    @BindView(R.id.mymoney_name)
    TextView mymoneyName;
    @BindView(R.id.mymoney_jifen)
    TextView mymoneyJifen;
    @BindView(R.id.mymoney_bi)
    TextView mymoneyBi;

    private UserBean user;

    public static  final  String TYPY_RECHARGE ="1";
    public static  final  String TYPY_TRANSFER_ACCOUNTS ="2";
    public static  final  String TYPY_EXCHANGE ="3";

    @Override
    public int getLayoutId() {
        return R.layout.activity_mymoney;
    }

    @Override
    public void initData() {
        super.initData();
        user = (UserBean) getIntent().getSerializableExtra("user");
    }

    @Override
    public void initView() {
        titleCenter.setText("我的钱包");
        if (null != user) {
            if (!TextUtils.isEmpty(user.getPhoto())) {
                ImageLoaderUtils.displayRound(this, mymoneyIcon, UrlConstants.BASE_URL + user.getPhoto());
            }
            if (!TextUtils.isEmpty(user.getName())) {
                mymoneyName.setText(user.getName());
            }
        }

    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.myWallet(uid);
    }

    @Override
    public void onSuccess(Object data) {
        ApiResponse response = (ApiResponse) data;
        if (UrlConstants.SUCCESE_CODE.equals(response.getStatusCode())) {
            Wallet wallet = (Wallet) response.getResults();
            if (wallet != null) {
                mymoneyJifen.setText(Utils.strDivide(wallet.getSellscore()));
                mymoneyBi.setText(Utils.strDivide(wallet.getGold()));
            }
        } else {
            ToastUtil.showToastS(this, response.getMessage());
        }
    }

    @Override
    public void onFailureMessage(String msg) {
        ToastUtil.showToastS(this,msg);
    }

    @Override
    public Context getContext() {
        return this;
    }


    @OnClick({R.id.title_left, R.id.mymoney_chognzhi, R.id.mymoney_zhuanzhang,R.id.mymoney_dh})
    public void onViewClicked(View view) {
        Bundle b = new Bundle();
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.mymoney_chognzhi:
                b.putSerializable("user",user);
                b.putString("type",TYPY_RECHARGE);
                startActivity(ChongzhiActivity.class,b);
                break;
            case R.id.mymoney_zhuanzhang:
                b.putSerializable("user",user);
                b.putString("type",TYPY_TRANSFER_ACCOUNTS);
                startActivity(ChongzhiActivity.class,b);
                break;
            case R.id.mymoney_dh:
                b.putSerializable("user",user);
                startActivity(ExchangeActivity.class,b);
                break;
        }
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
}
