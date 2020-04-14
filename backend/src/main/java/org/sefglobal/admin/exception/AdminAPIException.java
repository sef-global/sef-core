package org.sefglobal.admin.exception;

public class AdminAPIException extends Exception {

    public AdminAPIException() {
        super();
    }

    public AdminAPIException(String msg) {
        super(msg);
    }

    public AdminAPIException(String msg, Throwable e) {
        super(msg, e);
    }
}
