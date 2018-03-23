package com.cj.reocrd.view.fragment;

import android.view.View;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.base.BaseFragment;
import com.cj.reocrd.contract.IndexContract;
import com.cj.reocrd.presenter.IndexPresenter;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.view.activity.MainActivity;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/15.
 */

public class LoginFragment extends BaseFragment<IndexPresenter> implements IndexContract.View{
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;

    @Override
    protected void initPresenter() {
       mPresenter.setVM(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void initView() {
        titleCenter.setText(R.string.login_title);
    }


    @OnClick({R.id.login_lose, R.id.login,R.id.title_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_lose:
                ToastUtil.showShort("login_lose");
                break;
            case R.id.login:
                //TODO do login requst
                startActivity(MainActivity.class);
//                Intent intent = new Intentntent(mActivity, MainActivity.class);
//                startActivity(intent);
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
