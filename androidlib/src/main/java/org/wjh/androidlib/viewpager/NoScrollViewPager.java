package org.wjh.androidlib.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.lang.reflect.Field;

/**
 * Created by macpro on 2018/1/23.
 */

public class NoScrollViewPager extends ViewPager {

    public void setNeedScroll(boolean needScroll) {
        isNeedScroll = needScroll;
    }

    private boolean isNeedScroll = true;

    public NoScrollViewPager(Context context) {
        super(context);
        initViewPagerScroll(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViewPagerScroll(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isNeedScroll) {
            return super.onTouchEvent(ev);
        } else {
            return true;
        }
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // return false;//可行,不拦截事件,
        // return true;//不行,孩子无法处理事件
        //return super.onInterceptTouchEvent(ev);//不行,会有细微移动
        if (isNeedScroll) {
            return super.onInterceptTouchEvent(ev);
        } else {
            return false;
        }
    }


    public void initViewPagerScroll(Context context) {
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            mScroller.set(this, new ViewPagerNoScroller(context));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
