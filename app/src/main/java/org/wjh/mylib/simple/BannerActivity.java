package org.wjh.mylib.simple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.wjh.androidlib.banner.BannerImageLoader;
import org.wjh.androidlib.banner.BannerLayout;
import org.wjh.mylib.R;

import java.util.ArrayList;
import java.util.List;

public class BannerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);


        List<String> urls = getData();

        BannerLayout bannerLayout1 = findViewById(R.id.banner1);
        bannerLayout1.setImageLoader(new BannerImageLoader());
        bannerLayout1.setViewUrls(urls);

        BannerLayout bannerLayout2 = findViewById(R.id.banner2);
        bannerLayout2.setImageLoader(new BannerImageLoader());
        bannerLayout2.setViewUrls(urls);

        BannerLayout bannerLayout3 = findViewById(R.id.banner3);
        bannerLayout3.setImageLoader(new BannerImageLoader());
        bannerLayout3.setViewUrls(urls);

    }


    private List<String> getData() {

        List<String> urls = new ArrayList<>();
        urls.add("http://img3.imgtn.bdimg.com/it/u=2674591031,2960331950&fm=23&gp=0.jpg");
        urls.add("http://img5.imgtn.bdimg.com/it/u=3639664762,1380171059&fm=23&gp=0.jpg");
        urls.add("http://img0.imgtn.bdimg.com/it/u=1095909580,3513610062&fm=23&gp=0.jpg");

        return urls;
    }
}
