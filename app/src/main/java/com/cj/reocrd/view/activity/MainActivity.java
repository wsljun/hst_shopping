package com.cj.reocrd.view.activity;

import android.Manifest;
import android.app.Fragment;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.cj.reocrd.R;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.base.BaseFragment;
import com.cj.reocrd.base.BaseFragmentAdapter;
import com.cj.reocrd.contract.HomeContract;
import com.cj.reocrd.utils.LogUtil;
import com.cj.reocrd.utils.SPUtils;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.utils.UpdateUtil;
import com.cj.reocrd.view.adapter.ViewPagerAdapter;
import com.cj.reocrd.view.fragment.AllGoodsFragment;
import com.cj.reocrd.view.fragment.CartFragment;
import com.cj.reocrd.view.fragment.FriendsFragment;
import com.cj.reocrd.view.fragment.HomeFragment;
import com.cj.reocrd.view.fragment.MineFragment;
import com.cj.reocrd.view.view.MViewPager;
import com.jpeng.jptabbar.JPTabBar;
import com.jpeng.jptabbar.OnTabSelectListener;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

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
    public static MainActivity mainActivity = null;

    @Override
    public void initFragment(Bundle savedInstanceState) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        int currentTabPosition = 0;
        if (null == savedInstanceState) {
            mHomeFragment = new HomeFragment();
            mAllGoodsFragment = new AllGoodsFragment();
            mFriendsFragment = new FriendsFragment();
            mCartFragment = new CartFragment();
            mMineFragment = new MineFragment();

            fragmentTransaction.add(R.id.fl_body, mHomeFragment, "mHomeFragment");
            fragmentTransaction.add(R.id.fl_body, mAllGoodsFragment, "mAllGoodsFragment");
            fragmentTransaction.add(R.id.fl_body, mFriendsFragment, "mFriendsFragment");
            fragmentTransaction.add(R.id.fl_body, mCartFragment, "mCartFragment");
            fragmentTransaction.add(R.id.fl_body, mMineFragment, "mMineFragment");
        } else {
            mHomeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("mHomeFragment");
            mAllGoodsFragment = (AllGoodsFragment) getSupportFragmentManager().findFragmentByTag("mAllGoodsFragment");
            mFriendsFragment = (FriendsFragment) getSupportFragmentManager().findFragmentByTag("mFriendsFragment");
            mCartFragment = (CartFragment) getSupportFragmentManager().findFragmentByTag("mCartFragment");
            mMineFragment = (MineFragment) getSupportFragmentManager().findFragmentByTag("mMineFragment");
        }
        fragmentTransaction.commit();
        SwitchTo(currentTabPosition);
    }

    private void SwitchTo(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (position) {
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
    public void initData() {
        super.initData();
        mainActivity = this;
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Permission permission) throws Exception {
                        switch (permission.name) {
                            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                                if (permission.granted) {
                                } else if (permission.shouldShowRequestPermissionRationale) {
                                    ToastUtil.showToastS(mContext, "取消存储授权,不能存储图片文件");
                                } else {
                                    ToastUtil.showToastS(mContext, "您已经禁止弹出存储的授权操作,请在设置中手动开启");
                                }
                                break;
                            case Manifest.permission.CAMERA:
                                if (permission.granted) {
                                } else if (permission.shouldShowRequestPermissionRationale) {
                                    ToastUtil.showToastS(mContext, "取消照相机授权");
                                } else {
                                    ToastUtil.showToastS(mContext, "您已经禁止弹出照相机的授权操作,请在设置中手动开启");
                                }
                                break;
                            default:
                                break;
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
                        Log.i("--->>", "onError", throwable);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });

        doLogin();
    }

    @Override
    public void initView() {
        LogUtil.e(TAG, "initview");
        fragments.add(new HomeFragment());
        fragments.add(new AllGoodsFragment());
        fragments.add(new FriendsFragment());
        fragments.add(new CartFragment());
        fragments.add(new MineFragment());
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments, null));

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

    public void doLogin() {
        // 从本地读取上次登录成功时保存的用户登录信息
        String account = (String) SPUtils.get(this, SPUtils.SpKey.IM_ACCID, "");
        String token = (String) SPUtils.get(this, SPUtils.SpKey.IM_TOKEN, "");
        LoginInfo info = new LoginInfo(account, token);
        NimUIKit.login(info, new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo loginInfo) {
                //启动单聊界面
//                NimUIKit.startP2PSession(MyFansActivity.this, accid);
                // 启动群聊界面
                // NimUIKit.startTeamSession(MainActivity.this, "群ID");
            }

            @Override
            public void onFailed(int i) {
                switch (i) {
                    case 302:
                        Toast.makeText(MainActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                        break;
                    case 408:
                        Toast.makeText(MainActivity.this, "服务器没有相应", Toast.LENGTH_SHORT).show();
                        break;
                    case 415:
                        Toast.makeText(MainActivity.this, "网络断开或与服务器连接失败", Toast.LENGTH_SHORT).show();
                        break;
                    case 416:
                        Toast.makeText(MainActivity.this, "请求过度频繁", Toast.LENGTH_SHORT).show();
                        break;
                    case 417:
                        Toast.makeText(MainActivity.this, "当前用户已经登陆", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onException(Throwable throwable) {
                Log.i("SQW", "IM登陆异常");
            }
        });
    }

    // 用来计算返回键的点击间隔时间
    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtil.showShort( "再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
