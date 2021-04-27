package com.example.jogo.quartz;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@Configurable
@EnableScheduling
public class MyQuartz {
    private static final Logger logger = LogManager.getLogger(MyQuartz.class);

    @Scheduled(cron = "0 */10 * * * *")
    public void test(){
        logger.info("过了10min");
    }
}
