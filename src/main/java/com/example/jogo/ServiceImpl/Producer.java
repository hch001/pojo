package com.example.jogo.ServiceImpl;


import com.example.jogo.Entity.Task;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.Destination;

@Component
public class Producer {
    @Resource
    private JmsMessagingTemplate jmsMessagingTemplate;

//    static private Logger logger = LogManager.getLogger(Producer.class);

    public void sendTaskMessage(Destination destination, String msg){
        jmsMessagingTemplate.convertAndSend(destination,msg);
    }

    public void sendTaskMessage(Destination destination, Task task){
        jmsMessagingTemplate.convertAndSend(destination,task);
//        logger.info("收到信息:"+task.getContent());
    }

}
