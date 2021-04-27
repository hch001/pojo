package com.example.jogo;

import com.alibaba.fastjson.JSON;
import com.example.jogo.Entity.FileConfig;
import com.example.jogo.Entity.FileInfo;
import com.example.jogo.Entity.Member;
import com.example.jogo.Entity.Team;
import com.example.jogo.Service.MemberService;
import com.example.jogo.Token.TokenUtil;
import com.example.jogo.repository.MemberRepository;
import com.example.jogo.repository.TeamRepository;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Field;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
class JogoApplicationTests {

    @Test
    void contextLoads() {
    }

    @Resource
    private MemberService memberService;
    @Resource
    private TokenUtil tokenUtil;
    @Resource
    private RedisTemplate<Object,Object> redisTemplate;
    @Resource
    private MongoTemplate mongoTemplate;
    @Resource
    private TeamRepository teamRepository;
    @Resource
    private MemberRepository memberRepository;

    @Test
    void t() {
//        List<FileInfo> files = new ArrayList<>();
//        for(int i:new int[]{1,2,3,4,5,6}) {
//            FileInfo f = new FileInfo();
//            f.setTime(new Date());
//            f.setFileName("file"+i);
//            files.add(f);
//        }
//        Team t3 = new Team();
//        t3.setTeamName("team3");
//        t3.setfiles(files);
//        teamRepository.save(t3);
//        List<Team> teams = teamRepository.getFileInfoByTeamName("team3","file20");
//        System.out.println(JSON.toJSON(teams.get(0)));
//        teamRepository.deleteMemberByTeamName("team3","123");
//        System.out.println(teamRepository.modifyManagerByTeamName(mongoTemplate.getCollection("Team"),"team3","123"));
        memberRepository.modifySelfInfo(mongoTemplate.getCollection("Member"),"lily","email","1395959@s2.com");
    }
}
