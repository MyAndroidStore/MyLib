package org.wjh.mylib.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import org.wjh.androidlib.listadapter.LoadMoreMultiLayoutAdapter;
import org.wjh.androidlib.listadapter.RecyclerViewHolder;
import org.wjh.mylib.R;

public class Adapter2 extends LoadMoreMultiLayoutAdapter<String> {

    public Adapter2(Context context) {
        super(context);
    }

    @Override
    public int getCustomItemViewType(int position) {

        return position % 2;
    }

    @Override
    public RecyclerViewHolder onCreateCustomViewHolder(ViewGroup parent, int viewType) {

        Log.e("Ddd", viewType + "");

        if (viewType == 1) {
            View inflate = getAttachInflater().inflate(R.layout.item1, parent, false);
            return new ViewHolder1(inflate);
        } else {
            View inflate = getAttachInflater().inflate(R.layout.item2, parent, false);
            return new ViewHolder2(inflate);
        }

    }

    @Override
    public void onBindCustomViewHolder(RecyclerViewHolder holder, String s, int position) {
        if (holder instanceof ViewHolder1) {
            holder.getTextView(R.id.tv).setText(s);
        } else if (holder instanceof ViewHolder2) {
            holder.getTextView(R.id.tv2).setText(s);
        }
    }


    // 有几个布局就创建几个ViewHolder
    private static class ViewHolder1 extends RecyclerViewHolder {

        ViewHolder1(View itemView) {
            super(itemView);
        }

    }

    private static class ViewHolder2 extends RecyclerViewHolder {

        ViewHolder2(View itemView) {
            super(itemView);
        }
    }
}
