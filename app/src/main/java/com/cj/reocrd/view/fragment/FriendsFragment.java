package com.cj.reocrd.view.fragment;

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

public class FriendsFragment extends BaseFragment {
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
        return R.layout.fragment_friends;
    }

    @Override
    public void initView() {
        titleLeft.setVisibility(View.GONE);
        titleCenter.setText(getString(R.string.friends));
        titleRight.setText(getString(R.string.friends_publish));
        titleRight.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.title_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_right:
                ToastUtil.showToastS(mActivity, "title_right");
                break;
        }
    }
}
