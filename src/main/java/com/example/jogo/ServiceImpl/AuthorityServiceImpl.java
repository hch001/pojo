package com.example.jogo.ServiceImpl;

import com.example.jogo.Entity.Authority;
import com.example.jogo.Service.AuthorityService;
import com.example.jogo.repository.AuthorityRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AuthorityServiceImpl implements AuthorityService {
    @Resource
    private AuthorityRepository authorityRepository;

    @Override
    public Authority findByTeamIdAndProjectIdAndUsername(String teamId, String projectId, String username) {
        return authorityRepository.findByTeamIdAndProjectIdAndUsername(teamId,projectId,username);
    }

    @Override
    public List<Authority> findAllByTeamIdAndProjectId(String teamId, String projectId) {
        return authorityRepository.findAllByTeamIdAndProjectId(teamId,projectId);
    }

    @Override
    public boolean deleteByTeamIdAndProjectIdAndUsername(String teamId, String projectId, String username) {
        return authorityRepository.deleteByTeamIdAndProjectIdAndUsername(teamId,projectId,username);
    }

    @Override
    public boolean deleteAllByTeamIdAndProjectId(String teamId, String projectId) {
        return authorityRepository.deleteAllByTeamIdAndProjectId(teamId,projectId);
    }

    @Override
    public boolean save(Authority authority) {
        if(authorityRepository.findByTeamIdAndProjectIdAndUsername(authority.getTeamId(),authority.getProjectId(),authority.getUsername())!=null)
            return false;
        authorityRepository.save(authority);

        return true;
    }

    @Override
    public boolean hasAuthority(String teamId, String projectId, String username, String authorityField) {
        Authority authority = authorityRepository.findByTeamIdAndProjectIdAndUsername(teamId,projectId,username);
        if(authority==null)
            return false;

        return authority.getAuthority(authorityField);
    }

    @Override
    public boolean replace(Authority authority) {
        if(!authorityRepository.deleteByTeamIdAndProjectIdAndUsername(authority.getTeamId(),authority.getProjectId(),authority.getUsername()))
            return false;
        authorityRepository.save(authority);

        return true;
    }
}
