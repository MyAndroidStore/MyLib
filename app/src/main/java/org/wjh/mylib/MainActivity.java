package org.wjh.mylib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.wjh.androidlib.utils.ToastUtils;
import org.wjh.mylib.simple.BannerActivity;
import org.wjh.mylib.simple.Bottom1Activity;
import org.wjh.mylib.simple.Bottom2Activity;
import org.wjh.mylib.simple.ImgChoiceActivity;
import org.wjh.mylib.simple.List1Activity;
import org.wjh.mylib.simple.List2Activity;

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

    public void bottom1(View view) {
        startActivity(Bottom1Activity.class);
    }

    public void bottom2(View view) {
        startActivity(Bottom2Activity.class);
    }

    public void rv1(View view) {
        startActivity(List1Activity.class);
    }

    public void rv2(View view) {
        startActivity(List2Activity.class);
    }

    public void imgChoice(View view) {
        startActivity(ImgChoiceActivity.class);
    }
}
