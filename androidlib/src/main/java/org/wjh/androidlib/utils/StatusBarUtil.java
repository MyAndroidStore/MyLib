package org.wjh.androidlib.utils;

import android.content.Context;

import java.lang.reflect.Field;

public class StatusBarUtil {

    /**
     * 获取状态栏的高度(反射)
     */
    public static int getStatusBarHeight(Context context) {
        try {
            // 通过反射获取到类
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            // 创建对象
            Object obj = c.newInstance();
            // 拿取到属性
            Field field = c.getField("status_bar_height");
            // 获取值
            int x = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
