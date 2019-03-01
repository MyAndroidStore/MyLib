package org.wjh.androidlib.edittext;


import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

public class XEditText extends AppCompatEditText {


    // 左边图标的点击事件
    private DrawableLeftListener mLeftListener;
    // 右边图标的点击事件
    private DrawableRightListener mRightListener;
    // 上边图标的点击事件
    private DrawableTopListener mTopListener;
    // 下边图标的点击事件
    private DrawableBottomListener mBottomListener;


    private Context mContext;
    private Activity mActivity;


    final int DRAWABLE_LEFT = 0;
    final int DRAWABLE_TOP = 1;
    final int DRAWABLE_RIGHT = 2;
    final int DRAWABLE_BOTTOM = 3;

    public XEditText(Context context) {
        super(context);
        this.mContext = context;
    }

    public XEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public XEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Drawable drawableLeft = getCompoundDrawables()[DRAWABLE_LEFT];
        Drawable drawableRight = getCompoundDrawables()[DRAWABLE_RIGHT];
        Drawable drawableTop = getCompoundDrawables()[DRAWABLE_TOP];
        Drawable drawableBottom = getCompoundDrawables()[DRAWABLE_BOTTOM];


        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:

                // 如开发者未进行监听由父类super.onTouchEvent(event)去控制
                if (drawableRight != null && mRightListener != null) {

                    // event.getX():抬起时的坐标
                    // getWidth():得到控件的宽度
                    // getPaddingRight():图标右边缘至控件右边缘的距离

                    Rect bounds = drawableRight.getBounds();
                    int left = bounds.left;
                    int right = bounds.right;

                    float eventX = event.getX();
                    int width = getWidth();
                    int paddingRight = getPaddingRight();
                    int leftPoint = width - right + left - paddingRight;
                    int rightPoint = width - paddingRight;

                    boolean touchable = eventX > leftPoint
                            && (eventX < rightPoint);

                    doSomeThing(touchable, DRAWABLE_RIGHT, mRightListener);

                } else if (drawableLeft != null && mLeftListener != null) {

                    Rect bounds = drawableLeft.getBounds();
                    int left = bounds.left;
                    int right = bounds.right;

                    float eventX = event.getX();
                    int paddingLeft = getPaddingLeft();

                    // 按下的点 > paddingLeft 并且 < paddingLeft+图标绘制的宽度
                    boolean touchable = (eventX > paddingLeft)
                            && (eventX < (right - left + paddingLeft));

                    doSomeThing(touchable, DRAWABLE_LEFT, mLeftListener);

                } else if (drawableTop != null && mTopListener != null) {

                    Rect bounds = drawableTop.getBounds();
                    int bottom = bounds.bottom;
                    int top = bounds.top;

                    float eventY = event.getY();
                    int paddingTop = getPaddingTop();

                    boolean touchable = (eventY > paddingTop)
                            && (eventY < (bottom - top + paddingTop));

                    doSomeThing(touchable, DRAWABLE_TOP, mTopListener);

                } else if (drawableBottom != null && mBottomListener != null) {

                    Rect bounds = drawableBottom.getBounds();
                    int bottom = bounds.bottom;
                    int top = bounds.top;

                    float eventY = event.getY();

                    int draw_hight = bottom - top;
                    int paddingBottom = getPaddingBottom();
                    int height = getHeight();

                    boolean touchable = (eventY > height - paddingBottom - draw_hight)
                            && (eventY < (height - paddingBottom));

                    doSomeThing(touchable, DRAWABLE_BOTTOM, mBottomListener);
                } else {
                    setFocusableInTouchMode(true);
                    setFocusable(true);
                }

                break;
        }
        return super.onTouchEvent(event);
    }


    private void doSomeThing(boolean touchable, int drawableType, BaseDrawableListener listener) {

        if (touchable) {

            boolean isShowing = isSoftShowing();

            //设置点击EditText右侧图标EditText失去焦点，
            // 防止点击EditText右侧图标EditText获得焦点，软键盘弹出
            setFocusableInTouchMode(false);
            setFocusable(false);

            //点击EditText图标事件接口回调
            switch (drawableType) {

                case DRAWABLE_LEFT:
                    ((DrawableLeftListener) listener).onDrawableLeftClick(this);
                    break;
                case DRAWABLE_TOP:
                    ((DrawableTopListener) listener).onDrawableTopClick(this);
                    break;
                case DRAWABLE_RIGHT:
                    ((DrawableRightListener) listener).onDrawableRightClick(this);
                    break;
                case DRAWABLE_BOTTOM:
                    ((DrawableBottomListener) listener).onDrawableBottomClick(this);
                    break;

            }

            if (isShowing) {
                setFocusableInTouchMode(true);
                setFocusable(true);
            }

        } else {
            //设置点击EditText输入区域，EditText请求焦点，软键盘弹出，EditText可编辑
            //setFocusableInTouchMode(true);
            //setFocusable(true);
            //设置点击EditText输入区域，EditText不请求焦点，软键盘不弹出，EditText不可编辑
            setFocusableInTouchMode(true);
            setFocusable(true);
        }
    }


    private boolean isSoftShowing() {
        //获取当前屏幕内容的高度
        int screenHeight = mActivity.getWindow().getDecorView().getHeight();
        //获取View可见区域的bottom
        Rect rect = new Rect();
        //DecorView即为activity的顶级view
        mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        //考虑到虚拟导航栏的情况（虚拟导航栏情况下：screenHeight = rect.bottom + 虚拟导航栏高度）
        //选取screenHeight*2/3进行判断
        return screenHeight > (rect.bottom + getSoftButtonsBarHeight());
    }


    /**
     * 底部虚拟按键栏的高度
     */
    private int getSoftButtonsBarHeight() {
        DisplayMetrics metrics = new DisplayMetrics();
        //这个方法获取可能不是真实屏幕的高度
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        //获取当前屏幕的真实高度
        mActivity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight) {
            return realHeight - usableHeight;
        } else {
            return 0;
        }
    }

    private void closeKeyboard() {

        try {
            InputMethodManager imm = (InputMethodManager) mContext
                    .getSystemService(Context.INPUT_METHOD_SERVICE);

            if (imm != null)
                imm.hideSoftInputFromWindow(this.getWindowToken(), 0);

        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }
    }

    public void setDrawableLeftListener(DrawableLeftListener mLeftListener, Activity activity) {
        this.mLeftListener = mLeftListener;
        this.mActivity = activity;
    }

    public void setDrawableRightListener(DrawableRightListener mRightListener, Activity activity) {
        this.mRightListener = mRightListener;
        this.mActivity = activity;
    }

    public void setDrawableTopListener(DrawableTopListener mTopListener, Activity activity) {
        this.mTopListener = mTopListener;
        this.mActivity = activity;
    }

    public void setDrawableBottomListener(DrawableBottomListener mBottomListener, Activity activity) {
        this.mBottomListener = mBottomListener;
        this.mActivity = activity;
    }
}
