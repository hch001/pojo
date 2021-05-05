package com.example.jogo.Controller;

import com.example.jogo.Entity.Member;
import com.example.jogo.Service.MemberService;
import com.example.jogo.Utils.TokenUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
            if(memberService.match(member.getUsername(),member.getPassword())){
                res.put("code","200");
                String token = tokenUtil.sign(member);
                res.put("token",token);
                memberService.cacheToken(member.getUsername(), token);
            }
            else res.put("code","406");
        }catch (UnsupportedEncodingException e){
            logger.warn("UnsupportedEncodingException");
        }

        return res;
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> register(@RequestBody Member member){
        Map<String,String> res = new HashMap<>();
        String msg = memberService.inspect(member.getUsername(),member.getPassword()).getMsg();
        if(msg.equals("注册成功"))
            res.put("code","200");
        else res.put("code","406");
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
        else res.put("code","406");
        return res;
    }


}
