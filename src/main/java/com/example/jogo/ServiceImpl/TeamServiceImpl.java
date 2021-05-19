package com.example.jogo.ServiceImpl;

import com.example.jogo.Entity.FileInfo;
import com.example.jogo.Entity.Team;
import com.example.jogo.Service.TeamService;
import com.example.jogo.repository.TeamRepository;
import com.mongodb.client.MongoClientFactory;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.print.Doc;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {
    @Bean(name = "teamCollection")
    public MongoCollection<Document> mongoCollection(MongoTemplate mongoTemplate){
        return mongoTemplate.getCollection("Team");
    }
    @Resource
    private TeamRepository teamRepository;
    @Resource(name = "teamCollection")
    private MongoCollection<Document> mongoCollection;

    @Override
    public void save(Team team) {
        teamRepository.save(team);
    }

    @Override
    public Team findByTeamId(String teamId) {
        return teamRepository.findBy_id(teamId);
    }

    @Override
    public boolean deleteByTeamId(String teamId) {
        if(teamRepository.findBy_id(teamId)==null)
            return false;
        teamRepository.deleteBy_id(teamId);
        return true;
    }

    @Override
    public boolean setTeamNameByTeamId(String teamId, String newTeamName) {
        Bson filter = Filters.eq("_id",teamId);
        Document operations = new Document("$set",new Document("teamName",newTeamName));
        UpdateResult result = mongoCollection.updateOne(filter,operations);

        return (int)result.getModifiedCount() > 0;
    }

    @Override
    public boolean setManagerByTeamId(String teamId, String username) {
        Bson filter = Filters.eq("_id",teamId);
        Document operations = new Document("$set",new Document("teamManager",username));
        UpdateResult result = mongoCollection.updateOne(filter,operations);

        return (int)result.getModifiedCount() > 0;
    }

    @Override
    public boolean removeMemberByTeamId(String teamId, String username) {
        Bson filter = Filters.eq("_id",teamId);
        Document operations = new Document("$pull",new Document("members",username));
        UpdateResult result = mongoCollection.updateOne(filter,operations);

        return (int)result.getModifiedCount() > 0;
    }

    @Override
    public boolean addMemberByTeamId(String teamId, String username) {
        Bson filter = Filters.eq("_id",teamId);
        Document operations = new Document("$push",new Document("members",username));
        UpdateResult result = mongoCollection.updateOne(filter,operations);

        return (int)result.getModifiedCount() > 0;
    }

}
