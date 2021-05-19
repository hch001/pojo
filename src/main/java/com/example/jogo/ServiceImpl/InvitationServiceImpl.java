package com.example.jogo.ServiceImpl;

import com.example.jogo.Entity.Invitation;
import com.example.jogo.Service.InvitationService;
import com.example.jogo.repository.InvitationRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class InvitationServiceImpl implements InvitationService {

    @Resource
    private InvitationRepository invitationRepository;

    @Override
    public boolean deleteAllByTeamIdAndProjectId(String teamId, String projectId) {
        invitationRepository.deleteAllByTeamIdAndProjectId(teamId,projectId);
        return true;
    }

    @Override
    public boolean deleteAllByTeamId(String teamId) {
        invitationRepository.deleteAllByTeamId(teamId);
        return true;
    }

    @Override
    public boolean deleteBy_id(String _id) {
        return invitationRepository.deleteAllBy_id(_id)>0;
    }

    @Override
    public List<Invitation> findAllByInvitee(String invitee) {
        return invitationRepository.findAllByInvitee(invitee);
    }

    @Override
    public List<Invitation> findAllByInviter(String inviter) {
        return invitationRepository.findAllByInviter(inviter);
    }

    @Override
    public void save(Invitation invitation) {
        invitationRepository.save(invitation);
    }

    @Override
    public Invitation invitation(String teamId, String projectId, String inviter, String invitee, String description) {
        Invitation invitation = new Invitation();
        invitation.setTeamId(teamId);
        invitation.setProjectId(projectId);
        invitation.setInvitee(invitee);
        invitation.setInviter(inviter);
        invitation.setDescription(description);
        return invitation;
    }
}
