package org.wjh.androidlib.dialog;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;

import org.wjh.androidlib.R;

public class WaitCanBackDialog extends LoadingDialog implements DialogInterface.OnKeyListener {

    private AlertDialog dialog;
    private DismissListener listener;

    public WaitCanBackDialog(DismissListener listener) {
        this.listener = listener;
    }

    @Override
    public void show() {

        if (dialog == null && getmContext() != null) {
            dialog = new AlertDialog.Builder(getmContext(), R.style.DialogNoBackgroundDimStyle).create();
            dialog.setCancelable(false);

            dialog.setOnKeyListener(this);
        }


        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
            dialog.setContentView(R.layout.mylib_waiting_dialog);
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

    @Override
    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        dialogInterface.dismiss();
        if (listener != null)
            listener.goBack();
        return false;
    }
}
