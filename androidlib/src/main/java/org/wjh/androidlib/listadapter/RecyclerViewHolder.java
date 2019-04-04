package org.wjh.androidlib.listadapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mCacheViews;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        mCacheViews = new SparseArray<>();
    }


    //获得常用控件
    public ImageView getImageView(int id) {
        return getView(id);
    }

    public TextView getTextView(int id) {
        return getView(id);
    }

    public EditText getEditText(int id) {
        return getView(id);
    }

    public Button getButton(int id) {
        return getView(id);
    }

    public ImageButton getImageButton(int id) {
        return getView(id);
    }

    public CheckBox getCheckBox(int id) {
        return getView(id);
    }

    public ProgressBar getProgressBar(int id) {
        return getView(id);
    }

    public LinearLayout getLinearLayout(int id) {
        return getView(id);
    }

    public RelativeLayout getRelativeLayout(int id) {
        return getView(id);
    }

    public FrameLayout getFrameLayout(int id) {
        return getView(id);
    }

    public CircleImageView getCircleImageView(int id) {
        return getView(id);
    }

    public Switch getSwitch(int id) {
        return getView(id);
    }

    public ToggleButton getToggleButton(int id) {
        return getView(id);
    }

    public <T extends View> T getView(int resId) {
        View view = mCacheViews.get(resId);
        if (view == null) {
            view = itemView.findViewById(resId);
            mCacheViews.put(resId, view);
        }
        return (T) view;
    }
}
