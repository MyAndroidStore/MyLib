package cn.qq.imageview.util;

import android.view.ViewGroup;

public class ViewHelper {

    public static void setScrollY(ViewGroup viewGroup, int value) {
        viewGroup.scrollTo(viewGroup.getScrollX(), value);
    }
}
