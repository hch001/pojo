package com.example.jogo.Service;

import com.example.jogo.Entity.Assessment;

import java.util.List;

public interface AssessmentService {
    List<Assessment> findAll();
    Assessment findByTeamIdAndProjectIdAndUsername(String teamId,String projectId,String username);
    boolean deleteAllByUsername(String username);
}
