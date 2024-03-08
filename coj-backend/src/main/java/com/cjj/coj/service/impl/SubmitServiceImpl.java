package com.cjj.coj.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cjj.coj.common.JudgeInfo;
import com.cjj.coj.common.JudgeStateEnum;
import com.cjj.coj.common.ResultBody;
import com.cjj.coj.common.ReturnCodeEnum;
import com.cjj.coj.mapper.ProblemMapper;
import com.cjj.coj.mapper.SubmitMapper;
import com.cjj.coj.modle.dto.submit.SubmitUpload;
import com.cjj.coj.modle.entity.Problem;
import com.cjj.coj.modle.entity.Submit;
import com.cjj.coj.modle.vo.submit.SubmitListVo;
import com.cjj.coj.modle.vo.submit.SubmitVo;
import com.cjj.coj.service.JudgeService;
import com.cjj.coj.service.SubmitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class SubmitServiceImpl implements SubmitService {

    private final SubmitMapper submitMapper;

    private final ProblemMapper problemMapper;

    private final JudgeService judgeService;

    @Autowired
    public SubmitServiceImpl(SubmitMapper submitMapper, ProblemMapper problemMapper, JudgeService judgeService) {
        this.submitMapper = submitMapper;
        this.problemMapper = problemMapper;
        this.judgeService = judgeService;
    }

    @Override
    public ResultBody getDetail(Long id) {
        Submit submission = submitMapper.selectById(id);
        if (submission == null) {
            return ResultBody.fail(ReturnCodeEnum.SUBMIT_NOT_EXIST);
        }

        return ResultBody.success(new SubmitVo(submission));
    }

    @Override
    public ResultBody getDetailList(Long userId, Long problemId) {
        QueryWrapper<Submit> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("problem_id", problemId);

        // 不要查询code字段
        queryWrapper.select(Submit.class, info -> !info.getColumn().equals("code"));

        List<Submit> submissions = submitMapper.selectList(queryWrapper);
        List<SubmitListVo> submitListVos = new ArrayList<>();
        submissions.forEach(submission -> submitListVos.add(new SubmitListVo(submission)));

        return ResultBody.success(submitListVos);
    }

    @Override
    public ResultBody upload(SubmitUpload upload){
        // 题目不存在
        Problem problem = problemMapper.selectById(upload.getProblemId());
        if (problem == null) {
            return ResultBody.fail(ReturnCodeEnum.QUESTION_NOT_EXIST);
        }


        // 如果在 pending / judging 状态，不允许提交，减轻判题模块负担
        Submit submission = submitMapper.selectExistPendingOrJudging(upload.getUserId(), upload.getProblemId());
        log.warn("submit:{}", submission);

        if (submission != null) {
            if (JudgeStateEnum.PENDING.getCode().equals(submission.getState())) {
                return ResultBody.fail(ReturnCodeEnum.SUBMIT_PENDING);
            }
            if (JudgeStateEnum.JUDGING.getCode().equals(submission.getState())) {
                return ResultBody.fail(ReturnCodeEnum.SUBMIT_JUDGING);
            }
        }

        // 插入提交记录
        submission = new Submit();
        submission.setProblemId(upload.getProblemId());
        submission.setUserId(upload.getUserId());
        submission.setLanguage(upload.getLanguage());
        submission.setCode(upload.getCode());
        submission.setState(JudgeStateEnum.PENDING.getCode());

        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setState(JudgeStateEnum.PENDING.getCode());
        judgeInfo.setMessage(JudgeStateEnum.PENDING.getMessage());
        judgeInfo.setStderr(null);
        judgeInfo.setUseTime(null);
        judgeInfo.setUseMemory(null);
        submission.setJudgeInfo(JSON.toJSONString(judgeInfo));

        submission.setTime(new Date());
        submitMapper.insert(submission);

        // 启动判题服务，异步执行
        judgeService.doJudge(submission, problem);

        return ResultBody.success();
    }
}
