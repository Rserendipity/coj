package com.cjj.coj.common;

import lombok.Data;

@Data
public class JudgeInfo {
    private Integer state;
    private String message;
    private String stderr;
    private Integer useTime;
    private Integer useMemory;
}
