package com.cj.reocrd.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.contract.OrderContract;
import com.cj.reocrd.model.entity.OrderBean;
import com.cj.reocrd.presenter.OrderPresenter;
import com.cj.reocrd.utils.CollectionUtils;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.view.adapter.OrderAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/17.
 */

public class OrderActivity extends BaseActivity<OrderPresenter> implements OrderAdapter.OnItemListener
        ,OrderContract.View{
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.undone_recycler)
    RecyclerView undoneRecycler;
    OrderAdapter orderAdapter;
    List<String> mDatas;
    List<OrderBean> orderBeans;
    int type;
    public static final int ALL = 0;//全部
    public static final int PAY = 1;//待付款
    public static final int SEND = 2;//待发货
    public static final int CONFIM = 3;//待确认
    public static List<OrderBean.OdlistBean> mOrderGoodsDatas;
    public static final int EVALUATE = 4;//待评价

    public static void actionActivity(Context context, int type) {
        Intent sIntent = new Intent(context, OrderActivity.class);
        sIntent.putExtra("type", type);
        context.startActivity(sIntent);
    }



    @Override
    public int getLayoutId() {
        return R.layout.activity_undone;
    }

    @Override
    public void initData() {
        super.initData();
        //这四个页面都差不多，就放到一块了，type标示，加载不同布局
        type = getIntent().getIntExtra("type", 1);
        orderBeans = new ArrayList<>();
        mPresenter.getOrderList("20","0",uid, String.valueOf(type));
        mDatas = new ArrayList<>();
        mDatas.add("1");
        mDatas.add("2");
        mDatas.add("3");
    }

    @Override
    public void initView() {
        switch (type){
            case 0:
                titleCenter.setText(getString(R.string.mine_order_all));
                break;
            case 1:
                titleCenter.setText(getString(R.string.mine_daifukuan));
                break;
            case 2:
                titleCenter.setText(getString(R.string.mine_daifahuo));
                break;
            case 3:
                titleCenter.setText(getString(R.string.mine_daiqueren));
                break;
            case 4:
                titleCenter.setText(getString(R.string.mine_daipingjia));
                break;
        }
        //type传给adapter，调整不同布局
        orderAdapter = new OrderAdapter(this, orderBeans,type);
        orderAdapter.setOnItemListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        undoneRecycler.setLayoutManager(layoutManager);
        undoneRecycler.setAdapter(orderAdapter);
        undoneRecycler.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
    }


    @OnClick({R.id.title_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
        }
    }

    @Override
    public void takeClick(int position) {
//        ToastUtil.showToastS(this, "takeClick" + position);
        mPresenter.cancelOrder(orderBeans.get(position).getId());
    }

    @Override
    public void refundClick(int position) {
//        ToastUtil.showToastS(this, "refundClick" + position);
    }

    @Override
    public void orderDetail(int position) {
        if(!CollectionUtils.isNullOrEmpty(orderBeans)){
            mOrderGoodsDatas = orderBeans.get(position).getOdlist();
            Bundle bundle = new Bundle();
            bundle.putString(OrderDetailActivity.BUNDLE_KEY_OID,orderBeans.get(position).getId());
            startActivity(OrderDetailActivity.class,bundle);
        }
    }

    @Override
    public void onSuccess(Object data) {

    }

    @Override
    public void onFailureMessage(String msg) {
          ToastUtil.showShort(msg);
    }

    @Override
    public Context getContext() {
        return this.getApplicationContext();
    }

    @Override
    public void showOrderList(List<OrderBean> orderBeanList) {
         if(orderBeanList.size()>0){
             orderAdapter.updateData(orderBeanList);
         }else{
             ToastUtil.showShort("没有订单！");
         }
    }

    @Override
    public void updateOrderList() {
        mPresenter.getOrderList("20","0",uid, String.valueOf(type));
    }
}
