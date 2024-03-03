package com.cjj.coj.modle.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName user
 */
@TableName(value = "user")
@Data
public class User implements Serializable {
    @TableId(type =  IdType.AUTO)
    private Long id;

    private String account;

    private String nickname;

    private String password;

    private String salt;

    private String avatar;

    private String role;
}
