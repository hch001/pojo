package com.example.jogo.Service;

import com.example.jogo.Entity.FileConfig;

import java.util.List;

public interface FileConfigService {
    /**
     * Add a default fileConfig setting into a new team or project. If a team, just set projectId as null.
     * @param teamId
     * @param projectId
     * @return true if and only if this FileConfig object not exists and dir where store files created successfully
     */
    boolean init(String teamId,String projectId);

    /**
     * A team or project allow files of this type or not.
     * @param teamId
     * @param projectId
     * @param type
     * @return
     */
    boolean isAllowedType(String teamId,String projectId,String type);

    /**
     * The team or project has enough space to store file of {@code size} or not.
     * @param teamId
     * @param projectId
     * @param fileSize
     * @return
     */
    boolean hasEnoughSpace(String teamId,String projectId,int fileSize);

    FileConfig findByTeamIdAndProjectId(String teamId, String projectId);

    boolean deleteAllByTeamIdAndProjectId(String teamId, String projectId);

    boolean setMaxSizePerFile(String teamId,String projectId,Integer newSize);

    boolean setAllowedTypes(String teamId, String projectId, List<String> types);

    boolean setUsed(String teamId,String projectId,int newUsed);
}
