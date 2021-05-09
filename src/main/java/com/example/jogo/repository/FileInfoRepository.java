package com.example.jogo.repository;

import com.example.jogo.Entity.FileInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileInfoRepository extends MongoRepository<FileInfo,String> {
    boolean deleteAllByTeamIdAndProjectId(String teamId,String projectId);
    FileInfo findByTeamIdAndProjectId(String teamId,String projectId);
}
