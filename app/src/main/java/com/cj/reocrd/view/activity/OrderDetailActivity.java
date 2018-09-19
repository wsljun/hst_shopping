package com.cj.reocrd.view.activity;


import android.content.Context;
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
import com.cj.reocrd.view.adapter.OrderDetailAdapter;
import com.cj.reocrd.view.adapter.OrderExpinInfoAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;
import static com.cj.reocrd.api.UrlConstants.TYPE_ALIPAY;
import static com.cj.reocrd.api.UrlConstants.TYPE_DZB;
import static com.cj.reocrd.api.UrlConstants.TYPE_JIFEN;
import static com.cj.reocrd.api.UrlConstants.TYPE_WECHAT;
import static com.cj.reocrd.api.UrlConstants.TYPE_YUER;
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
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.rl_expinInfos)
    RecyclerView rlExpinInfos;
    @BindView(R.id.tv_exp_time)
    TextView tvExpTime;

    private String oid;
    public static final String BUNDLE_KEY_OID = "oid";
    public static final String REQUEST_TYEP = "ORDER_DETAIL";
    private String rType = "";
    private OrderDetail orderDetail;
    private OrderDetailAdapter mAdapter;
    private OrderExpinInfoAdapter mExpinInfoAdapter;
    private List<OrderBean.OdlistBean> mOrderDetailGoodsDatas;
    private List<OrderDetail.ExpinfoBean.DataBean> expinfos ; // 快递信息

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initData() {
        oid = getIntent().getStringExtra(BUNDLE_KEY_OID);
        mOrderDetailGoodsDatas = new ArrayList<>();
        expinfos = new ArrayList<>();
        if (!CollectionUtils.isNullOrEmpty(mOrderGoodsDatas)) {
            for (int i = 0; i < mOrderGoodsDatas.size(); i++) {
                mOrderDetailGoodsDatas.addAll(mOrderGoodsDatas);
            }
        }
    }

    @Override
    public void initView() {
        titleCenter.setText("订单详情");
        mAdapter = new OrderDetailAdapter(this, mOrderDetailGoodsDatas);
        mExpinInfoAdapter = new OrderExpinInfoAdapter(R.layout.item_order_expininfos,expinfos);
        // 订单中 不同商品图片展示
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(VERTICAL);
        rlGoodsImg.setLayoutManager(linearLayoutManager);
        LinearLayoutManager layoutManager =  new LinearLayoutManager(this);
        layoutManager.setOrientation(VERTICAL);
        rlExpinInfos.setLayoutManager(layoutManager);
        //设置适配器
        rlGoodsImg.setAdapter(mAdapter);
        rlExpinInfos.setAdapter(mExpinInfoAdapter);
        if (!TextUtils.isEmpty(oid)) {
            rType = REQUEST_TYEP;
            mPresenter.getOrderDetail(oid);
        }
    }

    private void updateView() {
        if (null == orderDetail) {
            return;
        }
        tvOrderId.setText("订单编号：" + orderDetail.getSn());
        tvGoodsPrice.setText(ConstantsUtils.RMB + orderDetail.getAmount());
        tvFreight.setText(ConstantsUtils.RMB + orderDetail.getExamount());
        tvTotalPrice.setText(ConstantsUtils.RMB + orderDetail.getAllamount());
        tvConsignee.setText("收货人：" + orderDetail.getConsignee());
        tvAddress.setText("收货地址：" + orderDetail.getFuladdress());
        tvSubmitOrderTime.setText("下单时间：" + orderDetail.getCreatetime());
//        tvArriveTime.setText("预计送达：" + null);
        String exp = orderDetail.getExpresstime();
        if("1".equals(exp)){
            tvExpTime.setText("送货时间：仅工作日配送");
        }else if("2".equals(exp)){
            tvExpTime.setText("送货时间：仅周六日配送");
        }else{
            tvExpTime.setText("送货时间：周一到周日皆可配送");
        }
        setPayWay(orderDetail.getWay());
        setOrderStatus(orderDetail.getStatus());
    }

    /**
     * @param s
     */
    private void setPayWay(String s) {
        String way = "";
        switch (s){
            case TYPE_ALIPAY:
                way = "支付宝";
                break;
            case TYPE_WECHAT:
                way = "微信";
                break;
            case TYPE_YUER:
                way = "余额";
                break;
            case TYPE_JIFEN:
                way = "消费积分";
                break;
            case TYPE_DZB:
                way = "电子币";
                break;
            default:
                way = "暂无";
                break;
        }
        tvPayWay.setText("支付方式：" +way );
    }

    /**
     *    1.未付款   2 待发货  3待收货，4待评价 5完成  6退货中 7 退货完成 8自行取消 9退货被拒绝
     * @param s
     */
    private void setOrderStatus(String s) {
        int status = Integer.parseInt(s);
        boolean isExpinInfo = true;
        String str_status = "";
        switch (status) {
            case OrderActivity.ORDER_STATUS_PAY:
                str_status = "待付款";
                isExpinInfo = false;
                break;
            case OrderActivity.ORDER_STATUS_SEND:
                str_status = "待发货";
                isExpinInfo = false;
                break;
            case OrderActivity.ORDER_STATUS_CONFIM:
                str_status = "待确认";
                break;
            case OrderActivity.ORDER_STATUS_EVALUATE:
                str_status = "待评价";
                break;
            case OrderActivity.ORDER_STATUS_OVER:
                str_status = "已完成";
                break;
            case OrderActivity.ORDER_STATUS_REFUNDING:
                str_status = "退款中";
                break;
            case OrderActivity.ORDER_STATUS_REFUNDOVER:
                str_status = "退款完成";
                break;
            case OrderActivity.ORDER_STATUS_CANCLE:
                str_status = "已取消";
                break;
            case OrderActivity.ORDER_STATUS_NOT_REFUND:
                str_status = "退款被拒绝";
                break;
            default:
                break;
        }
        tvOrderStatus.setText(str_status);
        if(!CollectionUtils.isNullOrEmpty(orderDetail.getExpinfo().getData())
                && isExpinInfo){
            expinfos = orderDetail.getExpinfo().getData();
            rlExpinInfos.setVisibility(View.VISIBLE);
            mExpinInfoAdapter.setNewData(expinfos);
        }
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    public void onSuccess(Object data) {
        if (REQUEST_TYEP.equals(rType)) {
            orderDetail = (OrderDetail) data;
            if (null != orderDetail) {
                mOrderDetailGoodsDatas = orderDetail.getOdlist();
                mAdapter.updateData(orderDetail.getOdlist());
                updateView();
            } else {
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

    @OnClick({R.id.title_left, R.id.tv_buy_again})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.tv_buy_again:

                break;
            default:
                break;
        }
    }
}
