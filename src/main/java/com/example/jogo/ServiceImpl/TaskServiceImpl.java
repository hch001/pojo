package com.example.jogo.ServiceImpl;

import com.example.jogo.Entity.Member;
import com.example.jogo.Entity.Task;
import com.example.jogo.Service.TaskService;
import com.example.jogo.repository.TaskRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
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
    public List<Task> findAllByTeamIdAndProjectIdAndUsername(String teamId, String projectId, String username) {
        List<Task> tasks = findAllByTeamIdAndProjectId(teamId,projectId);
        List<Task> res = new ArrayList<>();
        tasks.forEach((t)->{
            List<String> members = t.getMembers();
            if(members!=null&&members.contains(username))
                res.add(t);
        });

        return res;
    }

    @Override
    public boolean deleteAllByTeamIdAndProjectId(String teamId, String projectId) {
        taskRepository.deleteAllByTeamIdAndProjectId(teamId,projectId);
        return true;
    }

    @Override
    public boolean deleteBy_id(String _id) {
        if(taskRepository.findBy_id(_id)==null)
            return false;
        taskRepository.deleteBy_id(_id);
        return true;
    }

    @Override
    public void save(Task task) {
        taskRepository.save(task);
    }

    @Override
    public Task task(String teamId, String projectId, String taskName, List<String> members, Date deadline, String description,String state) {
        Task task = new Task();

        task.setTeamId(teamId);
        task.setProjectId(projectId);
        task.setTaskName(taskName);
        task.setMembers(members);
        task.setDeadline(deadline);
        task.setDescription(description);
        task.setState(state);

        return task;
    }

    @Override
    public List<Task> findByTeamIdAndProjectIdAndState(String teamId, String projectId, String state) {
        return taskRepository.findByTeamIdAndProjectIdAndState(teamId,projectId,state);
    }
}
