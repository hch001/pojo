package com.example.jogo.Service;


import com.example.jogo.Entity.Task;

import java.util.List;

public interface TaskService {
    List<Task> findAllByTeamIdAndProjectId(String teamId, String projectId);
    boolean deleteAllByTeamIdAndProjectId(String teamId,String projectId);
    boolean deleteBy_id(String _id);
    void save(Task task);
}
