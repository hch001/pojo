package com.example.jogo.Configuration;

import com.example.jogo.Entity.Member;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    /**
     * change the Serializer of java objects when using redis, otherwise, there will be some strange hexadecimal characters
     * @param redisConnectionFactory factory to create redisTemplate
     * @return redisTemplate
     */
    @Bean(name = "memberRedisTemplate")
    public RedisTemplate<String,Object> memberRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new FastJsonRedisSerializer<>(Member.class));
//        redisTemplate.setHashValueSerializer(new FastJsonRedisSerializer<>(Member.class));

        return redisTemplate;
    }

    @Bean(name = "tokenRedisTemplate")
    public RedisTemplate<String,String> stringRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,String > redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        return redisTemplate;
    }
}
