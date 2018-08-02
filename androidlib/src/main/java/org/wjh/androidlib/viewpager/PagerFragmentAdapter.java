package org.wjh.androidlib.viewpager;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by admin on 2017/6/28.
 */

public class PagerFragmentAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragments;
    private List<String> mTitles;
    private String[] mTitless;


    public PagerFragmentAdapter(FragmentManager fm, @NonNull List<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    public PagerFragmentAdapter(FragmentManager fm, @NonNull List<Fragment> fragments, List<String> titles) {
        super(fm);
        mFragments = fragments;
        mTitles = titles;
    }

    public PagerFragmentAdapter(FragmentManager fm, @NonNull List<Fragment> fragments, String[] titless) {
        super(fm);
        mFragments = fragments;
        mTitless = titless;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        String pageTitle = "";

        if (mTitles != null && mTitles.size() == mFragments.size()) {
            pageTitle = mTitles.get(position);
        } else if (mTitless != null && mTitless.length == mFragments.size()) {
            pageTitle = mTitless[position];
        }


        return pageTitle;
    }
}
