package com.example.jogo.Controller;

import com.example.jogo.Entity.Task;
import com.example.jogo.Service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.Date;

@Controller
@RequestMapping(value = "/task")
public class TaskController {
    @Resource
    private TaskService taskService;

    @RequestMapping(value = "",method = RequestMethod.GET)
    public String func(){

//        taskService.asynSave(task);
        return "loginPage";
    }
}
