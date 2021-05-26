package com.example.jogo.ServiceImpl;

import com.example.jogo.Entity.Message;
import com.example.jogo.Service.MessageService;
import com.example.jogo.repository.MessageRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    private MessageRepository messageRepository;

    @Override
    public boolean deleteAllByTeamIdAndProjectId(String teamId, String projectId) {
        messageRepository.deleteAllByTeamIdAndProjectId(teamId,projectId);
        return true;
    }

    @Override
    public boolean deleteAllByTeamId(String teamId) {
        messageRepository.deleteAllByTeamId(teamId);
        return true;
    }

    @Override
    public boolean deleteBy_id(String _id) {
        return messageRepository.deleteAllBy_id(_id)>0;
    }

    @Override
    public List<Message> findAllByToAndStateEquals(String to,String state) {
        return messageRepository.findAllByToAndStateEquals(to,state);
    }

    @Override
    public Message findById(String id) {
        return messageRepository.findBy_id(id);
    }

    @Override
    public List<Message> findAllByFrom(String from) {
        return messageRepository.findAllByFrom(from);
    }

    @Override
    public void save(Message message) {
        messageRepository.save(message);
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
