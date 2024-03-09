package com.cjj.coj.codesandbox.exception;

public class CompileException extends RuntimeException {
    public CompileException(String compileError) {
        super(compileError);
    }
}
