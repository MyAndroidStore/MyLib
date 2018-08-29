package org.wjh.mylib.adapter;

import android.content.Context;

import org.wjh.androidlib.listadapter.RecyclerViewHolder;
import org.wjh.androidlib.listadapter.SimpleSingleLayoutAdapter;
import org.wjh.mylib.R;
import org.wjh.mylib.bean.Photos;

public class Adapter3 extends SimpleSingleLayoutAdapter<Photos>{

    public Adapter3(Context context, int layoutResId) {
        super(context, layoutResId);
    }

    @Override
    public void bind(RecyclerViewHolder holder, Photos photos, int position) {

        holder.getTextView(R.id.uri).setText(photos.getUri());
        holder.getTextView(R.id.path).setText(photos.getPath());
    }
}
