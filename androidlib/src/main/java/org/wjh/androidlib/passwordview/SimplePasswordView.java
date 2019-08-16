package org.wjh.androidlib.passwordview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.wjh.androidlib.R;

public class SimplePasswordView extends LinearLayout {

    //取消按钮
    private ImageView mCancel;
    //忘记密码按钮
    private TextView mForget;
    private TextView mTitle;

    private NumKeyboardView numKeyboardView;
    private PassWordBox passWordBox;

    private SimplePassWordViewListener listener;

    public SimplePasswordView(Context context) {
        this(context, null);
    }

    public SimplePasswordView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //view布局
        LayoutInflater.from(context).inflate(R.layout.mylib_simple_password_view_layout, this, true);
        initViews();


        numKeyboardView.setNumKeyboardListener(new NumKeyboardListener() {
            @Override
            public void addByIndex(int index) {
                passWordBox.addPassWordByIndex(index);
            }

            @Override
            public void deleteByIndex(int index) {
                passWordBox.deltePassWordByIndex(index);
            }

            @Override
            public void complete(String password) {

                if (listener != null)
                    listener.complete(password);
            }
        });

        initEvents();
    }


    private void initViews() {

        passWordBox = findViewById(R.id.mylib_password_box);
        numKeyboardView = findViewById(R.id.mylib_num_keyboard_view);
        mForget = findViewById(R.id.mylib_forgetPwd);
        mCancel = findViewById(R.id.mylib_cancle);
        mTitle = findViewById(R.id.mylib_psd_title);
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void setForgetTextVisibility(int visibility) {
        mForget.setVisibility(visibility);
    }

    public int getFrameLayoutRes() {
        return R.id.mylib_psd_fl;
    }

    private void initEvents() {

        mForget.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (listener != null)
                    listener.forget();
            }
        });

        mCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (listener != null)
                    listener.cancle();
            }
        });
    }


    public void setSimplePassWordViewListener(SimplePassWordViewListener listener) {
        this.listener = listener;
    }

    public void reset() {
        passWordBox.clearAll();
        numKeyboardView.reset();
    }

    public interface SimplePassWordViewListener {
        void complete(String password);

        void cancle();

        void forget();
    }
}
