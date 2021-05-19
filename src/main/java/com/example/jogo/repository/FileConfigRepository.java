package com.example.jogo.repository;

import com.example.jogo.Entity.FileConfig;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileConfigRepository extends MongoRepository<FileConfig,String> {
    FileConfig findByTeamIdAndProjectId(String teamId,String projectId);
    Long deleteAllByTeamIdAndProjectId(String teamId, String projectId);

    default boolean setMaxSizePerFile(String teamId,String projectId,Integer newSize){
        FileConfig fileConfig = findByTeamIdAndProjectId(teamId,projectId);
        if(null==fileConfig)
            return false;
        fileConfig.setMaxSizePerFile(newSize);
        deleteAllByTeamIdAndProjectId(teamId,projectId);
        save(fileConfig);

        return true;
    }

    default boolean setAllowedTypes(String teamId,String projectId,List<String> types){
        FileConfig fileConfig = findByTeamIdAndProjectId(teamId,projectId);
        if(null==fileConfig)
            return false;
        fileConfig.setAllowedTypes(types);
        deleteAllByTeamIdAndProjectId(teamId,projectId);
        save(fileConfig);

        return true;
    }

    default boolean setUsed(String teamId,String projectId,int newUsed){
        FileConfig fileConfig = findByTeamIdAndProjectId(teamId,projectId);
        if(null==fileConfig)
            return false;
        fileConfig.setUsed(newUsed);
        deleteAllByTeamIdAndProjectId(teamId,projectId);
        save(fileConfig);

        return true;
    }


}
