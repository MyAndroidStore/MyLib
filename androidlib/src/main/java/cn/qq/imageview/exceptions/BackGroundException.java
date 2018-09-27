package cn.qq.imageview.exceptions;

public class BackGroundException extends RuntimeException {

    private static final long serialVersionUID = 2L;

    public BackGroundException() {
        super("Please set the parent view background in the xml which use FingerQQGroup viewGroup.");
    }
}
