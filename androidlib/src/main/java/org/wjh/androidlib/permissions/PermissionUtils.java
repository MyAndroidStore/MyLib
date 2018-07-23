package org.wjh.androidlib.permissions;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Setting;

import org.wjh.androidlib.utils.ToastUtils;

import java.util.List;

public class PermissionUtils {

    /**
     * Request permissions.
     *
     * @param context                上下文对象
     * @param permissionDiscribeName 权限描述：eg.存储、拍照
     * @param listener               权限回调
     * @param permissions            需要申请的权限
     */
    public static void requestPermission(final Context context, final String permissionDiscribeName, final GrantedListener listener, final String... permissions) {

        AndPermission.with(context)
                .runtime()
                .permission(permissions)
                .rationale(new RuntimeRationale())
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        listener.successfully();
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(@NonNull List<String> permissionList) {

                        if (AndPermission.hasAlwaysDeniedPermission(context, permissions)) {
                            showSettingDialog(context, permissionDiscribeName, listener, permissions);
                        } else {
                            ToastUtils.getInstance().shortToast("权限授予失败");
                        }
                    }
                })
                .start();
    }


    /**
     * Display setting dialog.
     */
    private static void showSettingDialog(final Context context, String permissionName, final GrantedListener listener, final String... permissions) {

        String message = "请在设置中开启" + permissionName + "权限";

        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle("提示")
                .setMessage(message)
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setPermission(context, listener, permissions);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }


    /**
     * Set permissions.
     */
    private static void setPermission(final Context context, final GrantedListener listener, final String... permissions) {
        AndPermission.with(context)
                .runtime()
                .setting()
                .onComeback(new Setting.Action() {
                    @Override
                    public void onAction() {

                        AndPermission.with(context)
                                .runtime()
                                .permission(permissions)
                                .rationale(new RuntimeRationale())
                                .onGranted(new Action<List<String>>() {
                                    @Override
                                    public void onAction(List<String> permissions) {
                                        listener.successfully();
                                    }
                                })
                                .onDenied(new Action<List<String>>() {
                                    @Override
                                    public void onAction(List<String> data) {
                                        ToastUtils.getInstance().shortToast("权限授予失败");
                                    }
                                })
                                .start();
                    }
                })
                .start();
    }
}
