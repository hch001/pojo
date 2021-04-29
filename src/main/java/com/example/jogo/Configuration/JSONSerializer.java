package com.example.jogo.Configuration;

import com.alibaba.fastjson.JSON;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;

public class JSONSerializer implements RedisSerializer<Object> {

    private Charset charset;

    public JSONSerializer(String charsetName){
        this.charset=Charset.forName(charsetName);
    }

    @Override
    public byte[] serialize(Object o) throws SerializationException {
        if(o==null) return null;
        return JSON.toJSONString(o).getBytes(charset);
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if(bytes==null) return null;
        return new String(bytes,charset);
    }

}
