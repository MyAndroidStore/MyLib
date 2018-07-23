package org.wjh.androidlib.permissions;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;

import java.util.List;

/**
 * 作者： macpro  on 2018/6/19.
 * 邮箱： xxx.com
 */
public final class RuntimeRationale implements Rationale<List<String>> {

    @Override
    public void showRationale(Context context, List<String> permissions, final RequestExecutor executor) {
        List<String> permissionNames = Permission.transformText(context, permissions);
        String message = "授予权限以继续该操作";

        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle("提示")
                .setMessage(message)
                .setPositiveButton("继续", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        executor.execute();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        executor.cancel();
                    }
                })
                .show();
    }
}
