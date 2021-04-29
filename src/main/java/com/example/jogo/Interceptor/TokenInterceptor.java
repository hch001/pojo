package com.example.jogo.Interceptor;


import com.example.jogo.Utils.TokenUtil;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Resource
    private TokenUtil tokenUtil;

    // controller之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        System.out.println("----- preHandle -----");
        return true;
//        if(null==token) {
//            System.out.println("抓住一个不带token的");
//            response.setStatus(403);
//
//            return false;
//        }
//        else {
//            if(tokenUtil.verify(token)){
//                System.out.println("认证过的token");
//                return true;
//            }
//            return false;
//        }
    }

    // controller之后
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        System.out.println("----- postHandle -----");
    }

    // 渲染视图之后
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        System.out.println("----- afterCompletion -----");
    }
}
