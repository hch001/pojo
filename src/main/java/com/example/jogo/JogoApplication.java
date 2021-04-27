package com.example.jogo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ServletComponentScan // 自动注册 Servlet Filter Listener WebServlet WebFilter WebListener
@EnableAsync
public class JogoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JogoApplication.class, args);
    }

}
