package com.example.jogo.ServiceImpl;

import com.example.jogo.Entity.FileInfo;
import com.example.jogo.Entity.Team;
import com.example.jogo.Service.TeamService;
import com.example.jogo.repository.TeamRepository;
import com.mongodb.client.MongoClientFactory;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    public Team getTeamByTeamName(String teamName) {
        return teamRepository.getTeamByTeamName(teamName);
    }

    @Override
    public Team getTeamByTeamNameWithField(String teamName, String field) {
        List<Team> teams = teamRepository.getTeamByTeamNameWithField(teamName,field);
        return teams.size()==0?null:teams.get(0);
    }

    @Override
    public Team getTeamByTeamNameWithField(String teamName, String field, String attr, String value) {
        List<Team> teams = teamRepository.getTeamByTeamNameWithField(teamName,field,attr,value);
        return teams.size()==0?null:teams.get(0);
    }


}
