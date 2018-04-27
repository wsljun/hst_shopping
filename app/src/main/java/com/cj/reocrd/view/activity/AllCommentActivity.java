package com.cj.reocrd.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.model.entity.GoodsCommentBean;
import com.cj.reocrd.utils.CollectionUtils;
import com.cj.reocrd.view.adapter.AllCommentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.cj.reocrd.view.activity.GoodDetailActivity.goodsCommentBeans;

public class AllCommentActivity extends BaseActivity {


    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.recycler_comment)
    RecyclerView recyclerComment;

    private AllCommentAdapter allCommentAdapter;
    private List<GoodsCommentBean> mDatas;


    @Override
    public int getLayoutId() {
        return R.layout.activity_all_comment;
    }

    @Override
    public void initData() {
        mDatas = new ArrayList<>();
        if(!CollectionUtils.isNullOrEmpty(goodsCommentBeans)){
            mDatas.addAll(goodsCommentBeans);
        }
    }

    @Override
    public void initView() {
        titleCenter.setText("全部评论");
        allCommentAdapter = new AllCommentAdapter(mContext,R.layout.item_comment,mDatas);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerComment.setLayoutManager(linearLayoutManager);
        //设置适配器
        recyclerComment.setAdapter(allCommentAdapter);
    }

    @Override
    public void initPresenter() {

    }

    @OnClick({R.id.title_left, R.id.title_center, R.id.recycler_comment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.title_center:
                break;
            case R.id.recycler_comment:
                break;
        }
    }
}
