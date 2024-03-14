package com.cjj.coj.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cjj.coj.common.ResultBody;
import com.cjj.coj.common.ReturnCodeEnum;
import com.cjj.coj.mapper.ProblemMapper;
import com.cjj.coj.modle.dto.problem.ProblemAnswerDto;
import com.cjj.coj.modle.dto.problem.UploadProblemDto;
import com.cjj.coj.modle.entity.Problem;
import com.cjj.coj.modle.vo.problem.ProblemQueryListVo;
import com.cjj.coj.modle.vo.problem.ProblemQueryOneAdminVo;
import com.cjj.coj.modle.vo.problem.ProblemQueryOneVo;
import com.cjj.coj.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 22490
 * &#064;description  针对表【problem】的数据库操作Service实现
 * &#064;createDate  2024-02-16 22:46:39
 */
@Service
public class ProblemServiceImpl implements ProblemService {

    private final ProblemMapper problemMapper;

    @Autowired
    public ProblemServiceImpl(ProblemMapper problemMapper) {
        this.problemMapper = problemMapper;
    }

    @Override
    public ResultBody getProblemList() {
        QueryWrapper<Problem> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "title", "level", "tags", "pass", "user_id");
        List<Problem> problems = problemMapper.selectList(queryWrapper);
        List<ProblemQueryListVo> vos = new ArrayList<>();
        for (Problem problem : problems) {
            ProblemQueryListVo vo = new ProblemQueryListVo(problem);
            vos.add(vo);
        }
        return ResultBody.success(vos);
    }

    @Override
    public ResultBody getProblemDetail(Long id) {
        QueryWrapper<Problem> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);

        Problem problem = problemMapper.selectOne(wrapper);
        if (problem == null) {
            return ResultBody.fail(ReturnCodeEnum.QUESTION_NOT_EXIST);
        }

        return ResultBody.success(new ProblemQueryOneVo(problem));
    }

    @Override
    public ResultBody getProblemDetailForAdmin(Long id) {
        Problem problem = problemMapper.selectById(id);
        if (problem == null) {
            return ResultBody.fail(ReturnCodeEnum.QUESTION_NOT_EXIST);
        }

        return ResultBody.success(new ProblemQueryOneAdminVo(problem));
    }

    @Override
    public ResultBody addProblem(UploadProblemDto dto) {

        Problem problem = new Problem();
        DtoToEntity(dto, problem);

        int insert = problemMapper.insert(problem);
        if (insert != 1) {
            return ResultBody.fail(ReturnCodeEnum.SERVICE_ERROR);
        }

        return ResultBody.success();
    }

    @Override
    public ResultBody editProblem(UploadProblemDto dto, Long id) {
        Problem problem = problemMapper.selectById(id);
        if (problem == null) {
            return ResultBody.fail(ReturnCodeEnum.QUESTION_NOT_EXIST);
        }

        DtoToEntity(dto, problem);

        int row = problemMapper.updateById(problem);

        if (row != 1) {
            return ResultBody.fail(ReturnCodeEnum.SERVICE_ERROR);
        }

        return ResultBody.success();
    }

    @Override
    public ResultBody deleteProblem(Long id) {
        int row = problemMapper.deleteById(id);
        if (row != 1) {
            return ResultBody.fail(ReturnCodeEnum.QUESTION_NOT_EXIST);
        }
        return ResultBody.success();
    }

    @Override
    public ResultBody getAnswer(Long id) {
        Problem problem = problemMapper.selectById(id);
        if (problem == null) {
            return ResultBody.fail(ReturnCodeEnum.QUESTION_NOT_EXIST);
        }
        return ResultBody.success(problem.getAnswer());
    }

    @Override
    public ResultBody submitAnswer(ProblemAnswerDto answer) {
        Problem problem = problemMapper.selectById(answer.getId());
        if (problem == null) {
            return ResultBody.fail(ReturnCodeEnum.QUESTION_NOT_EXIST);
        }
        problem.setAnswer(answer.getAnswer());
        problemMapper.updateById(problem);
        return ResultBody.success();
    }

    private void DtoToEntity(UploadProblemDto dto, Problem problem) {
        problem.setTitle(dto.getTitle());
        problem.setLevel(dto.getLevel());
        problem.setDescription(dto.getDescription());
        problem.setTags(JSON.toJSONString(dto.getTags()));
        problem.setPass(0);
        problem.setJudgeConfig(JSON.toJSONString(dto.getJudgeConfig()));
        problem.setJudgeCases(JSON.toJSONString(dto.getJudgeCases()));
        problem.setDeleted(0);
        problem.setUserId(dto.getUserId());
    }
}

