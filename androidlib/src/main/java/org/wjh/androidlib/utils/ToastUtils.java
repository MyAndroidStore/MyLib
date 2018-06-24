package org.wjh.androidlib.utils;

import android.app.Application;
import android.widget.Toast;

import org.wjh.androidlib.exceptions.ToastUtilsInitException;

/**
 * 作者： macpro  on 2018/6/17.
 * 邮箱： xxx.com
 */
public class ToastUtils {

    private static ToastUtils instance;

    private Toast mToast = null;

    private static Application mContext;

    private ToastUtils() {

        if (mContext == null)
            throw new ToastUtilsInitException();
    }

    public static ToastUtils getInstance() {
        if (instance == null)
            synchronized (ToastUtils.class) {
                if (instance == null)
                    instance = new ToastUtils();
            }
        return instance;
    }

    // 需要在Application进行全局初始化
    public static void init(Application context) {
        mContext = context;
    }

    public void longToast(String msg) {
        cancle();
        mToast = Toast.makeText(mContext, msg, Toast.LENGTH_LONG);
        mToast.show();
    }

    public void longToast(int stringResMsg) {
        cancle();
        mToast = Toast.makeText(mContext, stringResMsg, Toast.LENGTH_LONG);
        mToast.show();
    }

    public void shortToast(String msg) {
        cancle();
        mToast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
        mToast.show();
    }

    public void shortToast(int stringResMsg) {
        cancle();
        mToast = Toast.makeText(mContext, stringResMsg, Toast.LENGTH_SHORT);
        mToast.show();
    }

    private void cancle() {
        if (mToast != null) {
            mToast.cancel();
        }
    }
}


