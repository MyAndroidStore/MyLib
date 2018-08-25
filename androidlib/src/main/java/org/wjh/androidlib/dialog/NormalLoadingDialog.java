package org.wjh.androidlib.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import org.wjh.androidlib.R;

public class NormalLoadingDialog {

    private AlertDialog dialog;
    private Context mContext;

    public NormalLoadingDialog(Context context) {
        this.mContext = context;
    }

    public void show() {

        if (dialog == null && mContext != null) {
            dialog = new AlertDialog.Builder(mContext, R.style.DialogNoBackgroundDimStyle).create();
            dialog.setCancelable(false);
        }

        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
            dialog.setContentView(R.layout.mylib_waiting_dialog);
        }
    }


    public void dismiss() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }


    public boolean isShowing() {
        return dialog != null && dialog.isShowing();
    }
}
