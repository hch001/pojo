package com.example.jogo.Producer;

import com.example.jogo.Entity.Log;

import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.Destination;

@Component
public class LogProducer {
    @Resource
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void sendMsg(Destination destination, Log log){
        jmsMessagingTemplate.convertAndSend(destination,log);
    }
}
