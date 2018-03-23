package com.cj.reocrd.view.fragment;

import android.widget.EditText;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/15.
 */

public class UpdatePwdFragment extends BaseFragment {
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

    @Override
    protected void initPresenter() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_update_pwd;
    }

    @Override
    public void initView() {
        titleCenter.setText(getString(R.string.my_update_pwd));
    }


    @OnClick(R.id.update_login)
    public void onViewClicked() {
        getMyActivity().getViewPager().setCurrentItem(0);
    }
}
