package org.wjh.androidlib.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.view.ViewGroup;

import org.wjh.androidlib.exceptions.TsnackBarUtilsInitException;
import org.wjh.androidlib.tsnackbar.Prompt;
import org.wjh.androidlib.tsnackbar.ScreenUtil;
import org.wjh.androidlib.tsnackbar.TSnackbar;

public class TsnackBarUtils {

    private static TsnackBarUtils instance;
    private static Application mContext;

    public static int mToolBarHight = 0;
    public static int mStateBarHeight = 0;


    private TsnackBarUtils() {

        if (mContext == null)
            throw new TsnackBarUtilsInitException();
        else
            mStateBarHeight = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? ScreenUtil.getStatusHeight(mContext) : 0);
    }

    public static TsnackBarUtils getInstance() {
        if (instance == null)
            synchronized (TsnackBarUtils.class) {
                if (instance == null)
                    instance = new TsnackBarUtils();
            }
        return instance;
    }

    // 需要在Application进行全局初始化
    public static void init(Application context, int toolBarHightPX) {
        mToolBarHight = toolBarHightPX;
        mContext = context;
    }

    public void showShortSuccessNoIcon(Activity activity, String msg) {

        ViewGroup viewGroup = (ViewGroup) activity.findViewById(android.R.id.content).getRootView();

        TSnackbar snackbar = TSnackbar.make(viewGroup, msg, TSnackbar.LENGTH_SHORT, TSnackbar.APPEAR_FROM_TOP_TO_DOWN);

        snackbar.setPromptThemBackground(Prompt.SUCCESS_NOICON);

        snackbar.setMinHeight(mStateBarHeight, mToolBarHight);

        snackbar.show();
    }

    public void showShortErrorNoIcon(Activity activity, String msg) {

        ViewGroup viewGroup = (ViewGroup) activity.findViewById(android.R.id.content).getRootView();

        TSnackbar snackbar = TSnackbar.make(viewGroup, msg, TSnackbar.LENGTH_SHORT, TSnackbar.APPEAR_FROM_TOP_TO_DOWN);

        snackbar.setPromptThemBackground(Prompt.ERROR_NOICON);

        snackbar.setMinHeight(mStateBarHeight, mToolBarHight);

        snackbar.show();
    }

    public void showShortWaringNoIcon(Activity activity, String msg) {

        ViewGroup viewGroup = (ViewGroup) activity.findViewById(android.R.id.content).getRootView();

        TSnackbar snackbar = TSnackbar.make(viewGroup, msg, TSnackbar.LENGTH_SHORT, TSnackbar.APPEAR_FROM_TOP_TO_DOWN);

        snackbar.setPromptThemBackground(Prompt.WARNING_NOICON);

        snackbar.setMinHeight(mStateBarHeight, mToolBarHight);

        snackbar.show();
    }
}
