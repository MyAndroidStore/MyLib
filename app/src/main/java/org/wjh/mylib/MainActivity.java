package org.wjh.mylib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import org.wjh.androidlib.edittext.CashEditText;
import org.wjh.androidlib.edittext.DrawableRightListener;
import org.wjh.androidlib.edittext.XEditText;
import org.wjh.androidlib.tsnackbar.Prompt;
import org.wjh.androidlib.tsnackbar.TSnackbar;
import org.wjh.mylib.simple.ImgChoiceActivity;
import org.wjh.mylib.simple.List1Activity;
import org.wjh.mylib.simple.List2Activity;
import org.wjh.mylib.simple.PraiseActivity;

public class MainActivity extends AppCompatActivity {

    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TSnackbar.make(this, Prompt.ERROR, "多大的").show();

        CashEditText viewById = (CashEditText) findViewById(R.id.et);
        final XEditText xv = (XEditText) findViewById(R.id.xv);
        viewById.setMaxCashAndEditListener(5000, null);

        xv.setDrawableRightListener(new DrawableRightListener() {
            @Override
            public void onDrawableRightClick(View view) {
                xv.setText("");
                xv.clearAllDrawable();
            }
        });

        xv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (TextUtils.isEmpty(charSequence.toString())) {
                    xv.clearAllDrawable();
                } else {
                    xv.setDrawableRight(R.mipmap.ic_launcher);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

//        xv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.e("ddd","all");
//            }
//        });

    }

    private void startActivity(Class jumpClass) {
        startActivity(new Intent(this, jumpClass));
    }

    public void toast(View view) {

    }

    public void rv1(View view) {
        startActivity(List1Activity.class);
    }

    public void rv2(View view) {
        startActivity(List2Activity.class);
    }

    public void imgChoice(View view) {
        startActivity(ImgChoiceActivity.class);
    }

    public void praise(View view) {
        startActivity(PraiseActivity.class);
    }
}
