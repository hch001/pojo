package com.example.jogo.ServiceImpl;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.jogo.Entity.Member;
import com.example.jogo.Service.MemberService;
import com.example.jogo.Utils.TokenUtil;
import com.example.jogo.repository.MemberRepository;
import com.mongodb.client.MongoCollection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Chenhan Huang
 * @since 2021.4.25
 */
@Service
public class MemberServiceImpl implements MemberService {
    @Bean(name = "memberCollection")
    public MongoCollection<Document> mongoCollection(MongoTemplate mongoTemplate){
        return mongoTemplate.getCollection("Member");
    }

    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    @Resource
    private TokenUtil tokenUtil;
    @Resource
    private MemberRepository memberRepository;
    @Resource(name = "memberCollection")
    private MongoCollection<Document> mongoCollection;
    private static final Logger logger = LogManager.getLogger(MemberServiceImpl.class);

    /** PREFIX will be add to the head of the username which serves as a key of token in Redis.
     * It will help to distinguish itself({@code PREFIX}+username) from other keys(username) without {@code PREFIX} */
    public static final String PREFIX="token_";

    @Override
    public Member findByUsername(String username){
        return memberRepository.findByUsername(username);
    }

    @Override
    public List<Member> findAll(){
        return memberRepository.findAll();
    }

    @Override
    public boolean isExist(String username){
        return null!=memberRepository.findByUsername(username);
    }

    @Override
    public boolean save(Member member){
        if(isExist(member.getUsername())) return false;
        memberRepository.save(member);
        return true;
    }

    @Override
    public boolean match(String username, String password) {
        return memberRepository.findByUsernameAndPassword(username,password)!=null;
    }

    @Override
    public boolean setSelfInfo(String username,String field,String value){
        return memberRepository.setSelfInfo(mongoCollection,username,field,value);
    }

    @Override
    public boolean leaveTeam(String username){
        return memberRepository.leaveTeam(mongoCollection,username);
    }

    @Override
    public boolean joinTeam(String username,String team_id){
        return memberRepository.joinTeam(mongoCollection,username,team_id);
    }

    @Override
    public boolean leaveProject(String username,String project_id){
        return memberRepository.leaveProject(mongoCollection,username,project_id);
    }

    @Override
    public boolean joinProject(String username,String project_id){
        return memberRepository.joinProject(mongoCollection,username,project_id);
    }

    @Override
    public boolean cacheMembers(){
        Map<String,Member> membersMap = new HashMap<>();
        List<Member> members = findAll();
        members.forEach((member)->{
            membersMap.put(member.getUsername(),member);
        });

        Boolean result = redisTemplate.opsForValue().multiSetIfAbsent(membersMap);
        boolean ret = result != null && result;
        if(!ret) logger.warn("用户数据缓存没有清空，存在已缓存的数据");

        return ret;
    }

    @Override
    public boolean cacheToken(String username,String token){
        username = PREFIX+username;
        Object o = redisTemplate.opsForValue().get(username);
        boolean replaced = false;
        if(o!=null) {
            redisTemplate.delete(username);
            replaced=true;
        }

        redisTemplate.opsForValue().append(username,token);
        redisTemplate.expire(username, TokenUtil.getLiveTime(), TimeUnit.MILLISECONDS);

        if(!replaced) logger.info("添加用户"+username+"的新token");
        else logger.info("替换用户"+username+"的旧token");

        return replaced;
    }

    @Override
    public boolean removeToken(String token) {
        boolean res = false;
        try{
            Object o = tokenUtil.getDataFromPayLoad(token,"username");
            String username = (String)o;
            res = redisTemplate.delete(PREFIX+username);
        }catch (JWTVerificationException | UnsupportedEncodingException e){
            logger.info("删除token出错，token:"+token);
        }
        return res;
    }

    @Override
    public void flushDB(){
        Set<String> set = redisTemplate.keys("*");
        assert set != null;
        redisTemplate.delete(set);
    }
    private String getTokenFromCache(String username){
        username = PREFIX+username;
        return (String)(redisTemplate.opsForValue().get(username));
    }

    @Override
     public InspectResult inspect(String username,String password){
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

        Member member = new Member();
        member.setUsername(username);
        member.setPassword(password);
        if(!save(member)) return InspectResult.AlreadyExisted;
        redisTemplate.opsForValue().set(username,member);

        return InspectResult.Success;
    }

}
