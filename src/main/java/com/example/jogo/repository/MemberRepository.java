package com.example.jogo.repository;

import com.example.jogo.Entity.Member;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;

@Repository
public interface MemberRepository extends MongoRepository<Member,String> {
    @NotNull List<Member> findAll();
    Member findByUsername(String username);
    List<Member> findAllByNickNameStartsWith(String nickName);
    List<Member> findAllByUsernameStartsWith(String username);

    /**
     * modify {@code Member.email}, {@code Member.phone} or {@code Member.nickName}.
     * @param mongoCollection
     * @param username
     * @param field
     * @param value
     * @return
     */
    default boolean setStringField(MongoCollection<Document> mongoCollection, String username, String field, String value){
        HashSet<String> set = new HashSet<>(){{add("email");add("phone");add("nickName");add("gender");add("birthday");}};
        if(!set.contains(field)) return false;
        Bson filter = Filters.eq("username",username);
        Document operations = new Document("$set",new Document(field,value));

        return mongoCollection.updateOne(filter,operations).getModifiedCount()>=1;
    }

    default boolean leaveTeam(MongoCollection<Document> mongoCollection,String username){
        Bson filter = Filters.eq("username",username);
        Document operations = new Document("$unset",new Document("teamId",""));

        return mongoCollection.updateOne(filter,operations).getModifiedCount()>=1;
    }

    default boolean joinTeam(MongoCollection<Document> mongoCollection,String username,String teamId){
        Bson filter = Filters.eq("username",username);
        Document operations = new Document("$set",new Document("teamId",teamId));

        return mongoCollection.updateOne(filter,operations).getModifiedCount()>=1;
    }

    default boolean leaveProject(MongoCollection<Document> mongoCollection,String username,String projectId){
        Bson filter = Filters.eq("username",username);
        Document operations = new Document("$pull",new Document("projectIds",projectId));

        return mongoCollection.updateOne(filter,operations).getModifiedCount()>=1;
    }

    default boolean joinProject(MongoCollection<Document> mongoCollection,String username,String projectIds){
        Bson filter = Filters.eq("username",username);
        Document operations = new Document("$push",new Document("projectIds",projectIds));

        return mongoCollection.updateOne(filter,operations).getModifiedCount()>=1;
    }

    default boolean setInformed(MongoCollection<Document> mongoCollection,String username,boolean informed) {
        Bson filter = Filters.eq("username",username);
        Document operations = new Document("$set",new Document("informed",informed));

        return mongoCollection.updateOne(filter,operations).getModifiedCount()>=1;
    }
}
