package com.example.jogo.ServiceImpl;

import com.example.jogo.Entity.Member;
import com.example.jogo.Service.MemberService;
import com.example.jogo.Utils.TokenUtil;
import com.example.jogo.repository.MemberRepository;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private MemberRepository memberRepository;
    @Resource(name = "memberCollection")
    private MongoCollection<Document> mongoCollection;

    /** PREFIX will be add to the head of the username which serves as a key of token in Redis.
     * It will help to distinguish itself({@code PREFIX}+username) from other keys(username) without {@code PREFIX} */
    public static final String PREFIX="uuid_";

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
        return null==memberRepository.findByUsername(username);
    }

    @Override
    public boolean save(Member member){
        if(findByUsername(member.getUsername())!=null) return false;
        memberRepository.save(member);
        return true;
    }

    @Override
    public boolean verify(String username, String password) {
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
    public boolean cacheMember(){
        Map<String,Member> membersMap = new HashMap<>();
        List<Member> members = findAll();
        members.forEach((member)->{
            membersMap.put(member.getUsername(),member);
        });

        Boolean result = redisTemplate.opsForValue().multiSetIfAbsent(membersMap);
        return result != null && result;
    }

    @Override
    public boolean cacheToken(String username,String token){
        username = PREFIX+username;
        Object o = redisTemplate.opsForValue().get(username);
        if(o!=null) redisTemplate.delete(username);

        redisTemplate.opsForValue().append(username,token);
        redisTemplate.expire(username, TokenUtil.getLiveTime(), TimeUnit.MILLISECONDS);
        return true;
    }

}
