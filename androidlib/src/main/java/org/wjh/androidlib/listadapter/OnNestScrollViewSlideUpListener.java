package org.wjh.androidlib.listadapter;


import android.support.v4.widget.NestedScrollView;

/**
 * Created by macpro on 2018/1/16.
 */

public abstract class OnNestScrollViewSlideUpListener implements NestedScrollView.OnScrollChangeListener {


    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

        if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
            onNvLoadMore();
        }
    }

    /**
     * 加载更多回调
     */
    public abstract void onNvLoadMore();

}
