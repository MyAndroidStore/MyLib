package org.wjh.androidlib.passwordview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import org.wjh.androidlib.R;

import java.util.ArrayList;
import java.util.List;

public class NumKeyboardView extends GridView {

    // 密码0-9
    private int[] num = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
    // 输入密码的集合
    private List<Integer> list = new ArrayList<>();
    // 是否随机数
    private boolean random;
    // 适配器
    private PasswordAdapter passwordAdapter;
    // 密码的集合
    private List<Integer> psd = new ArrayList<>();
    // 密码的监听
    private NumKeyboardListener listener;

    public NumKeyboardView(Context context) {
        super(context);
    }

    public NumKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttrs(context, attrs);
        init();

        passwordAdapter = new PasswordAdapter(context, list);
        this.setAdapter(passwordAdapter);
        this.setNumColumns(3);
        this.setHorizontalSpacing(1);
        this.setVerticalSpacing(1);
        this.setBackgroundColor(Color.parseColor("#999999"));
        this.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 9) {

                } else if (i == 11) {

                    // 删除操作
                    if (psd.size() != 0) {
                        // 执行删除操作
                        psd.remove(psd.size() - 1);
                        // 通知数据源发生变化
                        if (listener != null)
                            listener.deleteByIndex(psd.size());
                    }

                } else {

                    if (psd.size() == 6) {
                        return;
                    }

                    // 添加到数据源
                    psd.add(list.get(i));

                    // 通知数据源发生变化
                    if (listener != null)
                        listener.addByIndex(psd.size() - 1);

                    if (psd.size() == 6 && listener != null) {
                        listener.complete(getPassWord());
                    }
                }
            }
        });
    }

    private void getAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.NumKeyboardViewStyle);
        random = ta.getBoolean(R.styleable.NumKeyboardViewStyle_random, false);
        ta.recycle();
    }

    private void init() {

        // 是否随机

        if (random)
            shuffleSort(num);

        // 构造适配器数据

        int index_temp = 0;

        for (int i = 0; i < 12; i++) {

            if (i == 9) {
                list.add(-1);
            } else if (i == 11) {
                list.add(-1);
            } else {
                list.add(num[index_temp]);
                index_temp++;
            }
        }
    }

    private void shuffleSort(int[] data) {
        for (int i = 0; i < data.length - 1; i++) {
            int j = (int) (data.length * Math.random());
            swap(data, i, j);
        }
    }

    private void swap(int[] data, int i, int j) {
        if (i == j) {
            return;
        }
        data[i] = data[i] + data[j];
        data[j] = data[i] - data[j];
        data[i] = data[i] - data[j];
    }

    private String getPassWord() {

        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < psd.size(); i++) {
            buffer.append(psd.get(i));
        }

        return buffer.toString();
    }

    public void setNumKeyboardListener(NumKeyboardListener listener) {
        this.listener = listener;
    }

    public void reset() {
        psd.clear();
    }
}
