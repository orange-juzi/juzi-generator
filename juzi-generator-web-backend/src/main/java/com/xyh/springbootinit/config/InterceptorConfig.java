package com.xyh.springbootinit.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器
 */
//@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    // 注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，校验规则为 StpUtil.checkLogin() 登录校验。
        registry.addInterceptor(new SaInterceptor(handle -> StpUtil.checkLogin()))
                .addPathPatterns("/**")
                .excludePathPatterns("/**/login", "/**/register")
                .excludePathPatterns("/v3/api-docs/**", // 排除Swagger生成的API文档路径，这里要查看控制台对应的路径来进行排除
                        "/swagger-resources/**", // 排除Swagger的资源路径
                        "/swagger-ui/**", // 排除Swagger的UI路径
                        "/swagger-ui.html", // 排除Swagger UI的首页路径
                        "/webjars/**",
                        "/**/doc-api.html/**"); // 排除Swagger的webjars路径
    }
}