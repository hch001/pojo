package com.example.jogo.Configuration;

import com.alibaba.fastjson.JSON;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.*;

public class MySerializer implements RedisSerializer<Object> {

    private static final Logger logger = LogManager.getLogger(MySerializer.class);

    @Override
    public byte[] serialize(Object o) throws SerializationException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(o);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("序列化对象时出错");
        }
        return null;
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        try{
            /* bytes will be null when key not found in redis */
            if(bytes==null) return null;
            ByteArrayInputStream bias = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bias);
            return ois.readObject();
        }
        catch (IOException e){
            logger.error("反序列化时输入的字节流头部出错");
        }catch (ClassNotFoundException e){
            logger.error("找不到反序列化时对象对应的类");
        }
        return null;
    }

}
