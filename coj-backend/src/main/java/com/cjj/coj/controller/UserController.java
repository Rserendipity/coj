package com.cjj.coj.controller;

import com.cjj.coj.common.ResultBody;
import com.cjj.coj.common.ReturnCodeEnum;
import com.cjj.coj.modle.dto.user.UserLoginDto;
import com.cjj.coj.modle.dto.user.UserRegisterDto;
import com.cjj.coj.modle.dto.user.UserUpdateInfoDto;
import com.cjj.coj.modle.entity.User;
import com.cjj.coj.service.UserService;
import com.cjj.coj.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResultBody login(@RequestBody UserLoginDto user) {
        if (user == null) {
            return ResultBody.fail(ReturnCodeEnum.PARAMETER_MISS);
        }
        return userService.login(user);
    }

    @PostMapping("/register")
    public ResultBody register(@RequestBody UserRegisterDto user) {
        if (user == null) {
            return ResultBody.fail(ReturnCodeEnum.PARAMETER_MISS);
        }

        // 两次输入的密码不一致
        if (!user.getPassword().equals(user.getConfirmPass())) {
            return ResultBody.fail(ReturnCodeEnum.USER_INFO_ILLEGAL);
        }

        return userService.register(user);
    }

    @GetMapping("/userinfo")
    // 也可以在前端做Base64解析，也就不需要这个接口了
    public ResultBody getUserInfo() {
        User user = (User) ThreadLocalUtil.get();
        return userService.getUserInfo(user.getId());
    }

    @PostMapping("/update_info")
    public ResultBody updateUserInfo(@RequestBody UserUpdateInfoDto dto) {
        User user = (User) ThreadLocalUtil.get();
        if (!user.getAccount().equals(dto.getAccount())) {
            return ResultBody.fail(ReturnCodeEnum.USER_INFO_ERROR);
        }

        if (dto.getNickname() == null || dto.getNickname().isEmpty()) {
            return ResultBody.fail(ReturnCodeEnum.PARAMETER_MISS);
        }

        if (dto.getNickname().length() > 15) {
            return ResultBody.fail(ReturnCodeEnum.USER_INFO_ILLEGAL);
        }

        return userService.updateUserInfo(dto);
    }

    @PostMapping("/update_password")
    public ResultBody updateUserPassword(@RequestBody UserUpdateInfoDto dto) {
        User user = (User) ThreadLocalUtil.get();
        if (!user.getAccount().equals(dto.getAccount())) {
            return ResultBody.fail(ReturnCodeEnum.USER_INFO_ERROR);
        }

        if (!dto.getNewPass().equals(dto.getConfirmPass())) {
            return ResultBody.fail(ReturnCodeEnum.USER_INFO_ILLEGAL);
        }

        return userService.updateUserPassword(dto);
    }

    @PostMapping("/update_avatar")
    public ResultBody updateAvatar(MultipartFile file) throws IOException {
        User user = (User) ThreadLocalUtil.get();

        if (file.getSize() > 1024 * 1024) { // 大于1MB
            return ResultBody.fail(ReturnCodeEnum.USER_AVATAR_TOO_BIG);
        }

        return userService.uploadAvatar(user.getId(), file);
    }

}
