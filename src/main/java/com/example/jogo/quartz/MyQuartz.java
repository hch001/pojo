package com.example.jogo.quartz;

import com.example.jogo.Entity.Member;
import com.example.jogo.Entity.Message;
import com.example.jogo.Entity.Task;
import com.example.jogo.Service.MemberService;
import com.example.jogo.Service.TaskService;
import com.example.jogo.Utils.EmailUtil;
import com.example.jogo.repository.TaskRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.channels.MembershipKey;
import java.util.Date;
import java.util.List;
import java.util.Properties;


@Component
@Configurable
@EnableScheduling
public class MyQuartz {
    private static final Logger logger = LogManager.getLogger(MyQuartz.class);
    @Resource
    private EmailUtil emailUtil;
    @Resource
    private TaskRepository taskRepository;
    @Resource
    private MemberService memberService;

    /* 00:01:00 each day */
    @Scheduled(cron = "0 1 0 * * ?")
    public void notice(){
        List<Task> tasks = taskRepository.findAllByDeadlineBeforeAndStateEquals(new Date(),"未完成");
        tasks.forEach(task -> {
            try{
                String subject = "任务即将截止<"+task.getTaskName()+">";
                String content = task.getDescription();
                List<String> taskMembers = task.getMembers();
                taskMembers.forEach(username -> {
                    Member member = memberService.findByUsername(username);
                    if(member.isInformed()){
                        emailUtil.sendEmail(member.getEmail(),subject,content);
                    }
                });
            } catch (Exception e){

            }
        });
    }

}
