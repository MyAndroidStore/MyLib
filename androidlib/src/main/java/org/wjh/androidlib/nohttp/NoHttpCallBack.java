package org.wjh.androidlib.nohttp;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.NotFoundCacheError;
import com.yanzhenjie.nohttp.error.TimeoutError;
import com.yanzhenjie.nohttp.error.URLError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import org.wjh.androidlib.R;
import org.wjh.androidlib.dialog.LoadingDialog;

/**
 * 作者： macpro  on 2018/6/17.
 * 邮箱： xxx.com
 */
public abstract class NoHttpCallBack implements OnResponseListener<String> {

    protected Context mContext;
    private LoadingDialog mLoadingDialog;

    // 无Loading
    public NoHttpCallBack(Context context) {
        this.mContext = context;
    }

    // 有Loading
    public NoHttpCallBack(Context context, LoadingDialog loadingDialog) {
        this.mContext = context;
        this.mLoadingDialog = loadingDialog;
        if (mLoadingDialog != null)
            this.mLoadingDialog.setContext(context);
    }

    @Override
    public void onStart(int what) {

        if (mLoadingDialog != null && !mLoadingDialog.isShowing())
            mLoadingDialog.show();

    }

    @Override
    public void onSucceed(int what, Response<String> response) {

        if (response.responseCode() == 200) {
            String json = response.get();


            if (TextUtils.isEmpty(json)) {
                Toast.makeText(mContext, "", Toast.LENGTH_SHORT).show();
                Toast.makeText(mContext, "获取服务器数据发生异常", Toast.LENGTH_SHORT).show();
                onFailed();
            } else {

                if (json.startsWith("{") && json.endsWith("}") || json.startsWith("[") && json.endsWith("]")) {
                    onSucceed(json);
                } else {
                    Toast.makeText(mContext, "获取服务器数据发生异常", Toast.LENGTH_SHORT).show();
                    onFailed();
                }

            }

        } else {

            Exception exception = response.getException();
            if (exception instanceof NetworkError) {// 网络不好
                Toast.makeText(mContext, R.string.error_please_check_network, Toast.LENGTH_SHORT).show();
            } else if (exception instanceof TimeoutError) {// 请求超时
                Toast.makeText(mContext, R.string.error_timeout, Toast.LENGTH_SHORT).show();
            } else if (exception instanceof UnKnownHostError) {// 找不到服务器
                Toast.makeText(mContext, R.string.error_not_found_server, Toast.LENGTH_SHORT).show();
            } else if (exception instanceof URLError) {// URL是错的
                Toast.makeText(mContext, R.string.error_url_error, Toast.LENGTH_SHORT).show();
            } else if (exception instanceof NotFoundCacheError) {
                // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
                // 没有缓存一般不提示用户，如果需要随你。
            } else {
                Toast.makeText(mContext, "出错了", Toast.LENGTH_SHORT).show();
            }

            onFailed();
        }

    }

    @Override
    public void onFailed(int what, Response<String> response) {

        Exception exception = response.getException();
        if (exception instanceof NetworkError) {// 网络不好
            Toast.makeText(mContext, R.string.error_please_check_network, Toast.LENGTH_SHORT).show();
        } else if (exception instanceof TimeoutError) {// 请求超时
            Toast.makeText(mContext, R.string.error_timeout, Toast.LENGTH_SHORT).show();
        } else if (exception instanceof UnKnownHostError) {// 找不到服务器
            Toast.makeText(mContext, R.string.error_not_found_server, Toast.LENGTH_SHORT).show();
        } else if (exception instanceof URLError) {// URL是错的
            Toast.makeText(mContext, R.string.error_url_error, Toast.LENGTH_SHORT).show();
        } else if (exception instanceof NotFoundCacheError) {
            // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
            // 没有缓存一般不提示用户，如果需要随你。
        } else {
            Toast.makeText(mContext, R.string.error_unknow, Toast.LENGTH_SHORT).show();
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
