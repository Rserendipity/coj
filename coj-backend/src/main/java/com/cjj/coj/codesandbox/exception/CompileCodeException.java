package com.cjj.coj.codesandbox.exception;

public class CompileCodeException extends RuntimeException {
    public CompileCodeException(String compileError) {
        super(compileError);
    }
}
