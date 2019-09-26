package org.wjh.androidlib.tsnackbar;


import android.app.Activity;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.SwipeDismissBehavior;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import org.wjh.androidlib.R;
import org.wjh.androidlib.utils.BarUtil;
import org.wjh.androidlib.utils.UiUtils;


public final class TSnackbar {

    private final ViewGroup mParent;
    private final Activity mContext;
    // SnackBar的Layout
    private final View mView;
    // SnackBar的内容
    private final TextView snackContent;
    // 管理snackbar
    private final TsnackManager manager;

    // 构造方法私有化
    private TSnackbar(Activity activity) {
        // 最外层的viewgroup
        mParent = (ViewGroup) activity.findViewById(android.R.id.content).getRootView();
        mContext = activity;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        mView = inflater.inflate(R.layout.mylib_view_tsnackbar_layout, mParent, false);
        snackContent = mView.findViewById(R.id.snack_content);
        // 设置默认高度
        setMinHeight(0);
        manager = TsnackManager.getInstance();
    }

    /**
     * 设置 actionBarHeight
     */
    public TSnackbar setMinHeight(int actionBarHeight) {

        int stateBarHeight = BarUtil.getStatusBarHeight(mContext);

        if (actionBarHeight <= 0) {
            actionBarHeight = TSnackInit.getInstance().getActionBarHight();
        }

        // 大于4.4版本 顶部开始显示 反之 则状态栏下开始显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            snackContent.setPadding(0, stateBarHeight, 0, 0);
            snackContent.setMinimumHeight(stateBarHeight + actionBarHeight);

        } else {
            mView.setMinimumHeight(actionBarHeight);
            UiUtils.setMargins(mView, 0, stateBarHeight, 0, 0);
        }
        return this;
    }

    /**
     * 显示信息
     */
    @NonNull
    public static TSnackbar make(@NonNull Activity activity, Prompt prompt, @NonNull CharSequence text) {
        TSnackbar tSnackbar = new TSnackbar(activity);
        tSnackbar.snackContent.setText(text);
        tSnackbar.setPromptThemBackground(prompt);
        return tSnackbar;
    }

    @NonNull
    public static TSnackbar make(@NonNull Activity activity, Prompt prompt, @StringRes int resId) {
        return make(activity, prompt, activity.getResources().getText(resId));
    }

    @NonNull
    public static TSnackbar make(@NonNull Activity activity, Prompt prompt, int height, @NonNull CharSequence text) {
        TSnackbar tSnackbar = new TSnackbar(activity);
        tSnackbar.snackContent.setText(text);
        tSnackbar.setPromptThemBackground(prompt);
        tSnackbar.setMinHeight(height);
        return tSnackbar;
    }

    @NonNull
    public static TSnackbar make(@NonNull Activity activity, Prompt prompt, int height, @StringRes int resId) {
        return make(activity, prompt, height, activity.getResources().getText(resId));
    }

    /**
     * 设置 Snack样式
     */
    public TSnackbar setPromptThemBackground(Prompt prompt) {
        if (prompt == Prompt.SUCCESS) {
            setBackgroundColor(mContext.getResources().getColor(Prompt.SUCCESS.getBackgroundColor()));
        } else if (prompt == Prompt.ERROR) {
            setBackgroundColor(mContext.getResources().getColor(Prompt.ERROR.getBackgroundColor()));
        } else if (prompt == Prompt.WARNING) {
            setBackgroundColor(mContext.getResources().getColor(Prompt.WARNING.getBackgroundColor()));
        }
        return this;
    }

    /**
     * 设置 Snack背景色
     */
    public TSnackbar setBackgroundColor(int colorId) {
        snackContent.setBackgroundColor(colorId);
        return this;
    }

    public ViewGroup getmParent() {
        return mParent;
    }

    public View getmView() {
        return mView;
    }

    /**
     * 显示
     */
    public void show() {

        manager.addTsnackBar(this);

        mParent.addView(mView);
        animateViewIn();

        // 侧滑删除
        ViewGroup.LayoutParams params = snackContent.getLayoutParams();
        if (params instanceof CoordinatorLayout.LayoutParams) {
            CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) params;
            CoordinatorLayout.Behavior behavior = p.getBehavior();
            if (behavior instanceof SwipeDismissBehavior) {
                SwipeDismissBehavior sb = (SwipeDismissBehavior) behavior;
                sb.setListener(new SwipeDismissBehavior.OnDismissListener() {
                    @Override
                    public void onDismiss(View view) {
                        mView.clearAnimation();
                        // 动画完毕移除控件
                        mParent.removeView(mView);
                    }

                    @Override
                    public void onDragStateChanged(int state) {
                    }
                });
            }
        }
    }

    /**
     * 动画
     */
    private Animation getAnimationInFromTopToDown() {
        return AnimationUtils.loadAnimation(mContext, R.anim.mylib_snack_in_out);
    }

    /**
     * 开启动画
     */
    private void animateViewIn() {

        Animation anim = getAnimationInFromTopToDown();

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                manager.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        mParent.removeView(mView);
                    }
                });


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mView.startAnimation(anim);
    }
}