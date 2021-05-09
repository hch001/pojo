package com.example.jogo.repository;

import com.example.jogo.Entity.FileInfo;
import com.example.jogo.Entity.Member;
import com.example.jogo.Entity.Team;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.lang.annotation.Documented;
import java.util.List;

@Repository
public interface TeamRepository extends MongoRepository<Team,String> {
    Team getTeamByTeamName(String teamName);
    @Query(value = "{'teamName':?0}",fields = "{?1:{$elemMatch:{'?2':?3}}}")
    List<Team> getTeamByTeamNameWithField(String teamName,String field,String attr,String value);
    @Query(value = "{'teamName':?0}",fields = "{?1:true}")
    List<Team> getTeamByTeamNameWithField(String teamName,String field);

    boolean deleteAllByTeamName(String teamName);

    default int setTeamNameByTeamName(MongoCollection<Document> mongoCollection,String teamName,String newTeamName){
        Bson filter = Filters.eq("teamName",teamName);
        Document operations = new Document("$set",new Document("teamName",newTeamName));
        UpdateResult result = mongoCollection.updateOne(filter,operations);

        return (int)result.getModifiedCount();
    }

    default int setManagerByTeamName(MongoCollection<Document> mongoCollection, String teamName,String manager_id){
        Bson filter = Filters.eq("teamName",teamName);
        Document operations = new Document("$set",new Document("teamManager",manager_id));
        UpdateResult result = mongoCollection.updateOne(filter,operations);

        return (int)result.getModifiedCount();
    }

    default int deleteMemberByTeamName(MongoCollection<Document> mongoCollection, String teamName, String member_id){

        Bson filter = Filters.eq("teamName",teamName);
        Document operations = new Document("$pull",new Document("members",member_id));
        UpdateResult result = mongoCollection.updateOne(filter,operations);

        return (int)result.getModifiedCount();
    }

    default int addMemberByTeamName(MongoCollection<Document> mongoCollection, String teamName, String member_id){
        Bson filter = Filters.eq("teamName",teamName);
        Document operations = new Document("$push",new Document("members",member_id));
        UpdateResult result = mongoCollection.updateOne(filter,operations);

        return (int)result.getModifiedCount();
    }



}
