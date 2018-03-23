package com.cj.reocrd.view.fragment;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.base.BaseFragment;
import com.cj.reocrd.base.baseadapter.BaseQuickAdapter;
import com.cj.reocrd.base.baseadapter.OnItemClickListener;
import com.cj.reocrd.contract.HomeContract;
import com.cj.reocrd.model.entity.FirstBean;
import com.cj.reocrd.presenter.HomePresenter;
import com.cj.reocrd.utils.GlideImageLoader;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.view.adapter.GoodsAdapter;
import com.cj.reocrd.view.adapter.HomeAdapter;
import com.cj.reocrd.view.refresh.NormalRefreshViewHolder;
import com.cj.reocrd.view.refresh.RefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2018/3/16.
 */

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View
        ,BaseQuickAdapter.RequestLoadMoreListener,RefreshLayout.RefreshLayoutDelegate,OnBannerListener {
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.refresh)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.rv_content)
    RecyclerView recyclerViewContent;
    @BindView(R.id.banner)
    Banner banner;
//    @BindView(R.id.home_recycler)
    RecyclerView homeRecycler;
    GoodsAdapter goodAdapter;
    List<String> images;
    List<String> mDatas;


    private Context mContext;
    private HomeAdapter mHomeTabAdapter;
    private int size = 20;



    @Override
    protected void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    public int getLayoutId() {
        mContext = getActivity();
        return R.layout.fragment_home;
    }

    @Override
    public void initData() {
        super.initData();
        images = new ArrayList<>();
        images.add("http://pic35.photophoto.cn/20150528/0005018358146030_b.jpg");
        images.add("http://pic28.photophoto.cn/20130827/0005018362405048_b.jpg");
        images.add("http://pic28.photophoto.cn/20130827/0005018371946994_b.jpg");
    }

    @Override
    public void initView() {
        titleLeft.setVisibility(View.GONE);
        titleCenter.setText(getString(R.string.home));
        titleRight.setBackgroundResource(R.mipmap.gouwuche);
        titleRight.setVisibility(View.VISIBLE);

        //轮播图
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(images);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setOnBannerListener(this);
        banner.start();

        initRecycleView();
    }

    private void initRecycleView() {
        mHomeTabAdapter = new HomeAdapter(R.layout.item_good,null);
        recyclerViewContent.setLayoutManager(new GridLayoutManager(mContext,2));
        recyclerViewContent.setHasFixedSize(true);
        //设置适配器加载动画
        mHomeTabAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        recyclerViewContent.setAdapter(mHomeTabAdapter);
        //设置适配器可以上拉加载
        mHomeTabAdapter.setOnLoadMoreListener(this);
        //设置下拉、上拉
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new NormalRefreshViewHolder(mContext,true));

        mPresenter.getListDataTest(20,1);

        //条目的点击事件
        recyclerViewContent.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                System.out.println("position == "+position);
                ToastUtil.showShort("position == "+position);
            }
        });

    }

    @OnClick({R.id.title_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_right:
                getMainActivity().getViewPager().setCurrentItem(3);
                break;
        }
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
    public void onLoadMoreRequested() {
        // todo 加载更多 BaseQuickAdapter的上拉加载更多方法，和onRefreshLayoutBeginLoadingMore使用其中一个就可以
    }

    @Override
    public void onRefreshLayoutBeginRefreshing(RefreshLayout refreshLayout) {
        // 开始刷新
        System.out.println("onRefreshLayoutBeginRefreshing===");
        mPresenter.getListDataTest(size,1);
    }

    @Override
    public boolean onRefreshLayoutBeginLoadingMore(RefreshLayout refreshLayout) {
        size += 20;
        mPresenter.getListDataTest(size,1);
        return false;
    }

    @Override
    public void OnBannerClick(int position) {
        ToastUtil.showShort("Banner : " +position);
    }
}
