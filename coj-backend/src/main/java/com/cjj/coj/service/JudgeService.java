package com.cjj.coj.service;

import com.cjj.coj.modle.entity.Problem;
import com.cjj.coj.modle.entity.Submit;
import org.springframework.stereotype.Service;

@Service
public interface JudgeService {
    void doJudge(Submit submission, Problem problem);
}
