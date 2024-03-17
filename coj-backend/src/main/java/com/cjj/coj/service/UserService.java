package com.cjj.coj.service;


import com.cjj.coj.common.ResultBody;
import com.cjj.coj.modle.dto.user.UserLoginDto;
import com.cjj.coj.modle.dto.user.UserRegisterDto;
import com.cjj.coj.modle.dto.user.UserUpdateInfoDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface UserService {
    ResultBody login(UserLoginDto user);

    ResultBody register(UserRegisterDto user);

    ResultBody updateUserInfo(UserUpdateInfoDto dto);

    ResultBody updateUserPassword(UserUpdateInfoDto dto);

    ResultBody getUserInfo(Long id);

    ResultBody uploadAvatar(Long dto, MultipartFile file) throws IOException;
}
