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
import com.cj.reocrd.utils.LogUtil;
import com.cj.reocrd.utils.SPUtils;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.view.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/3/15.
 */

public class LoginPwdFragment extends BaseFragment<IndexPresenter> implements IndexContract.View {
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    Unbinder unbinder;
    @BindView(R.id.login_phone)
    EditText loginPhone;
    @BindView(R.id.login_password)
    EditText loginPassword;
    private final String TAG = "LoginPwdFragment";
    @Override
    protected void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_login_pwd;
    }

    @Override
    public void initView() {
        titleCenter.setText(R.string.login_title);
    }


    @OnClick({R.id.login_lose, R.id.login, R.id.title_left, R.id.login_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_lose:
                getIndexActivity().getVpLogin().setCurrentItem(4);
                break;
            case R.id.login:
                String phone = loginPhone.getText().toString();
                String pwd = loginPassword.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtil.showToastS(mActivity, R.string.input_phone);
                    break;
                }
                if (TextUtils.isEmpty(pwd)) {
                    ToastUtil.showToastS(mActivity, R.string.password_cannot_empty);
                    break;
                }
                mPresenter.loginRequest(UrlConstants.UrLType.LOGIN_PWD, phone, pwd);
                break;
            case R.id.title_left:
                getIndexActivity().getVpLogin().setCurrentItem(0);
                break;
            case R.id.login_code:
                getIndexActivity().getVpLogin().setCurrentItem(3);
                break;
        }
    }

    @Override
    public void onSuccess(Object data) {
        ApiResponse response = (ApiResponse) data;
        ToastUtil.showToastS(mActivity, response.getMessage());
        if ("1".equals(response.getStatusCode())) {
            UserBean userBean = (UserBean) response.getResults();
            LogUtil.e(TAG, userBean.getId());
            SPUtils.put(mActivity, UrlConstants.key.USERID, userBean.getId());
            BaseActivity.uid = userBean.getId();
            startActivity(MainActivity.class);
            mActivity.finish();
        }
    }

    @Override
    public void onFailureMessage(String msg) {
        ToastUtil.showToastS(mActivity, msg);
    }

}
