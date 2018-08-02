package org.wjh.androidlib.viewpager;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.List;

/**
 * 可动态添加、删除的ViewPager适配器
 */
public class PagerFragmentDynamicsAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragments = null;

    public PagerFragmentDynamicsAdapter(FragmentManager fm, @NonNull List<Fragment> fragments) {
        super(fm);
        this.mFragments = fragments;
    }

    public void updateData(@NonNull List<Fragment> totalData) {
        this.mFragments.clear();
        this.mFragments.addAll(totalData);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    /**
     * 动态添加fragment 需要重写此方法
     */
    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    /**
     * 删除指定位置的fragment
     */
    public void removeByIndex(int pos) {
        mFragments.remove(pos);
        notifyDataSetChanged();
    }
}
