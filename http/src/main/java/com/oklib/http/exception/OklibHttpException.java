package com.oklib.http.exception;

public class OklibHttpException extends Exception {

    public static OklibHttpException INSTANCE(String msg) {
        return new OklibHttpException(msg);
    }

    public OklibHttpException() {
        super();
    }

    public OklibHttpException(String detailMessage) {
        super(detailMessage);
    }

    public OklibHttpException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public OklibHttpException(Throwable throwable) {
        super(throwable);
    }
}