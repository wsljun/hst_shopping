package com.cj.reocrd.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.contract.SubmitOrderContract;
import com.cj.reocrd.model.entity.GoodsDetailsBean;
import com.cj.reocrd.model.entity.OrderBean;
import com.cj.reocrd.model.entity.OrderDetail;
import com.cj.reocrd.presenter.SubmitOrderPresenter;
import com.cj.reocrd.utils.SPUtils;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.view.adapter.SubmitOrderAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.cj.reocrd.view.fragment.CartFragment.cartGoodsImgs;

public class SubmitOrderActivity extends BaseActivity<SubmitOrderPresenter> implements SubmitOrderContract.View {
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.address_consignee_phone)
    TextView tv_AddressConsigneePhone;
    @BindView(R.id.address_detail)
    TextView tv_AddressDetail;
    @BindView(R.id.rl_address)
    RelativeLayout rlAddress;
    @BindView(R.id.tv_goods_num)
    TextView tvGoodsNum;
    //    @BindView(R.id.iv_goods_img)
//    ImageView ivGoodsImg;
    @BindView(R.id.tv_goods_price)
    TextView tvGoodsPrice;
    @BindView(R.id.tv_freight)
    TextView tvFreight;
    @BindView(R.id.rl_goods_info)
    RelativeLayout rlGoodsInfo;
    @BindView(R.id.et_leave_message)
    EditText etLeaveMessage;
    @BindView(R.id.good_total_price)
    TextView goodTotalPrice;
    @BindView(R.id.tv_submit_order)
    TextView tvSubmitOrder;
    @BindView(R.id.fl_address)
    FrameLayout flAddress;
    @BindView(R.id.rl_goods_img)
    RecyclerView rlGoodsImg;
    @BindView(R.id.rg_exp_time)
    RadioGroup rgExpTime;

    private OrderBean orderBean;
    private String consignee;
    private String phone;
    private String addressDetital;
    private String totalPrice = "";
    private GoodsDetailsBean goodsDetails;
    private String oid, aid;
    private OrderDetail orderDetail;
    private String type;
    public static final String TYPE_FOR_DETAIL = "1";
    public static final String TYPE_FOR_CART = "2";
    public static final String TYPE_FOR_PAYING = "3"; // 代付款

    public static final String BUNDLE_KEY_OID = "oid";
    public static final String BUNDLE_KEY_TYPE = "type";
    public static final String BUNDLE_KEY_TOTALPRICE = "total_rice";
    public static final String BUNDLE_KEY_PHONE = "phone";
    public static final String BUNDLE_KEY_ADDRESS_DETITAL = "address";
    public static final String BUNDLE_KEY_CONSIGNEE = "consignee";
    public static final String TYPE_SUBMITORDER = "submitorder";
    private List<String> imgURls;
    private int goodsNum = 0;
    private String requestType = "";
    private static final String TYPE_SUBMIT = "SUBMIT_ORDER";
    private SubmitOrderAdapter mAdapter;
    private final String exp_time_1 = "1";// 工作日
    private final String exp_time_2 = "2";// 周六日
    private final String exp_time_3 = "3";// 全部时间
    private String expTime  = exp_time_3;

    @Override
    public int getLayoutId() {
        return R.layout.activity_submit_order;
    }

    @Override
    public void initData() {
        super.initData();
//        orderBean = getIntent().getParcelableExtra("orderBean");
        oid = getIntent().getStringExtra(BUNDLE_KEY_OID); // 511cbafb-1b74-476d-a3bf-05908d3a0f21
        type = getIntent().getStringExtra(BUNDLE_KEY_TYPE);
//        totalPrice = getIntent().getStringExtra(BUNDLE_KEY_TOTALPRICE);
        // init photo list
        imgURls = new ArrayList<>();
        if (TYPE_FOR_DETAIL.equals(type)) {
            goodsNum = 1;
            if (null == goodsDetails) {
                goodsDetails = GoodDetailActivity.goodsDetailsBean;
                imgURls.add(goodsDetails.getImgurl());
            }
        }
        if (TYPE_FOR_CART.equals(type)) {
            goodsNum = cartGoodsImgs.size();
            imgURls.addAll(cartGoodsImgs);
        }
//        if (TYPE_FOR_PAYING.equals(type)) {
//            goodsNum = OrderActivity.odlist.size();
//            for (int i = 0; i <OrderActivity.odlist.size() ; i++) {
//                imgURls.add(OrderActivity.odlist.get(i).getImgurl());
//            }
//        }
        mPresenter.getOrderDetail(oid);
    }

    @Override
    public void initView() {
        titleCenter.setText("提交订单");
        updateAddress();
//        if (null != goodsDetails) {
        tvGoodsNum.setText(goodsNum + "件商品");
//        tvGoodsPrice.setText("￥"+totalPrice);
//        goodTotalPrice.setText("￥"+totalPrice);
//        tvFreight.setText("￥");
//            ImageLoaderUtils.display(this, ivGoodsImg, UrlConstants.BASE_URL + goodsDetails.getImgurl());
//        }

        // 订单中 不同商品图片展示
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rlGoodsImg.setLayoutManager(linearLayoutManager);
        mAdapter = new SubmitOrderAdapter(mContext, imgURls);
        rlGoodsImg.setAdapter(mAdapter);
        rgExpTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_1:
                        expTime = exp_time_1;
                        break;
                    case R.id.rb_2:
                        expTime = exp_time_2;
                        break;
                    case R.id.rb_3:
                        expTime = exp_time_3;
                        break;
                }
//                ToastUtil.showShort(expTime);// TODO: 2018/9/14  422 更新送货时间
                mPresenter.updateExpTimr(oid,expTime);
            }
        });
    }

    private void updateAddress() {
        aid = (String) SPUtils.get(mContext, SPUtils.SpKey.DEFAULT_ADDRESS_ID, "");//13741f0b-4dc7-4cde-9221-5f13db9f010c
        consignee = (String) SPUtils.get(mContext, SPUtils.SpKey.DEFAULT_ADDRESS_CONSIGNEE, "");
        phone = (String) SPUtils.get(mContext, SPUtils.SpKey.DEFAULT_ADDRESS_PHONE, "");
        addressDetital = (String) SPUtils.get(mContext, SPUtils.SpKey.DEFAULT_ADDRESS_DETAIL, "");
        if (TextUtils.isEmpty(aid)) {
            flAddress.setVisibility(View.VISIBLE);
            rlAddress.setVisibility(View.INVISIBLE);
        } else {
            tv_AddressConsigneePhone.setText(consignee + " " + phone);
            tv_AddressDetail.setText(addressDetital);
            flAddress.setVisibility(View.INVISIBLE);
            rlAddress.setVisibility(View.VISIBLE);
        }
        requestType = TYPE_SUBMIT;
        mPresenter.updateOrderAddress(oid, aid);
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
    }


    @OnClick({R.id.title_left, R.id.rl_address, R.id.tv_submit_order, R.id.fl_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.rl_address:
            case R.id.fl_address:
                Bundle bundle = new Bundle();
                bundle.putString("type", TYPE_SUBMITORDER);
                startActivityForResult(AddressActivity.class, bundle, 1);
                break;
            case R.id.tv_submit_order:
                if (TextUtils.isEmpty(aid)) {
                    ToastUtil.showShort("请添加收货地址");
                    return;
                }
                Bundle b = new Bundle();
                b.putString(PayActivity.BUNDLE_KEY_OID, oid);
                b.putString(PayActivity.BUNDLE_KEY_PRICE, totalPrice);
                startActivity(PayActivity.class, b);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case RESULT_OK:
                Bundle b = data.getExtras(); //data为B中回传的Intent
                String str = b.getString("aid");//str即为回传的值
                updateAddress();
                break;
            default:
                break;
        }
    }

    @Override
    public void onSuccess(Object data) {
        if (requestType.equals(TYPE_SUBMIT)) {
//            todo 去支付
//            finish();
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
    public void updateOrderInfo(OrderDetail orderDetail) {
//        for (int i = 0; i < orderDetail.getOdlist().size(); i++) {
//            imgURls.add(orderDetail.getOdlist().get(i).getImgurl());
//        }
//        mAdapter.notifyDataSetChanged();
//        goodsNum = imgURls.size();
//        tvGoodsNum.setText(goodsNum + "件商品");
        totalPrice = orderDetail.getAllamount();
        tvGoodsPrice.setText("￥"+orderDetail.getAmount());
        goodTotalPrice.setText("￥"+totalPrice);
        tvFreight.setText("￥"+orderDetail.getExamount());
    }

}
