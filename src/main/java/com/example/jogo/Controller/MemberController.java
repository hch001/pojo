package com.example.jogo.Controller;

import com.example.jogo.Entity.Member;
import com.example.jogo.Service.MemberService;
import com.example.jogo.Utils.TokenUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/member")
public class MemberController {
    @Resource
    private TokenUtil tokenUtil;
    @Resource
    private MemberService memberService;
    private static final Logger logger = LogManager.getLogger(MemberController.class);

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> login(@RequestBody Member member){
        Map<String,String> res = new HashMap<>();
        try{
            String username = member.getUsername();
            String password = member.getPassword();
            if(memberService.match(username,password)){
                res.put("code","200");
                String token = tokenUtil.sign(member);
                res.put("token",token);
                if(memberService.cacheToken(member.getUsername(), token)) {
                    res.put("msg", "登录成功,已退出另一方登录");
                    res.replace("code","201");
                }
                else res.put("msg","登录成功");
            }
            else {
                res.put("code","403");
                res.put("msg","用户名账号不匹配");
            }
        }catch (UnsupportedEncodingException e){
            logger.warn("UnsupportedEncodingException");
        }

        return res;
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> register(@RequestBody Member member){
        Map<String,String> res = new HashMap<>();
        String msg = memberService.inspectAndAddNewMember(member.getUsername(),member.getPassword()).getMsg();
        if(msg.equals("注册成功"))
            res.put("code","200");
        else
            res.put("code","403");
        res.put("msg",msg);

        return res;
    }

    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> logout(HttpServletRequest request){
        Map<String,String> res = new HashMap<>();
        boolean success = memberService.removeToken(request.getHeader("token"));
        if(success) {
            res.put("code","200");
        }
        else res.put("code","403");

        return res;
    }

    @RequestMapping(value = "/get",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> get(HttpServletRequest request){
        Map<String,Object> res = new HashMap<>();
        try{
            String username = (String)tokenUtil.getDataFromPayLoad(request.getHeader("token"),"username");

            Member member = memberService.findByUsername(username);

            if(member==null){
                res.put("code","403");
                return res;
            }
            res.put("code","200");
            member.setPassword("");
            res.put("member",member);
        }catch (UnsupportedEncodingException e){
            res.put("code","403");
        }
        return res;
    }

    @RequestMapping(value = "/modify",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> modify(HttpServletRequest request, @RequestBody Member member){
        Map<String,String> res = new HashMap<>();
        try{
            String username = (String)tokenUtil.getDataFromPayLoad(request.getHeader("token"),"username");

            Member oldMember = memberService.findByUsername(username);
            if(member == null || !member.getUsername().equals(username)){
                res.put("code","403");
                return res;
            }
            if(isChanged(oldMember.getEmail(),member.getEmail()))
                memberService.setStringField(username,"email",member.getEmail());
            if(isChanged(oldMember.getPhone(),member.getPhone()))
                memberService.setStringField(username,"phone",member.getPhone());
            if(isChanged(oldMember.getNickName(),member.getNickName()))
                memberService.setStringField(username,"nickName",member.getPhone());
            if(isChanged(oldMember.getGender(),member.getGender()))
                memberService.setStringField(username,"gender",member.getGender());
            if(isChanged(oldMember.getBirthday(),member.getBirthday()))
                memberService.setStringField(username,"birthday",member.getBirthday());
            if(isChanged(oldMember.isInformed(),member.isInformed()))
                memberService.setInformed(username, member.isInformed());

            res.put("code","200");
        }catch (UnsupportedEncodingException e){
            res.put("code","403");
        }
        return res;
    }

    private boolean isChanged(Object o1,Object o2){
        return o1==null||!o1.equals(o2);
    }
}
