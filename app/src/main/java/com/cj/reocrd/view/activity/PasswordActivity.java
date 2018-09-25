package com.cj.reocrd.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.contract.MyContract;
import com.cj.reocrd.model.entity.UserBean;
import com.cj.reocrd.presenter.MyPrresenter;
import com.cj.reocrd.utils.CountDownTimerUtils;
import com.cj.reocrd.utils.LogUtil;
import com.cj.reocrd.utils.SPUtils;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.view.view.VerificationCode.VerificationCodeInput;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/9/10.
 */

public class PasswordActivity extends BaseActivity<MyPrresenter> implements MyContract.View {
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.password_phone)
    TextView passwordPhone;
    @BindView(R.id.password_code_tv)
    TextView passwordCodeTv;
    @BindView(R.id.password_getcode)
    TextView passwordGetcode;
    @BindView(R.id.password_code)
    EditText passwordCode;
    @BindView(R.id.password_next1)
    TextView passwordNext1;
    @BindView(R.id.passowrd_layout1)
    LinearLayout passowrdLayout1;
    @BindView(R.id.passowrd_password)
    VerificationCodeInput passowrdPassword;
    @BindView(R.id.password_next2)
    TextView passwordNext2;
    @BindView(R.id.passowrd_layout2)
    LinearLayout passowrdLayout2;
    @BindView(R.id.passowrd_password2)
    VerificationCodeInput passowrdPassword2;
    @BindView(R.id.password_next3)
    TextView passwordNext3;
    @BindView(R.id.passowrd_layout3)
    LinearLayout passowrdLayout3;

    String phone;
    int type;
    String password;
    String password2;

    @Override
    public int getLayoutId() {
        return R.layout.activity_password;
    }

    @Override
    public void initView() {
        passowrdLayout2.setVisibility(View.GONE);
        passowrdLayout3.setVisibility(View.GONE);
        passowrdPassword.setOnCompleteListener(new VerificationCodeInput.Listener() {
            @Override
            public void onComplete(String content) {
                password = content;
            }
        });
        passowrdPassword2.setOnCompleteListener(new VerificationCodeInput.Listener() {
            @Override
            public void onComplete(String content) {
                password2 = content;
            }
        });
        titleCenter.setText("设置支付密码");
        phone = getIntent().getExtras().getString("phone");
        passwordPhone.setText("请输入发送到绑定的手机号" + phone.substring(0, 3) + "****" + phone.substring(7, 11) + "上的验证码，以验证您的身份");
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
    }


    @OnClick({R.id.password_getcode, R.id.password_next1, R.id.password_next2, R.id.password_next3, R.id.title_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.password_getcode:
                type = 1;
                mPresenter.getCode(UrlConstants.UrLType.GET_CODE, phone, UrlConstants.codeType.S_PWD);
                CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(passwordGetcode, 60000, 1000);
                mCountDownTimerUtils.start();
                break;
            case R.id.password_next1:
                if (TextUtils.isEmpty(passwordGetcode.getText().toString())) {
                    ToastUtil.showToastS(mContext, "验证码未填写");
                    return;
                }
                passowrdLayout1.setVisibility(View.GONE);
                passowrdLayout2.setVisibility(View.VISIBLE);
                break;
            case R.id.password_next2:
                if (TextUtils.isEmpty(password)) {
                    ToastUtil.showToastS(mContext, "请设置密码");
                    return;
                }
                passowrdLayout2.setVisibility(View.GONE);
                passowrdLayout3.setVisibility(View.VISIBLE);
                break;
            case R.id.password_next3:
                if (TextUtils.isEmpty(password2)) {
                    ToastUtil.showToastS(mContext, "请再次确认密码");
                    return;
                }
                if (!password.equals(password2)) {
                    ToastUtil.showToastS(mContext, "两次密码不一致，请重新输入");
                    return;
                }
                type = 2;
                mPresenter.updatePwd("110", uid, passwordCode.getText().toString(), password);
                break;
            case R.id.title_left:
                finish();
                break;
        }
    }

    @Override
    public void onSuccess(Object data) {
        ApiResponse response = (ApiResponse) data;
        switch (type) {
            case 1:
                ToastUtil.showToastS(mContext, response.getMessage());
                if ("1".equals(response.getStatusCode())) {

                }
                break;
            case 2:
                ToastUtil.showToastS(mContext, response.getMessage());
                if ("1".equals(response.getStatusCode())) {
                    finish();
                }
                break;
        }
    }

    @Override
    public void onFailureMessage(String msg) {
        ToastUtil.showToastS(mContext, msg);
    }

    @Override
    public Context getContext() {
        return null;
    }

}
