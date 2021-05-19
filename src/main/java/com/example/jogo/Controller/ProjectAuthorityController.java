package com.example.jogo.Controller;

import com.example.jogo.Entity.Authority;
import com.example.jogo.Entity.Member;
import com.example.jogo.Entity.Project;
import com.example.jogo.Service.AuthorityService;
import com.example.jogo.Service.MemberService;
import com.example.jogo.Service.ProjectService;
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

@RequestMapping(value = "/project/authority")
@Controller
public class ProjectAuthorityController {
    @Resource
    private TokenUtil tokenUtil;
    @Resource
    private AuthorityService authorityService;
    @Resource
    private ProjectService projectService;
    @Resource
    private MemberService memberService;

    @RequestMapping(value = "/get",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getAuthority(HttpServletRequest request, @RequestBody Map<String,String> params){
        Map<String,Object> res = new HashMap<>();
        try{
            String username = (String)tokenUtil.getDataFromPayLoad(request.getHeader("token"),"username");
            String projectId = params.get("projectId");
            Project project = projectService.findByProjectId(projectId);

            /* NullPointerException if project is null. */
            if(!project.getProjectManager().equals(username))
                throw new IllegalAccessException();

            List<Authority> authorities = authorityService.findAllByTeamIdAndProjectId(project.getTeamId(),projectId);
            List<String> nicknames = new ArrayList<>();
            authorities.forEach(authority -> {
                nicknames.add(memberService.findByUsername(authority.getUsername()).getNickName());
            });
            res.put("authorities",authorities);
            res.put("isManager",project.getProjectManager().equals(username));
            res.put("nickNames",nicknames);
            StateUtil.setSuccess(res);
        } catch (UnsupportedEncodingException e){
            StateUtil.setTokenError(res);
        } catch (IllegalAccessException e){
            StateUtil.setAuthorityError(res);
        } catch (NullPointerException e){
            StateUtil.setNullObjectError(res);
        }
        return res;
    }

    @RequestMapping(value = "/modify",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> modifyAuthority(HttpServletRequest request,@RequestBody Authority authority){
        Map<String,Object> res = new HashMap<>();
        try{
            String username = (String)tokenUtil.getDataFromPayLoad(request.getHeader("token"),"username");
            String projectId = authority.getProjectId();
            Project project = projectService.findByProjectId(projectId);

            if(!project.getProjectManager().equals(username))
                throw new IllegalAccessException();
            if(username.equals(authority.getUsername()))
                throw new IllegalAccessException();

            authorityService.replace(authority);
            StateUtil.setSuccess(res);
        } catch (UnsupportedEncodingException e){
            StateUtil.setTokenError(res);
        } catch (IllegalAccessException e){
            StateUtil.setAuthorityError(res);
        } catch (NullPointerException e){
            StateUtil.setNullObjectError(res);
        }
        return res;
    }
}
