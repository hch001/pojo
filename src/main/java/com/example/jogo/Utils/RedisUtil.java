package com.example.jogo.Utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RedisUtil {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
}
