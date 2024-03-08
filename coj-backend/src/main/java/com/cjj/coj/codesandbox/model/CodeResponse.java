package com.cjj.coj.codesandbox.model;

import lombok.Data;

import java.util.List;

@Data
public class CodeResponse {
    private Integer state; // 状态码，运行成功为0，编译失败为1 运行失败为2（崩溃等）
    private String stderr; // 标准错误，如果编译运行完成则为空

    // 如果发生了任何错误，下列三个字段都为null
    private List<String> stdout; // 输出
    private Integer useTime; // 用时
    private Integer useMemory; // 内存
}
