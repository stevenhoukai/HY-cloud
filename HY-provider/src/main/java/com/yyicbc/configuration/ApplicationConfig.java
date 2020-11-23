package com.yyicbc.configuration;


import com.yyicbc.component.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author stv
 * 向容器中注册拦截器
 */

//@Configuration
//public class ApplicationConfig extends WebMvcConfigurationSupport {
//
//
//    @Autowired
//    private JwtInterceptor jwtInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(jwtInterceptor).
//                addPathPatterns("/**")
//                .excludePathPatterns("/**/login")
//                .excludePathPatterns("/**/loginsso")
//                .excludePathPatterns("/**/logout")
//                .excludePathPatterns("/**/changepwd")
//                .excludePathPatterns("/login")
//                .excludePathPatterns("/**/login/findpwd");
//    }
//
//}