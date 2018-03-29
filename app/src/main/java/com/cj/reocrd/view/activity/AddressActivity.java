package com.cj.reocrd.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.view.adapter.AddressAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/17.
 */

public class AddressActivity extends BaseActivity implements AddressAdapter.OnItemListener {
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.address_recycler)
    RecyclerView addressRecycler;
    AddressAdapter addressAdapter;
    List<String> mDatas;

    @Override
    public void initFragment(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_address;
    }

    @Override
    public void initData() {
        super.initData();
        mDatas = new ArrayList<>();
        mDatas.add("1");
        mDatas.add("2");
        mDatas.add("3");
    }

    @Override
    public void initView() {
        titleCenter.setText(getString(R.string.address));
        titleLeft.setText(getString(R.string.back));
        addressAdapter = new AddressAdapter(this, mDatas);
        addressAdapter.setOnItemListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        addressRecycler.setLayoutManager(layoutManager);
        addressRecycler.setAdapter(addressAdapter);
        addressRecycler.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void initPresenter() {

    }


    @OnClick({R.id.title_left, R.id.address_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.address_add:
                Intent intent = new Intent(this, AddressEditActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void checkClick(int position) {
        ToastUtil.showToastS(this, "checkClick" + position);
    }

    @Override
    public void editClick(int position) {
        ToastUtil.showToastS(this, "editClick" + position);
    }

    @Override
    public void deleteClick(int position) {
        addressAdapter.removeData(position);
    }
}
