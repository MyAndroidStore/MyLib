package org.wjh.androidlib.widget;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;



/**
 * Created by Administrator on 2016/12/21 0021.
 */

public class BammerImageLoader implements BannerLayout.ImageLoader {
    @Override
    public void displayImage(Context context, String path, ImageView imageView) {
        Glide.with(context).load(path)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }
}
