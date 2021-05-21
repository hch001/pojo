package com.example.jogo;


import com.example.jogo.Entity.Authority;
import com.example.jogo.Entity.FileConfig;
import com.example.jogo.Entity.Member;
import com.example.jogo.Entity.Project;
import com.example.jogo.Service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

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
//    @Autowired
//    private JavaMailSender mailSender;
    @Test
    void t() {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("13959582448@163.com");
//        message.setSubject("外面小程序项目组");
//        message.setTo("huangchenhan123@163.com");
//        message.setText("任务<小程序界面的开发>即将于 2021-05-21 截止");
//        mailSender.send(message);
    }
}
