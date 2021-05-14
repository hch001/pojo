package com.example.jogo.Controller;

import com.example.jogo.Entity.*;
import com.example.jogo.Service.*;
import com.example.jogo.Utils.TokenUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/project")
public class ProjectController {
    @Resource
    private MemberService memberService;
    @Resource
    private ProjectService projectService;
    @Resource
    private AuthorityService authorityService;
    @Resource
    private AssessmentService assessmentService;
    @Resource
    private FileConfigService fileConfigService;
    @Resource
    private FileInfoService fileInfoService;
    @Resource
    private LogService logService;
    @Resource
    private NoticeService noticeService;
    @Resource
    private TaskService taskService;
    @Resource
    private TokenUtil tokenUtil;

    /**
     * The {@code projectId} and the {@code teamId} will be inspected at the front-end, making sure they will be always not null.
     */
    @RequestMapping(value = "/projectView",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getBasicData(HttpServletRequest request, @RequestBody Project p){
        Map<String,Object> res = new HashMap<>();
        try{
            String projectId = p.get_id();
            String username = (String)tokenUtil.getDataFromPayLoad(request.getHeader("token"),"username");
            System.out.println("projectId:"+projectId);
            Project project = projectService.findByProjectId(projectId);
            /* not such member or not such project will throw NullPointerException. */
            if(!project.getMembers().contains(username))
                throw new NullPointerException();

            /* set isManager to true if this member is the manager of the project and will throw NullPointerException if projectManager not set. */
            if(project.getProjectManager().equals(username))
                res.put("isManager",true);
            else res.put("isManager",false);
            res.put("code","200");

        } catch (UnsupportedEncodingException | NullPointerException e) {
            res.put("code","403");
            res.put("msg","参数错误");
            return res;
        }
        return res;
    }

    @RequestMapping(value = "/projectMain",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getMain(HttpServletRequest request, @RequestBody Project p) {
        Map<String ,Object> res = new HashMap<>();
        try {
            String projectId = p.get_id();
            Project project = projectService.findByProjectId(projectId);
            String username = (String)tokenUtil.getDataFromPayLoad(request.getHeader("token"),"username");
            if(!project.getMembers().contains(username))
                throw new NullPointerException();
            res.put("project",project);
            res.put("code","200");
        } catch (Exception e){
            res.put("code","403");
        }
        return res;
    }

    @RequestMapping(value = "/destroy",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> destroyProject(HttpServletRequest request, Project p, Team t) {
        Map<String,String> res = new HashMap<>();
        try{
            String username = (String)tokenUtil.getDataFromPayLoad(request.getHeader("token"),"username");
            String projectId = p.get_id();
            String teamId = t.get_id();
            Project project = projectService.findByProjectId(projectId);
            if(project.getProjectManager().equals(username)) {
                assessmentService.deleteAllByTeamIdAndProjectId(teamId,projectId);
                authorityService.deleteAllByTeamIdAndProjectId(teamId,projectId);
                fileConfigService.deleteAllByTeamIdAndProjectId(teamId,projectId);
                fileInfoService.deleteAllByTeamIdAndProjectId(teamId,projectId);
            }
        } catch (Exception e) {
            res.put("code","403");
        }
        return res;
    }

}
