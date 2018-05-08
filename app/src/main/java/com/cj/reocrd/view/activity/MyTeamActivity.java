package com.cj.reocrd.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.base.baseadapter.BaseQuickAdapter;
import com.cj.reocrd.contract.MyContract;
import com.cj.reocrd.model.entity.BannerData;
import com.cj.reocrd.model.entity.HomeBean;
import com.cj.reocrd.model.entity.Team;
import com.cj.reocrd.presenter.MyPrresenter;
import com.cj.reocrd.utils.LogUtil;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.view.adapter.HomeAdapter;
import com.cj.reocrd.view.adapter.OnRecyclerItemClickListener;
import com.cj.reocrd.view.adapter.TeamAdapter;
import com.cj.reocrd.view.refresh.NormalRefreshViewHolder;
import com.cj.reocrd.view.refresh.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/8.
 */

public class MyTeamActivity extends BaseActivity<MyPrresenter> implements MyContract.View,RefreshLayout.RefreshLayoutDelegate {
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.refresh)
    RefreshLayout refresh;

    private List<Team> teamList = new ArrayList<>();
    TeamAdapter teamAdapter;
    private int size = 20;  //pageSize
    private int pageno = 0; // 页码
    private final static String TAG = "HomeFragment";


    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_myteam;
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter.myTeam(UrlConstants.UrLType.GET_MYTEAM, size, pageno, uid);
    }

    @Override
    public void initView() {
        titleCenter.setText("我的团队");
        initRecycleView();
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
    }

    private void initRecycleView() {
        teamAdapter = new TeamAdapter(R.layout.item_team, null);
        rvContent.setLayoutManager(new LinearLayoutManager(this));
        rvContent.setHasFixedSize(true);
        //设置适配器加载动画
        teamAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        rvContent.setAdapter(teamAdapter);
        //设置下拉、上拉
        refresh.setDelegate(this);
        refresh.setRefreshViewHolder(new NormalRefreshViewHolder(this, true));
    }

    @Override
    public void onSuccess(Object data) {
        ApiResponse apiResponse = (ApiResponse) data;
        if (UrlConstants.SUCCESE_CODE.equals(apiResponse.getStatusCode())) {
            HomeBean homeBean = (HomeBean) apiResponse.getResults();
            if (null != homeBean.getFs() && homeBean.getFs().size() > 0) {
                teamList = homeBean.getFs();
                if (pageno > 0) {
                    teamAdapter.addData(teamList);
                } else {
                    teamAdapter.setNewData(teamList);
                }
            } else {
                ToastUtil.showShort("暂时没有商品信息");
            }
            refresh.endRefreshing();
            refresh.endLoadingMore();
        } else {
            ToastUtil.showShort(apiResponse.getMessage());
        }
    }

    @Override
    public void onFailureMessage(String msg) {
        ToastUtil.showShort(msg);
        teamAdapter.loadComplete();
        refresh.endRefreshing();
        refresh.endLoadingMore();
    }

    @Override
    public void onRefreshLayoutBeginRefreshing(RefreshLayout refreshLayout) {
        LogUtil.e(TAG, "onRefreshLayoutBeginRefreshing");
        teamList.clear();
        pageno = 0;
        mPresenter.myTeam(UrlConstants.UrLType.GET_MYTEAM, size, pageno, uid);
    }

    @Override
    public boolean onRefreshLayoutBeginLoadingMore(RefreshLayout refreshLayout) {
        LogUtil.e(TAG, "onRefreshLayoutBeginLoadingMore");
        if (teamList.size() == size * (pageno + 1)) {
            pageno++;
            mPresenter.myTeam(UrlConstants.UrLType.GET_MYTEAM, size, pageno, uid);
            return true;
        }else{
            return false;
        }
    }
}
