package com.example.jogo.repository;

import com.example.jogo.Entity.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<Task,String> {
    List<Task> findAllByTeamIdAndProjectId(String teamId,String projectId);
    boolean deleteAllByTeamIdAndProjectId(String teamId,String projectId);
    boolean deleteBy_id(String _id);
}
