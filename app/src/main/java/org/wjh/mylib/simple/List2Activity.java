package org.wjh.mylib.simple;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.wjh.androidlib.listadapter.LoadingState;
import org.wjh.androidlib.listadapter.OnFooterErrorListener;
import org.wjh.androidlib.listadapter.OnSlideUpListener;
import org.wjh.mylib.R;
import org.wjh.mylib.adapter.Adapter2;

import java.util.ArrayList;
import java.util.List;

public class List2Activity extends AppCompatActivity {

    int index = 0;

    private RecyclerView recyclerView;
    Adapter2 adapter2;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (adapter2.getAttachDatas().size() == 60) {
                adapter2.setLoadState(LoadingState.LOAD_END);
            } else {
                adapter2.addData(getData());
                adapter2.setLoadState(LoadingState.LOAD_COMPLETE);

            }


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list2);

        recyclerView = (RecyclerView) findViewById(R.id.rv);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter2 = new Adapter2(this);
        recyclerView.setAdapter(adapter2);

        // 模拟设置加载出错
        adapter2.setLoadState(LoadingState.LOAD_ERROR);


        adapter2.setOnFooterErrorListener(new OnFooterErrorListener() {
            @Override
            public void onClick() {
                request();
            }
        });


        recyclerView.addOnScrollListener(new OnSlideUpListener() {
            @Override
            public void onLoadMore() {
                request();
            }
        });
    }


    private void request() {

        adapter2.setLoadState(LoadingState.LOADING);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        }, 2000);

    }


    private List<String> getData() {

        List<String> strings = new ArrayList<>();


        for (int i = 0; i < 20; i++) {

            strings.add("测试" + (++index));
        }

        return strings;
    }
}
