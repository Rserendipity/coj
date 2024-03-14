package com.cjj.coj.common;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("统一返回体")
public class ResultBody {

    private int code;
    private String message;
    private Object data;


    public ResultBody(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ResultBody success() {
        return success(null);
    }

    public static ResultBody success(Object object) {
        return new ResultBody(ReturnCodeEnum.SUCCESS.getCode(), ReturnCodeEnum.SUCCESS.getMessage(), object);
    }

    public static ResultBody fail(ReturnCodeEnum code) {
        return fail(code, null);
    }

    public static ResultBody fail(ReturnCodeEnum code, Object data) {
        return new ResultBody(code.getCode(), code.getMessage(), data);
    }

}
