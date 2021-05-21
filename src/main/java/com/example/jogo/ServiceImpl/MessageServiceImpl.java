package com.example.jogo.ServiceImpl;

import com.example.jogo.Entity.Message;
import com.example.jogo.Service.MessageService;
import com.example.jogo.repository.MessgaeRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    private MessgaeRepository invitationRepository;

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
    public List<Message> findAllByTo(String to) {
        return invitationRepository.findAllByTo(to);
    }

    @Override
    public List<Message> findAllByFrom(String from) {
        return invitationRepository.findAllByFrom(from);
    }

    @Override
    public void save(Message message) {
        invitationRepository.save(message);
    }

    @Override
    public Message message(String teamId, String projectId, String from, String to, String description) {
        Message message = new Message();
        message.setTeamId(teamId);
        message.setProjectId(projectId);
        message.setFrom(from);
        message.setTo(to);
        message.setDescription(description);
        return message;
    }
}
