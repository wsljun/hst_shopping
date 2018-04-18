package com.cj.reocrd.base;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cj.reocrd.utils.LogUtil;
import com.cj.reocrd.utils.TUtil;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.view.IndexActivity;
import com.cj.reocrd.view.activity.MainActivity;
import com.cj.reocrd.view.activity.MyActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/3/15.
 */

public abstract class BaseFragment<T extends BasePresenter> extends Fragment {
    public BaseActivity mActivity;
    public Unbinder unbinder;
    public T mPresenter;
    private final String TAG = "BaseFragment";
    private String mParam1;
    private boolean isFirstLoad = true;//是否是第一次加载
    private boolean isVisible;//是否对用户可见
    private boolean isInitView;//是否初始化控件

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (BaseActivity) getActivity();
        LogUtil.e(TAG, "onCreateView: ");
        if (savedInstanceState != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (ft != null && isAdded()) {
                ft.remove(this);
                ft.commit();
            }
            if (getParentFragment() != null) {
                FragmentTransaction pft = getParentFragment().getChildFragmentManager().beginTransaction();
                if (pft != null && isAdded()) {
                    pft.remove(this);
                    pft.commit();
                }
            }
        }

        mPresenter = TUtil.getT(this, 0);
        if (mPresenter != null) {
            mPresenter.mContext = this.getActivity();
        }
        initPresenter();
    }

    ////简单页面无需mvp就不用管此方法即可
    protected abstract void initPresenter();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
//        if (getArguments() != null) { // todo 此功能存在bug
////            getArgumentData(getArguments());
//        }
        unbinder = ButterKnife.bind(this, view);
        LogUtil.e(TAG, "onCreateView: ");
        init();
        isInitView = true;//已经初始化
        lazyLoad();//懒加载
        return view;
    }

    public void getArgumentData(Bundle arguments) {
        mParam1 = getArguments().getString("key");
//        ToastUtil.showShort(mParam1);
    }
    public void putArgumentData(BaseFragment baseFragment,int position) {
//        Bundle  b = new Bundle();
//        b.putCharSequence("key",baseFragment.getClass().getName()+position);
//        baseFragment.setArguments(b);
//        LogUtil.e(TAG,baseFragment.getClass().getName()+" ; "+position);
//        ToastUtil.showShort(mParam1);
    }

    private final void init() {
        initView();
    }



    public abstract int getLayoutId();

    public abstract void initView();

    public void initData() {
    }


    /**
     * 懒加载
     * 如果不是第一次加载、没有初始化控件、不对用户可见就不加载
     */
    protected  void lazyLoad(){
        if(!isFirstLoad || !isInitView || !isVisible){
            return;
        }
        initData();//初始化数据
//        isFirstLoad = false;//已经不是第一次加载
    }

    /**
     * 是否对用户可见
     * @param isVisibleToUser true:表示对用户可见，反之则不可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            isVisible = true;
            //这里根据需求，如果不要每次对用户可见的时候就加载就不要调用lazyLoad()这个方法，根据个人需求
            lazyLoad();
        }else{
            isVisible = false;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null){
//            mPresenter.onDestroy();
        }
        isInitView = false;
        unbinder.unbind();
        LogUtil.e(TAG, "onCreateView: ");
    }

    public IndexActivity getIndexActivity() {
        if (mActivity != null && !mActivity.isFinishing() && mActivity instanceof IndexActivity) {
            return (IndexActivity) mActivity;
        }
        return null;
    }

    public MainActivity getMainActivity() {
        if (mActivity != null && !mActivity.isFinishing() && mActivity instanceof MainActivity) {
            return (MainActivity) mActivity;
        }
        return null;
    }

    public MyActivity getMyActivity() {
        if (mActivity != null && !mActivity.isFinishing() && mActivity instanceof MyActivity) {
            return (MyActivity) mActivity;
        }
        return null;
    }


    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }




}
