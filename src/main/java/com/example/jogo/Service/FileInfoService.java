package com.example.jogo.Service;


import com.example.jogo.Entity.FileInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface FileInfoService {
    /**
     * Delete all fileInfo objects that belong to a team or a project.
     * If the fileInfo only belongs to a team rather than a project in this team, just set projectId as "".
     * @param teamId
     * @param projectId
     * @return true if successfully
     */
    boolean deleteAllByTeamIdAndProjectId(String teamId,String projectId);

    /**
     * Delete a fileInfo object that belongs a team or a project.
     * If a team, just set projectId as projectId as "".
     * @param teamId
     * @param projectId
     * @param fileName
     * @return
     */
    boolean deleteByTeamIdAndProjectIdAndFileName(String teamId,String projectId,String fileName);

    /**
     * Delete a file.
     * @param fileInfo
     * @return
     */
    boolean deleteAFile(FileInfo fileInfo);

    /**
     * Delete all files that belong to a team or a project. If a team, just set projectId to "".
     */
    boolean deleteAllFiles(String teamId,String projectId);

    /**
     * Get all fileInfo objects that belong to a team or o project.
     * If a team, just set projectId as "".
     * @param teamId
     * @param projectId
     * @return
     */
    List<FileInfo> findAllByTeamIdAndProjectId(String teamId, String projectId);

    /**
     * Get a specific fileInfo object that belongs to a team or a project.
     * If a team, just set projectId as "".
     * @param teamId
     * @param projectId
     * @param fileName
     * @return
     */
    FileInfo findByTeamIdAndProjectIdAndFileName(String teamId,String projectId,String fileName);

    /**
     * Store file. If the file only belongs to a team rather than a project, just set projectId as "".
     * @param teamId
     * @param projectId
     * @param httpServletRequest
     * @return true if successfully or false if I/O error occurs.
     */
    boolean storeFile(String teamId, String projectId, HttpServletRequest httpServletRequest);

    /**
     * Save a fileInfo object if not exists.
     * @param fileInfo object to save.
     * @return true if not exists and saved successfully.
     */
    boolean save(FileInfo fileInfo);

    FileInfo fileInfo(String teamId,String projectId,String filename,String uploader,double size,int downloads);

    boolean increaseDownloads(String teamId,String projectId,String filename);
}
