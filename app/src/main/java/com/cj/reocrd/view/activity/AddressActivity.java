package com.cj.reocrd.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.contract.AddressContract;
import com.cj.reocrd.model.entity.AddressBean;
import com.cj.reocrd.presenter.AddressPresenter;
import com.cj.reocrd.utils.SPUtils;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.view.adapter.AddressAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/17.
 */

public class AddressActivity extends BaseActivity<AddressPresenter> implements AddressAdapter.OnItemListener,
        AddressContract.View{
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.address_recycler)
    RecyclerView addressRecycler;
    AddressAdapter addressAdapter;
    List<AddressBean> addressBeans ;
    public static AddressBean addrForEdit;


    @Override
    public int getLayoutId() {
        return R.layout.activity_address;
    }

    @Override
    public void initData() {
        super.initData();
        addressBeans = new ArrayList<>();
        mPresenter.getAddressList(uid);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getAddressList(uid);
    }

    @Override
    public void initView() {
        titleCenter.setText(getString(R.string.address));
        titleLeft.setText(getString(R.string.back));
        addressAdapter = new AddressAdapter(this, addressBeans);
        addressAdapter.setOnItemListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        addressRecycler.setLayoutManager(layoutManager);
        addressRecycler.setAdapter(addressAdapter);
        addressRecycler.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
    }


    @OnClick({R.id.title_left, R.id.address_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.address_add:
                addrForEdit = null;
                Intent intent = new Intent(this, AddressEditActivity.class);
                Bundle b =  new Bundle();
                b.putString("type","add");
                intent.putExtras(b);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void checkClick(String addrId) {
//        ToastUtil.showToastS(this, "checkClick" + position);
        SPUtils.put(this,SPUtils.SpKey.DEFAULT_ADDRESS_ID,addrId);
        mPresenter.setDefaultAddress(addrId);
    }

    @Override
    public void editClick(int position) {
        ToastUtil.showToastS(this, "editClick" + position);
        addrForEdit = addressBeans.get(position);
        Bundle b =  new Bundle();
        b.putString("type","edit");
        startActivity(AddressEditActivity.class,b);
    }

    @Override
    public void deleteClick(String id) {
        mPresenter.delAddress(id);
    }

    @Override
    public void onSuccess(Object data) {
        ToastUtil.showShort((String) data);
    }

    @Override
    public void onFailureMessage(String msg) {
        ToastUtil.showShort(msg);
    }

    @Override
    public Context getContext() {
        return this.mContext;
    }

    @Override
    public void showAddressList(List<AddressBean> beans) {
        addressBeans = beans;
        if(addressBeans.size()>0){
            addressAdapter.updateData(addressBeans);
        }else{
            ToastUtil.showShort("暂时没有数据");
        }
    }
}
