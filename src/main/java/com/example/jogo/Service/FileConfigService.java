package com.example.jogo.Service;

import com.example.jogo.Entity.FileConfig;

import java.util.List;

public interface FileConfigService {
    public static final int MAX_SIZE = 100; // MB
    /**
     * Add a default fileConfig setting into a new team or project.
     * If a team, just set projectId as "".
     * @param teamId
     * @param projectId
     * @return true if and only if this FileConfig object not exists and directory where files will be stored created successfully
     */
    boolean init(String teamId,String projectId);

    /**
     * A team or project allow files of this type or not.
     * If a team, just set projectId as "".
     * @param teamId
     * @param projectId
     * @param type
     * @return
     */
    boolean isAllowedType(String teamId,String projectId,String type);

    /**
     * The team or project has enough space to store file of {@code size} or not.
     * If a team, just set projectId as "".
     * @param teamId
     * @param projectId
     * @param fileSize
     * @return
     */
    boolean hasEnoughSpace(String teamId,String projectId,double fileSize);

    /**
     * Get fileConfig of a team or project.
     * If a team, just set projectId as "".
     * @param teamId
     * @param projectId
     * @return
     */
    FileConfig findByTeamIdAndProjectId(String teamId, String projectId);

    /**
     * Delete all fileConfig of a team or a project.
     * If a team, just set projectId as "".
     * @param teamId
     * @param projectId
     * @return
     */
    boolean deleteAllByTeamIdAndProjectId(String teamId, String projectId);

    boolean setMaxSizePerFile(String teamId,String projectId,double newSize);

    boolean setAllowedTypes(String teamId, String projectId, List<String> types);

    boolean setUsed(String teamId,String projectId,double newUsed);

    FileConfig fileConfig(String teamId,String projectId,double maxSizePerFile,List<String> allowedTypes);

    void save(FileConfig fileConfig);
}
