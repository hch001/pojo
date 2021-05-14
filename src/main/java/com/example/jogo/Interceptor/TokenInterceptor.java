package com.example.jogo.Interceptor;


import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.jogo.Utils.TokenUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Resource
    private TokenUtil tokenUtil;
    private static final Logger logger = LogManager.getLogger(TokenInterceptor.class);

    // controller之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        System.out.println("----- preHandle -----");
        if(null==token) {
            logger.info("收到不带token的非法请求");
            response.setStatus(401);
        }
        else {
            try{
                if(tokenUtil.verify(token)){
                    return true;
                }
            }
            catch (UnsupportedEncodingException | JWTVerificationException e){
                logger.warn("token解析错误或者已经过期");
                response.setStatus(401);
            }
            finally {
//                System.out.println("token为"+token);
            }
        }
        return false;
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
