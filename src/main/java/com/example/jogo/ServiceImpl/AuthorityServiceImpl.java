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
        if(authorityRepository.findByTeamIdAndProjectIdAndUsername(teamId,projectId,username)==null)
            return false;
        authorityRepository.deleteByTeamIdAndProjectIdAndUsername(teamId,projectId,username);
        return true;
    }

    @Override
    public boolean deleteAllByTeamIdAndProjectId(String teamId, String projectId) {
        authorityRepository.deleteAllByTeamIdAndProjectId(teamId,projectId);

        return true;
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
        Authority old = authorityRepository.findByTeamIdAndProjectIdAndUsername(authority.getTeamId(),authority.getProjectId(),authority.getUsername());
        if(null==old)
            return false;
        authorityRepository.deleteByTeamIdAndProjectIdAndUsername(authority.getTeamId(),authority.getProjectId(),authority.getUsername());
        authorityRepository.save(authority);

        return true;
    }

    @Override
    public boolean setAsProjectLeader(String teamId, String projectId, String username) {
        Authority authority = findByTeamIdAndProjectIdAndUsername(teamId,projectId,username);
        if (setTrue(authority)) return false;

        replace(authority);

        return true;
    }

    @Override
    public boolean setAsTeamLeader(String teamId, String username) {
        Authority authority = findByTeamIdAndProjectIdAndUsername(teamId,"",username);
        if (setTrue(authority)) return false;
        authority.setModifyTeamInfo(true);

        replace(authority);

        return true;

    }

    private boolean setTrue(Authority authority) {
        if(authority==null)
            return true;

        authority.setUpload(true);
        authority.setDownload(true);
        authority.setRemoveFile(true);

        authority.setAddMember(true);
        authority.setRemoveMember(true);

        authority.setModifyProjectInfo(true);
        authority.setModifyTask(true);
        authority.setModifyNotice(true);

        authority.setAssess(true);
        return false;
    }

    @Override
    public boolean setAllFalse(String teamId, String projectId, String username) {
        Authority authority = findByTeamIdAndProjectIdAndUsername(teamId,projectId,username);
        if(authority==null)
            return false;

        authority.setUpload(false);
        authority.setDownload(false);
        authority.setRemoveFile(false);

        authority.setAddMember(false);
        authority.setRemoveMember(false);

        authority.setModifyProjectInfo(false);
        authority.setModifyTeamInfo(false);
        authority.setModifyTask(false);
        authority.setModifyNotice(false);

        authority.setAssess(false);

        replace(authority);

        return true;
    }

    @Override
    public Authority authority(String teamId, String projectId, String username) {
        Authority authority = new Authority();

        authority.setTeamId(teamId);
        authority.setProjectId(projectId);
        authority.setUsername(username);

        authority.setUpload(false);
        authority.setDownload(false);
        authority.setRemoveFile(false);

        authority.setAddMember(false);
        authority.setRemoveMember(false);

        authority.setModifyProjectInfo(false);
        authority.setModifyTeamInfo(false);
        authority.setModifyTask(false);
        authority.setModifyNotice(false);

        authority.setAssess(false);

        return authority;
    }


}
