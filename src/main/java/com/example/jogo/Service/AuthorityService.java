package com.example.jogo.Service;

public interface AuthorityService {
    boolean hasAuthority(String teamId,String projectId,String username,String authorityField);
    boolean removeAuthority(String teamId,String projectId,String username);
}
