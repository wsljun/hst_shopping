package com.cj.reocrd.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.contract.MyContract;
import com.cj.reocrd.model.entity.AddressBean;
import com.cj.reocrd.model.entity.BankBean;
import com.cj.reocrd.presenter.MyPrresenter;
import com.cj.reocrd.utils.LogUtil;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 添加我的银行卡
 */

public class WalletBindActivity extends BaseActivity<MyPrresenter> implements MyContract.View {
    public static final int BIND_LIST_RESULT = 17;
    public static final int BIND_LIST_REQUEST = 317;
    private static String TAG = "WalletBindActivity";
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.wallet_bind_card)
    EditText walletBindCard;
    @BindView(R.id.wallet_bind_bank)
    TextView walletBindBank;
    @BindView(R.id.wallet_bind_address)
    TextView walletBindAddress;
    @BindView(R.id.wallet_bind_name)
    EditText walletBindName;
    @BindView(R.id.wallet_bind_hu)
    EditText walletBindHu;
    @BindView(R.id.wallet_bind_phone)
    EditText walletBindPhone;

    BankBean.Bank bank;
    AddressBean address;

    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet_bind;
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initView() {
        titleCenter.setText("提现");
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    public void onSuccess(Object data) {
        ApiResponse response = (ApiResponse) data;
        if (UrlConstants.SUCCESE_CODE.equals(response.getStatusCode())) {
            setResult(BIND_LIST_RESULT);
            finish();
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


    @OnClick({R.id.title_left, R.id.wallet_bind_bank_rl, R.id.wallet_bind_address_rl, R.id.wallet_bind_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.wallet_bind_bank_rl:
                startActivityForResult(WalletBindListActivity.class, WalletBindListActivity.BIND_LIST_REQUEST);
                break;
            case R.id.wallet_bind_address_rl:
                break;
            case R.id.wallet_bind_next:
                String bid = bank.getId();
                String rid = address.getP_re_id();
                String cardsn = walletBindCard.getText().toString();
                String username = walletBindName.getText().toString();
                String bankname = walletBindHu.getText().toString();
                String phone = walletBindPhone.getText().toString();
                if (TextUtils.isEmpty(bid)) {
                    ToastUtil.showToastS(this, "分属银行获取失败");
                    return;
                }
                if (TextUtils.isEmpty(rid)) {
                    ToastUtil.showToastS(this, "地址获取失败");
                    return;
                }
                if (TextUtils.isEmpty(cardsn)) {
                    ToastUtil.showToastS(this, "请输入卡号");
                    return;
                }
                if (TextUtils.isEmpty(username)) {
                    ToastUtil.showToastS(this, "请输入持卡人姓名");
                    return;
                }
                if (TextUtils.isEmpty(bankname)) {
                    ToastUtil.showToastS(this, "请输入开户行");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    ToastUtil.showToastS(this, "请输入预留手机号");
                    return;
                }
                if (!Utils.checkMobileNumber(phone)) {
                    ToastUtil.showToastS(this, R.string.format_not_correct);
                    return;
                }
                mPresenter.bindCard(UrlConstants.UrLType.BIND_CARD, uid, bid, rid, username, bankname, cardsn, phone);
                break;
            case R.id.title_left:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == WalletBindListActivity.BIND_LIST_REQUEST && resultCode == WalletBindListActivity.BIND_LIST_RESULT) {
            bank = (BankBean.Bank) data.getSerializableExtra("bank");
            walletBindBank.setText(bank.getName());
        }
        //下边获取地址
    }
}
