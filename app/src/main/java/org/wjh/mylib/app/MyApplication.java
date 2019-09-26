package org.wjh.mylib.app;


import android.app.Application;

import org.wjh.androidlib.tsnackbar.TSnackInit;

/**
 * 作者： macpro  on 2018/6/17.
 * 邮箱： xxx.com
 */
public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        TSnackInit.init(180);
    }
}
