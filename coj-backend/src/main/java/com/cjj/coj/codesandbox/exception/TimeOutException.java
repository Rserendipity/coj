package com.cjj.coj.codesandbox.exception;

public class TimeOutException extends RuntimeException {
    public TimeOutException(String executionTimedOut) {
        super(executionTimedOut);
    }
}
