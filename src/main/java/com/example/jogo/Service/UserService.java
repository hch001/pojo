package com.example.jogo.Service;

import com.example.jogo.Entity.User;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.List;
import java.util.concurrent.Future;

@EnableAsync
public interface UserService {
    void addUser(String username,String password);
    boolean verify(String username,String password);
    List<User> findAll();
    User searchByUsername(String username);
    Future<List<User>> asynFindAll();
    boolean loadCache();
}
