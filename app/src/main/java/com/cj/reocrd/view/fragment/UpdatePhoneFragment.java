package com.cj.reocrd.view.fragment;

import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/3/17.
 */

public class UpdatePhoneFragment extends BaseFragment {
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;

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
    }

}
