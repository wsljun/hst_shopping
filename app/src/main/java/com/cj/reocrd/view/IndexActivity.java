package com.cj.reocrd.view;

import android.view.KeyEvent;

import com.cj.reocrd.R;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.base.BaseFragment;
import com.cj.reocrd.view.adapter.ViewPagerAdapter;
import com.cj.reocrd.view.fragment.LeadFragment;
import com.cj.reocrd.view.fragment.LoginCodeFragment;
import com.cj.reocrd.view.fragment.LoginPwdFragment;
import com.cj.reocrd.view.fragment.RegisterFragment;
import com.cj.reocrd.view.fragment.UpdatePwdFragment;
import com.cj.reocrd.view.view.MViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class IndexActivity extends BaseActivity {
    @BindView(R.id.vp_index)
    MViewPager vpIndex;
    List<BaseFragment> fragments = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_index;
    }

    @Override
    public void initView() {
        fragments.add(new LeadFragment());//0
        fragments.add(new LoginPwdFragment());//1密码登陆
        fragments.add(new RegisterFragment());//2注册
        fragments.add(new LoginCodeFragment());//3验证码登陆
        fragments.add(new UpdatePwdFragment());//4忘记密码，修改密码
        vpIndex.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments, null));
    }

    @Override
    public void initPresenter() {

    }

    public MViewPager getVpLogin() {
        return vpIndex;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            switch (vpIndex.getCurrentItem()) {
                case 0:
                    finish();
                    return true;
                default:
                    vpIndex.setCurrentItem(0);
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
