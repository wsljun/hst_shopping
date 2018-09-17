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
import com.cj.reocrd.utils.CollectionUtils;
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
        AddressContract.View {
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.address_recycler)
    RecyclerView addressRecycler;
    AddressAdapter addressAdapter;
    List<AddressBean> addressBeans;
    public static AddressBean addrForEdit;
    private String type;
    private String aid;
    private boolean isPause;
    private  String consignee,address,phone;



    @Override
    public int getLayoutId() {
        return R.layout.activity_address;
    }

    @Override
    public void initData() {
        super.initData();
        type = getIntent().getStringExtra("type");
        addressBeans = new ArrayList<>();
        updateAddressList();
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
                setResult();
                finish();
                break;
            case R.id.address_add:
                addrForEdit = null;
                Intent intent = new Intent(this, AddressEditActivity.class);
                Bundle b =  new Bundle();
                b.putString("type",AddressEditActivity.TYPE_ADD);
                intent.putExtras(b);
                startActivity(intent);
                break;
                default:
                    break;
        }
    }

    @Override
    public void checkClick(int position) {
        setDefulatAddress(position);
        mPresenter.setDefaultAddress(aid);
    }

    private void setDefulatAddress(int position){
        if(CollectionUtils.isNullOrEmpty(addressBeans)){
            if(position == -1){
                SPUtils.remove(this,SPUtils.SpKey.DEFAULT_ADDRESS_ID);
                SPUtils.remove(this,SPUtils.SpKey.DEFAULT_ADDRESS_CONSIGNEE);
                SPUtils.remove(this,SPUtils.SpKey.DEFAULT_ADDRESS_PHONE);
                SPUtils.remove(this,SPUtils.SpKey.DEFAULT_ADDRESS_DETAIL);
            }
            return;
        }
        aid = addressBeans.get(position).getId();
        consignee = addressBeans.get(position).getConsignee();
        address = addressBeans.get(position).getFuladdress();
        phone = addressBeans.get(position).getPhone();
        SPUtils.put(this, SPUtils.SpKey.DEFAULT_ADDRESS_ID, aid);
        SPUtils.put(this, SPUtils.SpKey.DEFAULT_ADDRESS_CONSIGNEE,consignee );
        SPUtils.put(this, SPUtils.SpKey.DEFAULT_ADDRESS_PHONE, phone);
        SPUtils.put(this, SPUtils.SpKey.DEFAULT_ADDRESS_DETAIL, address);
    }

    @Override
    public void editClick(int position) {
        addrForEdit = addressBeans.get(position);
        Bundle b =  new Bundle();
        b.putString("type",AddressEditActivity.TYPE_EDIT);
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
            if(addressBeans.get(0).getIsdefault().equals("1")){
                setDefulatAddress(0);
            }
            addressAdapter.updateData(addressBeans);
        }else{
            addressBeans = new ArrayList<>();
            addressAdapter.updateData(addressBeans);
            setDefulatAddress(-1);
//            ToastUtil.showShort("暂时没有数据");
        }
    }

    @Override
    public void updateAddressList() {
        mPresenter.getAddressList(uid);
    }

    @Override
    public void onBackPressed() {
        setResult();
        finish();
    }

    private void setResult(){
        if(SubmitOrderActivity.TYPE_SUBMITORDER.equals(type) ||
                WalletBindActivity.TYPE_BINDBANK.equals(type)){
            Intent intent  = new Intent();
            intent.putExtra("aid",aid);
            setResult(RESULT_OK,intent);
        }
        if(InvoiceSubmitActivity.TAG.equals(type)){
            String personInfo = consignee+","+phone+","+address;
            Intent intent  = new Intent();
            intent.putExtra("personInfo",personInfo);
            setResult(RESULT_OK,intent);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isPause){
            isPause = false;
            updateAddressList();
        }
    }
}
