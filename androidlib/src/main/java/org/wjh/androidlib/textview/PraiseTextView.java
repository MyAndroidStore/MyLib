package org.wjh.androidlib.textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatTextView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import org.wjh.androidlib.R;

import java.util.List;

/**
 * 点赞TextView
 */
public class PraiseTextView extends AppCompatTextView {

    private List<PraiseInfo> mPraiseInfos;
    private OnPraiseClickListener mListener;

    /**
     * 昵称的颜色
     */
    private int mNameTextColor;

    /**
     * 第一个显示的左侧图标
     */
    private int mIcon;

    /**
     * 中间分割的文本
     */
    private String mMiddleStr = "，";
    /**
     * 文字dp大小
     */
    private float textSize;


    /**
     * 构造方法（暂时只支持xml）
     */
    public PraiseTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttrs(context, attrs);
    }

    /**
     * 得到属性值
     *
     * @param context
     * @param attrs
     */
    private void getAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PraiseTextViewStyle);
        mIcon = ta.getResourceId(R.styleable.PraiseTextViewStyle_leftIcon, R.drawable.ic_mylib_praise);
        mNameTextColor = ta.getColor(R.styleable.PraiseTextViewStyle_nameTextColor, getResources().getColor(R.color.praise_color));
        textSize = ta.getInteger(R.styleable.PraiseTextViewStyle_textSizeDp, 15);
        ta.recycle();
    }

    /**
     * 设置点赞昵称监听
     */
    public PraiseTextView setOnPraiseListener(OnPraiseClickListener mListener) {
        this.mListener = mListener;
        return this;
    }

    /**
     * 设置点赞的数据
     */
    public PraiseTextView setData(List<PraiseInfo> mPraiseInfos) {
        this.mPraiseInfos = mPraiseInfos;
        this.setText(getPraiseString());
        //设置选中文本的高亮颜色
        this.setHighlightColor(getResources().getColor(android.R.color.transparent));
        this.setMovementMethod(LinkMovementMethod.getInstance());
        this.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
        return this;
    }

    private SpannableStringBuilder getPraiseString() {

        SpannableStringBuilder mBuilder = new SpannableStringBuilder("我");

        Drawable drawable = getResources().getDrawable(mIcon);
        drawable.setBounds(0, 0, dip2px(textSize + 2.5f), dip2px(textSize + 2.5f));
        mBuilder.setSpan((new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM)), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        mBuilder.append(" ");

        for (int mI = 0; mI < mPraiseInfos.size(); mI++) {

            // 昵称
            String nickname = mPraiseInfos.get(mI).getNickname();

            if (!TextUtils.isEmpty(nickname)) {

                mBuilder.append(nickname + mMiddleStr);

                final int finalMI = mI;

                mBuilder.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(final View mView) {
                        if (mListener != null) {
                            mListener.onClick(finalMI, mPraiseInfos.get(finalMI));
                        }
                    }

                    @Override
                    public void updateDrawState(final TextPaint ds) {
                        ds.setUnderlineText(false);
                        ds.setColor(mNameTextColor);
                    }
                }, mBuilder.length() - nickname.length() - mMiddleStr.length(), mBuilder.length() - mMiddleStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        if (mPraiseInfos.size() != 0) {
            mBuilder = new SpannableStringBuilder(mBuilder, 0, mBuilder.length() - 1);
            mBuilder.append(" ");
        }

        return mBuilder;
    }

    public class IconImage extends ImageSpan {
        private Drawable mDrawable;
        private float dip;

        public IconImage(Drawable d) {
            super(d);
            mDrawable = d;
        }

        public IconImage setDip(float dip) {
            this.dip = dip;
            return this;
        }

        @Override
        public Drawable getDrawable() {
            mDrawable.setBounds(0, 0, dip2px(dip), dip2px(dip));
            return mDrawable;
        }

    }

    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 点击 赞的昵称的 监听
     */
    public interface OnPraiseClickListener {
        void onClick(int position, PraiseInfo mPraiseInfo);
    }

    public static class PraiseInfo {
        // 标识
        private String mark;
        private String nickname;

        public PraiseInfo(String nickname) {
            this.nickname = nickname;
        }

        public PraiseInfo(String mark, String nickname) {
            this.mark = mark;
            this.nickname = nickname;
        }

        public String getMark() {
            return mark;
        }

        public void setMark(String mark) {
            this.mark = mark;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }
}
