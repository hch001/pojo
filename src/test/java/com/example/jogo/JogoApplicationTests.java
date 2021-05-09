package com.example.jogo;

import com.alibaba.fastjson.JSON;
import com.example.jogo.Entity.Authority;
import com.example.jogo.Entity.FileInfo;
import com.example.jogo.Entity.Member;
import com.example.jogo.Service.FileInfoService;
import com.example.jogo.Service.MemberService;
import com.example.jogo.Service.TeamService;
import com.example.jogo.Utils.TokenUtil;
import com.example.jogo.repository.AuthorityRepository;
import com.example.jogo.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
class JogoApplicationTests {

    @Test
    void contextLoads() {
    }

    @Resource
    private TeamService teamService;

    @Test
    void t() {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileName("1.jpg");
        fileInfo.setDownloads(0);

    }
}
