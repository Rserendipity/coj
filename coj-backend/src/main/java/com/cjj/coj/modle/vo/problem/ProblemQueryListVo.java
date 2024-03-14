package com.cjj.coj.modle.vo.problem;

import com.alibaba.fastjson.JSON;
import com.cjj.coj.modle.entity.Problem;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class ProblemQueryListVo implements Serializable {
    public ProblemQueryListVo(Problem problem) {
        this.id = problem.getId().toString(); // 为了解决前端精度丢失的问题
        this.title = problem.getTitle();
        this.level = problem.getLevel();


        this.tags = JSON.parseArray(problem.getTags(), String.class);

        this.pass = problem.getPass();
        this.userid = problem.getUserId().toString(); // 为了解决前端精度丢失的问题
    }

    private String id;
    private String title;
    private String level;

    private List<String> tags;
    private Integer pass;

    private String userid;
}