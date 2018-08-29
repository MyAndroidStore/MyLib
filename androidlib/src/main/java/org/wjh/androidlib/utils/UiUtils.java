package org.wjh.androidlib.utils;

import android.view.View;
import android.view.ViewGroup;

public class UiUtils {

    /**
     * 设置view margin
     *
     * @param v
     * @param l
     * @param t
     * @param r
     * @param b
     */
    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }
}
