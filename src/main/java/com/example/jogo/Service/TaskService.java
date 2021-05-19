package com.example.jogo.Service;


import com.example.jogo.Entity.Task;

import java.util.Date;
import java.util.List;

public interface TaskService {
    List<Task> findAllByTeamIdAndProjectId(String teamId, String projectId);
    List<Task> findAllByTeamIdAndProjectIdAndUsername(String teamId, String projectId,String username);
    boolean deleteAllByTeamIdAndProjectId(String teamId,String projectId);
    boolean deleteBy_id(String _id);
    void save(Task task);

    Task task(String teamId, String projectId, String taskName, List<String> members, Date deadline,String description);
}
