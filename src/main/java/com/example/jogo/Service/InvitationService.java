package com.example.jogo.Service;

import com.example.jogo.Entity.Invitation;

import java.util.List;

public interface InvitationService {
    boolean deleteAllByTeamIdAndProjectId(String teamId,String projectId);
    boolean deleteAllByTeamId(String teamId);
    boolean deleteBy_id(String _id);

    List<Invitation> findAllByInvitee(String invitee);
    List<Invitation> findAllByInviter(String inviter);

    void save(Invitation invitation);
    Invitation invitation(String teamId,String projectId,String inviter,String invitee,String description);
}
