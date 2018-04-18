package com.cj.reocrd.view.activity;

import android.os.Bundle;
import android.view.KeyEvent;

import com.cj.reocrd.R;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.base.BaseFragment;
import com.cj.reocrd.view.adapter.ViewPagerAdapter;
import com.cj.reocrd.view.fragment.AddPhoneFragment;
import com.cj.reocrd.view.fragment.MyFragment;
import com.cj.reocrd.view.fragment.UpdateNameFragment;
import com.cj.reocrd.view.fragment.UpdatePhoneFragment;
import com.cj.reocrd.view.fragment.UpdatePwdFragment;
import com.cj.reocrd.view.view.MViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/3/17.
 */

public class MyActivity extends BaseActivity {
    @BindView(R.id.my_viewPager)
    MViewPager myViewPager;
    List<BaseFragment> fragments = new ArrayList<>();

    public static String pNumber = "";

    @Override
    public void initFragment(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my;
    }

    public MViewPager getViewPager() {
        return myViewPager;
    }

    @Override
    public void initView() {
        fragments.add(new MyFragment());
        fragments.add(new UpdateNameFragment());
        fragments.add(new AddPhoneFragment());
        fragments.add(new UpdatePhoneFragment());
        fragments.add(new UpdatePwdFragment());
        myViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments, null));
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            switch (myViewPager.getCurrentItem()) {
                case 0:
                    finish();
                    return true;
                case 1:
                    myViewPager.setCurrentItem(0);
                    return true;
                case 2:
                    myViewPager.setCurrentItem(0);
                    return true;
                case 4:
                    myViewPager.setCurrentItem(0);
                    return true;
                case 5:
                    myViewPager.setCurrentItem(4);
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
