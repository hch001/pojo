package com.example.jogo.ServiceImpl;

import com.example.jogo.Entity.Authority;
import com.example.jogo.Service.AuthorityService;
import com.example.jogo.repository.AuthorityRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AuthorityServiceImpl implements AuthorityService {
    @Resource
    private AuthorityRepository authorityRepository;

    @Override
    public boolean hasAuthority(String teamId, String projectId, String username, String authorityField) {
        Authority authority = authorityRepository.findByTeamIdAndProjectIdAndUsername(teamId,projectId,username);
        if(authority==null) return false;
        return authority.getAuthority(authorityField);
    }

    @Override
    public boolean removeAuthority(String teamId, String projectId, String username) {
        return authorityRepository.deleteAllByTeamIdAndProjectIdAndUsername(teamId,projectId,username);
    }
}
