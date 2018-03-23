package com.cj.reocrd.view.fragment;

import android.view.View;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/15.
 */

public class LeadFragment extends BaseFragment {
    @BindView(R.id.lead_login)
    TextView tvLogin;
    @BindView(R.id.lead_register)
    TextView tvRegister;

    @Override
    protected void initPresenter() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_lead;
    }

    @Override
    public void initView() {

    }
    @OnClick({R.id.lead_login, R.id.lead_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lead_login:
                getIndexActivity().getVpLogin().setCurrentItem(1);
                break;
            case R.id.lead_register:
                getIndexActivity().getVpLogin().setCurrentItem(2);
                break;
        }
    }

}
