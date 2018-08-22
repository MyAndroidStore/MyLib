package org.wjh.androidlib.listadapter;

public class NineImageUrl {

    private String localUrl;
    private int resId;

    public NineImageUrl(String localUrl) {
        this.localUrl = localUrl;
    }

    public NineImageUrl(int resId) {
        this.resId = resId;
    }

    public NineImageUrl(String localUrl, int resId) {
        this.localUrl = localUrl;
        this.resId = resId;
    }

    public String getLocalUrl() {
        return localUrl;
    }

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
