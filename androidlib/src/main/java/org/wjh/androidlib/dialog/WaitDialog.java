package org.wjh.androidlib.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import org.wjh.androidlib.R;

public class WaitDialog extends LoadingDialog {

    private AlertDialog dialog;

    public WaitDialog(Context context) {
        super(context);

        dialog = new AlertDialog.Builder(context, R.style.DialogNoBackgroundDimStyle).create();
        dialog.setCancelable(false);
    }

    @Override
    public void show() {

        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
            dialog.setContentView(R.layout.waiting_dialog);
        }

    }

    @Override
    public void dismiss() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

    @Override
    public boolean isShowing() {
        return dialog != null && dialog.isShowing();
    }
}
