package com.example.jogo.Controller;

import com.example.jogo.Entity.Member;
import com.example.jogo.Entity.Project;
import com.example.jogo.Entity.Team;
import com.example.jogo.Service.MemberService;
import com.example.jogo.Service.ProjectService;
import com.example.jogo.Service.TeamService;
import com.example.jogo.Utils.StateUtil;
import com.example.jogo.Utils.TokenUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/main")
public class MainController {
    @Resource
    private TokenUtil tokenUtil;
    @Resource
    private MemberService memberService;
    @Resource
    private TeamService teamService;
    @Resource
    private ProjectService projectService;
    private static final Logger logger = LogManager.getLogger(MainController.class);

    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> loadMainPage(HttpServletRequest request){
        Map<String,Object> res = new HashMap<>();
        try{
            String username = (String) tokenUtil.getDataFromPayLoad(request.getHeader("token"),"username");
            Member member = memberService.findByUsername(username);
            if(member==null)
                throw new NullPointerException();

            List<String> projectIds = member.getProjectIds();
            List<Object> projects = new ArrayList<>();
            projectIds.forEach((id)->{
                Project project = projectService.findByProjectId(id);
                projects.add(new HashMap<>(){{
                    put("_id",id);
                    put("projectName",project.getProjectName());
                }});
            });

            Team team = teamService.findByTeamId(member.getTeamId());
            res.put("projects",projects);
            res.put("team",new HashMap<>(){{
                put("teamId",team.get_id());
                put("teamName",team.getTeamName());
            }});
            StateUtil.setSuccess(res);
        }catch (UnsupportedEncodingException e){
            StateUtil.setTokenError(res);
        }
        return res;
    }


}
