package com.example.jogo;


import com.example.jogo.Service.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
class JogoApplicationTests {

    @Test
    void contextLoads() {
    }

    @Resource
    private ProjectService projectService;
    @Resource
    private TeamService teamService;
    @Resource
    private MemberService memberService;
    @Resource(name = "memberRedisTemplate")
    private RedisTemplate<String,Object> redisTemplate;
    @Resource
    private FileInfoService fileInfoService;

    @Test
    void t() {
        System.out.println(fileInfoService.deleteAllFiles("team1","project1"));
    }
}
