package com.cjj.coj.controller;

import com.cjj.coj.common.ResultBody;
import com.cjj.coj.common.ReturnCodeEnum;
import com.cjj.coj.modle.dto.submit.SubmitUpload;
import com.cjj.coj.modle.entity.User;
import com.cjj.coj.service.SubmitService;
import com.cjj.coj.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/submit")
public class SubmitController {

    private final SubmitService submitService;

    @Autowired
    public SubmitController(SubmitService submitService) {
        this.submitService = submitService;
    }

    @GetMapping("/get_detail")
    // 获取某一次提交的具体信息
    public ResultBody getDetail(@RequestParam Long id) {
        return submitService.getDetail(id);
    }

    @GetMapping("/get_detail_list")
    // 通过 用户id 和 题目id 查询提交记录详情
    public ResultBody getDetailList(@RequestParam Long userId, @RequestParam Long problemId) {
        User user = (User) ThreadLocalUtil.get();
        if (!user.getId().equals(userId)) {
            return ResultBody.fail(ReturnCodeEnum.USER_NOT_MATCH);
        }
        return submitService.getDetailList(userId, problemId);
    }

    @PostMapping("/upload")
    public ResultBody upload(@RequestBody SubmitUpload upload) {
        User user = (User) ThreadLocalUtil.get();
        if (!user.getId().equals(upload.getUserId())) {
            return ResultBody.fail(ReturnCodeEnum.USER_NOT_MATCH);
        }
        return submitService.upload(upload);
    }

}
