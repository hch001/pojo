package com.example.jogo.Controller;

import com.example.jogo.Entity.Message;
import com.example.jogo.Service.*;
import com.example.jogo.Utils.StateUtil;
import com.example.jogo.Utils.TokenUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/message")
@Controller
public class MessageController {
    @Resource
    private TokenUtil tokenUtil;
    @Resource
    private MessageService messageService;
    @Resource
    private MemberService memberService;
    @Resource
    private ProjectService projectService;
    @Resource
    private AuthorityService authorityService;
    @Resource
    private LogService logService;

    @RequestMapping(value = "/get",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getMessage(HttpServletRequest request){
        Map<String,Object> res = new HashMap<>();
        try{
            String username = (String)tokenUtil.getDataFromPayLoad(request.getHeader("token"),"username");
            List<Message> messages = messageService.findAllByToAndStateEquals(username,"waiting");
            List<Map<String,String>> message1 = new ArrayList<>();

            messages.forEach(message -> {
                message1.add(new HashMap<>(){{
                    put("fromNickName",memberService.findByUsername(message.getFrom()).getNickName());
                    put("description",message.getDescription());
                    put("_id",message.get_id());
                    put("invitor",message.getFrom());
                }});
            });

            res.put("messages",message1);
            StateUtil.setSuccess(res);
        }catch (UnsupportedEncodingException e){
            StateUtil.setTokenError(res);
        }
        return res;
    }

    @RequestMapping(value = "/accept",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> accept(HttpServletRequest request,@RequestBody Map<String,Object> params){
        Map<String,Object> res = new HashMap<>();
        try{
            String username = (String)tokenUtil.getDataFromPayLoad(request.getHeader("token"),"username");
            String messageId = (String)params.get("messageId");
            String invitor = (String)params.get("invitor");
            boolean accepted = (Boolean)params.get("accepted");

            Message message = messageService.findById(messageId);

            String teamId = message.getTeamId();
            String projectId = message.getProjectId();

            if(!message.getTo().equals(username))
                throw new IllegalAccessException();



            /* check authority again */
            if(!authorityService.hasAuthority(message.getTeamId(), message.getProjectId(),invitor,"addMember")) {
                messageService.deleteBy_id(messageId);
                throw new IllegalAccessException();
            }

            if(memberService.findByUsername(username).getProjectIds().contains(projectId)) {
                messageService.deleteBy_id(messageId);
                throw new IllegalAccessException();
            }

            if(accepted){
                projectService.addMemberByProjectId(projectId,username);
                authorityService.save(authorityService.authority(teamId,projectId,username));
                memberService.joinProject(username,projectId);
                logService.save(logService.log(teamId,projectId,username,"<"+username+">"+"新成员加入"));
                messageService.setAccepted(messageId);
            }
            else messageService.setRefused(messageId);

            StateUtil.setSuccess(res);
        } catch (UnsupportedEncodingException e) {
            StateUtil.setTokenError(res);
        } catch (IllegalAccessException e) {
            StateUtil.setAuthorityError(res);
        } catch (NullPointerException e){
            StateUtil.setNullObjectError(res);
        }
        return res;
    }
}
