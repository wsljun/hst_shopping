package com.cj.reocrd.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.cj.reocrd.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thinkpad on 2017/5/31.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> list = new ArrayList<>();
    private List<String> mTitles = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm, List<BaseFragment> list, List<String> mTitles) {
        super(fm);
        this.list = list;
        if (mTitles != null){
            this.mTitles = mTitles;
        }
    }


    @Override
    public Fragment getItem(int position) {
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