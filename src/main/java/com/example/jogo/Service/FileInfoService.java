package com.example.jogo.Service;

import com.example.jogo.Entity.FileInfo;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface FileInfoService {
    boolean deleteAllByTeamIdAndProjectId(String teamId,String projectId);
    boolean storeFile(String teamId, String projectId, HttpServletRequest httpServletRequest) throws IOException;
}
