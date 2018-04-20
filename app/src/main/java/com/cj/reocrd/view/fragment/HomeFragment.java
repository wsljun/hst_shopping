package com.cj.reocrd.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseFragment;
import com.cj.reocrd.base.baseadapter.BaseQuickAdapter;
import com.cj.reocrd.base.baseadapter.OnItemClickListener;
import com.cj.reocrd.contract.HomeContract;
import com.cj.reocrd.model.entity.BannerData;
import com.cj.reocrd.model.entity.GoodsBean;
import com.cj.reocrd.model.entity.HomeBean;
import com.cj.reocrd.presenter.HomePresenter;
import com.cj.reocrd.utils.GlideImageLoader;
import com.cj.reocrd.utils.LogUtil;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.view.activity.GoodDetailActivity;
import com.cj.reocrd.view.activity.SearchActivity;
import com.cj.reocrd.view.adapter.HomeAdapter;
import com.cj.reocrd.view.adapter.OnRecyclerItemClickListener;
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
    @BindView(R.id.fl_home_banner)
    FrameLayout flHomeBannerLyout;
    List<String> images;
    private List<GoodsBean> goodsBeanList = new ArrayList<>();

    private HomeAdapter mHomeTabAdapter;
    private int size = 20;  //pageSize
    private int pageno = 0; // 页码
    private final static String TAG = "HomeFragment";
    private Bundle goodBundle = new Bundle();

    @Override
    protected void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    public int getLayoutId() {
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
        LogUtil.e(TAG,"initview");
        titleLeft.setVisibility(View.GONE);
        titleCenter.setText(getString(R.string.home));
        titleRight.setBackgroundResource(R.mipmap.gouwuche);
        titleRight.setVisibility(View.VISIBLE);

        initRecycleView();
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
        b.putCharSequence("key",position+"; This is Home Fragment");
        baseFragment.setArguments(b);
    }

    private void setBannerView(List<String> images){
        //轮播图
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(images);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setOnBannerListener(this);
        banner.start();
    }

    private void initRecycleView() {
        mHomeTabAdapter = new HomeAdapter(R.layout.item_good,null);
        recyclerViewContent.setLayoutManager(new GridLayoutManager(mActivity,2));
        recyclerViewContent.setHasFixedSize(true);
        //设置适配器加载动画
        mHomeTabAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        recyclerViewContent.setAdapter(mHomeTabAdapter);
        //设置适配器可以上拉加载
        mHomeTabAdapter.setOnLoadMoreListener(this);
        //设置下拉、上拉
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new NormalRefreshViewHolder(mActivity,true));
        recyclerViewContent.addOnItemTouchListener(new OnRecyclerItemClickListener(recyclerViewContent) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                ToastUtil.showShort("position == "+viewHolder.getAdapterPosition());
                goodBundle.clear();
                goodBundle.putString("goodsID",goodsBeanList.get(viewHolder.getAdapterPosition()).getId());
                startActivity(GoodDetailActivity.class,goodBundle);
            }

            @Override
            public void onLongClick(RecyclerView.ViewHolder viewHolder) {

            }
        });
//        //条目的点击事件
//        recyclerViewContent.addOnItemTouchListener(new OnItemClickListener() {
//            @Override
//            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
//
//            }
//        });

        mPresenter.getHomeData(size,pageno);

    }

    @OnClick({R.id.home_search,R.id.title_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_search:
                startActivity(SearchActivity.class);
                break;
            case R.id.title_right:
                getMainActivity().getViewPager().setCurrentItem(3);
                break;
        }
    }

    @Override
    public void onSuccess(Object data) {
//        List<FirstBean> girlDataList = (List<FirstBean>) data;
//        mHomeTabAdapter.setNewData(girlDataList);
//        mRefreshLayout.endRefreshing();
//        mRefreshLayout.endLoadingMore();
    }

    @Override
    public void onFailureMessage(String msg) {
         ToastUtil.showShort(msg);
    }

    @Override
    public void onLoadMoreRequested() {
        // todo 加载更多 BaseQuickAdapter的上拉加载更多方法，和onRefreshLayoutBeginLoadingMore使用其中一个就可以
    }

    @Override
    public void onRefreshLayoutBeginRefreshing(RefreshLayout refreshLayout) {
        // 开始刷新
        System.out.println("onRefreshLayoutBeginRefreshing===");
//        mPresenter.getListDataTest(size,1);
    }

    @Override
    public boolean onRefreshLayoutBeginLoadingMore(RefreshLayout refreshLayout) {
        size += 20;
        mPresenter.getHomeData(size,pageno);
        return false;
    }

    @Override
    public void OnBannerClick(int position) {
        ToastUtil.showShort("Banner : " +position);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.e(TAG,"onCreate");
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtil.e(TAG,"onStart");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.e(TAG,"onStart");
    }

    @Override
    public void onRefreshHomeData(HomeBean homeBean) {
        if(null!=homeBean.getMlist()&&homeBean.getMlist().size()>0){
            goodsBeanList.clear();
            goodsBeanList.addAll(homeBean.getMlist());
            mHomeTabAdapter.setNewData(goodsBeanList);
            mRefreshLayout.endRefreshing();
            mRefreshLayout.endLoadingMore();
        }else{
            ToastUtil.showShort("暂时没有商品信息");
            mHomeTabAdapter.loadComplete();
            mRefreshLayout.endRefreshing();
            mRefreshLayout.endLoadingMore();
        }
        if(null!=homeBean.getBlist()&&homeBean.getBlist().size()>0){
            images.clear();
            for (BannerData uri  : homeBean.getBlist()) {
                  images.add(UrlConstants.BASE_URL+uri.getImgurl());
            }
            setBannerView(images);
//            banner.update(images);
        }
    }
}
