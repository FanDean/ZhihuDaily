package com.fandean.zhihudaily.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fan on 17-6-17.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        //因为FragmentPagerAdapter没有默认构造函数，所以需要
        super(fm);
    }

    public void addFragment(Fragment fragment,String fragmentTitle){
        mFragmentList.add(fragment);
        mFragmentTitleList.add(fragmentTitle);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //若想只显示图标，则返回null（前提是设置了图标）
        return mFragmentTitleList.get(position);
    }
}
