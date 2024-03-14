package com.cjj.coj.modle.dto.problem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("上传题目")
public class UploadProblemDto {

    @ApiModelProperty("题目标题")
    private String title;

    @ApiModelProperty("题目描述")
    private String level;

    @ApiModelProperty("题目描述")
    private String description;

    @ApiModelProperty("标签数组")
    private List<String> tags;

    @ApiModelProperty("判题配置")
    private JudgeConfig judgeConfig;

    @ApiModelProperty("判题用例")
    private List<JudgeCase> judgeCases;

    @ApiModelProperty("用户id")
    private Long userId;
}
