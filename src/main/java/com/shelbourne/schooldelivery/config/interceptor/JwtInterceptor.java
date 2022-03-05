package com.shelbourne.schooldelivery.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.shelbourne.schooldelivery.common.States;
import com.shelbourne.schooldelivery.entity.User;
import com.shelbourne.schooldelivery.exception.ServiceException;
import com.shelbourne.schooldelivery.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果不是映射到方法上直接通过（放过一些静态资源）
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        String token = request.getHeader("token");
        if (StrUtil.isBlank(token)) {
            throw new ServiceException(States.USER_EXCEPTION, "请先登录！");
        }
        //获取token中的userId
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException jde) {
            throw new ServiceException(States.USER_EXCEPTION, "token验证失败，请重新登录！");
        }
        //根据userId得到user，在得到password
        User user = userService.getById(userId);
        if (user == null) {
            throw new ServiceException(States.USER_EXCEPTION, "用户不存在，请重新登录！");
        }
        // 用户密码加签验证token
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            verifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new ServiceException(States.USER_EXCEPTION, "token验证失败，请重新登录！");
        }
        return true;
    }
}
