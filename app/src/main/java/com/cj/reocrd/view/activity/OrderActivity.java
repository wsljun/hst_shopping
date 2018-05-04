package com.cj.reocrd.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.TokenWatcher;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
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
    public static final int ORDER_STATUS_ALL = 0;//全部
    public static final int ORDER_STATUS_PAY = 1;//待付款
    public static final int ORDER_STATUS_SEND = 2;//待发货
    public static final int ORDER_STATUS_CONFIM = 3;//待确认
    public static List<OrderBean.OdlistBean> mOrderGoodsDatas;
    public static final int ORDER_STATUS_EVALUATE = 4;//待评价
    public static final int ORDER_STATUS_OVER = 5;//完成
    public static final int ORDER_STATUS_REFUNDING = 6;//退款中
    public static final int ORDER_STATUS_REFUNDOVER = 7;//退款完成
    public static final int ORDER_STATUS_CANCLE = 8;//自行取消
    public static final int ORDER_STATUS_NOT_REFUND = 9 ;//退货被拒绝
    //(1.未付款   2 待发货  3待确认，4待评价 5完成  6退款中 7 退款完成 8自行取消)
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
            case 5:
                titleCenter.setText(R.string.mine_tuihuo);
                break;
                default:
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
    public void cancleClick(View v, int position) {
        int tag = (int) v.getTag();
        if(ORDER_STATUS_PAY == tag){
            mPresenter.cancelOrder(orderBeans.get(position).getId());
        }
    }

    @Override
    public void refundClick(View v, int position) {
        int tag = (int) v.getTag();
        switch (tag){
            case ORDER_STATUS_PAY : // 去支付
                Bundle b = new Bundle();
                b.putString(PayActivity.BUNDLE_KEY_OID,orderBeans.get(position).getId());
                b.putString(PayActivity.BUNDLE_KEY_PRICE,orderBeans.get(position).getAllamount());
                startActivity(PayActivity.class,b);
                finish();
                break;
            case ORDER_STATUS_CONFIM : //确认后去评价
                mPresenter.comfirmTakeGoods(orderBeans.get(position).getId());
                break;
            case ORDER_STATUS_EVALUATE : // 评价
                goComment(orderBeans.get(position).getId());
                break;
            case ORDER_STATUS_OVER : //申请退货
                Bundle nb =  new Bundle();
                nb.putString("oid",orderBeans.get(position).getId());
                startActivity(RefundActivity.class,nb);
                break;
            default:
                break;
        }
    }

    private void goComment(String id) {
      new MaterialDialog.Builder(this)
              .title("输入评论")
              .inputType(InputType.TYPE_CLASS_TEXT )
                .input("输入评论内容", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        ToastUtil.showShort("input"+input);
                        mPresenter.goComment(id,input.toString());
                    }
                }).onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    }
                })
                .show();
    }

    @Override
    public void orderDetail(int position) {
//        if(!CollectionUtils.isNullOrEmpty(orderBeans)){
//            mOrderGoodsDatas = orderBeans.get(position).getOdlist();
//            int status = Integer.parseInt( orderBeans.get(position).getStatus());
//             如果订单已经申请退款
//            if(ORDER_STATUS_REFUNDING == status || status == ORDER_STATUS_REFUNDOVER
//                  || ORDER_STATUS_NOT_REFUND == status){
//                Bundle bundle = new Bundle();
//                bundle.putString(OrderDetailActivity.BUNDLE_KEY_OID,orderBeans.get(position).getId());
//                startActivity(RefundDetailActivity.class,bundle);
//            }else{
//                Bundle bundle = new Bundle();
//                bundle.putString(OrderDetailActivity.BUNDLE_KEY_OID,orderBeans.get(position).getId());
//                startActivity(OrderDetailActivity.class,bundle);
//            }
//        }
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
            orderBeanList = new ArrayList<>();
            orderAdapter.updateData(orderBeanList);
            ToastUtil.showShort("没有订单！");
        }
    }

    @Override
    public void updateOrderList() {
        mPresenter.getOrderList("20","0",uid, String.valueOf(type));
    }
}
