package org.wjh.mylib.adapter;

import android.content.Context;

import org.wjh.androidlib.listadapter.LoadMoreSingleLayoutAdapter;
import org.wjh.mylib.R;

public class Adapter1 extends LoadMoreSingleLayoutAdapter<String>{


    public Adapter1(Context context, int layoutResId) {
        super(context, layoutResId);
    }

    @Override
    public void bind(ViewHolder holder, String s, int position) {
        holder.getTextView(R.id.tv).setText(s);
    }
}
