package com.cj.reocrd.view.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.contract.OrderContract;
import com.cj.reocrd.model.entity.OrderBean;
import com.cj.reocrd.model.entity.OrderDetail;
import com.cj.reocrd.presenter.OrderPresenter;
import com.cj.reocrd.utils.CollectionUtils;
import com.cj.reocrd.utils.ConstantsUtils;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.view.adapter.GalleryAdapter;
import com.cj.reocrd.view.adapter.OrderDetailAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.cj.reocrd.view.activity.OrderActivity.mOrderGoodsDatas;

public class OrderDetailActivity extends BaseActivity<OrderPresenter> implements OrderContract.View {


    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.tv_order_id)
    TextView tvOrderId;
    @BindView(R.id.tv_order_status)
    TextView tvOrderStatus;
    @BindView(R.id.rl_goods_img)
    RecyclerView rlGoodsImg;
    @BindView(R.id.tv_goods_price)
    TextView tvGoodsPrice;
    @BindView(R.id.tv_freight)
    TextView tvFreight;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.rl_goods_info)
    RelativeLayout rlGoodsInfo;
    @BindView(R.id.tv_consignee)
    TextView tvConsignee;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_pay_way)
    TextView tvPayWay;
    @BindView(R.id.tv_submit_order_time)
    TextView tvSubmitOrderTime;
    @BindView(R.id.tv_arrive_time)
    TextView tvArriveTime;
    @BindView(R.id.tv_buy_again)
    TextView tvBuyAgain;

    private String oid ;
    public static  final String BUNDLE_KEY_OID = "oid";
    public static  final String REQUEST_TYEP = "ORDER_DETAIL";
    private  String rType = "";
    private OrderDetail orderDetail ;
    private OrderDetailAdapter mAdapter;
    private List<OrderBean.OdlistBean> mOrderDetailGoodsDatas;

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initData() {
        oid = getIntent().getStringExtra(BUNDLE_KEY_OID);
        mOrderDetailGoodsDatas = new ArrayList<>();
        if(!CollectionUtils.isNullOrEmpty(mOrderGoodsDatas)){
//            for (int i = 0; i <mOrderGoodsDatas.size() ; i++) {
            mOrderDetailGoodsDatas.addAll(mOrderGoodsDatas);
//            }
        }
        if(!TextUtils.isEmpty(oid)){
            rType = REQUEST_TYEP;
            mPresenter.getOrderDetail(oid);
        }
    }

//    public static Intent newIntent(Context context, List<OrderBean> orderBeans) {
//        Intent intent = new Intent(context, GoodDetailActivity.class);
//        intent.putExtra("oid", oid);
//        return intent;
//    }

    /**
//     * @param context
//     * @param oid 订单id
     */
//    public static void intentTo(Context context, String oid) {
//        context.startActivity(newIntent(context, oid));
//    }

    @Override
    public void initView() {
        rlGoodsImg = new RecyclerView(this);
        // 订单中 不同商品图片展示
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rlGoodsImg.setLayoutManager(linearLayoutManager);
        //设置适配器
        mAdapter = new OrderDetailAdapter(this, mOrderDetailGoodsDatas);
        rlGoodsImg.setAdapter(mAdapter);
    }

    private void updateView() {
        if(null == orderDetail){
            return;
        }
        tvOrderId.setText("订单编号："+orderDetail.getSn());
        setOrderStatus(orderDetail.getStatus());
        tvGoodsPrice.setText(ConstantsUtils.RMB+orderDetail.getAmount());
        tvFreight.setText(ConstantsUtils.RMB+orderDetail.getExamount());
        tvTotalPrice.setText(ConstantsUtils.RMB+orderDetail.getAllamount());
        tvConsignee.setText("收货人："+orderDetail.getConsignee());
        tvAddress.setText("收货地址："+orderDetail.getFuladdress());
        tvPayWay.setText("支付方式："+orderDetail.getWay());
        tvSubmitOrderTime.setText("下单时间："+orderDetail.getCreatetime());
        tvArriveTime.setText("预计送达："+null);
        mAdapter.updateData(mOrderDetailGoodsDatas);
    }

    private void setOrderStatus(String s) {
        //(1.未付款   2 待发货  3待确认，4待评价 5完成  6退款中 7 退款完成 8自行取消)
        int status = Integer.parseInt(s);
        String str_status = "";
        switch (status){
            case OrderActivity.ALL:
                break;
            case OrderActivity.PAY:
                str_status = "待付款";
                break;
            case OrderActivity.SEND:
                str_status = "待发货";
                break;
            case OrderActivity.CONFIM:
                str_status = "待确认";
                break;
            case OrderActivity.EVALUATE:
                str_status = "待评价";
                break;
            default:
                break;
        }
        tvOrderStatus.setText(str_status);
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    public void onSuccess(Object data) {
        if(REQUEST_TYEP.equals(rType)){
            orderDetail = (OrderDetail) data;
            if(null!=orderDetail){
                mOrderDetailGoodsDatas = orderDetail.getOdlist();
                for (int i = 0; i < mOrderGoodsDatas.size() ; i++) {
                    mOrderDetailGoodsDatas.get(i).setImgurl(mOrderGoodsDatas.get(i).getImgurl());
                }
                mAdapter.updateData(mOrderDetailGoodsDatas);
                updateView();
            }else{
                ToastUtil.showShort("orderDetail == null");
            }
        }
    }

    @Override
    public void onFailureMessage(String msg) {

    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void showOrderList(List<OrderBean> goodsBeanList) {

    }

    @Override
    public void updateOrderList() {

    }


    @OnClick(R.id.tv_buy_again)
    public void onViewClicked() {
        ToastUtil.showShort("再次购买逻辑未确认！");
    }
}
