package org.wjh.androidlib.bottomtab;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.wjh.androidlib.R;
import org.wjh.androidlib.badge.Badge;
import org.wjh.androidlib.utils.DrawableSelectorUtils;


/**
 * Created by macpro on 2018/1/6.
 * 底部导航栏 ITEM
 * - 图片、文字、Badge
 */

public class BottomBarItem extends RelativeLayout {

    private ImageView img;
    private TextView tv;
    private Badge badge;


    /**
     * @param context          上下文对象
     * @param imgResUnSelected 未选中状态时的图片
     * @param imgResSelected   选中状态时的图片
     * @param title            文字描述
     */
    public BottomBarItem(Context context, int imgResUnSelected, int imgResSelected, String title) {
        super(context);


        // 加载布局
        LayoutInflater.from(context).inflate(R.layout.mylib_bottom_bar_item, this, true);
        img = findViewById(R.id.mylib_bottom_bar_img);
        tv = findViewById(R.id.bottom_bar_tv);
        badge = findViewById(R.id.mylib_bottombar_badge);

        img.setImageDrawable(DrawableSelectorUtils.getStateListDrawable(context, imgResUnSelected, imgResSelected));
        tv.setText(title);

        // 默认数字提醒,默认文字颜色
        badge.setCircleStyle(CircleStyle.REDSOLID);
    }


    public void setTextColor(int textColorUnSelected, int textColorSelected) {
        tv.setTextColor(DrawableSelectorUtils.getStateListColor(textColorUnSelected, textColorSelected));
    }


    public void setNoticeNum(int num) {

        badge.setBadge_Num_ByInt(num);
    }


    // 设置Badge样式
    public void setCircleSytle(CircleStyle circleStyle) {
        badge.setCircleStyle(circleStyle);
    }

}
