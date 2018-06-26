package org.wjh.androidlib.nohttp;

import android.app.Activity;

import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.NotFoundCacheError;
import com.yanzhenjie.nohttp.error.TimeoutError;
import com.yanzhenjie.nohttp.error.URLError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import org.wjh.androidlib.R;
import org.wjh.androidlib.dialog.LoadingDialog;
import org.wjh.androidlib.utils.ToastUtils;

/**
 * 作者： macpro  on 2018/6/17.
 * 邮箱： xxx.com
 */
public abstract class NoHttpCallBack implements OnResponseListener<String> {

    private Activity mActivity;
    private LoadingDialog mLoadingDialog;

    // 无Loading
    public NoHttpCallBack(Activity activity) {
        this.mActivity = activity;
    }

    // 有Loading
    public NoHttpCallBack(Activity mActivity, LoadingDialog loadingDialog) {
        this.mActivity = mActivity;
        this.mLoadingDialog = loadingDialog;
    }

    @Override
    public void onStart(int what) {

        if (mLoadingDialog != null && !mLoadingDialog.isShowing())
            mLoadingDialog.show();

    }

    @Override
    public void onSucceed(int what, Response<String> response) {

        if (response.responseCode() == 200) {
            onSucceed(response.get());
        }else {

            if (mActivity != null) {

                Exception exception = response.getException();
                if (exception instanceof NetworkError) {// 网络不好
                    ToastUtils.getInstance().shortToast(R.string.error_please_check_network);
                } else if (exception instanceof TimeoutError) {// 请求超时
                    ToastUtils.getInstance().shortToast(R.string.error_timeout);
                } else if (exception instanceof UnKnownHostError) {// 找不到服务器
                    ToastUtils.getInstance().shortToast(R.string.error_not_found_server);
                } else if (exception instanceof URLError) {// URL是错的
                    ToastUtils.getInstance().shortToast(R.string.error_url_error);
                } else if (exception instanceof NotFoundCacheError) {
                    // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
                    // 没有缓存一般不提示用户，如果需要随你。
                } else {
                    ToastUtils.getInstance().shortToast("出错了");
                }
            }

            onFailed();
        }

    }

    @Override
    public void onFailed(int what, Response<String> response) {

        if (mActivity != null) {

            Exception exception = response.getException();
            if (exception instanceof NetworkError) {// 网络不好
                ToastUtils.getInstance().shortToast(R.string.error_please_check_network);
            } else if (exception instanceof TimeoutError) {// 请求超时
                ToastUtils.getInstance().shortToast(R.string.error_timeout);
            } else if (exception instanceof UnKnownHostError) {// 找不到服务器
                ToastUtils.getInstance().shortToast(R.string.error_not_found_server);
            } else if (exception instanceof URLError) {// URL是错的
                ToastUtils.getInstance().shortToast(R.string.error_url_error);
            } else if (exception instanceof NotFoundCacheError) {
                // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
                // 没有缓存一般不提示用户，如果需要随你。
            } else {
                ToastUtils.getInstance().shortToast(R.string.error_unknow);
            }
        }

        onFailed();
    }

    @Override
    public void onFinish(int what) {

        if (mLoadingDialog != null && mLoadingDialog.isShowing())
            mLoadingDialog.dismiss();
    }


    public abstract void onSucceed(String json);

    public abstract void onFailed();
}
