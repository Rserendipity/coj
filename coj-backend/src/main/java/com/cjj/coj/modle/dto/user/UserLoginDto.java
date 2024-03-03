package com.cjj.coj.modle.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户登录")
public class UserLoginDto {
    @ApiModelProperty("账号")
    String account;
    @ApiModelProperty("密码")
    String password;
}
