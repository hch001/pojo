package com.example.jogo.Service;

import com.example.jogo.Entity.Team;

/**
 * Simple interface that contains some method signatures to do with {@link Team}.
 *
 * @author Chenhan Huanng
 * @since 2021.4.25
 */
public interface TeamService {
    Team getTeamByTeamName(String teamName);
    Team getTeamByTeamNameWithField(String teamName,String field);
    Team getTeamByTeamNameWithField(String teamName,String field,String attr,String value);

}
