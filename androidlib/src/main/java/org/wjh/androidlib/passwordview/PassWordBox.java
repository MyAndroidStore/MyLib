package org.wjh.androidlib.passwordview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.wjh.androidlib.R;

public class PassWordBox extends LinearLayout {

    private TextView[] tvList = new TextView[6];

    public PassWordBox(Context context) {
        super(context);
    }

    public PassWordBox(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        setOrientation(HORIZONTAL);
        init(context);
    }

    private void init(Context context) {

        this.setBackgroundResource(R.drawable.mylib_psd_view_bg);

        /**
         * 同一个view不能添加两次 必须重新new，否则会抛出异常
         * You must call removeView() on the child's parent first
         */


        for (int i = 0; i < 6; i++) {

            TextView tv = new TextView(context);
            LayoutParams params = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
            tv.setLayoutParams(params);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 35);
            tv.setGravity(Gravity.CENTER);
            tv.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            tvList[i] = tv;

            this.addView(tv);

            if (i != 5) {
                TextView line = new TextView(context);
                line.setBackgroundColor(getResources().getColor(R.color.password_box));
                line.setLayoutParams(new LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT));
                this.addView(line);
            }
        }
    }


    public void addPassWordByIndex(int index) {
        tvList[index].setText("我");
    }

    public void deltePassWordByIndex(int index) {
        tvList[index].setText("");
    }

    public void clearAll() {

        for (int i = 0; i < tvList.length; i++) {
            tvList[i].setText("");
        }
    }
}
