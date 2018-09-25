package com.cj.reocrd.view.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
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
import com.cj.reocrd.base.BaseApplication;
import com.cj.reocrd.contract.MyContract;
import com.cj.reocrd.model.ApiModel;
import com.cj.reocrd.model.entity.PayKeys;
import com.cj.reocrd.model.entity.UserBean;
import com.cj.reocrd.model.entity.WXPayKeys;
import com.cj.reocrd.presenter.MyPrresenter;
import com.cj.reocrd.utils.ConstantsUtils;
import com.cj.reocrd.utils.ImageLoaderUtils;
import com.cj.reocrd.utils.LogUtil;
import com.cj.reocrd.utils.Utils;
import com.cj.reocrd.utils.alipay.OrderInfoUtil2_0;
import com.cj.reocrd.utils.SPUtils;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.utils.alipay.PayResult;
import com.cj.reocrd.view.dialog.LoadingDialog;
import com.cj.reocrd.view.view.verificationCodeView.VerificationCodeView;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;

import static com.cj.reocrd.api.UrlConstants.TYPE_ALIPAY;
import static com.cj.reocrd.api.UrlConstants.TYPE_DZB;
import static com.cj.reocrd.api.UrlConstants.TYPE_JIFEN;
import static com.cj.reocrd.api.UrlConstants.TYPE_WECHAT;
import static com.cj.reocrd.api.UrlConstants.TYPE_YUER;

public class PayActivity extends BaseActivity<MyPrresenter> implements MyContract.View {

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
    @BindView(R.id.rb_pay_jifen)
    RadioButton rbPayJifen;
    @BindView(R.id.rb_pay_dzb)
    RadioButton rbPayDzb;
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
    @BindView(R.id.tv_btn_order_pay)
    TextView tvBtnPay;

    private String oid, orderPrice, payWay;
    public static final String BUNDLE_KEY_OID = "oid";
    public static final String BUNDLE_KEY_PRICE = "price";

    private CountDownTimer countDownTimer;
    private long countTime = 30 * 60 * 1000;
    private int time_M = 29;
    private int time_S = 59;
    public static String APPID = "";
    private static final int SDK_PAY_FLAG = 1001;
    private String RSA_PRIVATE = "";
    private String aoid;
    private int serversLoadTimes = 0;
    private int maxLoadTimes = 3;
    private String phone;
    private String OutTradeNo;
    private ArrayList<RadioButton> rbs;

    private int type = 1;
    private UserBean userBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay;
    }

    @Override
    public void initData() {
        super.initData();
        oid = getIntent().getStringExtra(BUNDLE_KEY_OID);
        orderPrice = getIntent().getStringExtra(BUNDLE_KEY_PRICE);
        phone = (String) SPUtils.get(this, SPUtils.SpKey.USER_PHONE, "");
        getPayKey();
    }

    private void cheeckRadioButtonStatus(int index) {
        if (rbs == null) {
            rbs = new ArrayList<RadioButton>();
            rbs.add(rbPayAlipay);
            rbs.add(rbWechatPay);
            rbs.add(rbPayOther);
            rbs.add(rbPayJifen);
            rbs.add(rbPayDzb);
        }
        for (int i = 0; i < rbs.size(); i++) {
            if (index == i) {
                rbs.get(i).setChecked(true);
            } else {
                rbs.get(i).setChecked(false);
            }
        }
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
        mPresenter.setVM(this);
    }


    @OnClick({R.id.title_left, R.id.rb_pay_alipay, R.id.rb_wechat_pay, R.id.rb_pay_other
            , R.id.tv_btn_order_pay, R.id.tv_check_order_detail, R.id.title_right,
            R.id.rb_pay_jifen, R.id.rb_pay_dzb})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_right:
            case R.id.title_left:
                finish();
                break;
            case R.id.rb_pay_alipay:
                payWay = TYPE_ALIPAY;
                cheeckRadioButtonStatus(0);
                getOutTradeNo();
                break;
            case R.id.rb_wechat_pay:
                payWay = TYPE_WECHAT;
                cheeckRadioButtonStatus(1);
                getOutTradeNo();
                break;
            case R.id.rb_pay_other:
                payWay = TYPE_YUER;
                cheeckRadioButtonStatus(2);
                getOutTradeNo();
                break;
            case R.id.rb_pay_jifen:
                payWay = TYPE_JIFEN;
                cheeckRadioButtonStatus(3);
                break;
            case R.id.rb_pay_dzb:
                payWay = TYPE_DZB;
                cheeckRadioButtonStatus(4);
                break;
            case R.id.tv_btn_order_pay:
                if (TextUtils.isEmpty(payWay)) {
                    ToastUtil.showShort("请先选择支付方式");
                } else {
                    if (payWay.equals(TYPE_ALIPAY)) {
                        payByAlipay();
                    } else if (payWay.equals(TYPE_WECHAT)) {
                        getWXOrder();
                    } else { // 去除 支付宝，微信 支付
//                        inputPwdDialog();
                        //请求个人信息
                        type = 1;
                        mPresenter.getMYHome(UrlConstants.UrLType.MY_HOME, uid);
                    }
                }
                break;
            case R.id.tv_check_order_detail:
                //  支付成功后，跳转到订单详情
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
        aoid = OutTradeNo;//OrderInfoUtil2_0.getOutTradeNo();
        LogUtil.d("alipay", "aoid= " + aoid);
        String bizContent = OrderInfoUtil2_0.buildBizConetent(orderPrice, aoid);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa, bizContent);
        //构造支付订单参数信息
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        //对支付参数信息进行签名
        String sign = OrderInfoUtil2_0.getSign(params, RSA_PRIVATE, rsa);
        //订单信息
        final String orderInfo = orderParam + "&" + sign;
        LogUtil.d("alipay", orderInfo);
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
                        tvBtnPay.setClickable(false);
                        showPayOverView();
//                        sendPaySuccess();
                    } else {
//                        Toast.makeText(PayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                        tvBtnPay.setClickable(true);
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
        map.put("phone", phone);
        map.put("password", password);
        ApiModel.getInstance().getData(UrlConstants.UrLType.LOGIN_PWD, map, UserBean.class, new ApiCallback<String>() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (null != apiResponse) {
                    if ("1".equals(apiResponse.getStatusCode())) {
                        UserBean userBean = (UserBean) apiResponse.getResults();
                        tvBtnPay.setClickable(false);
                        sendPaySuccess();
                    } else {
                        tvBtnPay.setClickable(true);
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
     * : 余额支付时提示属于支付密码（登陆密码），需先判断余额是否充足（需在本地保存余额）
     */
    private void sendPaySuccess() {
        LoadingDialog.showDialogForLoading(PayActivity.this, "正在同步支付信息", false);
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
                LoadingDialog.cancelDialogForLoading();
                if (UrlConstants.SUCCESE_CODE.equals(apiResponse.getStatusCode())) {
                    Toast.makeText(PayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    showPayOverView();
                } else {
                    ToastUtil.showShort(apiResponse.getMessage());
                    tvBtnPay.setClickable(true);
                }
            }

            @Override
            public void onFailure(Call call, Throwable e) {
                //如果超时并未超过指定次数，则重新连接
                LoadingDialog.cancelDialogForLoading();
                if (e.toString().equals("java.net.SocketTimeoutException")
                        && serversLoadTimes < maxLoadTimes) {
                    serversLoadTimes++;
                    LogUtil.e("onFailure", "serversLoadTimes= " + serversLoadTimes);
                    sendPaySuccess();
                }
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
        } else if (TYPE_YUER.equals(payWay)) {
            tvOrderOverPayway.setText("支付方式:余额");
        } else if (TYPE_JIFEN.equals(payWay)) {
            tvOrderOverPayway.setText("支付方式:消费积分");
        } else if (TYPE_DZB.equals(payWay)) {
            tvOrderOverPayway.setText("支付方式:电子币");
        }
        setPayWay(payWay);

        tvOrderOverPirce.setText("支付金额:" + orderPrice);
        rlOrderPayOver.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
    }


    private void getPayKey() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        ApiModel.getInstance().getData(UrlConstants.UrLType.URL_OREDER_PAY_KEY, map, PayKeys.class, new ApiCallback() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (UrlConstants.SUCCESE_CODE.equals(apiResponse.getStatusCode())) {
                    PayKeys payKeys = (PayKeys) apiResponse.getResults();
                    if (!TextUtils.isEmpty(payKeys.getAppid())) {
                        APPID = payKeys.getAppid();
                    }
                    if (!TextUtils.isEmpty(payKeys.getPrivatekey())) {
                        RSA_PRIVATE = payKeys.getPrivatekey();
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;
    }


    private void getOutTradeNo() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("orderid", oid);
        map.put("payType", payWay);
        map.put("phone", phone);
        ApiModel.getInstance().getData(UrlConstants.UrLType.URL_OUT_TRADE_NO, map, PayKeys.class, new ApiCallback() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (UrlConstants.SUCCESE_CODE.equals(apiResponse.getStatusCode())) {
                    PayKeys payKeys = (PayKeys) apiResponse.getResults();
                    if (!TextUtils.isEmpty(payKeys.getOutTradeNo())) {
                        OutTradeNo = payKeys.getOutTradeNo();
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
            }
        });
    }

    private void setPayWay(String s) {
        String way = "";
        switch (s) {
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
                break;
        }
        tvOrderOverPayway.setText("支付方式：" + way);
    }

    private void getWXOrder() {
        String totalfen = Utils.changeY2F(orderPrice);
        HashMap<String, Object> map = new HashMap<>();
        String ip = Utils.getLocalIPAddress();
        map.put("out_trade_no", OutTradeNo);
        map.put("totalFee", totalfen);
        map.put("spbill_create_ip", ip);
        ApiModel.getInstance().getData(UrlConstants.UrLType.URL_WX_ORDER, map, WXPayKeys.class, new ApiCallback() {

            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (UrlConstants.SUCCESE_CODE.equals(apiResponse.getStatusCode())) {
                    WXPayKeys wxPayKeys = (WXPayKeys) apiResponse.getResults();
                    payWX(wxPayKeys);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
            }
        });
    }

    PayReq req;

    private void payWX(WXPayKeys wxPayKeys) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (null != wxPayKeys) {
                    req = new PayReq();
                    //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
                    req.appId = wxPayKeys.getAppid();//json.getString("appid");
                    req.partnerId = wxPayKeys.getPartnerid();//;json.getString("partnerid");
                    req.prepayId = wxPayKeys.getPrepayid();//json.getString("prepayid");
                    req.nonceStr = wxPayKeys.getNoncestr();//json.getString("noncestr");
                    req.timeStamp = wxPayKeys.getTimestamp();//json.getString("timestamp");
                    req.packageValue = "Sign=WXPay";//wxPayKeys.getPack_age();//json.getString("package");
                    req.sign = wxPayKeys.getSign();//.getString("sign");
                    req.extData = "app data"; // optional
//                    ToastUtil.shortToastInBackgroundThread(PayActivity.this, "正常调起支付");
                    toPay();
                } else {
                    ToastUtil.shortToastInBackgroundThread(PayActivity.this, "返回错误" + wxPayKeys.getMessage());
                }
            }
        }).start();

    }

    private void toPay() {
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        IWXAPI wxApi = WXAPIFactory.createWXAPI(this.getApplicationContext(), null);
        wxApi.registerApp(BaseApplication.APP_ID);
        boolean b = wxApi.sendReq(req);
//        if(b){
//            ToastUtil.shortToastInBackgroundThread(PayActivity.this,"请求支付成功！");
//        }else{
//            ToastUtil.shortToastInBackgroundThread(PayActivity.this,"请求支付失败！");
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (UrlConstants.WXPAY_CODE == 0) {
            UrlConstants.WXPAY_CODE = 999;
            showPayOverView();
        }
    }

    @Override
    public void onSuccess(Object data) {
        ApiResponse response = (ApiResponse) data;
        switch (type) {
            case 1:
                if ("1".equals(response.getStatusCode())) {
                    userBean = (UserBean) response.getResults();
                    if (userBean != null && !TextUtils.isEmpty(userBean.getIspaypwd())) {
                        showPWDDialog();
                    }
                }
                break;
            case 2:
                if ("1".equals(response.getStatusCode())) {
//                    Bundle bd = new Bundle();
//                    bd.putSerializable("user", userBean);
//                    startActivity(MyMoneyActivity.class, bd);
                    tvBtnPay.setClickable(false);
                    sendPaySuccess();
                } else {
                    tvBtnPay.setClickable(true);
                    ToastUtil.showToastS(this, response.getMessage());
                }
                break;
        }
    }

    @Override
    public void onFailureMessage(String msg) {
        ToastUtil.showShort(msg);
    }

    @Override
    public Context getContext() {
        return this;
    }

    //二级密码对话框
    public void showPWDDialog() {
        if ("1".equals(userBean.getIspaypwd())) {
            VerificationCodeView codeView = new VerificationCodeView(this);
            codeView.setEtNumber(6);
            codeView.setPwdMode(true);
            codeView.setmEtWidth(80);
            new android.support.v7.app.AlertDialog.Builder(this)
                    .setTitle("输入交易密码")
                    .setView(codeView)
                    .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            type = 2;
                            mPresenter.checkPwd("111", uid, codeView.getInputContent());
                        }
                    })
                    .setNeutralButton("忘记密码", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Bundle bundle = new Bundle();
                            bundle.putString("phone", userBean.getPhone());
                            startActivity(PasswordActivity.class, bundle);
                        }
                    })
                    .show();
        } else {
            new android.support.v7.app.AlertDialog.Builder(this)
                    .setMessage("为保障您的资金安全，请先设置交易密码")
                    .setNegativeButton("设置密码", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Bundle bundle = new Bundle();
                            bundle.putString("phone", userBean.getPhone());
                            startActivity(PasswordActivity.class, bundle);
                        }
                    })
                    .setNeutralButton("取消", null)
                    .show();
        }


    }

}
