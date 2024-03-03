package com.cjj.coj.common;

import lombok.Getter;

@Getter
public enum ReturnCode {
    SUCCESS(0, "操作成功"),
    PARAMETER_MISS(1001, "缺少参数"),

    // 用户
    USER_INFO_ILLEGAL(2000, "用户信息不符合规则"),
    USER_INFO_ERROR(2001, "用户账号或密码错误"),
    USER_NOT_EXIST(2002, "用户不存在"),
    USER_NOT_LOGIN(2003, "用户未登录"),
    USER_NOT_ADMIN(2004, "用户非管理员"),
    USER_NOT_MATCH(2005, "用户不匹配"),
    USER_EXISTS(2006, "用户已存在，请前往登录"),

    // 题目
    QUESTION_NOT_EXIST(3000, "题目不存在"),

    // Jwt无效
    JWT_INVALID(4000, "Jwt无效"),

    // 服务器内部问题
    SERVICE_ERROR(5000, "服务器出现问题，请联系管理员"),
    SERVICE_FILE_NOT_EXIST(5001, "服务器文件不存在"),;



    ReturnCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    final int code;
    final String message;
}
