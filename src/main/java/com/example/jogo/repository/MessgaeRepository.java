package com.example.jogo.repository;

import com.example.jogo.Entity.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessgaeRepository extends MongoRepository<Message,String> {
    Long deleteAllByTeamIdAndProjectId(String teamId,String projectId);
    Long deleteAllByTeamId(String teamId);
    Long deleteAllBy_id(String _id);
//    Long deleteAllByInviter(String inviter);

    List<Message> findAllByFrom(String from);
    List<Message> findAllByTo(String to);
}
