package com.cj.reocrd.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseFragment;
import com.cj.reocrd.contract.IndexContract;
import com.cj.reocrd.presenter.IndexPresenter;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.utils.Utils;
import com.dalimao.corelibrary.VerificationCodeInput;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 手机验证码登陆
 */

public class LoginCodeFragment extends BaseFragment<IndexPresenter> implements IndexContract.View, VerificationCodeInput.Listener {
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.login_phone)
    EditText loginPhone;
    @BindView(R.id.login_code)
    VerificationCodeInput loginCode;
    @BindView(R.id.login_getcode)
    TextView loginGetcode;

    private String phone;
    private String code;

    @Override
    protected void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_login_code;
    }

    @Override
    public void initView() {
        titleCenter.setText(R.string.login_title);
    }


    @Override
    public void onSuccess(Object data) {

    }

    @Override
    public void onFailureMessage(String msg) {
            ToastUtil.showShort(msg);
    }


    @OnClick({R.id.login, R.id.login_getcode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login:
                 ToastUtil.showShort(" login ");
                break;
            case R.id.login_getcode:
                phone = loginPhone.getText().toString();
                if (!TextUtils.isEmpty(phone)) {
                    if (Utils.checkMobileNumber(phone)) {
                        // todo get code
                          mPresenter.getCode(UrlConstants.UrLType.GET_CODE,phone,"1");
                    } else {
                        ToastUtil.showToastS(mActivity, R.string.format_not_correct);
                    }
                } else {
                    ToastUtil.showToastS(mActivity, R.string.input_phone);
                }
                break;
        }
    }

    //验证码输入完成监听
    @Override
    public void onComplete(String s) {
        loginCode.setClickable(true);
        code = s;
    }
}
