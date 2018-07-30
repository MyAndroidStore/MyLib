package org.wjh.androidlib.utils;

import android.app.Activity;
import android.view.ViewGroup;

import org.wjh.androidlib.tsnackbar.Prompt;
import org.wjh.androidlib.tsnackbar.TSnackbar;

public class TsnackBarUtils {


    public static void showShortSuccessNoIcon(Activity activity, String msg) {

        ViewGroup viewGroup = (ViewGroup) activity.findViewById(android.R.id.content).getRootView();

        TSnackbar snackbar = TSnackbar.make(viewGroup, msg, TSnackbar.LENGTH_SHORT, TSnackbar.APPEAR_FROM_TOP_TO_DOWN);

        snackbar.setPromptThemBackground(Prompt.SUCCESS_NOICON);

        snackbar.show();
    }

    public static void showShortErrorNoIcon(Activity activity, String msg) {

        ViewGroup viewGroup = (ViewGroup) activity.findViewById(android.R.id.content).getRootView();

        TSnackbar snackbar = TSnackbar.make(viewGroup, msg, TSnackbar.LENGTH_SHORT, TSnackbar.APPEAR_FROM_TOP_TO_DOWN);

        snackbar.setPromptThemBackground(Prompt.ERROR_NOICON);

        snackbar.show();
    }

    public static void showShortWaringNoIcon(Activity activity, String msg) {

        ViewGroup viewGroup = (ViewGroup) activity.findViewById(android.R.id.content).getRootView();

        TSnackbar snackbar = TSnackbar.make(viewGroup, msg, TSnackbar.LENGTH_SHORT, TSnackbar.APPEAR_FROM_TOP_TO_DOWN);

        snackbar.setPromptThemBackground(Prompt.WARNING_NOICON);

        snackbar.show();
    }
}
