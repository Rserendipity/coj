package com.cjj.coj.common;

import lombok.Getter;

@Getter
public enum JudgeStateEnum {
    PENDING(0, "Pending"),
    JUDGING(1, "Judging"),
    ACCEPTED(2, "Accepted"),
    WRONG_ANSWER(3, "Wrong Answer"),
    TIME_LIMIT_EXCEEDED(4, "Time Limit Exceeded"),
    MEMORY_LIMIT_EXCEEDED(5, "Memory Limit Exceeded"),
    COMPILE_ERROR(6, "Compile Error"),
    RUNTIME_ERROR(7, "Runtime Error"),
    SYSTEM_ERROR(8, "System Error");

    JudgeStateEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    final Integer code;
    final String message;
}
