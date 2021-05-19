package com.example.jogo.repository;

import com.example.jogo.Entity.FileInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileInfoRepository extends MongoRepository<FileInfo,String> {
    Long deleteAllByTeamIdAndProjectId(String teamId,String projectId);
    Long deleteByTeamIdAndProjectIdAndFileName(String teamId,String projectId,String fileName);
    List<FileInfo> findAllByTeamIdAndProjectId(String teamId, String projectId);
    FileInfo findByTeamIdAndProjectIdAndFileName(String teamId,String projectId,String fileName);
}
