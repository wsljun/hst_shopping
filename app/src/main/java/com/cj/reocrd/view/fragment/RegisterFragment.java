package com.cj.reocrd.view.fragment;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.base.BaseFragment;
import com.cj.reocrd.contract.IndexContract;
import com.cj.reocrd.model.entity.UserBean;
import com.cj.reocrd.presenter.IndexPresenter;
import com.cj.reocrd.utils.CountDownTimerUtils;
import com.cj.reocrd.utils.LogUtil;
import com.cj.reocrd.utils.SPUtils;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.utils.Utils;
import com.cj.reocrd.view.activity.MainActivity;
import com.cj.reocrd.view.activity.WebViewActivity;
import com.cj.reocrd.view.view.VerificationCode.VerificationCodeInput;

import butterknife.BindView;
import butterknife.OnClick;

import static com.cj.reocrd.utils.Utils.isChinese;

public class RegisterFragment extends BaseFragment<IndexPresenter> implements
        IndexContract.View, VerificationCodeInput.Listener ,CompoundButton.OnCheckedChangeListener{

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
    @BindView(R.id.register_recommend)
    TextView registerRecommend;
    @BindView(R.id.et_user_name)
    EditText et_user_name;
    @BindView(R.id.register_check)
    CheckBox registerCheck;
    @BindView(R.id.register_xy)
    TextView registerXy;

    private final String TAG = "RegisterFragment";
    private String phone;
    private String code;
    private String responseCode;
    private String recommend;
    private int type;
    private String pwd;
    InputFilter filter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                if (!isChinese(source.charAt(i))) {
                    ToastUtil.showShort("只能输入汉字！");
                    return "";
                }
            }
            return null;
        }
    };
    private String username;

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
        registerCode.setOnCompleteListener(this);
        et_user_name.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(5)});
        registerCheck.setOnCheckedChangeListener(this);
    }

    @OnClick({R.id.register, R.id.register_next, R.id.register_getcode, R.id.title_left,R.id.register_xy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register:
                pwd = updatePwd1.getText().toString();
                String secondPwd = updatePwd2.getText().toString();
                if (TextUtils.isEmpty(pwd) && TextUtils.isEmpty(secondPwd)) {
                    ToastUtil.showToastS(mActivity, R.string.password_cannot_empty);
                    break;
                }
                if (!pwd.equals(secondPwd)) {
                    ToastUtil.showToastS(mActivity, R.string.passwords_match);
                    break;
                }
                if (pwd.length() < 6) {
                    ToastUtil.showToastS(mActivity, R.string.password_can_only);
                    break;
                }
                recommend = registerRecommend.getText().toString();
                username = et_user_name.getText().toString();
                if (TextUtils.isEmpty(username)) {
                    ToastUtil.showToastS(mActivity, "请填写真实姓名");
                    break;
                }
                if(!registerCheck.isChecked()){
                    ToastUtil.showToastS(mActivity, "请阅读并同意以下协议");
                    break;
                }
                if (!TextUtils.isEmpty(recommend)) {
                    type = 3;
                    mPresenter.checkRecommend(UrlConstants.UrLType.CHECK_RECOMMEND, recommend);
                }else{
                    ToastUtil.showToastS(mActivity,"请填写推荐人");
                }
                break;
            case R.id.register_next:
                phone = registerPhone.getText().toString();
                if (!TextUtils.isEmpty(phone)) {
                    if (!Utils.checkMobileNumber(phone)) {
                        ToastUtil.showToastS(mActivity, R.string.format_not_correct);
                        break;
                    }
                } else {
                    ToastUtil.showToastS(mActivity, R.string.input_phone);
                    break;
                }
                if (TextUtils.isEmpty(code)) {
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
                        type = 1;
                        mPresenter.getCode(UrlConstants.UrLType.GET_CODE, phone, UrlConstants.codeType.REGISTER);
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
                if(registerPwdRl.getVisibility() == View.VISIBLE){
                    registerCodeRl.setVisibility(View.VISIBLE);
                    registerPwdRl.setVisibility(View.GONE);
                }else{
                    getIndexActivity().getVpLogin().setCurrentItem(0);
                }
                break;
            case R.id.register_xy:
                Bundle b = new Bundle();
                b.putInt(WebViewActivity.BUNDLE_WEBVIEW_TYPE, WebViewActivity.TYPE_XY);
                b.putString(WebViewActivity.BUNDLE_WEBVIEW_URL, "http://www.baidu.com");
                b.putString(WebViewActivity.BUNDLE_WEBVIEW_TITLE,registerXy.getText().toString());
                startActivity(WebViewActivity.class, b);
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
                        responseCode = userBean.getId();
                        LogUtil.e(TAG, responseCode);
                    }
                }
                break;
            case 2:
                ToastUtil.showToastS(mActivity, response.getMessage());
                if ("1".equals(response.getStatusCode())) {
                    UserBean userBean = (UserBean) response.getResults();
                    if (userBean != null) {
                        LogUtil.e(TAG, userBean.getId());
                        if(userBean.getId()==null||userBean.getAccid()==null||userBean.getToken()==null){
                            getIndexActivity().getVpLogin().setCurrentItem(1);
                        }else{
                            SPUtils.put(mActivity, UrlConstants.key.USERID, userBean.getId());
                            SPUtils.put(mActivity, SPUtils.SpKey.USER_PHONE, phone);
                            SPUtils.put(mActivity, SPUtils.SpKey.IM_ACCID, userBean.getAccid());
                            SPUtils.put(mActivity, SPUtils.SpKey.IM_TOKEN, userBean.getToken());
                            BaseActivity.uid = userBean.getId();
                            startActivity(MainActivity.class);
                            mActivity.finish();
                        }
                    }
                }
                break;
            case 3:
                ToastUtil.showToastS(mActivity, response.getMessage());
                if ("1".equals(response.getStatusCode())) {
//                    registerCodeRl.setVisibility(View.GONE);
//                    registerPwdRl.setVisibility(View.VISIBLE);
                    type = 2;
                    mPresenter.registerRequest(UrlConstants.UrLType.REGISTER, phone, pwd, code,recommend,username);
                }
                break;
        }
    }

    @Override
    public void onFailureMessage(String msg) {
        ToastUtil.showToastS(mActivity, msg);
    }

    //验证码输入完成监听
    @Override
    public void onComplete(String s) {
        registerCode.setEnabled(false);
        code = s;
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            registerCheck.setTextColor(getResources().getColor(R.color.black));
        }else{
            registerCheck.setTextColor(getResources().getColor(R.color.colorTexthintGrey));
        }
    }
}
