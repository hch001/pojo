package com.example.jogo.Controller;

import com.example.jogo.Entity.User;
import com.example.jogo.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Resource
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST)
//    @CrossOrigin
    public Map<String,String> login(@RequestBody User user){
        System.out.println("login Controller");
        HashMap<String,String> res = new HashMap<>();
        String username = user.getUsername(),password = user.getPassword();
        if(userService.verify(username,password))
            res.put("msg","valid");
        else res.put("msg","invalid");
        return res;
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String getLoginPage(){
        System.out.println("Controller");
        return "loginPage";
    }

    @RequestMapping(value = "/success",method = RequestMethod.GET)
    public String success(){
        return "success";
    }

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String test(){
        return "test";
    }

}
