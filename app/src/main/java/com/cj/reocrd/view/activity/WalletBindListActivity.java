package com.cj.reocrd.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.contract.MyContract;
import com.cj.reocrd.model.entity.BankBean;
import com.cj.reocrd.presenter.MyPrresenter;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.view.adapter.MyBankAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 获取所有银行的列表
 */

public class WalletBindListActivity extends BaseActivity<MyPrresenter> implements MyContract.View, MyBankAdapter.OnItemListener {

    public static final int BIND_LIST_RESULT = 15;
    public static final int BIND_LIST_REQUEST = 315;
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.bind_recycler)
    RecyclerView bindRecycler;
    MyBankAdapter adapter;
    List<BankBean.Bank> banks;

    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet_bind_list;
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter.cardList(UrlConstants.UrLType.CARD_LIST, uid);
    }

    @Override
    public void initView() {
        titleCenter.setText("银行列表");
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
    }

    private void initList() {
        adapter = new MyBankAdapter(this, banks);
        adapter.setOnItemListener(this);
        bindRecycler.setLayoutManager(new LinearLayoutManager(this));
        bindRecycler.setAdapter(adapter);
    }

    @Override
    public void onSuccess(Object data) {
        ApiResponse response = (ApiResponse) data;
        if (UrlConstants.SUCCESE_CODE.equals(response.getStatusCode())) {
            BankBean bankBean = (BankBean) response.getResults();
            if (bankBean != null && bankBean.getBlist().size() > 0) {
                banks = bankBean.getBlist();
                initList();
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
    public void itemClick(int position) {
        Intent intent = new Intent();
        intent.putExtra("bank", banks.get(position));
        setResult(BIND_LIST_RESULT, intent);
        finish();
    }

    @OnClick(R.id.title_left)
    public void onViewClicked() {
        finish();
    }
}
