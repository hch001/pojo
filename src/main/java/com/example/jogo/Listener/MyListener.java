package com.example.jogo.Listener;

import com.example.jogo.Service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyListener implements ServletContextListener {

    private UserService userService;
    private Logger logger = LogManager.getLogger(this.getClass());
    private static final String ALL_USERS = "user_list";

    // 初始化
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.info("servletContext 初始化...");

        ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContextEvent.getServletContext());
        userService=applicationContext.getBean(UserService.class);
        userService.loadCache();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent){
        logger.info("servletContext 销毁");
    }
}
