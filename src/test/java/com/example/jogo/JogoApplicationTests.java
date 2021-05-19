package com.example.jogo;


import com.example.jogo.Entity.Authority;
import com.example.jogo.Entity.FileConfig;
import com.example.jogo.Entity.Member;
import com.example.jogo.Entity.Project;
import com.example.jogo.Service.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
    @Resource
    private AuthorityService authorityService;
    @Resource
    private FileConfigService fileConfigService;

    @Test
    void t() {
    }
}
