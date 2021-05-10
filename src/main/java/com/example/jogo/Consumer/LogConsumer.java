package com.example.jogo.Consumer;

import com.example.jogo.Entity.Log;
import com.example.jogo.Service.LogService;
import com.example.jogo.repository.LogRepository;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class LogConsumer {
    @Resource
    private LogRepository logRepository;
    public static final String QUEUE_NAME = "LOG_QUEUE";

    @JmsListener(destination = QUEUE_NAME)
    public void receiveMsg(Log log){
        logRepository.save(log);
    }
}
