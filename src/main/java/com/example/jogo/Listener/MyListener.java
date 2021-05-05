package com.example.jogo.Listener;

import com.example.jogo.Entity.Member;
import com.example.jogo.Service.MemberService;
import com.example.jogo.Service.UserService;
import com.example.jogo.ServiceImpl.MemberServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyListener implements ServletContextListener {

    private MemberService memberService;
    private Logger logger = LogManager.getLogger(this.getClass());

    // 初始化
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.info("servletContext 初始化...");

        ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContextEvent.getServletContext());
        memberService=applicationContext.getBean(MemberServiceImpl.class);
        memberService.cacheMembers();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent){
        memberService.flushDB();
        logger.info("servletContext 销毁");
    }
}
