package com.cj.reocrd.view.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.base.BaseFragment;
import com.cj.reocrd.contract.MyContract;
import com.cj.reocrd.model.entity.UserBean;
import com.cj.reocrd.presenter.MyPrresenter;
import com.cj.reocrd.utils.CountDownTimerUtils;
import com.cj.reocrd.utils.LogUtil;
import com.cj.reocrd.utils.SPUtils;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.utils.Utils;
import com.cj.reocrd.view.activity.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;

import static com.cj.reocrd.base.BaseActivity.uid;
import static com.cj.reocrd.view.activity.MyActivity.pNumber;

/**
 * 重新绑定手机号
 */

public class AddPhoneFragment extends BaseFragment<MyPrresenter> implements MyContract.View {
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.update_phone_label1)
    TextView updatePhoneLabel1;
    @BindView(R.id.phnoe_number)
    EditText phnoeNumber;
    @BindView(R.id.phone_code)
    EditText phoneCode;
    @BindView(R.id.phone_get)
    TextView phoneGet;
    @BindView(R.id.phone_do)
    TextView phoneDo;

    private boolean isCreated = false;
    private boolean isNew = false;// 判断是当前手机号，还是new
    private String phone;
    private String code;
    private int type;

    private CountDownTimerUtils mCountDownTimerUtils;

    @Override
    protected void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    public void initData() {
        super.initData();
        isCreated = true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_add_phone;
    }

    @Override
    public void initView() {
        titleCenter.setText(getString(R.string.my_add_phone));
        if (!TextUtils.isEmpty(pNumber)) {
            phnoeNumber.setText(pNumber);
        }

    }


    @OnClick({R.id.title_left, R.id.phone_clear, R.id.phone_get, R.id.phone_do})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                getMyActivity().getViewPager().setCurrentItem(0);
                break;
            case R.id.phone_clear:
                phnoeNumber.setText("");
                break;
            case R.id.phone_get:
                phone = phnoeNumber.getText().toString();
                if (!TextUtils.isEmpty(phone)) {
                    if (Utils.checkMobileNumber(phone)) {
                        type = 1;
                        mPresenter.getCode(UrlConstants.UrLType.GET_CODE, phone, UrlConstants.codeType.UPDATE_PHONE);
                        mCountDownTimerUtils = new CountDownTimerUtils(phoneGet, 60000, 1000);
                        mCountDownTimerUtils.start();
                    } else {
                        ToastUtil.showToastS(mActivity, R.string.format_not_correct);
                    }
                } else {
                    ToastUtil.showToastS(mActivity, R.string.input_phone);
                }
                break;
            case R.id.phone_do:
                phone = phnoeNumber.getText().toString();
                code = phoneCode.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtil.showToastS(mActivity, R.string.input_phone);
                    break;
                }
                if (TextUtils.isEmpty(code)) {
                    ToastUtil.showShort(R.string.verification_code_empty);
                    break;
                }
                if (isNew) {
                    //确认更换
                    type = 2;
                    mPresenter.updatePhone(UrlConstants.UrLType.UPDATE_PHONE, uid, phone, code);
                } else {
                    //下一步，当前手机号获取验证码之后，添加验证码的判断，
                    type = 3;
                    mPresenter.updatePhone(UrlConstants.UrLType.CHECK_PHONE, uid, phone, code);
                }

                break;
        }
    }

    @Override
    public void onSuccess(Object data) {
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
                        pNumber = userBean.getPhone();
                        getMyActivity().getViewPager().setCurrentItem(3);
                    }
                }
                break;
            case 3:
                ToastUtil.showToastS(mActivity, response.getMessage());
                if ("1".equals(response.getStatusCode())) {
                    updatePhoneLabel1.setText(getString(R.string.phone_number_new));
                    phnoeNumber.setText("");
                    mCountDownTimerUtils.onFinish();
                    mCountDownTimerUtils.cancel();
                    phoneGet.setText(getString(R.string.getcode));
                    phoneDo.setText(getString(R.string.update_phone_do));
                    phoneCode.setText("");
                    isNew = true;
                }
                break;
        }
    }

    @Override
    public void onFailureMessage(String msg) {
        ToastUtil.showToastS(mActivity, msg);
    }

    /**
     * 此方法目前仅适用于标示ViewPager中的Fragment是否真实可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (!isCreated) {
            return;
        }

        if (isVisibleToUser) {
            if (!TextUtils.isEmpty(pNumber)) {
                phnoeNumber.setText(pNumber);
            }
        }
    }
}
