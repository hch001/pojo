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
     * 
     * @param username
     * @return
     */
    boolean leaveTeam(String username);
    boolean joinTeam(String username,String team_id);
    boolean leaveProject(String username,String project_id);
    boolean joinProject(String username,String project_id);

    boolean loadCache();
    //    boolean storeToken(String username,String token);

    /**
     * check the format of username and password
     * @param username username for check
     * @param password password for check
     * @return CheckResult
     */
    default CheckResultType check(String username,String password){
        if(username==null||password==null) return CheckResultType.NotNull;
        else if(username.length()<8 || username.length()>20 || password.length()<8 || password.length()>20)
            return CheckResultType.TooShort;
        boolean capital = false;
        Set<Character> allowedSet = new HashSet<>();
        for(char i=48;i<=57;i++) allowedSet.add(i);
        for(char i=65;i<=90;i++) allowedSet.add(i);
        for(char i=97;i<=122;i++) allowedSet.add(i);
        allowedSet.add('_');

        for(int i=0;i<password.length();i++) {
            char c = username.charAt(i);
            if (!allowedSet.contains(c))
                return CheckResultType.InvalidChar;
            if (c>=65&&c<=90) capital=true;
        }
        for(int i=0;i<username.length();i++)
            if(!allowedSet.contains(username.charAt(i))) return CheckResultType.InvalidChar;

        if(!capital) return CheckResultType.CapitalNeeded;

        return CheckResultType.Success;
    }

    /**
     * The return type of {@link MemberService#check(String username, String password)}
     * @author Chenhan Huang
     */
    enum CheckResultType{
        NotNull("输入不允许为空"),
        TooShort("用户名和密码均需要在8到20个字符之间"),
        InvalidChar("存在非法字符"),
        CapitalNeeded("密码需要至少一个大写字母"),
        Success("注册成功");

        private final String msg;
        CheckResultType(String msg){
            this.msg=msg;
        }
        public String getMsg(){
            return this.msg;
        }
    }
}
