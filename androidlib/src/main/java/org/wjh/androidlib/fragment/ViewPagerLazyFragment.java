package org.wjh.androidlib.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 用于ViewPager懒加载的Fragment
 * 1.如果此fragment是ViewPager中的第一页 public abstract boolean isFirstPage()请返回true
 * 其他页面返回false
 * 2.
 */
public abstract class ViewPagerLazyFragment extends Fragment {

    protected Activity mActivity;

    /**
     * 是否可见状态 为了避免和{@link Fragment#isVisible()}冲突 换个名字
     */
    private boolean isFragmentVisible = false;
    /**
     * 是否第一创建
     */
    private boolean isFirstViewCreated = false;

    /**
     * 是否是第一页
     */
    private boolean isFirstPage = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
        isFirstPage = isFirstPage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(inflateLayoutRes(), container, false);
        initUI(view);
        initEvent(view);
        return view;
    }

    /**
     * 填充布局资源
     */
    public abstract int inflateLayoutRes();

    public abstract boolean isFirstPage();

    /**
     * 初始化UI(创建适配器，初始化基本信息)
     */
    protected abstract void initUI(View view);

    protected abstract void initEvent(View view);


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // view已初始化完成
        isFirstViewCreated = true;
        // 第一页数据立即去执行加载
        if (isFirstPage)
            initData();
    }

    /**
     * 如果是与ViewPager一起使用，调用的是setUserVisibleHint
     *
     * @param isVisibleToUser 是否显示出来了
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            onVisible();
        } else {
            onInvisible();
        }
    }

    protected void onVisible() {
        isFragmentVisible = true;
        lazyLoad();
    }

    protected void onInvisible() {
        isFragmentVisible = false;
    }

    /**
     * 要实现延迟加载Fragment内容,需要在 onCreateView
     * UI已创建完毕以及可见情况下加载数据
     * 如果是第一页 并不执行懒加载的判断方法
     */
    protected void lazyLoad() {

        if (isFirstPage) {
            return;
        }

        if (isFragmentVisible && isFirstViewCreated) {
            isFirstViewCreated = false;
            initData();
        }
    }

    protected abstract void initData();
}
