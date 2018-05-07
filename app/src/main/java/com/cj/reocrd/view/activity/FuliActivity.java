package com.cj.reocrd.view.activity;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.contract.MyContract;
import com.cj.reocrd.model.entity.FuliBean;
import com.cj.reocrd.presenter.MyPrresenter;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.view.adapter.FuliAdapter;
import com.cj.reocrd.view.refresh.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/4/20.
 */

public class FuliActivity extends BaseActivity<MyPrresenter> implements MyContract.View,
        FuliAdapter.OnItemListener {
    private final static String TAG = "FuliActivity";
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.recycler_content)
    RecyclerView recyclerContent;
    @BindView(R.id.refresh_layout)
    RefreshLayout refreshLayout;


    private FuliAdapter adapter;
    private int type;
    private List<FuliBean.Fuli> wlist;
    private List<FuliBean.Fuli> rlist;

    @Override
    public int getLayoutId() {
        return R.layout.activity_fuli;
    }

    @Override
    public void initData() {
        super.initData();
        wlist = new ArrayList<>();
        rlist = new ArrayList<>();
        type = 1;
        mPresenter.wealList(UrlConstants.UrLType.WEAL_LIST, uid);
    }

    @Override
    public void initView() {
        titleCenter.setText("福利");
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    public Context getContext() {
        return this;
    }

    public void initList() {
        adapter = new FuliAdapter(this, wlist, rlist);
        adapter.setOnItemListener(this);
        recyclerContent.setLayoutManager(new LinearLayoutManager(this));
        recyclerContent.setAdapter(adapter);
    }

    @Override
    public void onSuccess(Object data) {
        ApiResponse response = (ApiResponse) data;
        switch (type) {
            case 1:
                if (UrlConstants.SUCCESE_CODE.equals(response.getStatusCode())) {
                    FuliBean fuliBean = (FuliBean) response.getResults();
                    if (fuliBean.getWlist() != null && fuliBean.getWlist().size() > 0) {
                        wlist = fuliBean.getWlist();
                    }
                    if (fuliBean.getRlist() != null && fuliBean.getRlist().size() > 0) {
                        rlist = fuliBean.getRlist();
                    }
                    initList();
                } else {
                    ToastUtil.showToastS(this, response.getMessage());
                }
                break;
            case 2:
                if (UrlConstants.SUCCESE_CODE.equals(response.getStatusCode())) {
                    type = 1;
                    mPresenter.wealList(UrlConstants.UrLType.WEAL_LIST, uid);
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

    @OnClick({R.id.title_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
        }

    }

    @Override
    public void doClick(int position) {
        type = 2;
        mPresenter.wealConvert(UrlConstants.UrLType.WEAL_CONVERT, uid, wlist.get(position).getId());
    }
}
