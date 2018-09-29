package org.wjh.androidlib.textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.AttributeSet;
import android.util.TypedValue;

import org.wjh.androidlib.R;

import java.util.List;

/**
 * 点赞TextView
 */
public class PraiseTextView extends AppCompatTextView {

    private List<PraiseInfo> mPraiseInfos;
    private OnPraiseClickListener mListener;
    private Context mContext;

    /**
     * 昵称的颜色
     */
    private int mNameTextColor;

    /**
     * 第一个显示的左侧图标
     */
    private int mIcon;

    /**
     * 文字dp大小
     */
    private float textSize;


    /**
     * 构造方法（暂时只支持xml）
     */
    public PraiseTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
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
        mIcon = ta.getResourceId(R.styleable.PraiseTextViewStyle_leftIcon, R.drawable.mylib_heart_drawable);
        mNameTextColor = ta.getColor(R.styleable.PraiseTextViewStyle_nameTextColor, getResources().getColor(R.color.praise_color));
        textSize = ta.getInteger(R.styleable.PraiseTextViewStyle_textSizeDp, 15);
        ta.recycle();
    }

    /**
     * 设置点赞的数据
     */
    public PraiseTextView setData(List<PraiseInfo> mPraiseInfos, OnPraiseClickListener mListener) {
        this.mPraiseInfos = mPraiseInfos;
        this.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
        this.mListener = mListener;
        this.setText(getPraiseString());
        //设置选中文本的高亮颜色
        this.setHighlightColor(getResources().getColor(android.R.color.transparent));
        this.setMovementMethod(new TextMovementMethod());
        return this;
    }


    // 具体的人名转换为SpannableStringBuilder
    private SpannableStringBuilder getPraiseString() {

        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append("  ");

        int praiseSize = mPraiseInfos.size();

        // for循环组装人名
        for (int mI = 0; mI < praiseSize; mI++) {

            // 昵称
            PraiseInfo praiseInfo = mPraiseInfos.get(mI);
            String nickname = praiseInfo.getNickname();
            int start = builder.length();
            int end = start + nickname.length();

            builder.append(nickname);
            if (mI != praiseSize - 1) {
                builder.append(", ");
            }
            builder.setSpan(new TextClickSpan(mContext, mI, praiseInfo, mListener, mNameTextColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        builder.setSpan(new VerticalImageSpan(mContext, ContextCompat.getDrawable(mContext, R.drawable.mylib_heart_drawable)),
                0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return builder;
    }
}
