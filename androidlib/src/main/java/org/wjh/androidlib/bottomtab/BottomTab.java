package org.wjh.androidlib.bottomtab;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.wjh.androidlib.R;
import org.wjh.androidlib.utils.DrawableSelectorUtils;

import java.util.List;

/**
 * Created by macpro on 2018/1/8.
 * BottomTab 底部导航栏控件
 */

public class BottomTab extends LinearLayout {


    // 自定义FragmentTabHost
    private FragmentTabHost mTabHost;
    // 底部导航栏上方的分割线
    private View line;
    // 上下文
    private Context mContext;

    // 超出父布局的控件
    private LinearLayout middle_layout;
    private ImageView middleImg;
    private TextView middleTv;

    private int tabSize;


    // 自定义的属性
    private boolean isOverparentLayout = false;// 是否中间的tab超出父布局
    private int middleSrc;// 中间的tab图片资源id
    private String middleText = "";// 中间的tab的文字描述
    private int selectedTextColor = 0xffff0000;// 选中的文字颜色
    private int unSelectedTextColor = 0x88888888;// 未选中的文字颜色


    public BottomTab(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);


        getAttrs(context, attrs);

        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.mylib_bottom_bar, this, true);
        this.line = findViewById(R.id.mylib_line);
        this.mTabHost = findViewById(R.id.mylib_tabHost);
        this.middleImg = findViewById(R.id.mylib_middleImg);
        this.middleTv = findViewById(R.id.mylib_middle_tv);
        this.middle_layout = findViewById(R.id.mylib_middle_layout);
    }


    /**
     * 得到属性值
     *
     * @param context
     * @param attrs
     */
    private void getAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BottomTabStyle);
        isOverparentLayout = ta.getBoolean(R.styleable.BottomTabStyle_isOverParentLayout, false);
        middleSrc = ta.getResourceId(R.styleable.BottomTabStyle_middleSrc, R.drawable.mylib_notice1);
        middleText = ta.getString(R.styleable.BottomTabStyle_middleText);
        unSelectedTextColor = ta.getColor(R.styleable.BottomTabStyle_unSelectedTextColor, unSelectedTextColor);
        selectedTextColor = ta.getColor(R.styleable.BottomTabStyle_selectedTextColor, selectedTextColor);
        ta.recycle();
    }

    /****************填充底部导航栏数据*****************************/

    public void setTabDatas(List<BottomBarEntity> datas) {

        FragmentManager fragmentManager = ((AppCompatActivity) mContext).getSupportFragmentManager();
        mTabHost.setup(mContext, fragmentManager, R.id.tabHostContent);


        tabSize = datas.size();

        if (isOverparentLayout) {

            for (int i = 0; i < datas.size(); i++) {

                BottomBarEntity bottomBarEntity = datas.get(i);

                BottomBarItem item = bottomBarEntity.getItem();
                Bundle bundle = bottomBarEntity.getBundle();
                item.setTextColor(unSelectedTextColor, selectedTextColor);

                // 修改中间的按钮
                final int middle = (int) datas.size() / 2;

                if (i == middle) {
                    item.setVisibility(INVISIBLE);
                    middle_layout.setVisibility(VISIBLE);

                    middle_layout.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setCurrentTab(middle);
                        }
                    });

                    middleImg.setImageDrawable(getResources().getDrawable(middleSrc));


                    if (TextUtils.isEmpty(middleText)) {
                        middleTv.setVisibility(GONE);
                        LinearLayout.LayoutParams layoutParams = (LayoutParams) middleImg.getLayoutParams();

                        layoutParams.bottomMargin = 20;
                        middleImg.setLayoutParams(layoutParams);
                    } else {
                        middleTv.setVisibility(VISIBLE);
                        middleTv.setTextColor(DrawableSelectorUtils.getStateListColor(unSelectedTextColor, selectedTextColor));
                    }
                }

                mTabHost.addTab(mTabHost.newTabSpec(String.valueOf(i)).setIndicator(item)
                        , bottomBarEntity.getClss(), bundle);

            }

        } else {


            for (int i = 0; i < datas.size(); i++) {

                BottomBarEntity bottomBarEntity = datas.get(i);

                BottomBarItem item = bottomBarEntity.getItem();
                item.setTextColor(unSelectedTextColor, selectedTextColor);
                Bundle bundle = bottomBarEntity.getBundle();

                mTabHost.addTab(mTabHost.newTabSpec(String.valueOf(i)).setIndicator(item)
                        , bottomBarEntity.getClss(), bundle);
            }

        }


        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);

        mTabHost.setCurrentTab(0);

    }


    /****************设置底部导航栏上方分割线颜色，0代表没有*****************************/

    public void setLineColor(int colorResId) {

        if (colorResId == 0)
            line.setVisibility(GONE);
        else {
            line.setVisibility(VISIBLE);
            line.setBackgroundColor(colorResId);
        }
    }


    /****************设置当前选中哪个Tab*****************************/

    public void setCurrentTab(int index) {

        mTabHost.setCurrentTab(index);
    }

    /****************返回当前选中底部button的index*****************************/

    public int getCurrentTab() {

        return mTabHost.getCurrentTab();
    }

    /****************重写Tab点击事件(例：登录拦截)*****************************/

    public void overwriteListener(int index, OnClickListener listener) {


        if (isOverparentLayout) {

            if (index == (int) tabSize / 2)
                middle_layout.setOnClickListener(listener);
            else
                getTabView(index).setOnClickListener(listener);

        } else
            getTabView(index).setOnClickListener(listener);
    }

    /****************返回TabView*****************************/

    public BottomBarItem getTabView(int index) {

        mTabHost.getTabWidget().setClipChildren(false);

        return (BottomBarItem) mTabHost.getTabWidget().getChildTabViewAt(index);

    }


    /****************设置指定下标的数字提醒*****************************/

    public void setNoticeNum(int index, int num) {

        getTabView(index).setNoticeNum(num);
    }


    /****************设置指定下标的数字提醒样式*****************************/

    public void setNoticeStyle(int index, CircleStyle style) {

        getTabView(index).setCircleSytle(style);
    }
}
