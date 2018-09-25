package com.davemorrissey.labs.exceptions;

public class BackGroundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BackGroundException() {
        super("Please set the parent view background in the xml which use FingerPanGroup viewGroup.");
    }
}
