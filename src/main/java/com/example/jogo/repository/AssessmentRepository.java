package com.example.jogo.repository;

import com.example.jogo.Entity.Assessment;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssessmentRepository extends MongoRepository<Assessment,String> {
    @NotNull List<Assessment> findAll();
    Assessment findByTeamIdAndProjectIdAndUsername(String teamId,String projectId,String username);
    boolean deleteAllByUsername(String username);
}
