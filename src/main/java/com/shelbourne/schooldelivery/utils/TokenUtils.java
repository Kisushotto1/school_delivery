package com.shelbourne.schooldelivery.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.shelbourne.schooldelivery.entity.User;
import com.shelbourne.schooldelivery.service.IUserService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

//通过JWT生成token，并设置拦截器，防止未登录的用户进入系统
@Component  //将TokenUtils注册为Springboot的一个bean
public class TokenUtils {
    //    @Resource
    private static IUserService staticUserService;

    @Resource  //把userService对象引进来
    private IUserService userService;

    @PostConstruct  //把引入的userService对象赋值给静态对象（静态注入）
    public void setUserService() {
        staticUserService = userService;
    }

    /**
     * 生成token
     *
     * @return
     */
    public static String getToken(String userId, String sign) {
        return JWT.create().withAudience(userId)//将user id存到token里面作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2))//2小时后过期
                .sign(Algorithm.HMAC256(sign));//password作为token的秘钥
    }

    /**
     * 通过toekn获取当前登录用户,没有返回null，可在后端任何地方使用
     *
     * @return user对象
     */
    public static User getCurrentUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("token");
        if (StrUtil.isNotBlank(token)) {
            try {
                String userId = JWT.decode(token).getAudience().get(0);
                return staticUserService.getById(Integer.valueOf(userId));
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
}
