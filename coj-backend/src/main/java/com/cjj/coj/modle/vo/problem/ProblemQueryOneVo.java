package com.cjj.coj.modle.vo.problem;

import com.alibaba.fastjson.JSON;
import com.cjj.coj.modle.dto.problem.JudgeConfig;
import com.cjj.coj.modle.entity.Problem;
import lombok.Data;

import java.util.List;

@Data
public class ProblemQueryOneVo {

    public ProblemQueryOneVo(Problem problem) {
        this.id = problem.getId().toString(); // 为了解决前端精度丢失的问题
        this.title = problem.getTitle();
        this.level = problem.getLevel();
        this.description = problem.getDescription();
        this.answer = problem.getAnswer();

        this.tags = JSON.parseArray(problem.getTags(), String.class);
        this.judgeConfig = JSON.parseObject(problem.getJudgeConfig(), JudgeConfig.class);

        this.pass = problem.getPass();
        this.userid = problem.getUserId().toString();
    }

    private String id;

    private String title;
    private String level;

    private String description;
    private String answer;

    private List<String> tags;
    private JudgeConfig judgeConfig;
    private Integer pass;

    private String userid;
}
