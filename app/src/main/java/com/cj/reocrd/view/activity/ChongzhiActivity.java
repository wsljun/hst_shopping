package com.cj.reocrd.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.cj.reocrd.R;
import com.cj.reocrd.api.ApiCallback;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.base.BaseApplication;
import com.cj.reocrd.contract.GoodsDetailContract;
import com.cj.reocrd.model.ApiModel;
import com.cj.reocrd.model.entity.GoodsCommentBean;
import com.cj.reocrd.model.entity.PayKeys;
import com.cj.reocrd.model.entity.UserBean;
import com.cj.reocrd.model.entity.WXPayKeys;
import com.cj.reocrd.presenter.GoodsDetailPresenter;
import com.cj.reocrd.utils.LogUtil;
import com.cj.reocrd.utils.SPUtils;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.utils.Utils;
import com.cj.reocrd.utils.alipay.OrderInfoUtil2_0;
import com.cj.reocrd.utils.alipay.PayResult;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

import static com.cj.reocrd.utils.Utils.isChinese;

/**
 * tv3.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
 */

public class ChongzhiActivity extends BaseActivity<GoodsDetailPresenter> implements GoodsDetailContract.View {

    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.title_rl)
    RelativeLayout titleRl;
    @BindView(R.id.tv_user_phone)
    TextView tv_user_phone;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.chongzhi_card_iv)
    ImageView chongzhiCardIv;
    @BindView(R.id.chongzhi_card_tv)
    TextView chongzhiCardTv;
    @BindView(R.id.chongzhi_card)
    TextView chongzhiCard;
    @BindView(R.id.cb_chongzhi_zhifubao)
    CheckBox cbAlipay;
    @BindView(R.id.cb_chongzhi_wx)
    CheckBox cbWXpay;
    @BindView(R.id.chognzhi_cneter)
    View chognzhiCneter;
    @BindView(R.id.rb_chognzhi_jifen)
    RadioButton rb_chognzhiJifen;
    @BindView(R.id.rb_chognzhi_dianzibi)
    RadioButton rb_chognzhi_dianzibi;
    @BindView(R.id.chognzhi_fuhao)
    TextView chognzhiFuhao;
    @BindView(R.id.et_chognzhi_money)
    EditText et_chognzhi_money;
    @BindView(R.id.tv_btn_chongzhi)
    TextView tv_btn_chongzhi;

    @BindView(R.id.ll_top)
    LinearLayout llTop;
    @BindView(R.id.money_type)
    TextView tvMoneyType;

    @BindView(R.id.et_change_phone)
    EditText et_change_phone;

    String title = "";
    public  String type = "";
    private UserBean user;
    private String money ;

    public static  String APPID = "";
    private static final int SDK_PAY_FLAG = 1001;
    private String RSA_PRIVATE = "";
    private   String  cz_type; //passback_params   uid,充值的手机号，充值类型（ 1消费积分2电子币）
    private String phone;
    private String OutTradeNo;
    private String passbackParam;
    private String paytype = "1";  // 支付类型  1,支付宝；2，微信
    private String t_phone;
    private String gold_money;
    private double gold;

    @Override
    public int getLayoutId() {
        return R.layout.activity_chongzhi;
    }
    InputFilter filter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            if (dend == 10) {
                t_phone = dest.toString()+source.toString();
                if (!Utils.checkMobileNumber(t_phone)) {
                    ToastUtil.showShort("请输入正确手机号！");
                    t_phone = null;
                    return "";
                }else{
                    checkPhone();
                }
            }
            return null;
        }
    };
    @Override
    public void initData() {
        super.initData();
        user = (UserBean) getIntent().getSerializableExtra("user");
        type = getIntent().getExtras().getString("type","");
        phone = (String) SPUtils.get(this, SPUtils.SpKey.USER_PHONE, "");
        gold = getIntent().getDoubleExtra("gold",0);
        et_change_phone.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(11)});
        if(MyMoneyActivity.TYPY_RECHARGE.equals(type)){
            title = "充值";
            llTop.setVisibility(View.VISIBLE);
            et_change_phone.setVisibility(View.VISIBLE);
            tv_user_name.setVisibility(View.GONE);
            tv_user_phone.setVisibility(View.GONE);
            tvMoneyType.setText("充值金额");
            titleRight.setText("充值记录");
            titleRight.setVisibility(View.VISIBLE);
        }
        if(MyMoneyActivity.TYPY_TRANSFER_ACCOUNTS.equals(type)){
            title = "转账";
            llTop.setVisibility(View.GONE);
            tvMoneyType.setText("转账金额");
            et_change_phone.setVisibility(View.VISIBLE);
            tv_user_name.setVisibility(View.GONE);
            tv_user_phone.setVisibility(View.GONE);
            rb_chognzhi_dianzibi.setChecked(true);
            rb_chognzhiJifen.setVisibility(View.GONE);
            rb_chognzhi_dianzibi.setText("电子币："+gold);
            titleRight.setText("转账记录");
            titleRight.setVisibility(View.VISIBLE);
        }
        if(MyMoneyActivity.TYPY_EXCHANGE.equals(type)){
            title = "兑换";
        }
        getPayKey();
    }

    @Override
    public void initView() {
        titleCenter.setText(title);
        tv_btn_chongzhi.setText(title);
        tv_user_phone.setText(phone);
        tv_user_name.setText(user.getName());
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    public void onSuccess(Object data) {

    }

    @Override
    public void onFailureMessage(String msg) {

    }

    @Override
    public Context getContext() {
        return null;
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


    @OnClick({R.id.rb_chognzhi_jifen, R.id.rb_chognzhi_dianzibi,R.id.tv_btn_chongzhi,R.id.title_left,
            R.id.cb_chongzhi_zhifubao, R.id.cb_chongzhi_wx,R.id.title_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.title_right:
                String url ="" ;
                if(MyMoneyActivity.TYPY_RECHARGE.equals(type)){
                    url = UrlConstants.URL_WEB_RECHARGE+uid;
                }
                if(MyMoneyActivity.TYPY_TRANSFER_ACCOUNTS.equals(type)){
                    url = UrlConstants.URL_WEB_CONVERTGOLD+uid;
                }
                Bundle b = new Bundle();
                b.putInt(WebViewActivity.BUNDLE_WEBVIEW_TYPE, WebViewActivity.TYPE_MONEY);
                b.putString(WebViewActivity.BUNDLE_WEBVIEW_URL, url);
                b.putString(WebViewActivity.BUNDLE_WEBVIEW_TITLE,titleRight.getText().toString() );
                startActivity(WebViewActivity.class, b);
                break;
            case R.id.cb_chongzhi_zhifubao:
                cbAlipay.setChecked(true);
                cbWXpay.setChecked(false);
                paytype ="1";
                break;
            case R.id.cb_chongzhi_wx:
                cbAlipay.setChecked(false);
                cbWXpay.setChecked(true);
                paytype ="2";
                break;
            case R.id.rb_chognzhi_jifen:
                cz_type ="1";
                break;
            case R.id.rb_chognzhi_dianzibi:
                cz_type ="2";
                break;
            case R.id.chognzhi_fuhao:
                break;
            case R.id.tv_btn_chongzhi:
                t_phone = et_change_phone.getText().toString();
                if(TextUtils.isEmpty(t_phone)){
                    ToastUtil.showShort("请输入正确手机号！");
                    return;
                }
                if(MyMoneyActivity.TYPY_RECHARGE.equals(type)){
                    money = et_chognzhi_money.getText().toString();
                    if(TextUtils.isEmpty(money)){
                        ToastUtil.showShort("请输入金额");
                        return;
                    }
                    if("1".equals(paytype)){
                        reChargeByAlipay();
                    }else{
                        reChargeByWX();
                    }
                }else{
                    // 转账，
                    transferAccounts();
                }
                break;
            default:
                break;
        }
    }

    // 检查账号是否存在
    private void checkPhone() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("phone", t_phone);
        ApiModel.getInstance().getData(UrlConstants.UrLType.CHECK_DUIFANG,map,UserBean.class, new ApiCallback() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                ToastUtil.showShort(apiResponse.getMessage());
                if (UrlConstants.SUCCESE_CODE.equals(apiResponse.getStatusCode())) {
                    UserBean userBean = (UserBean) apiResponse.getResults();
                    if(!TextUtils.isEmpty(userBean.getName())){
                        String changeName = userBean.getName();
                        tv_user_name.setText(changeName);
                        tv_user_name.setVisibility(View.VISIBLE);
                    }
                }else{
                    t_phone = null;
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                t_phone = null;
                tv_user_name.setVisibility(View.GONE);
            }
        });
    }

    // 转账
    private void transferAccounts () {
        gold_money = et_chognzhi_money.getText().toString();
        if(TextUtils.isEmpty(gold_money)){
            ToastUtil.showShort("请输入金额");
            return;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("fromuid", uid);
        map.put("phone", t_phone);
        map.put("gold", gold_money);
        ApiModel.getInstance().getData(UrlConstants.UrLType.TRANSFER_ACCOUNTS,map,null, new ApiCallback() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                ToastUtil.showShort(apiResponse.getMessage());
                if (UrlConstants.SUCCESE_CODE.equals(apiResponse.getStatusCode())) {
                    finish();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                ToastUtil.showShort("转账失败，请退出重试！");
            }
        });
    }


    /**
     * 支付宝支付
     */
    private void reChargeByAlipay() {
        //秘钥验证的类型 true:RSA2 false:RSA
        boolean rsa = true;
        //构造支付订单参数列表
//        aoid = OutTradeNo;//OrderInfoUtil2_0.getOutTradeNo();
        OutTradeNo = t_phone+ System.currentTimeMillis();
        passbackParam = uid+","+t_phone+","+paytype;
        LogUtil.d("alipay","OutTradeNo= "+OutTradeNo);
        LogUtil.d("alipay","passbackParam= "+passbackParam);
        String bizContent = OrderInfoUtil2_0.buildReChargeConetent(money,OutTradeNo,passbackParam);
        Map<String, String> params = OrderInfoUtil2_0.buildReChargeParamMap(APPID, rsa,bizContent);
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
                PayTask alipay = new PayTask(ChongzhiActivity.this);
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
                        Toast.makeText(ChongzhiActivity.this, "充值成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
//                        Toast.makeText(PayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    private void getPayKey() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        ApiModel.getInstance().getData(UrlConstants.UrLType.URL_OREDER_PAY_KEY, map, PayKeys.class, new ApiCallback() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (UrlConstants.SUCCESE_CODE.equals(apiResponse.getStatusCode())) {
                    PayKeys payKeys = (PayKeys) apiResponse.getResults();
                    if(!TextUtils.isEmpty(payKeys.getAppid())){
                        APPID = payKeys.getAppid();
                    }
                    if(!TextUtils.isEmpty(payKeys.getPrivatekey())){
                        RSA_PRIVATE = payKeys.getPrivatekey();
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
            }
        });
    }

    /**
     * 微信充值， todo
     */
    private void reChargeByWX() {
        String totalfen = Utils.changeY2F(money);
        HashMap<String, Object> map = new HashMap<>();
        String ip = Utils.getLocalIPAddress();
        map.put("uid", uid);
        map.put("phone", t_phone);
        map.put("type", cz_type);
        map.put("totalFee",totalfen);
        map.put("spbill_create_ip", ip);
        ApiModel.getInstance().getData(UrlConstants.UrLType.REEXCHANGE_WX, map, WXPayKeys.class, new ApiCallback() {

            @Override
            public void onSuccess(ApiResponse apiResponse) {
                ToastUtil.showShort(apiResponse.getMessage());
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

    PayReq req ;
    private void payWX(WXPayKeys wxPayKeys){
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
//                    ToastUtil.shortToastInBackgroundThread(ChongzhiActivity.this, "正常调起支付");
                    toPay();
                } else {
                    ToastUtil.shortToastInBackgroundThread(ChongzhiActivity.this, "返回错误" + wxPayKeys.getMessage());
                }
            }}).start();

    }

    private void toPay() {
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
//        IWXAPI wxApi = WXAPIFactory.createWXAPI(this.getApplicationContext(), null);
        BaseApplication.api.registerApp(BaseApplication.APP_ID);
        boolean b = BaseApplication.api.sendReq(req);
//        if(b){
//            ToastUtil.shortToastInBackgroundThread(ChongzhiActivity.this,"请求支付成功！");
//        }else{
//            ToastUtil.shortToastInBackgroundThread(ChongzhiActivity.this,"请求支付失败！");
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(UrlConstants.WXPAY_CODE == 0){
            UrlConstants.WXPAY_CODE = 999;
            finish();
        }
    }


}
