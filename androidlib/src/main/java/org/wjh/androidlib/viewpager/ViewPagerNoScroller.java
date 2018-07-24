package org.wjh.androidlib.viewpager;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * 作者： macpro  on 2018/6/15.
 * 邮箱： xxx.com
 */
public class ViewPagerNoScroller extends Scroller {

    public ViewPagerNoScroller(Context context) {
        super(context);
    }

    public ViewPagerNoScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public ViewPagerNoScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }


    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, 0);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, 0);
    }
}
