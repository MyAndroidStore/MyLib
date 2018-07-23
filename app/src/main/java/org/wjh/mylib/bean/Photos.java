package org.wjh.mylib.bean;

public class Photos {

    private String uri;
    private String path;

    public Photos(String uri, String path) {
        this.uri = uri;
        this.path = path;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
