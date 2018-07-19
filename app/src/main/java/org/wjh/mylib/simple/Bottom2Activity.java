package org.wjh.mylib.simple;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.wjh.androidlib.bottomtab.BottomBarEntity;
import org.wjh.androidlib.bottomtab.BottomBarItem;
import org.wjh.androidlib.bottomtab.BottomTab;
import org.wjh.androidlib.bottomtab.CircleStyle;
import org.wjh.mylib.R;
import org.wjh.mylib.fragment.Fragment1;
import org.wjh.mylib.fragment.Fragment2;
import org.wjh.mylib.fragment.Fragment3;

import java.util.ArrayList;
import java.util.List;

public class Bottom2Activity extends AppCompatActivity {


    private BottomTab viewById;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom2);

        viewById = (BottomTab) findViewById(R.id.bottom);

        viewById.setTabDatas(initData());

        viewById.getTabView(1).setNoticeNum(3);
        viewById.getTabView(1).setCircleSytle(CircleStyle.DOT);
        viewById.getTabView(1).setCircleSytle(CircleStyle.REDSOLID);

        viewById.overwriteListener(0, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewById.setCurrentTab(0);
            }
        });
        viewById.overwriteListener(1, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewById.setCurrentTab(1);

            }
        });
        viewById.overwriteListener(2, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewById.setCurrentTab(2);
            }
        });
    }

    private List<BottomBarEntity> initData() {

        List<BottomBarEntity> list = new ArrayList<>();
        list.add(new BottomBarEntity(Fragment1.class, new BottomBarItem(this, R.mipmap.msg_normal, R.mipmap.msg_press, "消息")));
        list.add(new BottomBarEntity(Fragment2.class, new BottomBarItem(this, R.mipmap.application_normal, R.mipmap.application_press, "应用")));
        list.add(new BottomBarEntity(Fragment3.class, new BottomBarItem(this, R.mipmap.my_normal, R.mipmap.my_press, "我的")));


        return list;

    }
}
