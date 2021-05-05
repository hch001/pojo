package com.example.jogo.Filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@WebFilter(filterName = "MyFilter", urlPatterns = "/**")
public class MyFilter implements Filter {
    Logger logger = LogManager.getLogger(MyFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("filter >>> 初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,FilterChain filterChain)
    throws IOException,ServletException {
        logger.info("^^^^^ filter >>> doSomething");
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        servletResponse.setCharacterEncoding("utf-8");

        response.setHeader("Access-Control-Allow-Origin","http://localhost:8080");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "1");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,Access-Token,Authorization,ybg");
        filterChain.doFilter(servletRequest,servletResponse);
        logger.info("^^^^^ filter >>> end doSomething");
    }

    @Override
    public void destroy(){
        logger.info("filter >>> 销毁");

    }
}
