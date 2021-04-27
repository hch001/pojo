package com.example.jogo.Service;


import com.example.jogo.Entity.Task;

public interface TaskService {
    Task save(Task task);
    Task asynSave(Task task);
}
