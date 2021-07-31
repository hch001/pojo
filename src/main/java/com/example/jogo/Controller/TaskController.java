package com.example.jogo.Controller;

import com.example.jogo.Entity.Project;
import com.example.jogo.Entity.Task;
import com.example.jogo.Service.*;
import com.example.jogo.Utils.StateUtil;
import com.example.jogo.Utils.TokenUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.nio.channels.MembershipKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/project/task")
public class TaskController {
    @Resource
    private TaskService taskService;
    @Resource
    private ProjectService projectService;
    @Resource
    private MemberService memberService;
    @Resource
    private LogService logService;
    @Resource
    private TokenUtil tokenUtil;
    @Resource
    private AuthorityService authorityService;

    @RequestMapping(value = "/publish",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> publish(HttpServletRequest request, @RequestBody Map<String,Object> params){
        Map<String,Object> res = new HashMap<>();
        try{
            String username = (String)tokenUtil.getDataFromPayLoad(request.getHeader("token"),"username");
            String projectId = (String)params.get("projectId");
            String teamId = projectService.findByProjectId(projectId).getTeamId();
            if(!authorityService.hasAuthority(teamId,projectId,username,"modifyTask")){
                throw new IllegalAccessException();
            }

            String taskName = (String)params.get("taskName");
            List<String> members = (List<String>) params.get("members");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date deadline = sdf.parse((String)params.get("deadline"));
            String description = (String)params.get("description");

            Task newTask = taskService.task(teamId,projectId,taskName,members,deadline,description,"待完成");
            taskService.save(newTask);
            logService.save(logService.log(teamId,projectId,username,"发布了任务<"+taskName+">"));
            StateUtil.setSuccess(res);
        }catch (UnsupportedEncodingException e){
            StateUtil.setTokenError(res);
        }catch (NullPointerException e){
            StateUtil.setNullObjectError(res);
        }catch (IllegalAccessException e){
            StateUtil.setAuthorityError(res);
        } catch (ParseException e) {
            StateUtil.setWrongArgumentError(res);
        }
        return res;
    }

    @RequestMapping(value = "/get",method =RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> get(HttpServletRequest request,@RequestBody Map<String,String> params){
        Map<String,Object> res = new HashMap<>();
        try{
            String username = (String)tokenUtil.getDataFromPayLoad(request.getHeader("token"),"username");
            String projectId = (String)params.get("projectId");
            String teamId = projectService.findByProjectId(projectId).getTeamId();
            Project project = projectService.findByProjectId(projectId);

            if(!project.getMembers().contains(username)){
                throw new IllegalAccessException();
            }
            List<Task> tasks = taskService.findAllByTeamIdAndProjectId(teamId,projectId);
            Map<String,String> username2nickName = new HashMap<>();
            project.getMembers().forEach(name->{
                username2nickName.put(name,memberService.findByUsername(name).getNickName());
            });
            res.put("tasks",tasks);
            res.put("username2nickName",username2nickName);
            StateUtil.setSuccess(res);
        }catch (UnsupportedEncodingException e){
            StateUtil.setTokenError(res);
        }catch (NullPointerException e){
            StateUtil.setNullObjectError(res);
        }catch (IllegalAccessException e){
            StateUtil.setAuthorityError(res);
        }
        return res;
    }

}
