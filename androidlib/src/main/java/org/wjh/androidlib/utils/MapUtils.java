package org.wjh.androidlib.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;

public class MapUtils {


    /**
     * 判断是否安装某地图应用
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


    // 获得百度地图路线规划的Intent
    // 起点名称或经纬度，或者可同时提供名称和经纬度，此时经纬度优先级高
    public static Intent getBaiDuRoutePlanning(String startingPointName, String startingPoint, String endPointName, String endPoint, BaiDuMapMode mode) {

        StringBuffer buffer = new StringBuffer("baidumap://map/direction?");


        // 拦截 抛出异常
        if (TextUtils.isEmpty(startingPointName) &&
                TextUtils.isEmpty(startingPoint) &&
                TextUtils.isEmpty(endPointName) &&
                TextUtils.isEmpty(endPoint)) {

            throw new RuntimeException("startingPointName,startingPoint,endPointName,endPoint至少有一个值不能为空");
        }

        // 起点名称不为空，起点坐标为空
        if (!TextUtils.isEmpty(startingPointName) && TextUtils.isEmpty(startingPoint)) {

            buffer.append("origin=");
            buffer.append(startingPointName);
            buffer.append("&");

        } else if (!TextUtils.isEmpty(startingPoint) && TextUtils.isEmpty(startingPointName)) {
            // 起点坐标不为空 起点名称为空

            buffer.append("origin=");
            buffer.append(startingPoint);
            buffer.append("&");

        } else if (!TextUtils.isEmpty(startingPointName) && !TextUtils.isEmpty(startingPoint)) {
            // 起点名称不为空，起点坐标不为空

            // origin=name:对外经贸大学|latlng:39.98871,116.43234
            buffer.append("origin=name:");
            buffer.append(startingPointName);
            buffer.append("|latlng:");
            buffer.append(startingPoint);
            buffer.append("&");
        }

        // 都为空无需append


        // 终点名称不为空，终点坐标为空
        if (!TextUtils.isEmpty(endPointName) && TextUtils.isEmpty(endPoint)) {

            buffer.append("destination=");
            buffer.append(endPointName);
            buffer.append("&");

        } else if (!TextUtils.isEmpty(endPoint) && TextUtils.isEmpty(endPointName)) {
            // 终点坐标不为空 终点名称为空
            // destination=40.057406655722,116.2964407172

            buffer.append("destination=");
            buffer.append(endPoint);
            buffer.append("&");

        } else if (!TextUtils.isEmpty(endPointName) && !TextUtils.isEmpty(endPoint)) {
            // 终点名称不为空，终点坐标不为空

            // destination=name:对外经贸大学|latlng:39.98871,116.43234
            buffer.append("destination=name:");
            buffer.append(endPointName);
            buffer.append("|latlng:");
            buffer.append(endPoint);
            buffer.append("&");

        }

        // 都为空无需append

        // 追加路线规划 是公交 驾车 步行还是骑行
        buffer.append("mode=");
        buffer.append(mode.getMode());

        buffer.append("&src=andr.baidu.openAPIdemo");


        Intent i1 = new Intent();
        // 公交路线规划
        i1.setData(Uri.parse(buffer.toString()));

        return i1;

    }


}
