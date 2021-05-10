package com.example.jogo.Service;

import com.example.jogo.Entity.Notice;

import java.util.List;

public interface NoticeService {
    List<Notice> findAllByTeamIdAndProjectId(String teamId, String projectId);
    boolean deleteBy_id(String _id);
    boolean deleteAllByTeamIdAndProjectId(String teamId,String projectId);

    /**
     * Save a {@link Notice} object into MongoDB. Duplicate Notice Objects are allowed.
     * @param notice
     */
    void save(Notice notice);
}
