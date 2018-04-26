package com.cj.reocrd.view.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.contract.GoodsDetailContract;
import com.cj.reocrd.contract.MyContract;
import com.cj.reocrd.model.entity.BankBean;
import com.cj.reocrd.model.entity.GoodsCommentBean;
import com.cj.reocrd.presenter.GoodsDetailPresenter;
import com.cj.reocrd.presenter.MyPrresenter;
import com.cj.reocrd.utils.LogUtil;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.view.adapter.MyBankAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/18.
 */

public class WalletGetActivity extends BaseActivity<MyPrresenter> implements MyContract.View, MyBankAdapter.OnItemListener {
    public static final int WALLET_GET_REQUEST = 318;
    private static String TAG = "WalletGetActivity";
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.wallet_get_recycler)
    RecyclerView walletGetRecycler;
    @BindView(R.id.wallet_get_et)
    EditText walletGetEt;
    @BindView(R.id.wallet_get_use)
    TextView walletGetUse;
    int useableblance;
    MyBankAdapter adapter;
    List<BankBean.Bank> banks;
    BankBean.Bank bank;
    String money;
    int type;

    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet_get;
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        useableblance = bundle.getInt("useableblance");
        type = 1;
        mPresenter.myCard(UrlConstants.UrLType.MY_CARD, uid);
    }

    @Override
    public void initView() {
        titleCenter.setText("提现");
        walletGetUse.setText("可用余额" + useableblance + "元");
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
    }

    private void initList() {
        adapter = new MyBankAdapter(this, banks);
        adapter.setOnItemListener(this);
        walletGetRecycler.setLayoutManager(new LinearLayoutManager(this));
        walletGetRecycler.setAdapter(adapter);
    }

    @Override
    public void onSuccess(Object data) {
        ApiResponse response = (ApiResponse) data;
        switch (type) {
            case 1:
                if (UrlConstants.SUCCESE_CODE.equals(response.getStatusCode())) {
                    BankBean bankBean = (BankBean) response.getResults();
                    if (bankBean != null && bankBean.getBlist().size() > 0) {
                        banks = bankBean.getBlist();
                        initList();
                    }
                } else {
                    ToastUtil.showToastS(this, response.getMessage());
                }
                break;
            case 2:
                if (UrlConstants.SUCCESE_CODE.equals(response.getStatusCode())) {
                    finish();
                } else {
                    ToastUtil.showToastS(this, response.getMessage());
                }
                break;
            case 3:
                if (UrlConstants.SUCCESE_CODE.equals(response.getStatusCode())) {
                    String tax = (String) response.getResults();
                    ratioDialog(tax);
                } else {
                    ToastUtil.showToastS(this, response.getMessage());
                }
                break;
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


    @OnClick({R.id.wallet_get_bind, R.id.wallet_get, R.id.title_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.wallet_get_bind:
                startActivityForResult(WalletBindActivity.class, WalletBindActivity.BIND_LIST_REQUEST);
                break;
            case R.id.wallet_get:
                if (bank == null || TextUtils.isEmpty(bank.getId())) {
                    ToastUtil.showToastS(this, "请选择银行卡");
                    return;
                }
                money = walletGetEt.getText().toString();
                if (TextUtils.isEmpty(money)) {
                    ToastUtil.showToastS(this, "输入提现金额");
                    return;
                }
                if (Integer.parseInt(money) > useableblance) {
                    ToastUtil.showToastS(this, "可提现金额为" + useableblance);
                    return;
                }

                //获取提现比例
                type = 3;
                mPresenter.getRatio(UrlConstants.UrLType.GET_RATIO);
                break;
            case R.id.title_left:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == WalletBindActivity.BIND_LIST_REQUEST && resultCode == WalletBindActivity.BIND_LIST_RESULT) {
            //刷新列表
            type = 1;
            mPresenter.myCard(UrlConstants.UrLType.MY_CARD, uid);
        }
    }

    @Override
    public void itemClick(int position) {
        bank = banks.get(position);
    }

    /**
     * 提现比例
     */
    private void ratioDialog(String tax) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("本次提现将收取百分之"+tax+"的手续费是否提现").setIcon(
                R.mipmap.ic_launcher);
        builder.setNegativeButton(getString(R.string.cancel), null);
        builder.setPositiveButton(getString(R.string.confirm),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        type = 2;
                        mPresenter.walletGet(UrlConstants.UrLType.WALLET_GET, uid, bank.getId(), money);
                    }
                });
        builder.show();
    }
}
