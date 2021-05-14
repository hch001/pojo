package com.example.jogo.Utils;

import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;

@Component
public class IDUtil {
    private static final String salt = "32ye7";

    public static String generateID(){
        Date date = new Date();
        String base = date.toString()+salt+new Random().nextInt();
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("md5");
            byte[] bytes = messageDigest.digest(base.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for(byte b:bytes){
                /* The first four bits and the last four bits of each byte constitute two characters respectively */
                sb.append(Character.forDigit(b>>4 & 0xf,16));
                sb.append(Character.forDigit(b & 0xf,16));
            }
            return sb.toString();
        }catch (NoSuchAlgorithmException e){
            return "";
        }

    }
}
