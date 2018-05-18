package com.cj.reocrd.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
import com.cj.reocrd.utils.SPUtils;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.view.adapter.FriendsAdapter;
import com.cj.reocrd.view.adapter.MyFansAdapter;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.LoginInfo;

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
    int removePosition;
    int fromType;

    public final static int FROM_MYFANS = 1;
    public final static int FROM_MYKEEP = 2;

    @Override
    public int getLayoutId() {
        return R.layout.activity_myfans;
    }

    @Override
    public void initData() {
        super.initData();
        fromType = getIntent().getIntExtra("type", 0);
        switch (fromType) {
            case 1:
                type = 1;
                mPresenter.friendGet(UrlConstants.UrLType.FRIEDNS_MYKEEP, uid);
                break;
            case 2:
                type = 2;
                mPresenter.friendGet(UrlConstants.UrLType.FRIEDNS_MYFANS, uid);
                break;
        }

    }

    @Override
    public void initView() {
        switch (fromType) {
            case 1:
                titleCenter.setText(getString(R.string.fans_title1));
                break;
            case 2:
                titleCenter.setText(getString(R.string.fans_title2));
                break;
        }

    }

    private void initRecycleView() {
        adapter = new MyFansAdapter(this, mDatas, fromType);
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
            case 3:
                if ("1".equals(response.getStatusCode())) {
                    adapter.removeData(removePosition);
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

    @Override
    public void keepClick(int position) {
        removePosition = position;
        type = 3;
        mPresenter.friendKeep(UrlConstants.UrLType.FRIEDNS_KEEP, uid, mDatas.get(position).getFid());
    }

    @Override
    public void chatClick(int position) {
        NimUIKit.startP2PSession(MyFansActivity.this, mDatas.get(position).getAccid());
    }


}
