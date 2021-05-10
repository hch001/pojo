package com.example.jogo.repository;

import com.example.jogo.Entity.Authority;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorityRepository extends MongoRepository<Authority,String> {
    Authority findByTeamIdAndProjectIdAndUsername(String teamId,String projectId,String username);
    List<Authority> findAllByTeamIdAndProjectId(String teamId,String projectId);
    boolean deleteByTeamIdAndProjectIdAndUsername(String teamId,String projectId,String username);
    boolean deleteAllByTeamIdAndProjectId(String teamId,String projectId);
}
