package com.cjj.coj.modle.vo.problem;

import com.alibaba.fastjson.JSON;
import com.cjj.coj.modle.dto.problem.JudgeCase;
import com.cjj.coj.modle.dto.problem.JudgeConfig;
import com.cjj.coj.modle.entity.Problem;
import lombok.Data;

import java.util.List;

@Data
public class ProblemQueryOneAdminVo {

    // 多了测试用例
    public ProblemQueryOneAdminVo(Problem problem) {
        this.id = problem.getId().toString(); // 为了解决前端精度丢失的问题
        this.title = problem.getTitle();
        this.level = problem.getLevel();
        this.description = problem.getDescription();
        this.answer = problem.getAnswer();

        this.tags = JSON.parseArray(problem.getTags(), String.class);
        this.pass = problem.getPass();
        this.judgeCases = JSON.parseArray(problem.getJudgeCases(), JudgeCase.class);
        this.judgeConfig = JSON.parseObject(problem.getJudgeConfig(), JudgeConfig.class);

        this.userid = problem.getUserId().toString();
    }

    private String id;

    private String title;
    private String level;

    private String description;
    private String answer;

    private List<String> tags;
    private Integer pass;
    private JudgeConfig judgeConfig;
    private List<JudgeCase> judgeCases;

    private String userid;
}
