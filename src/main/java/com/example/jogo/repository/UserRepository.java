package com.example.jogo.repository;

import com.example.jogo.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    User findUserByUsernameAndPassword(String username,String password);
    List<User> findUsersByUsername(String username);
    List<User> findAll();
}
