package com.example.jogo.Service;

import com.example.jogo.Entity.Member;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Simple interface that contains some method signatures to do with {@link Member}.
 *
 * @author Chenhan Huang
 * @since 2021.4.27
 */
public interface MemberService {
    Member findByUsername(String username);
    List<Member> findAll();

    /**
     * save a member into MongoDB
     * @param member member who will be saved
     * @return true if its username doesn't exist and it pass the check
     */
    boolean save(Member member);

    /**
     * username already existed or not
     * @param username username for check
     * @return true if username exists
     */
    boolean isExist(String username);

    /**
     * if username and password match
     * @param username username for verification
     * @param password password for verification
     * @return true if username and password match
     */
    boolean verify(String username,String password);

    /**
     * set value of field in {@link Member} object
     * @param username username of the member object
     * @param field which field you want to set value
     * @param value value you want to set
     * @return true if successful
     */
    boolean setSelfInfo(String username,String field,String value);

    /**
     * remove a team_id in {@link Member} object
     * @param username which member
     * @return true if removed successfully or false if no such team_id in the member
     */
    boolean leaveTeam(String username);

    /**
     * add a team_id into {@link Member} object
     * @param username which member
     * @param team_id _id of which team the member will be in
     * @return true if added successfully
     */
    boolean joinTeam(String username,String team_id);

    /**
     * remove a project_id in {@link Member} object
     * @param username which member
     * @param project_id _id of which project the member will be in
     * @return true if successfully or false if no such project_id in member object
     */
    boolean leaveProject(String username,String project_id);
    boolean joinProject(String username,String project_id);

    /**
     * cache all the {@link Member} objects into Redis and it should be called when webListener starts
     * @return true if loaded successfully
     */
    boolean cacheMember();

    /**
     * cache token into Redis
     * @param username which member
     * @param token token for the member
     * @return true if successfully
     */
    boolean cacheToken(String username,String token);

    /**
     * inspect the format of username and password and Front-end should use the same inspection.
     * @param username username for inspection
     * @param password password for inspection
     * @return InspectionType
     */
    default InspectResult inspect(String username,String password){
        if(username==null||password==null) return InspectResult.NotNull;
        else if(username.length()<8 || username.length()>20 || password.length()<8 || password.length()>20)
            return InspectResult.TooShort;
        boolean capital = false;
        Set<Character> allowedSet = new HashSet<>();
        for(char i=48;i<=57;i++) allowedSet.add(i);
        for(char i=65;i<=90;i++) allowedSet.add(i);
        for(char i=97;i<=122;i++) allowedSet.add(i);
        allowedSet.add('_');

        for(int i=0;i<password.length();i++) {
            char c = username.charAt(i);
            if (!allowedSet.contains(c))
                return InspectResult.InvalidChar;
            if (c>=65&&c<=90) capital=true;
        }
        for(int i=0;i<username.length();i++)
            if(!allowedSet.contains(username.charAt(i))) return InspectResult.InvalidChar;

        if(!capital) return InspectResult.CapitalNeeded;

        return InspectResult.Success;
    }

    /**
     * The return type of {@link MemberService#inspect(String username, String password)}
     * @author Chenhan Huang
     */
    enum InspectResult{
        NotNull("输入不允许为空"),
        TooShort("用户名和密码均需要在8到20个字符之间"),
        InvalidChar("存在非法字符"),
        CapitalNeeded("密码需要至少一个大写字母"),
        Success("注册成功");

        private final String msg;
        InspectResult(String msg){
            this.msg=msg;
        }
        public String getMsg(){
            return this.msg;
        }
    }
}
