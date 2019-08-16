package org.wjh.mylib.simple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.wjh.androidlib.passwordview.SimplePasswordView;
import org.wjh.mylib.R;
import org.wjh.mylib.fragment.Fragment4;

public class List1Activity extends AppCompatActivity {


    private SimplePasswordView simplePasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list1);

        simplePasswordView = (SimplePasswordView) findViewById(R.id.spv);


        Fragment4 fragment = new Fragment4();
        getSupportFragmentManager().beginTransaction().replace(simplePasswordView.getFrameLayoutRes(), fragment).commit();
    }


}
