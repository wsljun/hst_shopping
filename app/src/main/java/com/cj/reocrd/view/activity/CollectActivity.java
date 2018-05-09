package com.cj.reocrd.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.base.baseadapter.BaseQuickAdapter;
import com.cj.reocrd.contract.GoodsDetailContract;
import com.cj.reocrd.model.entity.Friends;
import com.cj.reocrd.model.entity.GoodsBean;
import com.cj.reocrd.model.entity.GoodsCommentBean;
import com.cj.reocrd.model.entity.HomeBean;
import com.cj.reocrd.presenter.GoodsDetailPresenter;
import com.cj.reocrd.utils.LogUtil;
import com.cj.reocrd.utils.TimeUtils;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.view.adapter.CollectAdapter;
import com.cj.reocrd.view.adapter.FriendsAdapter;
import com.cj.reocrd.view.refresh.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/4/20.
 */

public class CollectActivity extends BaseActivity<GoodsDetailPresenter> implements GoodsDetailContract.View,
        BaseQuickAdapter.OnBaseQuickAdapterItemClickListener, BaseQuickAdapter.RequestLoadMoreListener {

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
    @BindView(R.id.collect_search)
    TextView coolectSearch;

    List<GoodsBean> ALLDatas;

    private CollectAdapter collectAdapter;
    private int size = 10;  //pageSize
    private int pageno = 0; // 页码
    private int type;
    private final static String TAG = "CollectActivity";
    private boolean loading = false;
    private String from;

    private int position;//删除的项
    private static  String date = "";
    private static String pre_date = "";
    private String localTime ;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_collect;
    }

    @Override
    public void initData() {
        super.initData();
        ALLDatas = new ArrayList<>();
        localTime = TimeUtils.getSysCurDate();
        Bundle bundle = getIntent().getExtras();
        from = bundle.getString("from");
        if (!TextUtils.isEmpty(from)) {
            type = 1;
            if ("collect".equals(from)) {
                titleCenter.setText(getString(R.string.collect_title));
                mPresenter.collectList(uid, size, pageno);
                coolectSearch.setVisibility(View.VISIBLE);
            }
            if ("history".equals(from)) {
                titleCenter.setText("浏览历史");
                mPresenter.collectBrowse(uid, size, pageno);
                coolectSearch.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void initView() {
        initList();
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void acticonToSubmitOrder(ApiResponse apiResponse) {

    }

    @Override
    public void setCollectImg(boolean stuats) {

    }

    @Override
    public void showComment(List<GoodsCommentBean> goodsCommentBeanList) {

    }


    @Override
    public void onAdapterItemClickListener(View view, int position) {
        switch (view.getId()) {
            case R.id.collect_delete:
                type = 2;
                this.position = position;
                mPresenter.collectDelete(uid, ALLDatas.get(position).getId());
                break;
            case R.id.collect_img:
//                ToastUtil.showShort("商品详情");
                Bundle goodBundle = new Bundle();
                goodBundle.putString("goodsID",ALLDatas.get(position).getId());
                startActivity(GoodDetailActivity.class, goodBundle);
                break;
        }
    }

    public void initList() {
        if ("collect".equals(from)) {
            collectAdapter = new CollectAdapter(R.layout.item_collect, ALLDatas, CollectAdapter.COLLECT);
        }
        if ("history".equals(from)) {
            collectAdapter = new CollectAdapter(R.layout.item_collect, ALLDatas, CollectAdapter.HISTORY);
        }
        //设置适配器可以上拉加载
        collectAdapter.setOnBaseAdapterItemClickListener(this);
        collectAdapter.setOnLoadMoreListener(this);
        recyclerContent.setLayoutManager(new LinearLayoutManager(this));
        recyclerContent.setAdapter(collectAdapter);
    }

    @Override
    public void onSuccess(Object data) {
        ApiResponse response = (ApiResponse) data;
        switch (type) {
            case 1:
                if (UrlConstants.SUCCESE_CODE.equals(response.getStatusCode())) {
                    HomeBean homeBean = (HomeBean) response.getResults();
                    if (homeBean.getMlist() != null && homeBean.getMlist().size() > 0) {
                       /* if (loading) {
                            loading = false;
                            collectAdapter.addData(homeBean.getMlist());
                            refreshLayout.endLoadingMore();
                        } else {
                            ALLDatas = homeBean.getMlist();
                            initList();
                        }*/
                        ALLDatas.addAll(homeBean.getMlist());
                        if ("history".equals(from)) {
                            for (int i = 0; i <ALLDatas.size() ; i++) {
                                date = ALLDatas.get(i).getCreatetime();
                                if(!TextUtils.isEmpty(date)){
                                    String [] datas = date.split(" ");
                                    date = datas[0];
                                }
                                if(date.equals(localTime)){
                                    date = "今天";
                                }
                                ALLDatas.get(i).setCreatetime(date);
                                if(pre_date.equals(date)){
                                    ALLDatas.get(i).setShowDate(false);
                                }else{
                                    ALLDatas.get(i).setShowDate(true);
                                }
                                pre_date  = date ;
                            }
                        }
                        collectAdapter.setNewData(ALLDatas);
                        if(homeBean.getMlist().size()<10 ){
                            refreshLayout.endLoadingMore();
                            collectAdapter.loadComplete();
                        }
                    }else{
                        refreshLayout.endLoadingMore();
                        collectAdapter.loadComplete();
                    }
                } else {
                    ToastUtil.showToastS(this, response.getMessage());
                }
                break;
            case 2:
                if (UrlConstants.SUCCESE_CODE.equals(response.getStatusCode())) {
                    collectAdapter.remove(position);
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

    @OnClick({R.id.title_left, R.id.collect_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.collect_search:

                break;
        }

    }

    @Override
    public void onLoadMoreRequested() {
        loading = true;
        pageno++;
        type = 1;
        if ("collect".equals(from)) {
            mPresenter.collectList(uid, size, pageno);
        }
        if ("history".equals(from)) {
            mPresenter.collectBrowse(uid, size, pageno);
        }
    }
}
