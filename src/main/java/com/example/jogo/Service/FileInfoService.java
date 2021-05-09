package com.example.jogo.Service;


import com.example.jogo.Entity.FileInfo;

import javax.servlet.http.HttpServletRequest;

public interface FileInfoService {
    boolean deleteAllByTeamIdAndProjectId(String teamId,String projectId);

    /**
     * Store file.
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
}
