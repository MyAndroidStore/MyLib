package cn.qq.imageview.exceptions;

public class ChildException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ChildException() {
        super("The FingerQQGroup viewGroup must be has at least one View And the firstChild is PhotoView");
    }
}
