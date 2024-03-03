package com.cjj.coj.interceptor;

import com.alibaba.fastjson.JSON;
import com.cjj.coj.common.ResultBody;
import com.cjj.coj.common.ReturnCode;
import com.cjj.coj.modle.entity.User;
import com.cjj.coj.utils.JwtUtil;
import com.cjj.coj.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 未登录，或者令牌失效，直接返回
        try {
            String token = request.getHeader("Authorization");
            String user = JwtUtil.getInfo(token);
            ThreadLocalUtil.set(JSON.parseObject(user, User.class));
            return true;
        } catch (Exception e) {
            log.warn(request.getRequestURI() + "未携带令牌");
            response.setContentType("application/json; charset=utf-8");
            String s = JSON.toJSONString(ResultBody.fail(ReturnCode.JWT_INVALID));
            response.getWriter().write(s);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.remove();
    }
}
