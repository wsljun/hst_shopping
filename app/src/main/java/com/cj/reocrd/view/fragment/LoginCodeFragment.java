package com.cj.reocrd.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.base.BaseFragment;
import com.cj.reocrd.contract.IndexContract;
import com.cj.reocrd.model.entity.UserBean;
import com.cj.reocrd.presenter.IndexPresenter;
import com.cj.reocrd.utils.ConstantsUtils;
import com.cj.reocrd.utils.CountDownTimerUtils;
import com.cj.reocrd.utils.LogUtil;
import com.cj.reocrd.utils.SPUtils;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.utils.Utils;
import com.cj.reocrd.view.activity.MainActivity;
import com.cj.reocrd.view.view.ProgressWait.ProgressPopupWindow;
import com.cj.reocrd.view.view.VerificationCode.VerificationCodeInput;

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
    private final String TAG = "LoginCodeFragment";
    private String phone;
    private String code;
    private int type;
    ProgressPopupWindow progressPopupWindow;
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
        loginCode.setOnCompleteListener(this);
        progressPopupWindow = new ProgressPopupWindow(mActivity);
    }


    @Override
    public void onSuccess(Object data) {
        if (progressPopupWindow != null) {
            progressPopupWindow.dismiss();
        }
        ApiResponse response = (ApiResponse) data;
        switch (type) {
            case 1:
                ToastUtil.showToastS(mActivity, response.getMessage());
                if ("1".equals(response.getStatusCode())) {
                    UserBean userBean = (UserBean) response.getResults();
                    if (userBean != null) {
                        //todo
                    }
                }
                break;
            case 2:
                ToastUtil.showToastS(mActivity, response.getMessage());
                if ("1".equals(response.getStatusCode())) {
                    UserBean userBean = (UserBean) response.getResults();
                    if (userBean != null) {
                        LogUtil.e(TAG, userBean.getId());
                        SPUtils.put(mActivity, UrlConstants.key.USERID, userBean.getId());
                        SPUtils.put(mActivity, SPUtils.SpKey.USER_PHONE, phone);
                        SPUtils.put(mActivity, SPUtils.SpKey.IM_ACCID, userBean.getAccid());
                        SPUtils.put(mActivity, SPUtils.SpKey.IM_TOKEN, userBean.getToken());
                        BaseActivity.uid = userBean.getId();
                        startActivity(MainActivity.class);
                        mActivity.finish();
                    }
                }
                break;
        }

    }

    @Override
    public void onFailureMessage(String msg) {
        if (progressPopupWindow != null) {
            progressPopupWindow.dismiss();
        }
        ToastUtil.showShort(msg);
    }


    @OnClick({R.id.login, R.id.login_getcode,R.id.title_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login:
                phone = loginPhone.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtil.showToastS(mActivity, R.string.input_phone);
                    break;
                }
                if (TextUtils.isEmpty(code)) {
                    ToastUtil.showShort(R.string.verification_code_empty);
                    break;
                }
                progressPopupWindow.showPopupWindow();
                type = 2;
                mPresenter.loginRequestCode(UrlConstants.UrLType.LOGIN_CODE, phone, code);
                break;
            case R.id.login_getcode:
                phone = loginPhone.getText().toString();
                if (!TextUtils.isEmpty(phone)) {
                    if (Utils.checkMobileNumber(phone)) {
                        // todo get code
                        type = 1;
                        mPresenter.getCode(UrlConstants.UrLType.GET_CODE, phone, UrlConstants.codeType.LOGIN);
                        CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(loginGetcode, ConstantsUtils.millisInFuture, 1000);
                        mCountDownTimerUtils.start();
                    } else {
                        ToastUtil.showToastS(mActivity, R.string.format_not_correct);
                    }
                } else {
                    ToastUtil.showToastS(mActivity, R.string.input_phone);
                }
                break;
            case R.id.title_left:
                getIndexActivity().getVpLogin().setCurrentItem(1);
                break;
        }
    }

    //验证码输入完成监听
    @Override
    public void onComplete(String s) {
        code = s;
    }
}
