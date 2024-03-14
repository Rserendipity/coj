package com.cjj.coj.codesandbox.model;

import lombok.Data;

import java.util.List;

@Data
public class ExecuteResult {
    private List<String> output;
    private Integer useTime;
    private Integer useMemory;
}
