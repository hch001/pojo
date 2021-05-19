package com.example.jogo.ServiceImpl;

import com.example.jogo.Entity.Assessment;
import com.example.jogo.Service.AssessmentService;
import com.example.jogo.repository.AssessmentRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class AssessmentServiceImpl implements AssessmentService {
    @Resource
    private AssessmentRepository assessmentRepository;

    @Override
    public boolean save(Assessment assessment) {
        if(assessmentRepository.findByTeamIdAndProjectIdAndUsername(assessment.getTeamId(),assessment.getProjectId(),assessment.getUsername())!=null)
            return false;
        assessmentRepository.save(assessment);

        return true;
    }

    @Override
    public Assessment findByTeamIdAndProjectIdAndUsername(String teamId, String projectId, String username) {
        return assessmentRepository.findByTeamIdAndProjectIdAndUsername(teamId,projectId,username);
    }

    @Override
    public List<Assessment> findAllByTeamIdAndProjectId(String teamId, String projectId) {
        return assessmentRepository.findAllByTeamIdAndProjectId(teamId,projectId);
    }

    @Override
    public boolean deleteByTeamIdAndProjectIdAndUsername(String teamId, String projectId, String username) {
        if(assessmentRepository.findByTeamIdAndProjectIdAndUsername(teamId,projectId,username)==null)
            return false;
        assessmentRepository.deleteByTeamIdAndProjectIdAndUsername(teamId,projectId,username);
        return true;
    }

    @Override
    public boolean deleteAllByTeamIdAndProjectId(String teamId, String projectId) {
        assessmentRepository.deleteAllByTeamIdAndProjectId(teamId,projectId);
        return true;
    }

    @Override
    public boolean setScore(String teamId,String projectId,String username,double newScore,String evaluator){
        Assessment assessment = assessmentRepository.findByTeamIdAndProjectIdAndUsername(teamId,projectId,username);
        if(assessment==null)
            return false;

        assessment.setScore(newScore);
        assessment.setEvaluator(evaluator);
        assessmentRepository.deleteByTeamIdAndProjectIdAndUsername(teamId,projectId,username);
        assessmentRepository.save(assessment);

        return true;
    }

    @Override
    public boolean setDescription(String teamId,String projectId,String username,String description,String evaluator){
        Assessment assessment = assessmentRepository.findByTeamIdAndProjectIdAndUsername(teamId,projectId,username);
        if(assessment==null)
            return false;

        assessment.setDescription(description);
        assessment.setEvaluator(evaluator);
        assessmentRepository.deleteByTeamIdAndProjectIdAndUsername(teamId,projectId,username);
        assessmentRepository.save(assessment);

        return true;
    }

    @Override
    public Assessment assessment(String teamId, String projectId, String username, double score, String description, String evaluator) {
        Assessment assessment = new Assessment();
        assessment.setTeamId(teamId);
        assessment.setProjectId(projectId);
        assessment.setUsername(username);
        assessment.setScore(score);
        assessment.setTime(new Date());
        assessment.setDescription(description);
        assessment.setEvaluator(evaluator);

        return assessment;
    }
}
