package com.cjj.coj.controller;

import com.cjj.coj.common.Constants;
import com.cjj.coj.common.ResultBody;
import com.cjj.coj.common.ReturnCode;
import com.cjj.coj.modle.dto.submit.SubmitQueryDetail;
import com.cjj.coj.modle.entity.User;
import com.cjj.coj.utils.ThreadLocalUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/submit")
public class SubmitController {

    @GetMapping("/submit_all")
    public ResultBody submitAll() {
        User user = (User) ThreadLocalUtil.get();
        if (!user.getRole().equals(Constants.ADMIN_USER_ROLE)) {
            return ResultBody.fail(ReturnCode.USER_NOT_ADMIN);
        }
        // todo 查询所有提交记录，管理员
        return ResultBody.success();
    }

    @GetMapping("/submit_detail")
    public ResultBody submitDetail(@RequestBody SubmitQueryDetail queryDetail) {
        User user = (User) ThreadLocalUtil.get();
        if (!user.getAccount().equals(queryDetail.getUserId().toString())) {
            return ResultBody.fail(ReturnCode.USER_NOT_MATCH);
        }
        // todo 查询提交记录详情，用户可见
        return ResultBody.success();
    }

}
