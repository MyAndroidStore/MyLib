package org.wjh.androidlib.textview;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import org.wjh.androidlib.R;


public class TextClickSpan extends ClickableSpan {

    private Context mContext;
    private boolean mPressed;

    private PraiseInfo mPraiseInfo;
    private OnPraiseClickListener mListener;
    private int mTextColor;
    private int mPostion;

    public TextClickSpan(Context context, int postion, PraiseInfo praiseInfo, OnPraiseClickListener listener, int textColor) {
        this.mContext = context;
        this.mPostion = postion;
        this.mPraiseInfo = praiseInfo;
        this.mListener = listener;
        this.mTextColor = textColor;
    }

    public void setPressed(boolean isPressed) {
        this.mPressed = isPressed;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.bgColor = mPressed ? ContextCompat.getColor(mContext, R.color.click_bg) : Color.TRANSPARENT;
        ds.setColor(ContextCompat.getColor(mContext, mTextColor));
        ds.setUnderlineText(false);
    }

    @Override
    public void onClick(View widget) {
        if (mPraiseInfo != null)
            mListener.onClick(mPostion, mPraiseInfo);
    }
}
