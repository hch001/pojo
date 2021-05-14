package com.example.jogo.Service;

import com.example.jogo.Entity.FileInfo;
import com.example.jogo.Entity.Team;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 * Simple interface that contains some method signatures to do with {@link Team}.
 *
 * @author Chenhan Huanng
 * @since 2021.4.25
 */
public interface TeamService {
    Team findByTeamId(String teamId);

    boolean deleteByTeamId(String teamId);

    boolean setTeamNameByTeamId(String teamId, String newTeamName);

    boolean setManagerByTeamId(String teamId,String username);

    boolean removeMemberByTeamId(String teamId, String username);

    boolean addMemberByTeamId(String teamId, String username);

    void save(Team team);
}
