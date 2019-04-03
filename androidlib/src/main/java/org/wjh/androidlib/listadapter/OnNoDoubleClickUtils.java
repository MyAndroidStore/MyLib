package org.wjh.androidlib.listadapter;

public class OnNoDoubleClickUtils {

    // 两次点击按钮之间的点击间隔不能少于800毫秒
    private static final int MIN_CLICK_DELAY_TIME = 800;
    private static long lastClickTime;


    public static synchronized boolean isDoubleClick() {

        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            // 超过点击间隔后再将lastClickTime重置为当前点击时间
            lastClickTime = curClickTime;
            return false;
        }

        return true;
    }
}
