package com.shelbourne.schooldelivery.config;

import com.shelbourne.schooldelivery.config.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //new方式无法注入userService
//        registry.addInterceptor(new JwtInterceptor()).addPathPatterns("/**")
//        .excludePathPatterns("/user/login","/user/register");//拦截所有请求，通过判断token是否合法来决定是否需要登录
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**")//拦截所有请求，通过判断token是否合法来决定是否需要登录
                .excludePathPatterns("/user/login", "/user/register","/**/export","/**/import","/file/upload","/file/download/**");
    }

    @Bean //将方法注入到Springboot容器中
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }
}
