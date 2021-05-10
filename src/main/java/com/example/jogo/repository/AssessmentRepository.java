package com.example.jogo.repository;

import com.example.jogo.Entity.Assessment;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssessmentRepository extends MongoRepository<Assessment,String> {
    Assessment findByTeamIdAndProjectIdAndUsername(String teamId,String projectId,String username);
    List<Assessment> findAllByTeamIdAndProjectId(String teamId,String projectId);

    boolean deleteByTeamIdAndProjectIdAndUsername(String teamId,String projectId,String username);
    boolean deleteAllByTeamIdAndProjectId(String teamId,String projectId);
}
