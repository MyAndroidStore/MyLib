package org.wjh.androidlib.exceptions;

/**
 * 作者： macpro  on 2018/6/18.
 * 邮箱： xxx.com
 */
public class ToastUtilsInitException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ToastUtilsInitException() {
        super("Please invoke ToastUtils.init(Application) on Application#onCreate()");
    }
}
