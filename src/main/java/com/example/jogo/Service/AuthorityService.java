package com.example.jogo.Service;

import com.example.jogo.Entity.Authority;

import java.util.List;

public interface AuthorityService {
    Authority findByTeamIdAndProjectIdAndUsername(String teamId,String projectId,String username);
    List<Authority> findAllByTeamIdAndProjectId(String teamId, String projectId);
    boolean deleteByTeamIdAndProjectIdAndUsername(String teamId,String projectId,String username);
    boolean deleteAllByTeamIdAndProjectId(String teamId,String projectId);

    /**
     * save a {@link Authority} object into MongoDB.
     * @param authority
     * @return true if successfully or false if already existed.
     */
    boolean save(Authority authority);

    /**
     * the member in the project or team has some kind of authority or not
     * @param teamId
     * @param projectId
     * @param username
     * @param authorityField authority name
     * @return false if the Authority object not exists or this kind of authority is false
     */
    boolean hasAuthority(String teamId,String projectId,String username,String authorityField);

    /**
     * replace a {@link Authority} object by the new one.
     * If just want to save a new one rather than replace, call {@link AuthorityService#save(Authority)}.
     * @param authority new authority object to replace the old one.
     * @return false if the old object not exists.
     */
    boolean replace(Authority authority);

}
