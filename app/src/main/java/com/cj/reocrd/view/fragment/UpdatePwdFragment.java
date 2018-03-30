package com.cj.reocrd.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.base.BaseFragment;
import com.cj.reocrd.contract.IndexContract;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.utils.Utils;
import com.cj.reocrd.view.IndexActivity;
import com.cj.reocrd.view.activity.MainActivity;
import com.cj.reocrd.view.activity.MyActivity;
import com.cj.reocrd.view.view.VerificationCode.VerificationCodeInput;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 修改密码，并登陆
 */

public class UpdatePwdFragment extends BaseFragment<IndexContract.Presenter> implements IndexContract.View, VerificationCodeInput.Listener {
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.update_pwd1)
    EditText updatePwd1;
    @BindView(R.id.update_pwd2)
    EditText updatePwd2;
    @BindView(R.id.update_pwd_rl)
    RelativeLayout updatePwdRl;
    @BindView(R.id.update_next)
    TextView updateNext;
    @BindView(R.id.update_phone)
    EditText updatePhone;
    @BindView(R.id.update_code)
    VerificationCodeInput updateCode;
    @BindView(R.id.update_getcode)
    TextView updateGetcode;
    @BindView(R.id.update_code_rl)
    RelativeLayout updateCodeRl;

    private String phone;
    private String code;

    @Override
    protected void initPresenter() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_update_pwd;
    }

    @Override
    public void initData() {
        super.initData();

    }

    @Override
    public void initView() {
        titleCenter.setText(getString(R.string.my_update_pwd));
    }


    @OnClick({R.id.update_login, R.id.update_next, R.id.update_getcode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.update_login:
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
                // todo do login
                if(mActivity instanceof IndexActivity){
                    startActivity(MainActivity.class);
                    mActivity.finish();
                }
                if(mActivity instanceof MyActivity){
                    getMyActivity().getViewPager().setCurrentItem(0);
                }
                break;
            case R.id.update_next:
                if (TextUtils.isEmpty(code)) {
                    ToastUtil.showShort(R.string.verification_code_empty);
                    break;
                }
                updateCodeRl.setVisibility(View.GONE);
                updatePwdRl.setVisibility(View.VISIBLE);
                break;
            case R.id.update_getcode:
                phone = updatePhone.getText().toString();
                if (!TextUtils.isEmpty(phone)) {
                    if (Utils.checkMobileNumber(phone)) {
                        // todo get code

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

    @Override
    public void onComplete(String s) {
        updateNext.setClickable(true);
        code = s;
    }
}
