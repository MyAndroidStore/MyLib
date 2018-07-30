package org.wjh.androidlib.nohttp;

import android.content.Context;

import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.NotFoundCacheError;
import com.yanzhenjie.nohttp.error.TimeoutError;
import com.yanzhenjie.nohttp.error.URLError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;
import com.yanzhenjie.nohttp.rest.Response;

import org.wjh.androidlib.R;
import org.wjh.androidlib.dialog.LoadingDialog;
import org.wjh.androidlib.utils.ToastUtils;

import java.net.HttpCookie;
import java.util.List;

/**
 * 作者： macpro  on 2018/6/17.
 * 邮箱： xxx.com
 */
public abstract class HttpHeaderAndBodyCallBack extends NoHttpCallBack {


    public HttpHeaderAndBodyCallBack(Context context) {
        super(context);
    }

    public HttpHeaderAndBodyCallBack(Context context, LoadingDialog loadingDialog) {
        super(context, loadingDialog);
    }

    @Override
    public void onSucceed(int what, Response<String> response) {

        Headers headers = response.getHeaders();
        List<HttpCookie> cookies = headers.getCookies();

        if (cookies != null && cookies.size() > 0) {
            onCookie(cookies);
        }


        if (response.responseCode() == 200) {
            onSucceed(response.get());
        } else {

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

            onFailed();
        }


    }


    public abstract void onCookie(List<HttpCookie> cookies);
}
