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
    Team findBy_id(String teamId);

    Long deleteBy_id(String teamId);
}
