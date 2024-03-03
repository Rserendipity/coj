package com.cjj.coj.service.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cjj.coj.common.Constants;
import com.cjj.coj.common.ResultBody;
import com.cjj.coj.common.ReturnCode;
import com.cjj.coj.mapper.UserMapper;
import com.cjj.coj.modle.dto.user.UserLoginDto;
import com.cjj.coj.modle.dto.user.UserRegisterDto;
import com.cjj.coj.modle.dto.user.UserUpdateInfoDto;
import com.cjj.coj.modle.entity.User;
import com.cjj.coj.modle.vo.user.UserVo;
import com.cjj.coj.service.UserService;
import com.cjj.coj.utils.JwtUtil;
import com.cjj.coj.utils.UserPasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    private boolean checkUserInfoLength(String username, String password) {
        return username.length() >= 4 && password.length() >= 8;
    }

    public ResultBody login(UserLoginDto dto) {
        // 确认dto非空
        if (!StringUtils.hasLength(dto.getAccount()) || !StringUtils.hasLength(dto.getPassword())) {
            return ResultBody.fail(ReturnCode.PARAMETER_MISS);
        }

        // 检查账号密码长度
        if (!checkUserInfoLength(dto.getAccount(), dto.getPassword())) {
            return ResultBody.fail(ReturnCode.USER_INFO_ILLEGAL);
        }

        // 查询用户
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("account", dto.getAccount()));

        // 用户不存在
        if (user == null) {
            return ResultBody.fail(ReturnCode.USER_INFO_ERROR);
        }

        // 确认密码
        if (!UserPasswordUtil.checkPassword(dto.getPassword(), user.getSalt(), user.getPassword())) {
            return ResultBody.fail(ReturnCode.USER_INFO_ERROR);
        }

        // 使用jwt令牌
        UserVo userVo = new UserVo(user);
        Map<String, String> payload = new HashMap<>();
        payload.put("user", JSON.toJSONString(userVo));
        userVo.setToken(JwtUtil.generateToken(payload));

        System.out.println(payload.get("user"));
        System.out.println(userVo);

        return ResultBody.success(userVo);
    }

    public ResultBody register(UserRegisterDto dto) {
        // 确认dto非空
        if (!StringUtils.hasLength(dto.getAccount()) || !StringUtils.hasLength(dto.getPassword()) || !StringUtils.hasLength(dto.getConfirmPass())) {
            return ResultBody.fail(ReturnCode.PARAMETER_MISS);
        }

        // 检查密码长度
        if (!checkUserInfoLength(dto.getAccount(), dto.getPassword())) {
            return ResultBody.fail(ReturnCode.USER_INFO_ILLEGAL);
        }

        // 用户已存在
        Long count = userMapper.selectCount(new QueryWrapper<User>().eq("account", dto.getAccount()));
        if (count != 0) {
            return ResultBody.fail(ReturnCode.USER_EXISTS);
        }

        // 用户不存在，创建
        User user = new User();
        user.setAccount(dto.getAccount());
        user.setSalt(UserPasswordUtil.getSalt());
        user.setPassword(UserPasswordUtil.getPassword(dto.getPassword(), user.getSalt()));
        user.setRole(Constants.DEFAULT_USER_ROLE);

        userMapper.insert(user);

        return ResultBody.success();
    }

    @Override
    public ResultBody updateUserInfo(UserUpdateInfoDto dto) {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("account", dto.getAccount()));
        user.setNickname(dto.getNickname());
        userMapper.updateById(user);
        return ResultBody.success();
    }

    @Override
    public ResultBody updateUserPassword(UserUpdateInfoDto dto) {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("account", dto.getAccount()));
        if (!UserPasswordUtil.checkPassword(dto.getRawPass(),  user.getSalt(), user.getPassword())) {
            return ResultBody.fail(ReturnCode.USER_INFO_ERROR);
        }

        String salt = UserPasswordUtil.getSalt();
        String password = UserPasswordUtil.getPassword(dto.getNewPass(), salt);
        user.setSalt(salt);
        user.setPassword(password);
        userMapper.updateById(user);

        return ResultBody.success();
    }

    @Override
    public ResultBody getUserInfo(Long id) {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("id", id));
        return ResultBody.success(new UserVo(user));
    }
}
