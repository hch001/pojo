package com.example.jogo.Utils;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class StateUtil {
    public static void setSuccess(Map<String,Object> map){
        map.put("code","200");
    }

    public static void setTokenError(Map<String,Object> map) {
        map.put("code","403");
        map.put("msg","token解析错误");
    }

    public static void setAuthorityError(Map<String,Object> map) {
        map.put("code","403");
        map.put("msg","权限不足");
    }

    public static void setNullObjectError(Map<String,Object> map){
        map.put("code","403");
        map.put("msg","对象不存在");
    }
    public static void setWrongArgumentError(Map<String,Object> map){
        map.put("code","403");
        map.put("msg","参数错误");
    }
}
