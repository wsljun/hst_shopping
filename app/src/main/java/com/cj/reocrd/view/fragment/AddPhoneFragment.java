package com.cj.reocrd.view.fragment;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.base.BaseFragment;
import com.cj.reocrd.utils.CountDownTimerUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/17.
 */

public class AddPhoneFragment extends BaseFragment {
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.phnoe_number)
    EditText phnoeNumber;
    @BindView(R.id.phone_code)
    EditText phoneCode;
    @BindView(R.id.phone_get)
    TextView phoneGet;

    @Override
    protected void initPresenter() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_add_phone;
    }

    @Override
    public void initView() {
        titleCenter.setText(getString(R.string.my_add_phone));
    }


    @OnClick({R.id.title_left,R.id.phone_clear, R.id.phone_get, R.id.phone_do})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                getMyActivity().getViewPager().setCurrentItem(0);
                break;
            case R.id.phone_clear:
                phnoeNumber.setText("");
                break;
            case R.id.phone_get:
                //变灰、倒计时
                CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(phoneGet, 60000, 1000);
                mCountDownTimerUtils.start();
                break;
            case R.id.phone_do:
                break;
        }
    }
}
