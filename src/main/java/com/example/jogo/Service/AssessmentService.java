package com.example.jogo.Service;

import com.example.jogo.Entity.Assessment;

import java.util.List;

public interface AssessmentService {
    Assessment findByTeamIdAndProjectIdAndUsername(String teamId,String projectId,String username);
    List<Assessment> findAllByTeamIdAndProjectId(String teamId,String projectId);

    boolean deleteByTeamIdAndProjectIdAndUsername(String teamId,String projectId,String username);
    boolean deleteAllByTeamIdAndProjectId(String teamId,String projectId);

    /**
     * Save a {@link Assessment} object into MongoDB.
     * @param assessment
     * @return true if successfully or false if already existed.
     */
    boolean save(Assessment assessment);

    /**
     * Set new value of score in {@link Assessment} object by replacing the old object.
     * @param teamId
     * @param projectId
     * @param username
     * @param newScore
     * @param evaluator username of Member who give this score
     * @return true if set successfully or false if the Assessment Object not exists
     */
    boolean setScore(String teamId,String projectId,String username,double newScore,String evaluator);

    /**
     * Set new value of description in {@link Assessment} object by replacing the old object.
     * @param teamId
     * @param projectId
     * @param username
     * @param description
     * @param evaluator username of Member who give this description
     * @return true if set successfully or false if the Assessment Object not exists
     */
    boolean setDescription(String teamId,String projectId,String username,String description,String evaluator);

    Assessment assessment(String teamId,String projectId,String username,double score,String description,String evaluator);
}
