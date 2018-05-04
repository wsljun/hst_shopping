package com.cj.reocrd.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.cj.reocrd.R;
import com.cj.reocrd.api.ApiCallback;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.model.ApiModel;
import com.cj.reocrd.model.entity.UserBean;
import com.cj.reocrd.utils.ConstantsUtils;
import com.cj.reocrd.utils.LogUtil;
import com.cj.reocrd.utils.alipay.OrderInfoUtil2_0;
import com.cj.reocrd.utils.SPUtils;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.utils.alipay.PayResult;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;

import static android.provider.Telephony.Mms.Part.CHARSET;
import static android.provider.UserDictionary.Words.APP_ID;

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

    private String oid, orderPrice, payWay;
    private final String TYPE_ALIPAY = "1";
    private final String TYPE_WECHAT = "2";
    private final String TYPE_YUER = "3";  // 余e
    public static final String BUNDLE_KEY_OID = "oid";
    public static final String BUNDLE_KEY_PRICE = "price";

    private CountDownTimer countDownTimer;
    private long countTime = 30 * 60 * 1000;
    private int time_M = 29;
    private int time_S = 59;
    public static final String APPID = "2016091500515912";//正式："2018042902608871";
    private static final int SDK_PAY_FLAG = 1001;
    private String RSA_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCujCT3HUSt023ik5SG6P9cEt9DwR8iicoOS6DLNdXnTXVQc5e8fumJszwfME+FnhYwUy0nInFhKNqUSGB7fffhDLa4AKOf+JlNg0O2D2TNlhWdmEC0DWIDo939JjgoJJbasYwgVjlwSNLuJCSFaAMmF0vW0QJnNxr2YyW6zpj3P8joWw3FUOnA9Ma5Ipce0KxviAcRiMKIjkWpUsRbqdl0vzkuAzbfZ6lWZut0QW1HYINQ35M8SzgIvrJ/fu0C3BTE3FGjXJ9YvLUZLPujtrdJoILEriF/GbwT6Wyt5IFMUqS+In9fLaUsypJGPKWQeTo1Bl6uDpYgPlLsxeHUFGwnAgMBAAECggEAD+D78+cbulyDoJsgZe2IXY3x9a358SBr20USCaqKHRK0227InQNGp5pAm+2/Lj6lchv+/ZJqkhYvXFIBRPq+tCB99ttIB2OMPrr0S/m2h+VhbgbNzBNzmTUtGryBW1WIy7hfMQnnC8n0NmnfUKuXrb1szswQ1ebzcvquEgJ9c/CY0MSV/uF7qUbTybbWEnsiSobcgk+0tpjwpdVZixc0YW/utIQ50KwGqRcNkFW89JKLR3La9167B3gh1M0Qd12B/K7d1OfOHRrTIiCcUNM5xONnvzfoXsXwUwKxwgd57lpbfAmFFzx0i5LsruBRV5fN4NShlcusai1vWIV1Sc85AQKBgQDuD7c03cBQyg5arVD3rQw7zEKJF30hXADyiQKKM+Yivz2Ca0dkc8XBvsiRuZ01wHwzJV74QfCgW1gI/MG3ozWuK1NVqRuhtCPKa1e8g2bXQ8aNjTm43sDpJjT/cXE7gWC5TU5a+MPOusEi12gPwB2jyH1mVO1a7pygSw9NoAwJwQKBgQC7szknrUbOQvdhScue32F57VWBtHnEHO/xtU97JT3wkA0AKprqr2V5yxJBtbDJE1KV/Ql50dU1QONiyy5XGt0L57uLOwVZJiVQ1QEGYrb89WX/ENMmIM5XvWXm5KF57XSLwa6QvqD68ShtCXPKzdFvEJ/hqIMfSkjr0+q27dlf5wKBgQChJ5hhPGBtEGtpLPyrvcSTUnIUNgdqJsspZGCIyBtZmFZ+TDs3IDxWLN2/8IQhGB27zeedbZ4EH/HXm6tTsjOrpP6z9VeUPssfw0zQe6+JuALYXiseIAU9j7S+27/IhYvBxThu0wpSjxAhkuqKBwbJkfZ56LIJJZB33ngMl3SuwQKBgF78Uocmo/dLYj7khluutXhrR2Ms/pXlAvPxifhKn262py2XYfAztImO331LQdxvn9JxS7NPIbMiVC+xnJDN8mDM81aBFrLHoAio0iz2FZ4rXWNXk1LvNRynpwLSXnkCVoFHEV0Eaxiyo3+SUfumbj7jTdV3XmuePwrAoj3SXbevAoGBAMeirfCernEo5HYsSP67KWKpFaRnCLR9kCkevxb5wNNC5gAPC+bv9nf4VfEcCmWEzTD3OCDIB9dtE3B6cvOLD/hw6HN/U6moE2HlYDGe0gkAdy49TohJ19khQwFVow7ayllBw7RCAq4soWKODaONm3NNRvuYfgvbav7aMgbyqmSV";
    private   String  aoid;
    //"MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJmhD7W6lp8OrP9bsFGxDOICdhdgEKCCYxZg2Sk/x0rXvMdJTwI1EFHU0YENw5aw7mJaajtDLtKetvDe1qvqKrkqSmxGq+jwE1Py7hqMa+qwOG46fZCxbG0g+E4RXt75hHFtlQr6uQDAFD0LgP+ob2QzM2c+XgOZ5fMpoJtrLCaJAgMBAAECgYBytFKqeRokwCxirO7Ez9CynuvVICDJ9xBMkRsTNU9zjgihMxcOqtf4gVG7ba9vP8N9I8zVWqAHGgG1bmwSB9DFLzb35VIm4gacB1fmHbZdE3CTPmkToeqPJXtz/MUWREp/Mj1MkIvF7JevlV1JIgqcCVP1XeAgMDdCdBPhJPXVQQJBANG+KMmx6jT1Bbw6TsGu5IqLr7YZCQz2psaqDjiwkvdLqtfi4u18q5pxxSGA9IuJLbhhuFy6tFCAs2ZmsPuvu/UCQQC7gsnRLROoMUvmyFFcTdhe0Vo4J2mN/nGJEFxOU6rHpKLbxyGuJEDt+zgKE6OR0dxjrICvl/o3dsWde46v55fFAkBF9ChibOZyVy5cgw31Z0FuO/yUvXDZVAJ3zAwGOE4sEJ6gdlm9X5FUZ5GaBQqK52vKTT1SICs3llU/WAy7RPdpAkEApgYQgWgDDpnPVaCqAStf19xPoIas/C99AVn+ENAd1zTbifMPzCuHXe9gV/O5kUQaehpjupW6018Ta/PZi8Bn7QJBAJZ0E6CTkmfdHC6inbqk+7gm1FWIy9qRHjjTG4jzyajmDdTmWc2g3MxDERuYXONLukgdrgmH9cIETH3wPZNaAcw=";

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
                time_S = time_S - 1;
                if (time_S == 0) {
                    time_S = 59;
                    time_M = time_M - 1;
                }
                if (time_M == 0) {
                    countDownTimer.cancel();
                    tvTime.setText("00:00");
                } else if (time_S < 10) {
                    tvTime.setText(time_M + ":" + time_S + "0");
                } else {
                    tvTime.setText(time_M + ":" + time_S);
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
            , R.id.tv_btn_order_pay, R.id.tv_check_order_detail, R.id.title_right})
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
                if (TextUtils.isEmpty(payWay)) {
                    ToastUtil.showShort("请先选择支付方式");
                } else {
                    if (payWay.equals(TYPE_ALIPAY)) {
                        payByAlipay();
                    } else if (payWay.equals(TYPE_WECHAT)) {
                        ToastUtil.showShort("未开通");
                    } else if (payWay.equals(TYPE_YUER)) {
                        inputPwdDialog();
                    }
                }
                break;
            case R.id.tv_check_order_detail:
                //todo  支付成功后，跳转到订单详情
                if (!TextUtils.isEmpty(oid)) {
                    Bundle b = new Bundle();
                    b.putString(OrderDetailActivity.BUNDLE_KEY_OID, oid);
                    startActivity(OrderDetailActivity.class, b);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 支付宝支付
     */
    private void payByAlipay() {
        //秘钥验证的类型 true:RSA2 false:RSA
        boolean rsa = true;
        //构造支付订单参数列表
        aoid = OrderInfoUtil2_0.getOutTradeNo();
        LogUtil.d("alipay","aoid= "+aoid);
        String bizContent = OrderInfoUtil2_0.buildBizConetent(orderPrice,aoid);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa,bizContent);
        //构造支付订单参数信息
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        //对支付参数信息进行签名
        String sign = OrderInfoUtil2_0.getSign(params, RSA_PRIVATE, rsa);
        //订单信息
        final String orderInfo = orderParam + "&" + sign;
        LogUtil.d("alipay",orderInfo);
        //异步处理
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                //新建任务
                PayTask alipay = new PayTask(PayActivity.this);
                //获取支付结果
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    //同步获取结果
                    String resultInfo = payResult.getResult();
                    Log.i("Pay", "Pay:" + resultInfo);
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(PayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        sendPaySuccess();
                    } else {
                        Toast.makeText(PayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    /**
     * 輸入登陸密碼
     */
    private void inputPwdDialog() {
        final EditText inputServer = new EditText(this);
        inputServer.setLines(1);
        inputServer.setMaxEms(18);
        inputServer.setPadding(50, 50, 50, 50);
        inputServer.setFocusable(true);
        inputServer.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("输入登陆密码").setView(inputServer).setNegativeButton(R.string.cancel, null);
        builder.setPositiveButton(R.string.confirm,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String inputName = inputServer.getText().toString();
                        if (TextUtils.isEmpty(inputName)) {
                            ToastUtil.showToastS(PayActivity.this, "请输入登陆密码");
                            return;
                        }
                        doLogin(inputName);
                    }
                });
        builder.show();
    }

    private void doLogin(String password) {
        HashMap<String, Object> map = new HashMap<>();
        String phone = (String) SPUtils.get(this, SPUtils.SpKey.USER_PHONE, "");
        map.put("phone", phone);
        map.put("password", password);
        ApiModel.getInstance().getData(UrlConstants.UrLType.LOGIN_PWD, map, UserBean.class, new ApiCallback<String>() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (null != apiResponse) {
                    if ("1".equals(apiResponse.getStatusCode())) {
                        UserBean userBean = (UserBean) apiResponse.getResults();
                        sendPaySuccess();
                    } else {
                        ToastUtil.showShort(apiResponse.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                ToastUtil.showShort(t.toString());
            }

        });
    }

    /**
     * orderid 订单id(如果有多个订单 用逗号分割)
     * uid     用户ID
     * payType  1支付宝 2微信 3余额
     *  : 余额支付时提示属于支付密码（登陆密码），需先判断余额是否充足（需在本地保存余额）
     */
    private void sendPaySuccess() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("orderid", oid);
        map.put("uid", uid);
        map.put("payType", payWay);
        map.put("aoid", aoid);
        ApiModel.getInstance().getData(UrlConstants.UrLType.URL_OREDER_PAY_SUCCESS, map, null, new ApiCallback() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                //payType	1支付宝 2微信 3余额
                // sumAmount	金额
                // 展示支付成功view，
                ToastUtil.showShort(apiResponse.getMessage());
                if (UrlConstants.SUCCESE_CODE.equals(apiResponse.getStatusCode())) {
                    showPayOverView();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                rlOrderPayOver.setVisibility(View.GONE);
            }
        });
    }

    private void showPayOverView() {
        titleLeft.setVisibility(View.GONE);
        titleRight.setVisibility(View.VISIBLE);
        titleRight.setText("完成");
        titleCenter.setText("支付成功");
        if (TYPE_ALIPAY.equals(payWay)) {
            tvOrderOverPayway.setText("支付方式:支付宝");
        } else if (TYPE_WECHAT.equals(payWay)) {
            tvOrderOverPayway.setText("支付方式:微信");
        } else {
            tvOrderOverPayway.setText("支付方式:余额");
        }
        tvOrderOverPirce.setText("支付金额:" + orderPrice);
        rlOrderPayOver.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
    }


}
