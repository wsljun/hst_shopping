package com.cj.reocrd.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/17.
 */

public class AddressEditActivity extends BaseActivity {
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.address_name_et)
    EditText addressNameEt;
    @BindView(R.id.address_phone_et)
    EditText addressPhoneEt;
    @BindView(R.id.address_dz)
    TextView addressDz;
    @BindView(R.id.address_dz_detail)
    EditText addressDzDetail;
    @BindView(R.id.address_logo)
    EditText addressLogo;

    @Override
    public void initFragment(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_address_edit;
    }

    @Override
    public void initView() {
        titleCenter.setText(getString(R.string.address_edit));
    }

    @Override
    public void initPresenter() {

    }

    @OnClick({R.id.title_left,R.id.address_clear, R.id.address_dz_rl, R.id.address_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.address_clear:
                break;
            case R.id.address_dz_rl:
                break;
            case R.id.address_save:
                break;
        }
    }
}
