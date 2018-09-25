package com.davemorrissey.labs.utils;

import android.view.ViewGroup;

public class ViewHelper {

    public static void setScrollY(ViewGroup viewGroup, int value) {
        viewGroup.scrollTo(viewGroup.getScrollX(), value);
    }
}
