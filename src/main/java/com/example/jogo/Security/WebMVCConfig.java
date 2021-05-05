package com.example.jogo.Security;


import com.example.jogo.Interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {
    @Resource
    private TokenInterceptor tokenInterceptor;

//     跨域请求设置
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8081")
        .allowCredentials(true)
        .allowedMethods("POST","GET");
    }

    // 添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/member/login","/member/register");
    }

}
