package com.cjj.coj.modle.dto.user;

import lombok.Data;

@Data
public class UserUpdateInfoDto {
    private String account;
    private String nickname;
    private String rawPass;
    private String newPass;
    private String confirmPass;
    private String avatar;
}
