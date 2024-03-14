package com.cjj.coj.modle.vo.submit;

import com.alibaba.fastjson.JSON;
import com.cjj.coj.common.JudgeInfo;
import com.cjj.coj.modle.entity.Submit;
import lombok.Data;

import java.util.Date;

@Data
public class SubmitVo {

    public SubmitVo(Submit submission) {
        this.id = submission.getId();
        this.problemId = submission.getProblemId();
        this.userId = submission.getUserId();
        this.language = submission.getLanguage();
        this.code = submission.getCode();
        this.state = submission.getState();
        this.judgeInfo = JSON.parseObject(submission.getJudgeInfo(), JudgeInfo.class);
        this.time = submission.getTime();
    }

    private Long id;
    private Long problemId;
    private Long userId;
    private String language;
    private String code;
    private Integer state;
    private JudgeInfo judgeInfo;
    private Date time;
}
