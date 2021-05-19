package com.example.jogo.repository;

import com.example.jogo.Entity.Invitation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvitationRepository extends MongoRepository<Invitation,String> {
    Long deleteAllByTeamIdAndProjectId(String teamId,String projectId);
    Long deleteAllByTeamId(String teamId);
    Long deleteAllBy_id(String _id);
//    Long deleteAllByInviter(String inviter);

    List<Invitation> findAllByInvitee(String invitee);
    List<Invitation> findAllByInviter(String inviter);
}
