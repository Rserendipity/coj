package com.cjj.coj.modle.dto.problem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
@ApiModel("判题配置")
public class JudgeConfig {
    @ApiParam("时间限制(ms)")
    private Integer timeLimit;
    @ApiParam("内存限制(kb)")
    private Integer memoryLimit;
}
