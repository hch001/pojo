package com.example.jogo.Service;

import com.example.jogo.Entity.Member;
import com.example.jogo.Entity.User;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.List;

public interface MemberService {
    Member findByUsername(String username);
    List<Member> findAll();
    boolean storeToken(String username,String token);
    boolean save(Member member);
    boolean setSelfInfo(String username,String field,String value);
}
