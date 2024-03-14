package com.cjj.coj.exception;

import com.cjj.coj.common.ResultBody;
import com.cjj.coj.common.ReturnCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
@Slf4j
public class GlobeExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResultBody handlerNoFoundException(Exception e) {
        log.warn("请求了非法接口：{}", e.getMessage());
        return ResultBody.fail(ReturnCodeEnum.SERVICE_NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResultBody exceptionHandler(Exception e) {
        log.warn("未知异常：{}", e.getMessage());
        return ResultBody.fail(ReturnCodeEnum.SERVICE_ERROR);
    }
}
