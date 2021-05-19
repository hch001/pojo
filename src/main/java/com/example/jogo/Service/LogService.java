package com.example.jogo.Service;

import com.example.jogo.Entity.Log;

import java.util.List;

public interface LogService {
    List<Log> findAllByTeamIdAndProjectIdAndUsername(String teamId, String projectId, String username);
    List<Log> findAllByTeamIdAndProjectId(String teamId,String projectId);
    boolean deleteAllByTeamIdAndProjectId(String teamId,String projectId);
    boolean save(Log log);
    void asynSave(Log log);
    Log log(String teamId,String projectId,String username,String detail);
}
