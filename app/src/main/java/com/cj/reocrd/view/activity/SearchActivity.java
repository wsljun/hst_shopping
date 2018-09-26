package com.cj.reocrd.view.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.cj.reocrd.R;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.base.baseadapter.BaseQuickAdapter;
import com.cj.reocrd.base.baseadapter.OnItemClickListener;
import com.cj.reocrd.contract.MyContract;
import com.cj.reocrd.model.entity.GoodsBean;
import com.cj.reocrd.model.entity.HomeBean;
import com.cj.reocrd.presenter.MyPrresenter;
import com.cj.reocrd.utils.CollectionUtils;
import com.cj.reocrd.utils.LogUtil;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.view.adapter.HomeAdapter;
import com.cj.reocrd.view.refresh.NormalRefreshViewHolder;
import com.cj.reocrd.view.refresh.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/19.
 */

public class SearchActivity extends BaseActivity<MyPrresenter> implements MyContract.View
        , BaseQuickAdapter.RequestLoadMoreListener, RefreshLayout.RefreshLayoutDelegate {
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.et_search)
    EditText editTextSearch;
    @BindView(R.id.refresh)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.rv_content)
    RecyclerView recyclerViewContent;
    @BindView(R.id.rg_search_label)
    RadioGroup rgSearchLabel;

    List<GoodsBean> mDatas;
    private HomeAdapter mHomeTabAdapter;
    private int pagesize = 20;
    private int pageno = 0;
    private String label = "0";  // 价格0，热门1，新品2，促销3
    private final String SORT_ASC = "asc"; // 升序
    private final String SORT_DESC = "desc"; // 降序
    private String sort_label = SORT_ASC; // 默认值
    private String searchStr = "好货";
    private final String TAG = "SearchActivity";
    private List<RadioButton> radioButtons = new ArrayList<>();
    private Drawable drawableLabel;
    private List<GoodsBean> goodsBeanList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initData() {
        super.initData();
        drawableLabel = getResources().getDrawable(R.mipmap.zonghexuanze);
        mPresenter.getSearchData(searchStr, sort_label, label, pagesize, pageno);
    }

    @Override
    public void initView() {
        titleCenter.setText("搜索");
        rgSearchLabel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                radioButton.setTextColor(getResources().getColor(R.color.colorButton));
                if (!radioButtons.contains(radioButton)) {
                    radioButtons.add(radioButton);
                }
                for (RadioButton rb : radioButtons) {
                    if (rb.getId() != radioButton.getId()) {
                        rb.setTextColor(getResources().getColor(R.color.colorBlack));
                        rb.setCompoundDrawables(null, null, null, null);
                    } else {
                        drawableLabel.setBounds(0, 0, 30, 30);
                        radioButton.setCompoundDrawables(null, null, drawableLabel, null);
                    }
                }
                label = String.valueOf(radioButton.getTag());
                updateData();
            }
        });
        //商品列表
        initRecycleView();
    }

    private void initRecycleView() {
        mHomeTabAdapter = new HomeAdapter(R.layout.item_good_search, null);
        recyclerViewContent.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewContent.setHasFixedSize(true);
        //设置适配器加载动画
        mHomeTabAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        recyclerViewContent.setAdapter(mHomeTabAdapter);
        //设置适配器可以上拉加载
        mHomeTabAdapter.setOnLoadMoreListener(this);
        //设置下拉、上拉
//        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new NormalRefreshViewHolder(this, true));

//        mPresenter.getListDataTest(20, 1);

        //条目的点击事件
        recyclerViewContent.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("goodsID", goodsBeanList.get(position).getId());
                startActivity(GoodDetailActivity.class, bundle);
            }
        });

    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
    }

    @OnClick({R.id.search_iv, R.id.title_right, R.id.title_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search_iv:
                setSearchStr();
                break;
            case R.id.rg_search_label:
                break;
            case R.id.title_left:
                finish();
                break;
            default:
                break;
        }
    }

    private void setSearchStr() {
        String et_str = editTextSearch.getText().toString();
        if ("".equals(et_str)) {
            searchStr = "";
        } else {
            searchStr = et_str;
            updateData();
        }
    }

    private void updateData() {
        mPresenter.getSearchData(searchStr, sort_label, label, pagesize, pageno);
    }


    @Override
    public void onSuccess(Object data) {
        HomeBean homeBean = (HomeBean) data;
        if (!CollectionUtils.isNullOrEmpty(homeBean.getMlist())) {
            goodsBeanList = new ArrayList<>();
            goodsBeanList.addAll(homeBean.getMlist());
            mHomeTabAdapter.setNewData(goodsBeanList);
        } else {
            ToastUtil.showShort("暂时没有数据！");
        }
        mRefreshLayout.endRefreshing();
        mRefreshLayout.endLoadingMore();
        mHomeTabAdapter.loadComplete();
    }

    @Override
    public void onFailureMessage(String msg) {
        ToastUtil.showShort(msg);
        mHomeTabAdapter.loadComplete();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onLoadMoreRequested() {
        LogUtil.d(TAG, "onLoadMoreRequested");
//        pagesize += 20;
        mPresenter.getSearchData(searchStr, sort_label, label, pagesize, pageno);
    }

    @Override
    public void onRefreshLayoutBeginRefreshing(RefreshLayout refreshLayout) {
        // 开始刷新
//        mPresenter.getSearchData(searchStr,sort_label,label,pagesize, 0);
    }

    @Override
    public boolean onRefreshLayoutBeginLoadingMore(RefreshLayout refreshLayout) {
//        pagesize += 20;
//        mPresenter.getSearchData(searchStr,sort_label,label,pagesize, 0);
        return false;
    }

}
