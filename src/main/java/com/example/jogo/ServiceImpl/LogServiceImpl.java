package com.example.jogo.ServiceImpl;

import com.example.jogo.Entity.Log;
import com.example.jogo.Producer.LogProducer;
import com.example.jogo.Service.LogService;
import com.example.jogo.repository.LogRepository;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.stereotype.Service;
import static com.example.jogo.Consumer.LogConsumer.QUEUE_NAME;

import javax.annotation.Resource;
import javax.jms.Destination;
import java.util.List;

@Service
public class LogServiceImpl implements LogService {
    @Resource
    private LogRepository logRepository;
    @Resource
    private LogProducer logProducer;

    @Override
    public List<Log> findAllByTeamIdAndProjectIdAndUsername(String teamId, String projectId, String username) {
        return logRepository.findAllByTeamIdAndProjectIdAndUsername(teamId,projectId,username);
    }

    @Override
    public List<Log> findAllByTeamIdAndProjectId(String teamId, String projectId) {
        return logRepository.findAllByTeamIdAndProjectId(teamId,projectId);
    }

    @Override
    public boolean deleteAllByTeamIdAndProjectId(String teamId, String projectId) {
        return logRepository.deleteAllByTeamIdAndProjectId(teamId,projectId);
    }

    @Override
    public boolean save(Log log) {
        logRepository.save(log);
        return true;
    }

    @Override
    public void asynSave(Log log) {
        Destination destination = new ActiveMQQueue(QUEUE_NAME);
        logProducer.sendMsg(destination,log);

    }

}
