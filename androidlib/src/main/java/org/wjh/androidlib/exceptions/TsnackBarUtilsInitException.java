package org.wjh.androidlib.exceptions;

/**
 * 作者： macpro  on 2018/6/18.
 * 邮箱： xxx.com
 */
public class TsnackBarUtilsInitException extends RuntimeException {

    private static final long serialVersionUID = 2L;

    public TsnackBarUtilsInitException() {
        super("Please invoke TsnackBarUtils.init(Application,toolBarHightPX) on Application#onCreate()");
    }
}
