package com.cj.reocrd.view.activity;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.cj.reocrd.R;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.base.BaseFragment;
import com.cj.reocrd.base.baseadapter.BaseQuickAdapter;
import com.cj.reocrd.base.baseadapter.OnItemClickListener;
import com.cj.reocrd.contract.HomeContract;
import com.cj.reocrd.model.entity.FirstBean;
import com.cj.reocrd.presenter.HomePresenter;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.view.adapter.GoodsAdapter;
import com.cj.reocrd.view.adapter.HomeAdapter;
import com.cj.reocrd.view.refresh.NormalRefreshViewHolder;
import com.cj.reocrd.view.refresh.RefreshLayout;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/3/19.
 */

public class SearchActivity extends BaseActivity<HomePresenter> implements HomeContract.View
        , BaseQuickAdapter.RequestLoadMoreListener, RefreshLayout.RefreshLayoutDelegate {
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.search)
    EditText search;
    @BindView(R.id.refresh)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.rv_content)
    RecyclerView recyclerViewContent;
    List<String> mDatas;
    private HomeAdapter mHomeTabAdapter;
    private int size = 20;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initView() {
        titleCenter.setText("五谷杂粮");
        //商品列表
        initRecycleView();
    }

    private void initRecycleView() {
        mHomeTabAdapter = new HomeAdapter(R.layout.item_good, null);
        recyclerViewContent.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewContent.setHasFixedSize(true);
        //设置适配器加载动画
        mHomeTabAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        recyclerViewContent.setAdapter(mHomeTabAdapter);
        //设置适配器可以上拉加载
        mHomeTabAdapter.setOnLoadMoreListener(this);
        //设置下拉、上拉
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new NormalRefreshViewHolder(this, true));

        mPresenter.getListDataTest(20, 1);

        //条目的点击事件
        recyclerViewContent.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                System.out.println("position == " + position);
                ToastUtil.showShort("position == " + position);
            }
        });

    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    public void onSuccess(Object data) {
        List<FirstBean> girlDataList = (List<FirstBean>) data;
        mHomeTabAdapter.setNewData(girlDataList);
        mRefreshLayout.endRefreshing();
        mRefreshLayout.endLoadingMore();
    }

    @Override
    public void onFailureMessage(String msg) {

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onLoadMoreRequested() {

    }

    @Override
    public void onRefreshLayoutBeginRefreshing(RefreshLayout refreshLayout) {
        // 开始刷新
        System.out.println("onRefreshLayoutBeginRefreshing===");
        mPresenter.getListDataTest(size, 1);
    }

    @Override
    public boolean onRefreshLayoutBeginLoadingMore(RefreshLayout refreshLayout) {
        size += 20;
        mPresenter.getListDataTest(size, 1);
        return false;
    }

}
