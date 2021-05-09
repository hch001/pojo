package com.example.jogo.Service;

import com.example.jogo.Entity.Authority;

public interface AuthorityService {
    boolean hasAuthority(String teamId,String projectId,String username,String authorityField);
    boolean removeAuthority(String teamId,String projectId,String username);
    boolean replace(String teamId, String projectId, String username, Authority authority);
}
