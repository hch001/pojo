package com.example.jogo;

import com.example.jogo.Service.MemberService;
import com.example.jogo.Service.TeamService;
import com.example.jogo.Utils.TokenUtil;
import com.example.jogo.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

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
    private TeamService teamService;

    @Test
    void t() throws UnsupportedEncodingException {
        System.out.println(memberService.check("dsf24f34f","f314f34f").getMsg());
    }
}
