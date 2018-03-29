package com.cj.reocrd.view.activity;

import android.Manifest;
import android.app.Fragment;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.cj.reocrd.R;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.base.BaseFragment;
import com.cj.reocrd.base.BaseFragmentAdapter;
import com.cj.reocrd.contract.HomeContract;
import com.cj.reocrd.utils.LogUtil;
import com.cj.reocrd.view.adapter.ViewPagerAdapter;
import com.cj.reocrd.view.fragment.AllGoodsFragment;
import com.cj.reocrd.view.fragment.CartFragment;
import com.cj.reocrd.view.fragment.FriendsFragment;
import com.cj.reocrd.view.fragment.HomeFragment;
import com.cj.reocrd.view.fragment.MineFragment;
import com.cj.reocrd.view.view.MViewPager;
import com.jpeng.jptabbar.JPTabBar;
import com.jpeng.jptabbar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/3/16.
 */

public class MainActivity extends BaseActivity {

    @BindView(R.id.main_viewPager)
    MViewPager viewPager;
    @BindView(R.id.main_tabLayout)
    JPTabBar tabLayout;
    List<BaseFragment> fragments = new ArrayList<>();
    private HomeFragment mHomeFragment;
    private AllGoodsFragment mAllGoodsFragment;
    private FriendsFragment mFriendsFragment;
    private CartFragment mCartFragment;
    private MineFragment mMineFragment;
    private String TAG = "MainActivity";

    @Override
    public void initFragment(Bundle savedInstanceState) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        int currentTabPosition=0;
        if(null==savedInstanceState){
            mHomeFragment = new HomeFragment();
            mAllGoodsFragment = new AllGoodsFragment();
            mFriendsFragment = new FriendsFragment();
            mCartFragment = new CartFragment();
            mMineFragment = new MineFragment();
            
            fragmentTransaction.add(R.id.fl_body,mHomeFragment,"mHomeFragment");
            fragmentTransaction.add(R.id.fl_body,mAllGoodsFragment,"mAllGoodsFragment");
            fragmentTransaction.add(R.id.fl_body,mFriendsFragment,"mFriendsFragment");
            fragmentTransaction.add(R.id.fl_body,mCartFragment,"mCartFragment");
            fragmentTransaction.add(R.id.fl_body,mMineFragment,"mMineFragment");
        }else{
            mHomeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("mHomeFragment");
            mAllGoodsFragment = (AllGoodsFragment) getSupportFragmentManager().findFragmentByTag("mAllGoodsFragment");
            mFriendsFragment = (FriendsFragment) getSupportFragmentManager().findFragmentByTag("mFriendsFragment");
            mCartFragment= (CartFragment) getSupportFragmentManager().findFragmentByTag("mCartFragment");
            mMineFragment= (MineFragment) getSupportFragmentManager().findFragmentByTag("mMineFragment");
        }
        fragmentTransaction.commit();
        SwitchTo(currentTabPosition);
    }

    private void SwitchTo(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (position){
            case 0:
                transaction.show(mHomeFragment);
                transaction.hide(mAllGoodsFragment);
                transaction.hide(mFriendsFragment);
                transaction.hide(mCartFragment);
                transaction.hide(mMineFragment);
                transaction.commitAllowingStateLoss();
                break;
            case 1:
                transaction.hide(mHomeFragment);
                transaction.show(mAllGoodsFragment);
                transaction.hide(mFriendsFragment);
                transaction.hide(mCartFragment);
                transaction.hide(mMineFragment);
                transaction.commitAllowingStateLoss();
                break;
            case 2:
                transaction.hide(mHomeFragment);
                transaction.hide(mAllGoodsFragment);
                transaction.show(mFriendsFragment);
                transaction.hide(mCartFragment);
                transaction.hide(mMineFragment);
                transaction.commitAllowingStateLoss();
                break;
            case 3:
                transaction.hide(mHomeFragment);
                transaction.hide(mAllGoodsFragment);
                transaction.hide(mFriendsFragment);
                transaction.show(mCartFragment);
                transaction.hide(mMineFragment);
                transaction.commitAllowingStateLoss();
                break;
            case 4:
                transaction.hide(mHomeFragment);
                transaction.hide(mAllGoodsFragment);
                transaction.hide(mFriendsFragment);
                transaction.hide(mCartFragment);
                transaction.show(mMineFragment);
                transaction.commitAllowingStateLoss();
                break;
                default:
        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    public MViewPager getViewPager() {
        return viewPager;
    }




    @Override
    public void initView() {
        LogUtil.e(TAG,"initview");
        // todo test permissions
        if(hasPermissions(this, Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA)){
            requestPerminssions(Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.CAMERA);
        }
        fragments.add(new HomeFragment());
        fragments.add(new AllGoodsFragment());
        fragments.add(new FriendsFragment());
        fragments.add(new CartFragment());
        fragments.add(new MineFragment());
        viewPager.setAdapter(new BaseFragmentAdapter(getSupportFragmentManager(), fragments, null));

        tabLayout.setTitles(getString(R.string.home), getString(R.string.all), getString(R.string.friends), getString(R.string.car), getString(R.string.mine))
                .setNormalIcons(R.mipmap.shouye2, R.mipmap.quanbushangpin, R.mipmap.quanzi, R.mipmap.guwuche, R.mipmap.wode)
                .setSelectedIcons(R.mipmap.shouye, R.mipmap.quanbushangpinxuanzhjong, R.mipmap.quanzixuanzhong, R.mipmap.goouwucheshixin, R.mipmap.wodeshixin)
                .generate();
        tabLayout.setIconSize(19);
        tabLayout.setContainer(viewPager);

    }

    @Override
    public void initPresenter() {
        //todo 需要绑定view 时
//        mPresenter.setVM(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.e(TAG, "onDestroy: ");
    }

}
