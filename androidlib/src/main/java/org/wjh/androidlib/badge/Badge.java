package org.wjh.androidlib.badge;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.wjh.androidlib.R;
import org.wjh.androidlib.bottomtab.CircleStyle;


/**
 * Created by macpro on 2017/12/18.
 */

public class Badge extends RelativeLayout {

    // Badge文字
    private TextView badge_tv;
    private RelativeLayout layout;
    private ImageView badge_dot;

    private CircleStyle mCircleStyle;


    public Badge(Context context) {
        this(context, null);
    }

    public Badge(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        // 加载布局
        LayoutInflater.from(context).inflate(R.layout.mylib_badge, this, true);
        this.badge_tv = findViewById(R.id.mylib_badge_num);
        this.badge_dot = findViewById(R.id.mylib_badge_dot);
        this.layout = findViewById(R.id.layout_badge);
        this.layout.setVisibility(GONE);

    }

    // 设置Badge样式(只是设置样式)
    public void setCircleStyle(CircleStyle style) {

        this.mCircleStyle = style;

        switch (style) {

            // 红底白字
            case REDSOLID:
                this.layout.setBackgroundResource(R.drawable.mylib_notice1);
                this.badge_tv.setTextColor(getResources().getColor(R.color.badge_white));
                this.badge_dot.setVisibility(GONE);
                this.badge_tv.setVisibility(VISIBLE);
                break;
            // 白底红字+描边红
            case WHITESOLID:
                this.layout.setBackgroundResource(R.drawable.mylib_notice2);
                this.badge_tv.setTextColor(getResources().getColor(R.color.badge_red));
                this.badge_dot.setVisibility(GONE);
                this.badge_tv.setVisibility(VISIBLE);
                break;
            case DOT:
                this.badge_dot.setBackgroundResource(R.drawable.mylib_notice3);
                this.layout.setBackgroundColor(0);
                this.badge_dot.setVisibility(VISIBLE);
                this.badge_tv.setVisibility(GONE);
                break;
        }
    }

    // 设置提醒数字(int)-显示整个layout
    public void setBadge_Num_ByInt(int num) {

        if (num == 0) {
            this.layout.setVisibility(GONE);
        } else if (num > 0 && num <= 99) {
            this.layout.setVisibility(VISIBLE);
            if (mCircleStyle != CircleStyle.DOT)
                this.badge_tv.setText(String.valueOf(num));
        } else if (num > 99) {
            this.layout.setVisibility(VISIBLE);
            if (mCircleStyle != CircleStyle.DOT)
                this.badge_tv.setText(R.string.max_badge);
        }
    }

}
