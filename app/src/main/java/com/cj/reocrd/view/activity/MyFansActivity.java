package com.cj.reocrd.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.contract.FriendsContract;
import com.cj.reocrd.contract.MyContract;
import com.cj.reocrd.model.entity.FansBean;
import com.cj.reocrd.model.entity.FriendsBean;
import com.cj.reocrd.presenter.FriendsPresenter;
import com.cj.reocrd.presenter.MyPrresenter;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.view.adapter.FriendsAdapter;
import com.cj.reocrd.view.adapter.MyFansAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MultipartBody;

/**
 * Created by Administrator on 2018/4/18.
 */

public class MyFansActivity extends BaseActivity<FriendsPresenter> implements FriendsContract.View, MyFansAdapter.OnItemListener {
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.fans_recycler)
    RecyclerView fansRecycler;

    private int type;
    private MyFansAdapter adapter;
    private List<FansBean.Fans> mDatas;

    @Override
    public int getLayoutId() {
        return R.layout.activity_myfans;
    }

    @Override
    public void initData() {
        super.initData();
        type = 1;
        mPresenter.friendGet(UrlConstants.UrLType.FRIEDNS_GET, uid);
    }

    @Override
    public void initView() {
        titleCenter.setText(getString(R.string.fans_title));
    }

    private void initRecycleView() {
        adapter = new MyFansAdapter(this, mDatas);
        fansRecycler.setLayoutManager(new LinearLayoutManager(this));
        fansRecycler.setAdapter(adapter);
        fansRecycler.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemListener(this);
    }

    @OnClick({R.id.title_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
        }
    }
    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    public void onSuccess(Object data) {
        ApiResponse response = (ApiResponse) data;
        switch (type) {
            case 1:
                if ("1".equals(response.getStatusCode())) {
                    FansBean fansBean = (FansBean) response.getResults();
                    if (fansBean != null && fansBean.getFs().size() > 0) {
                        mDatas = fansBean.getFs();
                        initRecycleView();
                    }
                } else {
                    ToastUtil.showToastS(this, response.getMessage());
                }
                break;
            case 2:

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

    @Override
    public void keepClick(int position) {
//        type = 2;
//        mPresenter.friendKeep(UrlConstants.UrLType.FRIEDNS_KEEP, uid, mDatas.get(position).getFid());
    }
}
