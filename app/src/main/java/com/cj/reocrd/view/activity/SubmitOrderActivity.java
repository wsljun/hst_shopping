package com.cj.reocrd.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.model.entity.GoodsDetailsBean;
import com.cj.reocrd.model.entity.OrderBean;
import com.cj.reocrd.utils.ImageLoaderUtils;
import com.cj.reocrd.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SubmitOrderActivity extends BaseActivity {
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.address_consignee_phone)
    TextView addressConsigneePhone;
    @BindView(R.id.address_detail)
    TextView addressDetail;
    @BindView(R.id.rl_address)
    RelativeLayout rlAddress;
    @BindView(R.id.tv_goods_num)
    TextView tvGoodsNum;
    @BindView(R.id.iv_goods_img)
    ImageView ivGoodsImg;
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
    private OrderBean orderBean;
    private String consignee ;
    private String phone;
    private String addressDetital;
    private String totalPrice;
    private GoodsDetailsBean goodsDetailsBean;


    @Override
    public int getLayoutId() {
        return R.layout.activity_submit_order;
    }

    @Override
    public void initData() {
        super.initData();
//        orderBean = getIntent().getParcelableExtra("orderBean");
//        goodsDetailsBean = getIntent().getParcelableExtra("goodsDetails");
    }

    @Override
    public void initView() {
        titleCenter.setText("提交订单");
//        addressConsigneePhone.setText(orderBean.getConsignee()+" "+orderBean.getPhone());
//        addressDetail.setText(orderBean.getFuladdress());
//        tvGoodsPrice.setText(goodsDetailsBean.getPrice());
//        goodTotalPrice.setText(goodsDetailsBean.getPrice());
//        ImageLoaderUtils.display(this,ivGoodsImg, UrlConstants.BASE_URL+goodsDetailsBean.getImgurl());
    }

    @Override
    public void initPresenter() {
    }




    @OnClick({R.id.rl_address, R.id.tv_submit_order})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_address:
//                startActivityForResult(AddressActivity.class,1);
                ToastUtil.showShort("修改地址");
                break;
            case R.id.tv_submit_order:
                ToastUtil.showShort("提交订单");
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
