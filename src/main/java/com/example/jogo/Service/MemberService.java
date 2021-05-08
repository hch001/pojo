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
    /**
     * search Member in Redis. If not existed, search it in MongoDB.
     * @param username
     * @return
     */
    Member findByUsername(String username);

    /**
     * get all members from MongoDB.
     * @return
     */
    List<Member> findAll();

    /**
     * save a member into MongoDB and Redis
     * @param member member who will be saved
     * @return true if its username doesn't exist and it pass the check
     */
    boolean save(Member member);

    /**
     * username exists in Redis or MongoDB or not.
     * @param username
     * @return true if username exists
     */
    boolean isExist(String username);

    /**
     * if username and password match
     * @param username username for verification
     * @param password password for verification
     * @return
     */
    boolean match(String username,String password);

    /**
     * set value of field in {@link Member} object and cache in Redis will be also modified
     * @param username username of the member object
     * @param field which field you want to set value
     * @param value value you want to set
     * @return true if successful
     */
    boolean setSelfInfo(String username,String field,String value);

    /**
     * remove a teamId in {@link Member} object and cache in Redis will be also modified
     * @param username which member
     * @return true if removed successfully or false if no such teamId in the member
     */
    boolean leaveTeam(String username);

    /**
     * add a teamId into {@link Member} object and cache in Redis will be also modified
     * @param username which member
     * @param teamId _id of which team the member will be in
     * @return true if added successfully
     */
    boolean joinTeam(String username,String teamId);

    /**
     * remove a projectId in {@link Member} object and cache in Redis will be also modified
     * @param username which member
     * @param projectId _id of which project the member will be in
     * @return true if successfully or false if no such projectId in member object
     */
    boolean leaveProject(String username,String projectId);

    /**
     * add a projectId into {@link Member} object and cache in Redis will be also modified
     * @param username
     * @param projectId
     * @return
     */
    boolean joinProject(String username,String projectId);

    /**
     * store all the {@link Member} objects into Redis and it should be called when webListener{@link com.example.jogo.Listener.MyListener} starts
     * @return true if loaded successfully
     */
    boolean cacheMembers();

    /**
     * store token into Redis
     * @param username which member
     * @param token token for the member
     * @return true if old token replaced
     */
    boolean cacheToken(String username,String token);

    /**
     * remove token from Redis
     * @param token
     * @return
     */
    boolean removeToken(String token);

    /**
     * flush
     */
    void flushDB();

    /**
     * inspect the format of username and password and add new Member into Redis and MongoDB.
     * @param username username for inspection
     * @param password password for inspection
     * @return InspectionType
     */
    InspectResult inspectAndAddNewMember(String username,String password);

    /**
     * The return type of {@link MemberService#inspectAndAddNewMember(String username, String password)}
     * @author Chenhan Huang
     */
    enum InspectResult{
        NotNull("输入不允许为空"),
        TooShort("用户名和密码均需要在8到20个字符之间"),
        InvalidChar("存在非法字符"),
        CapitalNeeded("密码需要至少一个大写字母"),
        Success("注册成功"),
        AlreadyExisted("用户名已存在");

        private final String msg;
        InspectResult(String msg){
            this.msg=msg;
        }
        public String getMsg(){
            return this.msg;
        }
    }
}
