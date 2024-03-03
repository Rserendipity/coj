package com.cjj.coj.modle.dto.problem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
@ApiModel("判题用例")
public class JudgeCase {
    @ApiParam("输入")
    private String input;
    @ApiParam("输出")
    private String output;
}
