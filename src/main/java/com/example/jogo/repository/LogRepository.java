package com.example.jogo.repository;

import com.example.jogo.Entity.Log;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends MongoRepository<Log,String> {
    List<Log> findAllByTeamIdAndProjectIdAndUsername(String teamId,String projectId,String username);
    List<Log> findAllByTeamIdAndProjectId(String teamId,String projectId);
    Long deleteAllByTeamIdAndProjectId(String teamId,String projectId);
}
