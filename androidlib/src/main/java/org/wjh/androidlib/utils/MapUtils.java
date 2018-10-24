package org.wjh.androidlib.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;

public class MapUtils {


    /**
     * 判断是否安装某应用
     */
    private static boolean checkApkExist(Context context, String packageName) {

        if (TextUtils.isEmpty(packageName))
            return false;

        try {
            context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * 是否安装百度地图
     */
    public static boolean isInstallBaiDuMap(Context context) {
        return checkApkExist(context, "com.baidu.BaiduMap");
    }

    /**
     * 是否安装高德地图
     */
    public static boolean isInstallAMap(Context context) {
        return checkApkExist(context, "com.autonavi.minimap");
    }

    /**
     * 是否安装腾讯地图
     */
    public static boolean isInstallTencentMap(Context context) {
        return checkApkExist(context, "com.tencent.map");
    }
}
