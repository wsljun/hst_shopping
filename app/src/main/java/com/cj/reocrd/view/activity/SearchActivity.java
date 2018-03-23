package com.cj.reocrd.view.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.TextView;


import com.cj.reocrd.R;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.view.adapter.GoodsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/3/19.
 */

public class SearchActivity extends BaseActivity {
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.search)
    EditText search;
    @BindView(R.id.search_recycler)
    RecyclerView searchRecycler;
    GoodsAdapter goodAdapter;
    List<String> mDatas;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initData() {
        super.initData();
        mDatas = new ArrayList<>();
        mDatas.add("1");
        mDatas.add("2");
        mDatas.add("3");
        mDatas.add("4");
        mDatas.add("5");
        mDatas.add("6");
        mDatas.add("7");
    }

    @Override
    public void initView() {
        titleCenter.setText("五谷杂粮");
        //商品列表
//        goodAdapter = new GoodsAdapter(this, mDatas);
//        goodAdapter.setOnItemListener(this);
//        searchRecycler.setLayoutManager(new GridLayoutManager(this, 2));
//        searchRecycler.setAdapter(goodAdapter);
    }

    @Override
    public void initPresenter() {
         //todo
    }

}
