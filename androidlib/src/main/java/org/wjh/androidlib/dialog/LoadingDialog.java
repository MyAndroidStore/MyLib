package org.wjh.androidlib.dialog;

import android.content.Context;


/**
 * 作者： macpro  on 2018/6/17.
 * 邮箱： xxx.com
 */
public abstract class LoadingDialog {

    private Context mContext;

    public LoadingDialog(Context context) {
        this.mContext = context;
    }

    // 子类去实现显示dialog
    public abstract void show();
    // 子类去实现dialog关闭
    public abstract void dismiss();
    // 子类去实现dialog是否正在显示
    public abstract boolean isShowing();
}
