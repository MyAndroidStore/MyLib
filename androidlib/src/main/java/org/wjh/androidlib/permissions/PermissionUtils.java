package org.wjh.androidlib.permissions;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Camera;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Setting;

import java.lang.reflect.Field;
import java.util.List;

public class PermissionUtils {

    /**
     * 第一次打开App时调用
     *
     * @param context                上下文对象
     * @param permissionDiscribeName 权限描述：eg.存储、拍照
     * @param listener               权限回调
     * @param permissions            需要申请的权限
     */
    public static void openAppRequestPermission(final Activity context, final String permissionDiscribeName, final GrantedListener listener, final String... permissions) {

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
                        showExitAppDialog(context, permissionDiscribeName, listener, permissions);
                    }
                })
                .start();
    }

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
                            listener.failure();
                        }
                    }
                })
                .start();
    }


    private static void showExitAppDialog(final Activity context, String permissionName, final GrantedListener listener, final String... permissions) {

        String message = "当前应用缺少必要权限(" + permissionName + "权限)。"
                + "\n" + "\n"
                + "请点击" + "\"设置\"-"
                + "\"权限\"-" + "打开所需权限。"
                + "\n" + "\n"
                + "最后点击两次后退按钮，即可返回。";

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
                        context.finish();
                    }
                })
                .show();
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
                        listener.failure();
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
                                        listener.failure();
                                    }
                                })
                                .start();
                    }
                })
                .start();
    }


    /**
     * Request permissions.
     *
     * @param permissionDiscribeName 权限描述：eg.存储、拍照
     * @param listener               权限回调
     * @param permissions            需要申请的权限
     */
    public static void requestPermission(final Fragment fragment, final String permissionDiscribeName, final GrantedListener listener, final String... permissions) {

        AndPermission.with(fragment)
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

                        if (AndPermission.hasAlwaysDeniedPermission(fragment, permissions)) {
                            showSettingDialog(fragment, permissionDiscribeName, listener, permissions);
                        } else {
                            Toast.makeText(fragment.getActivity(), "权限授予失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .start();
    }


    /**
     * Display setting dialog.
     */
    private static void showSettingDialog(final Fragment fragment, String permissionName, final GrantedListener listener, final String... permissions) {

        String message = "请在设置中开启" + permissionName + "权限";

        new AlertDialog.Builder(fragment.getActivity())
                .setCancelable(false)
                .setTitle("提示")
                .setMessage(message)
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setPermission(fragment, listener, permissions);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(fragment.getActivity(), "权限授予失败", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }


    /**
     * Set permissions.
     */
    private static void setPermission(final Fragment fragment, final GrantedListener listener, final String... permissions) {
        AndPermission.with(fragment)
                .runtime()
                .setting()
                .onComeback(new Setting.Action() {
                    @Override
                    public void onAction() {

                        AndPermission.with(fragment)
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
                                        Toast.makeText(fragment.getActivity(), "权限授予失败", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .start();
                    }
                })
                .start();
    }

    public static boolean isCameraCanUse() {
        boolean canUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
            // setParameters 是针对魅族MX5 做的。MX5 通过Camera.open() 拿到的Camera
            // 对象不为null
            Camera.Parameters mParameters = mCamera.getParameters();
            mCamera.setParameters(mParameters);
        } catch (Exception e) {
            canUse = false;
        }
        if (mCamera != null) {
            mCamera.release();
        }
        return canUse;
    }


    public boolean isHasPermission() {
        Field fieldPassword = null;
        try {
            Camera camera = Camera.open();
            fieldPassword = camera.getClass().getDeclaredField("mHasPermission");
            fieldPassword.setAccessible(true);
            return (boolean) fieldPassword.get(camera);
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }

    }


    /**
     * 判断是是否有录音权限
     */
    public static boolean isHasRecordPermission(final Context context) {
        // 音频获取源
        int audioSource = MediaRecorder.AudioSource.MIC;
        // 设置音频采样率，44100是目前的标准，但是某些设备仍然支持22050，16000，11025
        int sampleRateInHz = 44100;
        // 设置音频的录制的声道CHANNEL_IN_STEREO为双声道，CHANNEL_CONFIGURATION_MONO为单声道
        int channelConfig = AudioFormat.CHANNEL_IN_STEREO;
        // 音频数据格式:PCM 16位每个样本。保证设备支持。PCM 8位每个样本。不一定能得到设备支持。
        int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
        // 缓冲区字节大小
        int bufferSizeInBytes = 0;


        bufferSizeInBytes = 0;
        bufferSizeInBytes = AudioRecord.getMinBufferSize(sampleRateInHz,
                channelConfig, audioFormat);
        AudioRecord audioRecord = new AudioRecord(audioSource, sampleRateInHz,
                channelConfig, audioFormat, bufferSizeInBytes);
        //开始录制音频
        try {
            // 防止某些手机崩溃，例如联想
            audioRecord.startRecording();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        /**
         * 根据开始录音判断是否有录音权限
         */
        if (audioRecord.getRecordingState() != AudioRecord.RECORDSTATE_RECORDING) {
            return false;
        }
        audioRecord.stop();
        audioRecord.release();
        audioRecord = null;

        return true;
    }


}
