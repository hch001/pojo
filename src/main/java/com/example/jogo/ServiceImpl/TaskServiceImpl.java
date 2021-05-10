package com.example.jogo.ServiceImpl;

import com.example.jogo.Entity.Task;
import com.example.jogo.Service.TaskService;
import com.example.jogo.repository.TaskRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    @Resource
    private TaskRepository taskRepository;

    @Override
    public List<Task> findAllByTeamIdAndProjectId(String teamId, String projectId) {
        return taskRepository.findAllByTeamIdAndProjectId(teamId,projectId);
    }

    @Override
    public boolean deleteAllByTeamIdAndProjectId(String teamId, String projectId) {
        return taskRepository.deleteAllByTeamIdAndProjectId(teamId,projectId);
    }

    @Override
    public boolean deleteBy_id(String _id) {
        return taskRepository.deleteBy_id(_id);
    }

    @Override
    public void save(Task task) {
        taskRepository.save(task);
    }
}
