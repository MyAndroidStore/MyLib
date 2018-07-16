package org.wjh.mylib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.wjh.mylib.simple.BannerActivity;

public class MainActivity extends AppCompatActivity {

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
}
