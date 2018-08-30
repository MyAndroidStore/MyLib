package org.wjh.androidlib.passwordview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.wjh.androidlib.R;

import java.util.List;

public class PasswordAdapter extends BaseAdapter {

    private Context mContext;
    private List<Integer> mList;
    private LayoutInflater mInflater;

    public PasswordAdapter(Context context, List<Integer> list) {
        this.mContext = context;
        this.mList = list;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        int type = getItemViewType(position);

        ViewHolder0 viewHolder0 = null;
        ViewHolder1 viewHolder1 = null;
        ViewHolder2 viewHolder2 = null;

        if (convertView == null) {
            switch (type) {
                case 0:
                    viewHolder0 = new ViewHolder0();
                    convertView = mInflater.inflate(R.layout.mylib_password_view_item0,
                            null);
                    viewHolder0.tv = (TextView) convertView
                            .findViewById(R.id.tv);
                    viewHolder0.tv.setText(mList.get(position) + "");
                    convertView.setTag(viewHolder0);
                    break;
                case 1:
                    viewHolder1 = new ViewHolder1();
                    convertView = mInflater.inflate(R.layout.mylib_password_view_item1,
                            null);
                    convertView.setTag(viewHolder1);
                    break;
                case 2:
                    viewHolder2 = new ViewHolder2();
                    convertView = mInflater.inflate(R.layout.mylib_password_view_item2,
                            null);
                    convertView.setTag(viewHolder2);
                    break;
            }

        } else {

            switch (type) {
                case 0:
                    viewHolder0 = (ViewHolder0) convertView.getTag();
                    viewHolder0.tv.setText(mList.get(position) + "");
                    break;
                case 1:
                    viewHolder1 = (ViewHolder1) convertView.getTag();
                    break;
                case 2:
                    viewHolder2 = (ViewHolder2) convertView.getTag();
                    break;
            }

        }
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 9) {
            return 1;
        } else if (position == 11) {
            return 2;
        } else {
            return 0;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }


    public void clearAll() {
        mList.clear();
        notifyDataSetChanged();
    }


    static class ViewHolder0 {
        TextView tv;
    }

    static class ViewHolder1 {
    }

    static class ViewHolder2 {

    }
}
