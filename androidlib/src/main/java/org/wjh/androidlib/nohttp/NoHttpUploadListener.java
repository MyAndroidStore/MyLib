package org.wjh.androidlib.nohttp;

public interface NoHttpUploadListener {

    void onCancel();

    void onProgress(int progress);

    void onFinish();

    void onError(Exception exception);
}
