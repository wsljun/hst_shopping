package com.cj.reocrd.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.base.BaseFragment;
import com.cj.reocrd.base.baseadapter.BaseQuickAdapter;
import com.cj.reocrd.base.baseadapter.OnItemClickListener;
import com.cj.reocrd.contract.GoodsContract;
import com.cj.reocrd.model.entity.FirstBean;
import com.cj.reocrd.presenter.GoodsPresenter;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.view.adapter.AllOneAdapter;
import com.cj.reocrd.view.adapter.GoodsAdapter;
import com.cj.reocrd.view.refresh.NormalRefreshViewHolder;
import com.cj.reocrd.view.refresh.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/16.
 */

public class AllGoodsFragment extends BaseFragment<GoodsPresenter> implements GoodsContract.View
        ,BaseQuickAdapter.RequestLoadMoreListener,RefreshLayout.RefreshLayoutDelegate{
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.rv_goods_type)
    RecyclerView rvGoodsType; // 商品类目

    AllOneAdapter oneAdapter;
    List<String> one;

    @BindView(R.id.refresh)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.rv_content)
    RecyclerView recyclerViewContent;

    private Context mContext;
    private GoodsAdapter mGoodsAdapter;
    private int size = 20;

    @Override
    protected void initPresenter() {
       mPresenter.setVM(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_all;
    }

    @Override
    public void initData() {
        super.initData();
        mContext = getActivity();
        one = new ArrayList<>();
        one.add("1");
        one.add("2");
        one.add("3");
        one.add("4");
    }

    @Override
    public void getArgumentData(Bundle arguments) {
        super.getArgumentData(arguments);
        ToastUtil.showShort(arguments.getString("key"));
    }

    @Override
    public void putArgumentData(BaseFragment baseFragment, int position) {
        super.putArgumentData(this, position);
        Bundle  b = new Bundle();
        b.putCharSequence("key",position+"; This is AllGoods Fragment");
        baseFragment.setArguments(b);
    }


    @Override
    public void initView() {
        titleCenter.setText(getString(R.string.all));

        oneAdapter = new AllOneAdapter(mActivity, one);
        rvGoodsType.setLayoutManager(new LinearLayoutManager(mActivity));
        rvGoodsType.setAdapter(oneAdapter);
        rvGoodsType.setItemAnimator(new DefaultItemAnimator());
        initRecycleView();
    }

    private void initRecycleView() {
        mGoodsAdapter = new GoodsAdapter(R.layout.item_all_two,null);
        recyclerViewContent.setLayoutManager(new GridLayoutManager(mContext,3));
        recyclerViewContent.setHasFixedSize(true);
        //设置适配器加载动画
        mGoodsAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        recyclerViewContent.setAdapter(mGoodsAdapter);
        //设置适配器可以上拉加载
        mGoodsAdapter.setOnLoadMoreListener(this);
        //设置下拉、上拉
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new NormalRefreshViewHolder(mContext,true));

        mPresenter.getGoodsTest(20,1);

        //条目的点击事件
        recyclerViewContent.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                System.out.println("position == "+position);
                ToastUtil.showShort("position == "+position);
            }
        });

    }


    @OnClick({R.id.title_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                getMainActivity().getViewPager().setCurrentItem(0);
                break;
                default:
                    break;
        }
    }

    @Override
    public void onSuccess(Object data) {
        List<FirstBean> girlDataList = (List<FirstBean>) data;
        mGoodsAdapter.setNewData(girlDataList);
        mGoodsAdapter.loadComplete();
        mRefreshLayout.endRefreshing();
        mRefreshLayout.endLoadingMore();
    }

    @Override
    public void onFailureMessage(String msg) {

    }

    @Override
    public void onLoadMoreRequested() {

    }

    @Override
    public void onRefreshLayoutBeginRefreshing(RefreshLayout refreshLayout) {
        mPresenter.getGoodsTest(size,1);
    }

    @Override
    public boolean onRefreshLayoutBeginLoadingMore(RefreshLayout refreshLayout) {
        size += 20;
        mPresenter.getGoodsTest(size,1);
        return false;
    }
}
