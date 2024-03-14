package com.cjj.coj.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtil {
    private static final String SIGN = "cjj";
    private static final long EXPIRE_TIME = 24 * 60 * 60 * 1000;  //设置过期时间为24小时

    public static String generateToken(Map<String, String> payload) {
        return JWT.create()
                .withClaim("payload", payload)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TIME))  //设置过期时间
                .sign(Algorithm.HMAC256(SIGN));
    }

    public static boolean verifyToken(String token) {
        try {
            JWT.require(Algorithm.HMAC256(SIGN))
                    .build()
                    .verify(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Retrieves user information from the provided token.
     *
     * @param  token  the JWT token containing user information
     * @return       the user information extracted from the token
     */
    public static String getInfo(String token) {
        return (String) JWT.require(Algorithm.HMAC256(SIGN))
                .build()
                .verify(token)
                .getClaim("payload")
                .asMap()
                .get("user");
    }

}
