package com.example.jogo.repository;

import com.example.jogo.Entity.Authority;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends MongoRepository<Authority,String> {
    Authority findByTeamIdAndProjectIdAndUsername(String teamId,String projectId,String username);
    boolean deleteAllByTeamIdAndProjectIdAndUsername(String teamId,String projectId,String username);
}
