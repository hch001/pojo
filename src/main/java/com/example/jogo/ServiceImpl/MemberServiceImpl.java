package com.example.jogo.ServiceImpl;

import com.example.jogo.Entity.Member;
import com.example.jogo.Service.MemberService;
import com.example.jogo.Token.TokenUtil;
import com.example.jogo.repository.MemberRepository;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class MemberServiceImpl implements MemberService {
    @Bean
    public MongoCollection<Document> mongoCollection(MongoTemplate mongoTemplate){
        return mongoTemplate.getCollection("Member");
    }
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    @Resource
    private MemberRepository memberRepository;
    private static final String ALL_MEMBERS="member_list";
    @Resource
    private MongoCollection<Document> mongoCollection;

    @Override
    public Member findByUsername(String username){
        return memberRepository.findByUsername(username);
    }

    @Override
    public List<Member> findAll(){
        return memberRepository.findAll();
    }

    @Override
    public boolean storeToken(String username,String token){
        Object o = redisTemplate.opsForValue().get(username);
        if(null==o) System.out.println("null");
        else redisTemplate.delete(username);
        redisTemplate.opsForValue().set(username,token);
        redisTemplate.expire(username, TokenUtil.getLiveTime(), TimeUnit.MILLISECONDS);
        return true;
    }

    @Override
    public boolean save(Member member){
        memberRepository.save(member);
        return true;
    }

    @Override
    public boolean setSelfInfo(String username,String field,String value){
        return memberRepository.setSelfInfo(mongoCollection,username,field,value);
    }

}
