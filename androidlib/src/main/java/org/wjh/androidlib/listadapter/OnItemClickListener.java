package org.wjh.androidlib.listadapter;

import android.view.View;

public interface OnItemClickListener<T> {
    // 传递当前点击的对象（List对应位置的数据）与位置
    void onClick(T t, int position, View view);
}