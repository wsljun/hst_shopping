package com.cj.reocrd.view.fragment;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/17.
 */

public class UpdateNameFragment extends BaseFragment {
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.update_name_et)
    EditText updateNameEt;

    @Override
    protected void initPresenter() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_update_name;
    }

    @Override
    public void initView() {
        titleCenter.setText(getString(R.string.my_update_name));
        titleRight.setText(getString(R.string.confirm));
        titleRight.setVisibility(View.VISIBLE);
    }


    @OnClick({R.id.title_left,R.id.title_right, R.id.update_name_ib})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                getMyActivity().getViewPager().setCurrentItem(0);
                break;
            case R.id.title_right:
                break;
            case R.id.update_name_ib:
                updateNameEt.setText("");
                break;
        }
    }
}
