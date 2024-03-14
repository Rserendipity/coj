package com.cjj.coj.service.impl;

import com.alibaba.fastjson.JSON;
import com.cjj.coj.codesandbox.model.web.CodeRequest;
import com.cjj.coj.codesandbox.model.web.CodeResponse;
import com.cjj.coj.codesandbox.service.CodeSandbox;
import com.cjj.coj.common.JudgeInfo;
import com.cjj.coj.common.JudgeStateEnum;
import com.cjj.coj.mapper.ProblemMapper;
import com.cjj.coj.mapper.SubmitMapper;
import com.cjj.coj.modle.dto.problem.JudgeCase;
import com.cjj.coj.modle.dto.problem.JudgeConfig;
import com.cjj.coj.modle.entity.Problem;
import com.cjj.coj.modle.entity.Submit;
import com.cjj.coj.service.JudgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class JudgeServiceImpl implements JudgeService {

    private final CodeSandbox codeSandbox;
    private final SubmitMapper submitMapper;
    private final ProblemMapper problemMapper;

    @Autowired
    public JudgeServiceImpl(CodeSandbox codeSandbox, SubmitMapper submitMapper, ProblemMapper problemMapper) {
        this.codeSandbox = codeSandbox;
        this.submitMapper = submitMapper;
        this.problemMapper = problemMapper;
    }

    @Override
    @Async
    public void doJudge(Submit submission, Problem problem) {
        // 状态更改为判题中
        updateSubmitState(submission, JudgeStateEnum.JUDGING, null, null, null);

        // 构造请求
        CodeRequest codeRequest = new CodeRequest();
        codeRequest.setCode(submission.getCode());
        codeRequest.setLanguage(submission.getLanguage());
        List<JudgeCase> judgeCases = JSON.parseArray(problem.getJudgeCases(), JudgeCase.class);
        List<String> inputs = new ArrayList<>();
        List<String> outputs = new ArrayList<>();
        judgeCases.forEach(judgeCase -> {
            inputs.add(judgeCase.getInput());
            outputs.add(judgeCase.getOutput());
        });
        codeRequest.setJudgeCases(inputs);

        // 用代码沙箱执行代码
        CodeResponse codeResponse = codeSandbox.executeCode(codeRequest);

        // 沙箱出问题
        if (codeResponse == null) {
            updateSubmitState(submission, JudgeStateEnum.SYSTEM_ERROR, null, null, null);
            return;
        }

        // 编译 / 运行 发生错误
        if (!codeResponse.getState().equals(0)) {
            if (codeResponse.getState().equals(1)) {
                updateSubmitState(submission, JudgeStateEnum.COMPILE_ERROR, codeResponse.getStderr(), null, null);
            } else {
                updateSubmitState(submission, JudgeStateEnum.RUNTIME_ERROR, codeResponse.getStderr(), null, null);
            }
            return;
        }

        // 超出时间限制
        JudgeConfig config = JSON.parseObject(problem.getJudgeConfig(), JudgeConfig.class);
        if (codeResponse.getUseTime() > config.getTimeLimit()) {
            updateSubmitState(submission, JudgeStateEnum.TIME_LIMIT_EXCEEDED, null, codeResponse.getUseTime(), codeResponse.getUseMemory());
            return;
        }

        // 超出内存限制
        if (codeResponse.getUseMemory() > config.getMemoryLimit()) {
            updateSubmitState(submission, JudgeStateEnum.MEMORY_LIMIT_EXCEEDED, null, codeResponse.getUseTime(), codeResponse.getUseMemory());
            return;
        }

        // 通过对比输出判断结果
        List<String> stdout = codeResponse.getStdout();
        // 输出不一致
        if (stdout.size() != outputs.size()) {
            updateSubmitState(submission, JudgeStateEnum.WRONG_ANSWER, null, null, null);
            return;
        }

        // 逐个对比
        for (int i = 0; i < stdout.size(); i++) {
            if (!stdout.get(i).equals(outputs.get(i))) {
                updateSubmitState(submission, JudgeStateEnum.WRONG_ANSWER, null, null, null);
                return;
            }
        }

        // 没有问题，通过
        updateSubmitState(submission, JudgeStateEnum.ACCEPTED, null, codeResponse.getUseTime(), codeResponse.getUseMemory());
        // 更新题目通过数
        problem.setPass(problem.getPass() + 1);
        problemMapper.updateById(problem);
    }

    private void updateSubmitState(Submit submission, JudgeStateEnum state, String error, Integer useTime, Integer useMemory) {
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setState(state.getCode());
        judgeInfo.setMessage(state.getMessage());
        judgeInfo.setUseTime(useTime);
        judgeInfo.setUseMemory(useMemory);
        judgeInfo.setStderr(error);

        submission.setState(state.getCode());
        submission.setJudgeInfo(JSON.toJSONString(judgeInfo));
        submission.setTime(new Date());
        submitMapper.updateById(submission);
    }
}
