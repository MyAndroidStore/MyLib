package org.wjh.androidlib.nohttp;

import android.content.Context;

import org.wjh.androidlib.dialog.LoadingDialog;

public abstract class NoHttpSimpleCallBack extends NoHttpCallBack {

    public NoHttpSimpleCallBack(Context context) {
        super(context);
    }

    public NoHttpSimpleCallBack(Context context, LoadingDialog loadingDialog) {
        super(context, loadingDialog);
    }

    @Override
    public void onFailed() {

    }
}
