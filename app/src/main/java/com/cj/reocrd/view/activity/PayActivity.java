package com.cj.reocrd.view.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.ApiCallback;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.model.ApiModel;
import com.cj.reocrd.utils.ConstantsUtils;
import com.cj.reocrd.utils.ToastUtil;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class PayActivity extends BaseActivity {

    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.tv_order_price)
    TextView tvOrderPrice;
    @BindView(R.id.rb_pay_alipay)
    RadioButton rbPayAlipay;
    @BindView(R.id.rb_wechat_pay)
    RadioButton rbWechatPay;
    @BindView(R.id.rb_pay_other)
    RadioButton rbPayOther;
    @BindView(R.id.tv_order_over_payway)
    TextView tvOrderOverPayway;
    @BindView(R.id.tv_order_over_pirce)
    TextView tvOrderOverPirce;
    @BindView(R.id.tv_order_over_youhui)
    TextView tvOrderOverYouhui;
    @BindView(R.id.rl_order_pay_over)
    RelativeLayout rlOrderPayOver;
    @BindView(R.id.tv_time)
    TextView tvTime;

    private String oid,orderPrice,payWay;
    private final String TYPE_ALIPAY = "1";
    private final String TYPE_WECHAT = "2";
    private final String TYPE_YUER = "3";  // 余e
    public static final String BUNDLE_KEY_OID = "oid";
    public static final String BUNDLE_KEY_PRICE= "price";

    private CountDownTimer countDownTimer;
    private long countTime = 30*60*1000;
    private int time_M = 29;
    private int time_S = 59;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay;
    }

    @Override
    public void initData() {
        super.initData();
        oid = getIntent().getStringExtra(BUNDLE_KEY_OID);
        orderPrice = getIntent().getStringExtra(BUNDLE_KEY_PRICE);

    }

    @Override
    public void initView() {
        //
        titleCenter.setText("支付");
        tvOrderPrice.setText(ConstantsUtils.RMB + orderPrice);
        countDownTimer = new CountDownTimer(countTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time_S = time_S-1;
                if(time_S == 0){
                    time_S = 59;
                    time_M = time_M-1;
                }
                if(time_M == 0){
                    countDownTimer.cancel();
                    tvTime.setText("00:00");
                }else{
                    tvTime.setText(time_M+":"+time_S);
                }
            }

            @Override
            public void onFinish() {
                tvTime.setText("订单超时");
            }
        };

        countDownTimer.start();
    }

    @Override
    public void initPresenter() {

    }


    @OnClick({R.id.title_left, R.id.rb_pay_alipay, R.id.rb_wechat_pay, R.id.rb_pay_other
            ,R.id.tv_btn_order_pay,R.id.tv_check_order_detail,R.id.title_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_right:
            case R.id.title_left:
                finish();
                break;
            case R.id.rb_pay_alipay:
                payWay = TYPE_ALIPAY;
                rbPayAlipay.setChecked(true);
                rbPayOther.setChecked(false);
                rbWechatPay.setChecked(false);
                break;
            case R.id.rb_wechat_pay:
                payWay = TYPE_WECHAT;
                rbPayAlipay.setChecked(false);
                rbPayOther.setChecked(false);
                rbWechatPay.setChecked(true);
                break;
            case R.id.rb_pay_other:
                payWay = TYPE_YUER;
                rbPayAlipay.setChecked(false);
                rbPayOther.setChecked(true);
                rbWechatPay.setChecked(false);
                break;
            case R.id.tv_btn_order_pay:
                //todo  发起第三方支付未申请，暂时直接调用支付成功接口
                if(TextUtils.isEmpty(payWay)){
                    ToastUtil.showShort("请先选择支付方式");
                }else{
                    sendPaySuccess();
                }
                break;
            case R.id.tv_check_order_detail:
                //todo  支付成功后，跳转到订单详情
                if(!TextUtils.isEmpty(oid)){
                    Bundle b = new Bundle();
                    b.putString(OrderDetailActivity.BUNDLE_KEY_OID,oid);
                    startActivity(OrderDetailActivity.class,b);
                }
                break;
            default:
                break;
        }
    }


    /**
     * orderid 订单id(如果有多个订单 用逗号分割)
     uid     用户ID
     payType  1支付宝 2微信 3余额
     */
    private void sendPaySuccess() {
        HashMap<String ,Object> map = new HashMap<>();
        map.put("orderid",oid);
        map.put("uid",uid);
        map.put("payType",payWay);
        ApiModel.getInstance().getData(UrlConstants.UrLType.URL_OREDER_PAY_SUCCESS, map, null, new ApiCallback() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                //payType	1支付宝 2微信 3余额
                // sumAmount	金额
                // 展示支付成功view，
                ToastUtil.showShort(apiResponse.getMessage());
                if(UrlConstants.SUCCESE_CODE.equals(apiResponse.getMessage())){
                    showPayOverView();
                }
            }
            @Override
            public void onFailure(Call call, Throwable t) {
                rlOrderPayOver.setVisibility(View.GONE);
            }
        });
    }

    private void showPayOverView(){
        titleLeft.setVisibility(View.GONE);
        titleRight.setVisibility(View.VISIBLE);
        titleRight.setText("完成");
        titleCenter.setText("支付成功");
        if(TYPE_ALIPAY.equals(payWay)){
            tvOrderOverPayway.setText("支付方式:支付宝");
        }else if(TYPE_WECHAT.equals(payWay)){
            tvOrderOverPayway.setText("支付方式:微信");
        }else{
            tvOrderOverPayway.setText("支付方式:余额");
        }
        tvOrderOverPirce.setText("支付金额:"+orderPrice);
        rlOrderPayOver.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
    }
}
