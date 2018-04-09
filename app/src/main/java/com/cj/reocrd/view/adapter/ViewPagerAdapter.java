package com.cj.reocrd.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.cj.reocrd.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thinkpad on 2017/5/31.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> list = new ArrayList<>();
    private List<String> mTitles = new ArrayList<>();
    private int currPosition = 0;
    private int prePosition = 0;

    public ViewPagerAdapter(FragmentManager fm, List<BaseFragment> list, List<String> mTitles) {
        super(fm);
        this.list = list;
        if (mTitles != null){
            this.mTitles = mTitles;
        }
    }



    @Override
    public Fragment getItem(int position) {
        Log.d("BaseFragment", "getItem: list.size= "+list.size());
        currPosition = position;
        if(prePosition != currPosition){
            // fragment 传值时应该获取上一个 fragment 所传递的值
            BaseFragment fragment = list.get(prePosition);
            if(null != fragment){
                fragment.putArgumentData(fragment,prePosition);
            }
        }
        prePosition = currPosition ;
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitles.size() > 0){
            return mTitles.get(position);
        }
        return super.getPageTitle(position);
    }
}