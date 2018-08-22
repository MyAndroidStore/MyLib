package org.wjh.androidlib.exceptions;

/**
 * 作者： macpro  on 2018/6/18.
 * 邮箱： xxx.com
 */
public class MoreThanNineImageException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public MoreThanNineImageException() {
        super("the NineImageWechatAdapter more than 9 pics , please check it");
    }
}
