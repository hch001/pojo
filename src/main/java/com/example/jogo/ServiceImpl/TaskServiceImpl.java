package com.example.jogo.ServiceImpl;

import com.example.jogo.Entity.Task;
import com.example.jogo.Service.TaskService;
import com.example.jogo.repository.TaskRepository;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;

@Service
public class TaskServiceImpl implements TaskService {
    @Resource
    private TaskRepository repository;
    @Resource
    private Producer producer;
    @Resource
    private Consumer consumer;

    @Override
    public Task save(Task task){
        repository.save(task);
        return task;
    }

    @Override
    public Task asynSave(Task task){
        Destination destination = new ActiveMQQueue("my_queue");
        producer.sendTaskMessage(destination,task);
        return task;
    }
}
