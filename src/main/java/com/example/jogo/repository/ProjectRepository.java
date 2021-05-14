package com.example.jogo.repository;

import com.example.jogo.Entity.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends MongoRepository<Project,String> {
    Project findBy_id(String _id);
    boolean deleteBy_id(String projectId);
    List<Project> findAllByTeamId(String teamId);
}
