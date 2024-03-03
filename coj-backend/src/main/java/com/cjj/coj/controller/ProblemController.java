package com.cjj.coj.controller;

import com.alibaba.fastjson.JSON;
import com.cjj.coj.common.Constants;
import com.cjj.coj.common.ResultBody;
import com.cjj.coj.common.ReturnCode;
import com.cjj.coj.modle.dto.problem.ProblemAnswerDto;
import com.cjj.coj.modle.dto.problem.UploadProblemDto;
import com.cjj.coj.modle.entity.User;
import com.cjj.coj.service.ProblemService;
import com.cjj.coj.utils.JwtUtil;
import com.cjj.coj.utils.ThreadLocalUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/problem")
public class ProblemController {

    private final ProblemService problemService;

    @Autowired
    public ProblemController(ProblemService problemService) {
        this.problemService = problemService;
    }


    @GetMapping("/problem_list")
    public ResultBody getAllProblems() {
        return problemService.getProblemList();
    }

    @GetMapping("/problem_detail")
    public ResultBody getProblemDetail(@RequestParam Long id) {
        User user = (User) ThreadLocalUtil.get();

        // 如果是管理员，则返回全部的信息，包括测试用例
        if (Constants.ADMIN_USER_ROLE.equals(user.getRole())) {
            return problemService.getProblemDetailForAdmin(id);
        } else {
            return problemService.getProblemDetail(id);
        }
    }

    @PostMapping("/upload_problem")
    public ResultBody uploadProblem(@RequestBody UploadProblemDto dto,
                                    @RequestParam(required = false) Long id) {
        User user = (User) ThreadLocalUtil.get();

        if (!Constants.ADMIN_USER_ROLE.equals(user.getRole())) {
            return ResultBody.fail(ReturnCode.USER_NOT_ADMIN);
        }

        if (id == null) {
            return problemService.addProblem(dto);
        } else {
            return problemService.editProblem(dto, id);
        }
    }

    @PostMapping("delete_problem")
    public ResultBody deleteProblem(@RequestParam Long id) {
        User user = (User) ThreadLocalUtil.get();

        if (!Constants.ADMIN_USER_ROLE.equals(user.getRole())) {
            return ResultBody.fail(ReturnCode.USER_NOT_ADMIN);
        }
        return problemService.deleteProblem(id);
    }

    @GetMapping("/get_answer")
    public ResultBody getAnswer(@RequestParam Long id) {
        return problemService.getAnswer(id);
    }

    @PostMapping("/submit_answer")
    public ResultBody submitAnswer(@RequestBody ProblemAnswerDto answer) {
        User user = (User) ThreadLocalUtil.get();
        if (!user.getRole().equals(Constants.ADMIN_USER_ROLE)) {
            return ResultBody.fail(ReturnCode.USER_NOT_ADMIN);
        }
        return problemService.submitAnswer(answer);
    }
}
