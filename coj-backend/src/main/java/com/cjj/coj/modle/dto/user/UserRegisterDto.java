package com.cjj.coj.modle.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户注册")
public class UserRegisterDto {
    @ApiModelProperty("账号")
    String account;
    @ApiModelProperty("密码")
    String password;
    @ApiModelProperty("确认密码")
    String confirmPass;
}
