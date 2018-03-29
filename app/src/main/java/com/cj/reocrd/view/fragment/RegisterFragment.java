package com.cj.reocrd.view.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.base.BaseFragment;
import com.cj.reocrd.contract.IndexContract;
import com.cj.reocrd.presenter.IndexPresenter;
import com.cj.reocrd.utils.CountDownTimerUtils;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.utils.Utils;
import com.dalimao.corelibrary.VerificationCodeInput;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterFragment extends BaseFragment<IndexPresenter> implements
        IndexContract.View, VerificationCodeInput.Listener {

    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.register)
    TextView register;
    @BindView(R.id.register_next)
    TextView registerNext;
    @BindView(R.id.register_phone)
    EditText registerPhone;
    @BindView(R.id.register_code)
    VerificationCodeInput registerCode;
    @BindView(R.id.register_getcode)
    TextView registerGetcode;
    @BindView(R.id.register_code_rl)
    RelativeLayout registerCodeRl;
    @BindView(R.id.update_pwd1)
    EditText updatePwd1;
    @BindView(R.id.update_pwd2)
    EditText updatePwd2;
    @BindView(R.id.register_pwd_rl)
    RelativeLayout registerPwdRl;

    private String phone;
    private String code;

    @Override
    protected void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    public void initView() {
        titleCenter.setText(R.string.register_title);
        registerNext.setClickable(false);
        registerCode.setOnCompleteListener(this);
    }

    @OnClick({R.id.register, R.id.register_next, R.id.register_getcode, R.id.title_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register:
                String pwd = updatePwd1.getText().toString();
                String secondPwd = updatePwd2.getText().toString();
                if (TextUtils.isEmpty(pwd) && TextUtils.isEmpty(secondPwd)) {
                    ToastUtil.showToastS(mActivity, R.string.password_cannot_empty);
                    break;
                }
                if (!pwd.equals(secondPwd)) {
                    ToastUtil.showToastS(mActivity, R.string.passwords_match);
                    break;
                }
                if (pwd.length() > 8 | pwd.length() < 6) {
                    ToastUtil.showToastS(mActivity, R.string.password_can_only);
                    break;
                }
                // todo do register
                break;
            case R.id.register_next:
                if (TextUtils.isEmpty(registerCode.toString())) {
                    ToastUtil.showShort(R.string.verification_code_empty);
                    break;
                }
                registerCodeRl.setVisibility(View.GONE);
                registerPwdRl.setVisibility(View.VISIBLE);
                break;
            case R.id.register_getcode:
                phone = registerPhone.getText().toString();
                if (!TextUtils.isEmpty(phone)) {
                    if (Utils.checkMobileNumber(phone)) {
                        // todo get code
                        CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(registerGetcode, 60000, 1000);
                        mCountDownTimerUtils.start();
                    } else {
                        ToastUtil.showToastS(mActivity, R.string.format_not_correct);
                    }
                } else {
                    ToastUtil.showToastS(mActivity, R.string.input_phone);
                }
                break;
            case R.id.title_left:
                getIndexActivity().getVpLogin().setCurrentItem(0);
                break;
        }
    }

    @Override
    public void onSuccess(Object data) {

    }

    @Override
    public void onFailureMessage(String msg) {

    }

    //验证码输入完成监听
    @Override
    public void onComplete(String s) {
        registerNext.setClickable(true);
        code = s;
    }
}
