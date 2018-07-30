package org.wjh.mylib.simple;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.listener.OnCheckedListener;

import org.wjh.androidlib.matisse.GifSizeFilter;
import org.wjh.androidlib.permissions.GrantedListener;
import org.wjh.androidlib.permissions.PermissionUtils;
import org.wjh.mylib.R;
import org.wjh.mylib.adapter.Adapter3;
import org.wjh.mylib.bean.Photos;

import java.util.ArrayList;
import java.util.List;

public class ImgChoiceActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Adapter3 adapter;

    private static final int REQUEST_CODE_CHOOSE = 23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_choice);

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        adapter = new Adapter3(this, R.layout.item3);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    public void select(View view) {

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

            List<Photos> list = new ArrayList<>();

            for (int i = 0; i < uris.size(); i++) {
                list.add(new Photos(uris.get(i).toString(), strings.get(i)));
            }

            adapter.addData(list);
        }
    }
}
