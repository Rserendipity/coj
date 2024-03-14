package com.cjj.coj.service;

import com.cjj.coj.common.ResultBody;
import com.cjj.coj.modle.dto.problem.ProblemAnswerDto;
import com.cjj.coj.modle.dto.problem.UploadProblemDto;

public interface ProblemService {
    ResultBody getProblemList();

    ResultBody getProblemDetail(Long id);

    ResultBody getProblemDetailForAdmin(Long id);

    ResultBody addProblem( UploadProblemDto dto);

    ResultBody editProblem( UploadProblemDto dto, Long id);

    ResultBody deleteProblem(Long id);

    ResultBody getAnswer(Long id);

    ResultBody submitAnswer(ProblemAnswerDto answer);
}
