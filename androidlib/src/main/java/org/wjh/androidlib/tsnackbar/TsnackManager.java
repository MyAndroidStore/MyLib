package org.wjh.androidlib.tsnackbar;


import android.util.Log;

public class TsnackManager {

    private static final TsnackManager ourInstance = new TsnackManager();
    // 保证只存一个
    private static final TSnackbar[] tList = new TSnackbar[1];

    public static TsnackManager getInstance() {
        return ourInstance;
    }

    private TsnackManager() {
    }

    /**
     * show调用此方法
     */
    public void addTsnackBar(TSnackbar tSnackbar) {

        try {

            TSnackbar snackbar = tList[0];

            if (snackbar != null) {
                snackbar.getmView().clearAnimation();
                snackbar.getmParent().removeView(snackbar.getmView());

                // 不用的对象显式地设为null，有利于GC收集器判定
                // 为垃圾,从而提高了GC的效率
                snackbar = null;
            }

        } catch (Exception e) {
            Log.e("TsnackManager.addTsnack", e.getMessage());
        }

        tList[0] = tSnackbar;
    }
}
