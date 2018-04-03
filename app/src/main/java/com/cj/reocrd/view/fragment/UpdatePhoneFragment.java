package com.cj.reocrd.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.cj.reocrd.view.activity.MyActivity.pNumber;

/**
 * 已有手机号
 */

public class UpdatePhoneFragment extends BaseFragment {
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.update_phone_number)
    TextView updatePhoneNumber;

    @Override
    protected void initPresenter() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_update_phone;
    }

    @Override
    public void initView() {
        titleCenter.setText(getString(R.string.my_update_phone));
        if(!TextUtils.isEmpty(pNumber)){
            updatePhoneNumber.setText(pNumber);
        }
    }


    @OnClick({R.id.title_left, R.id.phone_do})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                getMyActivity().getViewPager().setCurrentItem(0);
                break;
            case R.id.phone_do:
                getMyActivity().getViewPager().setCurrentItem(2);
                break;
        }
    }

}
