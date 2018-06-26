package org.wjh.androidlib.nohttp;

import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.Priority;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.download.DownloadListener;
import com.yanzhenjie.nohttp.download.DownloadRequest;
import com.yanzhenjie.nohttp.download.SimpleDownloadListener;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;

/**
 * 作者： macpro  on 2018/6/17.
 * 邮箱： xxx.com
 */
public class NohttpUtils {

    private static NohttpUtils instance;

    /**
     * 请求队列。
     */
    private RequestQueue requestQueue;

    private NohttpUtils() {
        requestQueue = NoHttp.newRequestQueue(3);
    }

    /**
     * 请求队列。
     */
    public static NohttpUtils getInstance() {
        if (instance == null)
            synchronized (NohttpUtils.class) {
                if (instance == null)
                    instance = new NohttpUtils();
            }
        return instance;
    }

    /**
     * 添加一个请求到请求队列。
     *
     * @param what     用来标志请求, 当多个请求使用同一个Listener时, 在回调方法中会返回这个what。
     * @param request  请求对象。
     * @param listener 结果回调对象。
     */
    public <T> void add(int what, Request<T> request, OnResponseListener listener) {
        requestQueue.add(what, request, listener);
    }

    /**
     * 取消这个sign标记的所有请求。
     *
     * @param sign 请求的取消标志。
     */
    public void cancelBySign(Object sign) {
        requestQueue.cancelBySign(sign);
    }


    public static void cancleDownLoadBySign(Object sign) {
        NoHttp.getDownloadQueueInstance().cancelBySign(sign);
    }

    /**
     * 取消队列中所有请求。
     */
    public void cancelAll() {
        requestQueue.cancelAll();
    }

    public static void cancleDownLoadAll() {
        NoHttp.getDownloadQueueInstance().cancelAll();
    }

    /**********************详细拆分***********************/

    // get请求String
    public void doGetString(String url, RequestParams params, Object sign, NoHttpCallBack hcb) {
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.GET);
        request.setPriority(Priority.DEFAULT);
        request.add(params.params());
        request.setCancelSign(sign);
        requestQueue.add(200, request, hcb);
    }

    // get请求String,支持优先级
    public void doGetStringByPriority(String url, RequestParams params, Object sign, Priority priority, NoHttpCallBack hcb) {
        Request<String> request = NoHttp.createStringRequest(url);
        request.add(params.params());
        request.setPriority(priority);
        request.setCancelSign(sign);
        requestQueue.add(200, request, hcb);
    }

    // post请求String
    public void doPostString(String url, RequestParams params, Object sign, NoHttpCallBack hcb) {
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        request.setPriority(Priority.DEFAULT);
        request.add(params.params());
        request.setCancelSign(sign);
        requestQueue.add(200, request, hcb);
    }

    // post请求String,支持优先级
    public void doPostStringByPriority(String url, RequestParams params, Object sign, Priority priority, NoHttpCallBack hcb) {
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        request.setPriority(Priority.DEFAULT);
        request.add(params.params());
        request.setPriority(priority);
        request.setCancelSign(sign);
        requestQueue.add(200, request, hcb);
    }


    // file文件下载,支持取消,断点续传
    public void downLoadFile(String url, String fileFolder, String fileName, Object sign, boolean isRange, DownloadListener hcb) {
        DownloadRequest request = new DownloadRequest(url, RequestMethod.GET, fileFolder, fileName, isRange, true);
        request.setPriority(Priority.DEFAULT);
        request.setCancelSign(sign);
        NoHttp.getDownloadQueueInstance().add(0, request, hcb);
    }

    public void downLoadFile(String url, String fileFolder, String fileName, Object sign, boolean isRange, SimpleDownloadListener hcb) {
        DownloadRequest request = new DownloadRequest(url, RequestMethod.GET, fileFolder, fileName, isRange, true);
        request.setPriority(Priority.DEFAULT);
        request.setCancelSign(sign);
        NoHttp.getDownloadQueueInstance().add(0, request, hcb);
    }
}