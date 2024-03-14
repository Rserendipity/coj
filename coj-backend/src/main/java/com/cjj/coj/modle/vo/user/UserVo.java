package com.cjj.coj.modle.vo.user;

import com.cjj.coj.modle.entity.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserVo implements Serializable {
    public UserVo(User user) {
        this.id = user.getId().toString(); // 为了解决前端精度丢失的问题
        this.account = user.getAccount();
        this.nickname = user.getNickname();
        this.role = user.getRole();
        this.avatar =  user.getAvatar();
    }

    private String id;

    private String account;

    private String nickname;

    private String role;

    private String avatar;

    private String token;
}
