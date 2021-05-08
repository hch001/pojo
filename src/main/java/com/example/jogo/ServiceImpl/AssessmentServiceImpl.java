package com.example.jogo.ServiceImpl;

import com.example.jogo.Entity.Assessment;
import com.example.jogo.Service.AssessmentService;
import com.example.jogo.repository.AssessmentRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AssessmentServiceImpl implements AssessmentService {
    @Resource
    private AssessmentRepository assessmentRepository;

    @Override
    public List<Assessment> findAll() {
        return assessmentRepository.findAll();
    }

    @Override
    public Assessment findByTeamIdAndProjectIdAndUsername(String teamId, String projectId, String username) {
        return assessmentRepository.findByTeamIdAndProjectIdAndUsername(teamId,projectId,username);
    }

    @Override
    public boolean deleteAllByUsername(String username) {
        return assessmentRepository.deleteAllByUsername(username);
    }
}
