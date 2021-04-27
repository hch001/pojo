package com.example.jogo.ServiceImpl;

import com.example.jogo.Entity.Task;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class Consumer {

    @Resource
    private TaskServiceImpl taskService;

//    @JmsListener(destination = "my_queue")
//    public void receiveTaskMsg(String msg){
//        System.out.println("收到消息"+msg);
//    }
//    @JmsListener(destination = "my_queue")
//    public void receiveTaskMsg(Task task){
//        System.out.println("收到结构体:"+task.getDate()+","+task.getContent());
//        taskService.save(task);
//    }
}
