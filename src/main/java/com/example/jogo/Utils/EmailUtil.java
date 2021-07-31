package com.example.jogo.Utils;

import com.github.houbb.email.bs.EmailBs;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {
    private String auth = "";
    public void sendEmail(String targetEmail,String subject,String content){
        EmailBs.auth("13959582448@163.com", auth)
                .content(subject,content)
                .sendTo(targetEmail);
    }
}
