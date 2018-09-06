package com.cj.reocrd.view.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.ApiCallback;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.model.ApiModel;
import com.cj.reocrd.model.entity.UserBean;
import com.cj.reocrd.model.entity.Wallet;
import com.cj.reocrd.model.entity.YongJINBean;
import com.cj.reocrd.utils.SPUtils;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.utils.Utils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class ExchangeActivity extends BaseActivity {


    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.tv_ye)
    TextView tvYe;
    @BindView(R.id.tv_dzb)
    TextView tvDzb;
    @BindView(R.id.tv_type_jifen)
    TextView tvTypeJifen;
    @BindView(R.id.tv_type_dzb)
    TextView tvTypeDzb;
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.tv_btn_dh)
    TextView tvBtnDh;
    private UserBean user;

    /**
     * 金额来源 1余额 2电子币（选了余额，兑换类型： 1和2都有。选了电子币，兑换类型只显示 1）
     * */
    private String fromtype ,totype;
    Double useableblance , gold;

    @Override
    public int getLayoutId() {
        return R.layout.activity_exchange;
    }

    @Override
    public void initView() {
        titleCenter.setText("兑换");
        tvYe.setText("余额\n"+useableblance);
        tvDzb.setText("电子币\n"+gold);
        titleRight.setText("兑换记录");
        titleRight.setVisibility(View.VISIBLE);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initData() {
        user = (UserBean) getIntent().getSerializableExtra("user");
        useableblance = getIntent().getDoubleExtra("useableblance",0);
        gold = getIntent().getDoubleExtra("gold",0);
        fromtype = "1";
        totype = "2";// 修改默认，余额兑换电子币
    }

    @OnClick({R.id.title_left, R.id.title_center, R.id.tv_ye, R.id.tv_dzb, R.id.tv_type_jifen,
            R.id.tv_type_dzb, R.id.et_money, R.id.tv_btn_dh,R.id.title_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.title_right:
                Bundle b = new Bundle();
                b.putInt(WebViewActivity.BUNDLE_WEBVIEW_TYPE, WebViewActivity.TYPE_MONEY);
                b.putString(WebViewActivity.BUNDLE_WEBVIEW_URL, UrlConstants.URL_WEB_CHANGE+uid);
                b.putString(WebViewActivity.BUNDLE_WEBVIEW_TITLE,titleRight.getText().toString() );
                startActivity(WebViewActivity.class, b);
                break;
            case R.id.tv_ye:
                fromtype = "1";
                break;
            case R.id.tv_dzb:
                fromtype = "2";
                totype = "1";
                break;
            case R.id.tv_type_jifen:
                totype = "1";
                break;
            case R.id.tv_type_dzb:
                totype = "2";
                break;
            case R.id.tv_btn_dh:
                exchange();
                break;
            default:
                break;
        }
        updateBtnStatus();
    }

    // 充值，转账，兑换
    private void exchange() {
        String money = etMoney.getText().toString();
        if(TextUtils.isEmpty(money)){
            ToastUtil.showShort("请输入兑换金额");
            return;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("fromtype", fromtype);
        map.put("totype", totype);
        map.put("num", money);
        ApiModel.getInstance().getData(UrlConstants.UrLType.EXCHANGE,map,null, new ApiCallback() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                ToastUtil.showShort(apiResponse.getMessage());
                if (UrlConstants.SUCCESE_CODE.equals(apiResponse.getStatusCode())) {
                    finish();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
            }
        });
    }

    private void updateBtnStatus(){
        if(fromtype.equals("1")){
            tvYe.setTextColor(getResources().getColor(R.color.colorWhite));
            tvYe.setBackground(getResources().getDrawable(R.mipmap.beijing2));
            tvDzb.setTextColor(getResources().getColor(R.color.colorBlack));
            tvDzb.setBackground(getResources().getDrawable(R.drawable.price_bg));
            tvTypeDzb.setVisibility(View.VISIBLE);
        }else{
            tvYe.setTextColor(getResources().getColor(R.color.colorBlack));
            tvYe.setBackground(getResources().getDrawable(R.drawable.price_bg));
            tvDzb.setTextColor(getResources().getColor(R.color.colorWhite));
            tvDzb.setBackground(getResources().getDrawable(R.mipmap.beijing2));
            tvTypeDzb.setVisibility(View.INVISIBLE);
        }
        if(totype.equals("1")){
            tvTypeJifen.setTextColor(getResources().getColor(R.color.colorWhite));
            tvTypeJifen.setBackground(getResources().getDrawable(R.mipmap.beijing2));
            tvTypeDzb.setTextColor(getResources().getColor(R.color.colorBlack));
            tvTypeDzb.setBackground(getResources().getDrawable(R.drawable.price_bg));
        }else{
            tvTypeDzb.setTextColor(getResources().getColor(R.color.colorWhite));
            tvTypeDzb.setBackground(getResources().getDrawable(R.mipmap.beijing2));
            tvTypeJifen.setTextColor(getResources().getColor(R.color.colorBlack));
            tvTypeJifen.setBackground(getResources().getDrawable(R.drawable.price_bg));
        }
    }



}
