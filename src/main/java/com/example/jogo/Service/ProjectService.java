package com.example.jogo.Service;

import com.example.jogo.Entity.Project;

import java.util.List;

public interface ProjectService {
    void save(Project project);

    Project findByProjectId(String projectId);

    List<Project> findAllByTeamId(String teamId);

    boolean deleteByProjectId(String projectId);

    boolean setProjectNameByProjectId(String projectId, String newProjectName);

    boolean setManagerByProjectId(String projectId, String username);

    boolean removeMemberByProjectId(String projectId, String username);

    boolean addMemberByProjectId(String projectId, String username);
}
