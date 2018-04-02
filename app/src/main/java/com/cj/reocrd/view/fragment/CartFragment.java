package com.cj.reocrd.view.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.base.BaseFragment;
import com.cj.reocrd.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/16.
 */

public class CartFragment extends BaseFragment {
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
        return R.layout.fragment_car;
    }

    @Override
    public void initView() {
        titleCenter.setText(getString(R.string.car));
    }

    @OnClick({R.id.title_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                getMainActivity().getViewPager().setCurrentItem(0);
                break;
        }
    }

    @Override
    public void getArgumentData(Bundle arguments) {
        super.getArgumentData(arguments);
        ToastUtil.showShort(arguments.getString("key"));
    }

    @Override
    public void putArgumentData(BaseFragment baseFragment, int position) {
        super.putArgumentData(this, position);
        Bundle  b = new Bundle();
        b.putCharSequence("key",position+"; This is Cart Fragment");
        baseFragment.setArguments(b);
    }

}
