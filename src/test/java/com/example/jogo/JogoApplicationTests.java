package com.example.jogo;


import com.example.jogo.Entity.*;
import com.example.jogo.Service.*;
import com.example.jogo.Utils.EmailUtil;
import com.example.jogo.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.annotation.Resource;
import java.text.ParseException;

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
    @Resource
    private MessageService messageService;
    @Resource
    private EmailUtil emailUtil;
    @Test
    void t() throws ParseException {
//        emailUtil.sendEmail("13959582448@163.com","111","222");
    }
}
