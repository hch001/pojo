package com.example.jogo.repository;

import com.example.jogo.Entity.Member;
import com.example.jogo.Entity.User;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Repository
public interface MemberRepository extends MongoRepository<Member,String> {
    List<Member> findAll();
    Member findByUsernameAndPassword(String username,String password);
    Member findByUsername(String username);

    default boolean setSelfInfo(MongoCollection<Document> mongoCollection, String username, String field, String value){
        HashSet<String> set = new HashSet<>(){{add("email");add("phone");add("nickName");}};
        if(!set.contains(field)) return false;
        Bson filter = Filters.eq("username",username);
        Document operations = new Document("$set",new Document(field,value));

        return mongoCollection.updateOne(filter,operations).getModifiedCount()>=1;
    }

    default boolean leaveTeam(MongoCollection<Document> mongoCollection,String username){
        Bson filter = Filters.eq("username",username);
        Document operations = new Document("$unset",new Document("team_id",""));

        return mongoCollection.updateOne(filter,operations).getModifiedCount()>=1;
    }

    default boolean joinTeam(MongoCollection<Document> mongoCollection,String username,String team_id){
        Bson filter = Filters.eq("username",username);
        Document operations = new Document("set",new Document("team_id",team_id));

        return mongoCollection.updateOne(filter,operations).getModifiedCount()>=1;
    }

    default boolean leaveProject(MongoCollection<Document> mongoCollection,String username,String project_id){
        Bson filter = Filters.eq("username",username);
        Document operations = new Document("$pull",new Document("project_ids",project_id));
        return mongoCollection.updateOne(filter,operations).getModifiedCount()>=1;
    }

    default boolean joinProject(MongoCollection<Document> mongoCollection,String username,String project_id){
        Bson filter = Filters.eq("username",username);
        Document operations = new Document("$push",new Document("project_ids",project_id));
        return mongoCollection.updateOne(filter,operations).getModifiedCount()>=1;
    }
}
