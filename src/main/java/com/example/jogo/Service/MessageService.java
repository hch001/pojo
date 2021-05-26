package com.example.jogo.Service;

import com.example.jogo.Entity.Message;

import java.util.List;

public interface MessageService {
    boolean deleteAllByTeamIdAndProjectId(String teamId,String projectId);
    boolean deleteAllByTeamId(String teamId);
    boolean deleteBy_id(String _id);

    List<Message> findAllByFrom(String from);
    List<Message> findAllByToAndStateEquals(String to,String state);
    Message findById(String id);
    void save(Message message);
    Message message(String teamId, String projectId, String from, String to, String description);
}
