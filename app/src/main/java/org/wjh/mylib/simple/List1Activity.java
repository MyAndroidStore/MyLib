package org.wjh.mylib.simple;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.wjh.androidlib.listadapter.LoadMoreSingleLayoutAdapter;
import org.wjh.androidlib.listadapter.LoadingState;
import org.wjh.androidlib.listadapter.OnSlideUpListener;
import org.wjh.mylib.R;
import org.wjh.mylib.adapter.Adapter1;

import java.util.ArrayList;
import java.util.List;

public class List1Activity extends AppCompatActivity {

    int index = 0;

    private RecyclerView recyclerView;
    Adapter1 adapter1;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (adapter1.getAttachDatas().size() == 60) {
                adapter1.setLoadState(LoadingState.LOAD_END);
            } else {
                adapter1.addData(getData());
                adapter1.setLoadState(LoadingState.LOAD_COMPLETE);

            }


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list1);

        recyclerView = (RecyclerView) findViewById(R.id.rv);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter1 = new Adapter1(this, R.layout.layout);
        recyclerView.setAdapter(adapter1);

        request();


        adapter1.setOnFooterErrorListener(new LoadMoreSingleLayoutAdapter.OnFooterErrorListener() {
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

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        }, 100);

    }


    private List<String> getData() {

        List<String> strings = new ArrayList<>();


        for (int i = 0; i < 2; i++) {

            strings.add("测试" + (++index));
        }

        return strings;
    }
}
