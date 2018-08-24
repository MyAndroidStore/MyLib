package org.wjh.mylib.simple;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.wjh.androidlib.textview.PraiseTextView;
import org.wjh.mylib.R;

import java.util.ArrayList;
import java.util.List;

public class PraiseActivity extends AppCompatActivity {

    private PraiseTextView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_praise);


        view = (PraiseTextView) findViewById(R.id.pt);


        List<PraiseTextView.PraiseInfo> list = new ArrayList<>();

        for (int i = 0; i < 5; i++) {

            PraiseTextView.PraiseInfo info = new PraiseTextView.PraiseInfo("mark","name"+i);

        }


        view.setData(list);

    }

    public void click(View view) {

    }


}
