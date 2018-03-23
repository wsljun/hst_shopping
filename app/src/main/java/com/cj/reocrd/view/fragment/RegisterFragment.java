package com.cj.reocrd.view.fragment;

import android.view.View;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.contract.IndexContract;
import com.cj.reocrd.base.BaseFragment;
import com.cj.reocrd.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/15.
 */

public class RegisterFragment extends BaseFragment<IndexContract.Presenter> implements IndexContract.View {
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
        return R.layout.fragment_register;
    }

    @Override
    public void initView() {
        titleCenter.setText(R.string.register_title);
    }

    @OnClick({R.id.register, R.id.register_getcode, R.id.title_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register:
                ToastUtil.showShort( "register");
                break;
            case R.id.register_getcode: // todo get code
                ToastUtil.showShort( "register_getcode");
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
}
