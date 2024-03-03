package com.cjj.coj.utils;


import org.springframework.util.DigestUtils;

import java.util.UUID;

public class UserPasswordUtil {

    /**
     * 获取随机盐值
     * @return 盐值
     */
    public static String getSalt() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 通过 原始密码 + 盐 生成真正的密码
     * @param rawPassword 明文密码
     * @param salt 盐
     * @return 真实存储的密码
     */
    public static String getPassword(String rawPassword, String salt) {
        return DigestUtils.md5DigestAsHex(( "cjj" + rawPassword + salt).getBytes());
    }

    /**
     *  通过 原始密码 + 盐 校验密码
     * @param rawPassword 明文密码
     * @param salt 盐
     * @param realPassword 数据库中存储的密码
     * @return 明文是否和数据库中的匹配
     */
    public static boolean checkPassword(String rawPassword, String salt, String realPassword) {
        return realPassword.equals(getPassword(rawPassword, salt));
    }
}
