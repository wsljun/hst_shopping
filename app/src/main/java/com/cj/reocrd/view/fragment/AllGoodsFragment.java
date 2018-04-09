package com.cj.reocrd.view.fragment;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.view.View;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.base.BaseFragment;
import com.cj.reocrd.base.baseadapter.BaseQuickAdapter;
import com.cj.reocrd.base.baseadapter.OnItemClickListener;
import com.cj.reocrd.contract.GoodsContract;
import com.cj.reocrd.model.entity.GoodsBean;
import com.cj.reocrd.model.entity.GoodsType;
import com.cj.reocrd.model.entity.HomeBean;
import com.cj.reocrd.presenter.GoodsPresenter;
import com.cj.reocrd.utils.LogUtil;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.view.activity.GoodDetailActivity;
import com.cj.reocrd.view.adapter.AllTypeAdapter;
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
        ,RefreshLayout.RefreshLayoutDelegate{
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.rv_goods_type)
    RecyclerView rvGoodsType; // 商品类目

    AllTypeAdapter allTypeAdapter;

    @BindView(R.id.refresh)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.rv_content)
    RecyclerView recyclerViewContent;

    private Context mContext;
    private GoodsAdapter mGoodsAdapter;
    private String tid = ""; // 商品分类
    private int pagesize = 20;  //pageSize
    private int pageno = 0; // 页码
    private List<GoodsType> goodsTypes  = new ArrayList<>();
    private List<GoodsBean> goodsBeanList = new ArrayList<>();

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
        // todo get goods type
        mPresenter.getGoodsType("");
    }

    @Override
    public void initView() {
        titleCenter.setText(getString(R.string.all));
        initRecycleView();
    }

    private void showType(){
        allTypeAdapter = new AllTypeAdapter(mActivity, goodsTypes);
        rvGoodsType.setLayoutManager(new LinearLayoutManager(mActivity));
        rvGoodsType.setAdapter(allTypeAdapter);
        rvGoodsType.setItemAnimator(new DefaultItemAnimator());
        allTypeAdapter.setOnItemClickListener(new AllTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                LogUtil.d("onItemClick",goodsTypes.get(position).getName());
                mPresenter.getGoodsData(goodsTypes.get(position).getId(),pageno,pagesize);
            }
        });
    }

    private void initRecycleView() {
        mGoodsAdapter = new GoodsAdapter(R.layout.item_all_two,null);
        recyclerViewContent.setLayoutManager(new GridLayoutManager(mContext,3));
        recyclerViewContent.setHasFixedSize(true);
        //设置适配器加载动画
        mGoodsAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        recyclerViewContent.setAdapter(mGoodsAdapter);
        //设置适配器可以上拉加载
//        mGoodsAdapter.setOnLoadMoreListener(this);
        //设置下拉、上拉
//        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new NormalRefreshViewHolder(mContext,true));

//初次进入 tid 传空
        mPresenter.getGoodsData("",pageno,pagesize);
        //条目的点击事件
        recyclerViewContent.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                System.out.println("position == "+position);
                ToastUtil.showShort("position == "+position);
                GoodDetailActivity.intentTo(mContext,goodsBeanList.get(position).getId());
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
        HomeBean homeBean  = (HomeBean) data;
        goodsBeanList = homeBean.getMlist();
        mGoodsAdapter.setNewData(goodsBeanList);
        mGoodsAdapter.loadComplete();
        mRefreshLayout.endRefreshing();
        mRefreshLayout.endLoadingMore();
    }

    @Override
    public void onFailureMessage(String msg) {
        ToastUtil.showShort(msg);
    }


    @Override
    public void onRefreshLayoutBeginRefreshing(RefreshLayout refreshLayout) {
        mPresenter.getGoodsData("", pageno, pagesize);
    }

    @Override
    public boolean onRefreshLayoutBeginLoadingMore(RefreshLayout refreshLayout) {
        mPresenter.getGoodsData(tid, pageno, pagesize);
        return false;
    }

    /**
     * @param list
     */
    @Override
    public void saveGoodsType(List<GoodsType> list) {
        if(null!= list && list.size()>0){
            this.goodsTypes.addAll(list);
            showType();
        }
    }
}
