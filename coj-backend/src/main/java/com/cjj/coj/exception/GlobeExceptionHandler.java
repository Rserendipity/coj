package com.cjj.coj.exception;

import com.cjj.coj.common.ResultBody;
import com.cjj.coj.common.ReturnCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobeExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResultBody exceptionHandler(Exception e) {
        e.printStackTrace();
        return ResultBody.fail(ReturnCode.SERVICE_ERROR);
    }
}
