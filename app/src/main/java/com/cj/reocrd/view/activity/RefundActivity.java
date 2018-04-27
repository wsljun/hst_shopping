package com.cj.reocrd.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.ApiCallback;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.model.ApiModel;
import com.cj.reocrd.utils.ToastUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by Administrator on 2018/3/18.
 */

public class RefundActivity extends BaseActivity {
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.refund_hint)
    EditText refundHint;
    @BindView(R.id.rb_order_error)
    RadioButton rbOrderError;
    @BindView(R.id.rb_reason_other)
    RadioButton rbReasonOther;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.refund_user)
    EditText refundUser;
    @BindView(R.id.refund_phone)
    EditText refundPhone;
    @BindView(R.id.address_dz_rl)
    RelativeLayout addressDzRl;
    @BindView(R.id.refund_confim)
    TextView refundConfim;
    private  String oid ,reason,refund_type ="1";

    @Override
    public int getLayoutId() {
        return R.layout.activity_refund;
    }

    @Override
    public void initData() {
        super.initData();
        oid = getIntent().getStringExtra("oid");
    }

    @Override
    public void initView() {
        titleCenter.setText("申请退款");

    }

    @Override
    public void initPresenter() {

    }

    @OnClick({R.id.title_left, R.id.rb_order_error, R.id.rb_reason_other, R.id.radioGroup, R.id.refund_confim})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.rb_order_error:
                refund_type = "1";
                break;
            case R.id.rb_reason_other:
                refund_type = "2";
                break;
            case R.id.radioGroup:
                break;
            case R.id.refund_confim:
                returnGoods();
                break;
        }
    }

    private void returnGoods() {
        reason = "不想买";
        HashMap<String ,Object> map = new HashMap<>();
        map.put("oid",oid);
        map.put("reason",reason);
        map.put("type",refund_type);
        ApiModel.getInstance().getData(UrlConstants.UrLType.URL_REFUND_ORDER,map,null, new ApiCallback() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                apiResponse.getMessage();// 退货中的商品依然在 订单状态不应该显示可申请退货
                ToastUtil.showShort(apiResponse.getMessage());
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                ToastUtil.showShort(t.toString());
            }
        });
    }
}
