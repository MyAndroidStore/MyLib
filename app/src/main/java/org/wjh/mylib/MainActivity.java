package org.wjh.mylib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.wjh.androidlib.utils.ToastUtils;
import org.wjh.mylib.simple.BannerActivity;

public class MainActivity extends AppCompatActivity {

    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void banner(View view) {

        startActivity(BannerActivity.class);
    }


    private void startActivity(Class jumpClass) {
        startActivity(new Intent(this, jumpClass));
    }

    public void toast(View view) {
        ToastUtils.getInstance().shortToast(++index + "");
        ToastUtils.getInstance().longToast(++index + "");
    }
}
