package org.wjh.mylib.app;


import android.app.Application;

import com.yanzhenjie.nohttp.InitializationConfig;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.OkHttpNetworkExecutor;
import com.yanzhenjie.nohttp.cache.DBCacheStore;
import com.yanzhenjie.nohttp.cookie.DBCookieStore;

/**
 * 作者： macpro  on 2018/6/17.
 * 邮箱： xxx.com
 */
public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        // NoHttp全局初始化
        NoHttp.initialize(this);
        // 开启NoHttp调试模式
        Logger.setDebug(true);
        // 设置NoHttp打印Log的TAG
        Logger.setTag("NoHttpSample");
    }


    private InitializationConfig initNohttpConfig() {

        // InitializationConfig config = InitializationConfig.newBuilder(context)
        // .addHeader("Token", "123") // 全局请求头。
        // .addHeader("Token", "456") // 全局请求头，不会覆盖上面的。
        // .addParam("AppVersion", "1.0.0") // 全局请求参数。
        // .addParam("AppType", "Android") // 全局请求参数。
        // .addParam("AppType", "iOS") // 全局请求参数，不会覆盖上面的两个。
        // .build();

        return InitializationConfig.newBuilder(this)
                // 全局连接服务器超时时间，单位毫秒，默认10s。
                .connectionTimeout(10 * 1000)
                // 全局等待服务器响应超时时间，单位毫秒，默认10s。
                .readTimeout(10 * 1000)
                // 配置缓存，默认保存数据库DBCacheStore，保存到SD卡使用DiskCacheStore。
                .cacheStore(
                        // 如果不使用缓存，setEnable(false)禁用。
                        new DBCacheStore(this).setEnable(true)
                )
                // 配置Cookie，默认保存数据库DBCookieStore，开发者可以自己实现CookieStore接口。
                .cookieStore(
                        // 如果不维护cookie，setEnable(false)禁用。
                        new DBCookieStore(this).setEnable(true)
                )
                // 配置网络层，默认URLConnectionNetworkExecutor，如果想用OkHttp：OkHttpNetworkExecutor。
                .networkExecutor(new OkHttpNetworkExecutor())
                // 全局通用Header，add是添加，多次调用add不会覆盖上次add。
                // .addHeader()
                // 全局通用Param，add是添加，多次调用add不会覆盖上次add。
                // .addParam()
                // .sslSocketFactory() // 全局SSLSocketFactory。
                // .hostnameVerifier() // 全局HostnameVerifier。
                .retry(3) // 全局重试次数，配置后每个请求失败都会重试x次。
                .build();
    }
}
