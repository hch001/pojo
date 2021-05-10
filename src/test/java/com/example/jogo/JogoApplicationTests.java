package com.example.jogo;

import com.example.jogo.Entity.FileConfig;
import com.example.jogo.Entity.Log;
import com.example.jogo.Service.FileConfigService;
import com.example.jogo.Service.FileInfoService;
import com.example.jogo.Service.LogService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class JogoApplicationTests {

    @Test
    void contextLoads() {
    }

    @Resource
    private LogService logService;

    @Test
    void t() {
        Log log = new Log();
        log.setDetail("download file");
        log.setTeamId("team1");
        log.setTeamId("project1");
        log.setUsername("user1");
        logService.asynSave(log);
    }
}
