package org.wjh.mylib.simple;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.listener.OnCheckedListener;

import org.wjh.androidlib.listadapter.NineGridItemDecoration;
import org.wjh.androidlib.listadapter.NineImageUrl;
import org.wjh.androidlib.listadapter.NineImageWechatAdapter;
import org.wjh.androidlib.matisse.GifSizeFilter;
import org.wjh.androidlib.permissions.GrantedListener;
import org.wjh.androidlib.permissions.PermissionUtils;
import org.wjh.mylib.R;

import java.util.ArrayList;
import java.util.List;

public class ImgChoiceActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NineImageWechatAdapter adapter;

    private static final int REQUEST_CODE_CHOOSE = 23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_choice);

        recyclerView = (RecyclerView) findViewById(R.id.rv);

        adapter = new NineImageWechatAdapter(this) {
            @Override
            protected ImageLoader initImageLoader() {
                return new ImageLoader() {
                    @Override
                    public void displayImage(Context context, String path, ImageView imageView) {
                        Glide.with(context).load(path)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(imageView);
                    }

                    @Override
                    public void displayImage(Context context, int resID, ImageView imageView) {
                        Glide.with(context).load(resID)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(imageView);
                    }
                };
            }
        };
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new NineGridItemDecoration(this));

        adapter.setOnAddOneImageListener(new NineImageWechatAdapter.OnAddOneImageListener() {
            @Override
            public void onClick() {
                select();
            }
        });

    }

    public void select() {

        PermissionUtils.requestPermission(this, "存储",
                new GrantedListener() {
                    @Override
                    public void successfully() {
                        Matisse.from(ImgChoiceActivity.this)
                                .choose(MimeType.ofAll(), false)
                                .countable(true)
                                .capture(true)
                                .captureStrategy(
                                        new CaptureStrategy(true, "com.zhihu.matisse.fileprovider"))
                                .maxSelectable(9)
                                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                                .gridExpectedSize(
                                        getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                                .thumbnailScale(0.85f)
                                .imageEngine(new GlideEngine())
                                .originalEnable(true)
                                .maxOriginalSize(10)
                                .setOnCheckedListener(new OnCheckedListener() {
                                    @Override
                                    public void onCheck(boolean isChecked) {
                                        // DO SOMETHING IMMEDIATELY HERE
                                        Log.e("isChecked", "onCheck: isChecked=" + isChecked);
                                    }
                                })
                                .forResult(REQUEST_CODE_CHOOSE);
                    }

                },
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        );
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {

            List<Uri> uris = Matisse.obtainResult(data);
            List<String> strings = Matisse.obtainPathResult(data);

            List<NineImageUrl> list = new ArrayList<>();

            for (int i = 0; i < uris.size(); i++) {
                list.add(new NineImageUrl(strings.get(i)));
            }

            adapter.addData(list);
        }
    }
}
