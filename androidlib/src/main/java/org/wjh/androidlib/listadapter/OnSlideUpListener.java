package org.wjh.androidlib.listadapter;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by macpro on 2018/1/16.
 */

public abstract class OnSlideUpListener extends RecyclerView.OnScrollListener {

    //用来标记是否正在向上滑动
    private boolean isSlidingUpward = false;

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        // 变量 最后一个可见的position
        int lastItemPosition = -1;
        // 布局管理器
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();

        // 开始滚动（SCROLL_STATE_FLING），正在滚动(SCROLL_STATE_TOUCH_SCROLL), 已经停止（SCROLL_STATE_IDLE）

        // 当不滑动时
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            //获取最后一个完全显示的itemPosition

            if (manager instanceof GridLayoutManager) {
                //通过LayoutManager找到当前显示的最后的item的position
                lastItemPosition = ((GridLayoutManager) manager).findLastCompletelyVisibleItemPosition();
            } else if (manager instanceof LinearLayoutManager) {
                lastItemPosition = ((LinearLayoutManager) manager).findLastCompletelyVisibleItemPosition();
            } else if (manager instanceof StaggeredGridLayoutManager) {
                //因为StaggeredGridLayoutManager的特殊性可能导致最后显示的item存在多个，所以这里取到的是一个数组
                //得到这个数组后再取到数组中position值最大的那个就是最后显示的position值了
                int[] lastPositions = new int[((StaggeredGridLayoutManager) manager).getSpanCount()];
                ((StaggeredGridLayoutManager) manager).findLastCompletelyVisibleItemPositions(lastPositions);
                lastItemPosition = findMax(lastPositions);
            }

            int itemCount = manager.getItemCount();

            // 判断是否滑动到了最后一个item，并且是向上滑动
            if (lastItemPosition == (itemCount - 1) && isSlidingUpward) {
                //加载更多
                onLoadMore();
            }
        }
    }


    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        // 大于0表示正在向上滑动，小于等于0表示停止或向下滑动
        isSlidingUpward = dy > 0;
    }

    /**
     * 加载更多回调
     */
    public abstract void onLoadMore();

}
