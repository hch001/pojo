package com.example.jogo.Controller;

import com.example.jogo.Entity.*;
import com.example.jogo.Service.*;
import com.example.jogo.Utils.StateUtil;
import com.example.jogo.Utils.TokenUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;

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
    private MessageService messageService;
    @Resource
    private TokenUtil tokenUtil;

    /**
     * The {@code projectId} and the {@code teamId} will be inspected at the front-end, making sure they will be always not null.
     */
    @RequestMapping(value = "/projectView",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getBasicData(HttpServletRequest request, @RequestBody Map<String,String> params){
        Map<String,Object> res = new HashMap<>();
        try{
            String projectId = params.get("projectId");
            String username = (String)tokenUtil.getDataFromPayLoad(request.getHeader("token"),"username");

            Project project = projectService.findByProjectId(projectId);

            /* not such member or not such project will throw NullPointerException. */
            if(!project.getMembers().contains(username))
                throw new IllegalAccessException();

            /* set isManager to true if this member is the manager of the project and will throw NullPointerException if projectManager not set. */
            if(project.getProjectManager().equals(username))
                res.put("isManager",true);
            else res.put("isManager",false);

            StateUtil.setSuccess(res);
        } catch (UnsupportedEncodingException e) {
            StateUtil.setTokenError(res);
        } catch (IllegalAccessException e) {
            StateUtil.setAuthorityError(res);
        } catch (NullPointerException e) {
            StateUtil.setNullObjectError(res);
        }
        return res;
    }

    @RequestMapping(value = "/projectMain",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getMain(HttpServletRequest request, @RequestBody Map<String,String> params) {
        Map<String ,Object> res = new HashMap<>();
        try {
            String projectId = params.get("projectId");
            Project project = projectService.findByProjectId(projectId);
            String username = (String)tokenUtil.getDataFromPayLoad(request.getHeader("token"),"username");

            if(!project.getMembers().contains(username))
                throw new IllegalAccessException();

            res.put("project",project);
            StateUtil.setSuccess(res);
        } catch (UnsupportedEncodingException e){
            StateUtil.setTokenError(res);
        } catch (IllegalAccessException e) {
            StateUtil.setAuthorityError(res);
        } catch (NullPointerException e) {
            StateUtil.setNullObjectError(res);
        }
        return res;
    }

    @RequestMapping(value = "/destroy",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> destroyProject(HttpServletRequest request,@RequestBody Map<String,String> params) {
        Map<String,Object> res = new HashMap<>();
        try{
            String projectId = params.get("projectId");
            String teamId = params.get("teamId");

            String username = (String)tokenUtil.getDataFromPayLoad(request.getHeader("token"),"username");
            Project project = projectService.findByProjectId(projectId);

            if(project.getProjectManager().equals(username)) {
                assessmentService.deleteAllByTeamIdAndProjectId(teamId,projectId);
                authorityService.deleteAllByTeamIdAndProjectId(teamId,projectId);
                fileConfigService.deleteAllByTeamIdAndProjectId(teamId,projectId);
                fileInfoService.deleteAllByTeamIdAndProjectId(teamId,projectId);
                fileInfoService.deleteAllFiles(teamId,projectId);
                messageService.deleteAllByTeamIdAndProjectId(teamId,projectId);
                logService.deleteAllByTeamIdAndProjectId(teamId,projectId);
                noticeService.deleteAllByTeamIdAndProjectId(teamId,projectId);
                taskService.deleteAllByTeamIdAndProjectId(teamId,projectId);

                List<String> members = project.getMembers();
                members.forEach((name)->{
                    memberService.leaveProject(name,projectId);
                });

                projectService.deleteByProjectId(projectId);

                Log log = new Log();
                log.setUsername(username);
                log.setProjectId(projectId);
                log.setTeamId(teamId);
                log.setTime(new Date());
                log.setDetail("解散了<"+project.getProjectName()+">项目("+projectId+")");
                logService.asynSave(log);

                StateUtil.setSuccess(res);
            } else {
                throw new IllegalAccessException();
            }
        } catch (UnsupportedEncodingException e) {
            StateUtil.setTokenError(res);
        } catch (IllegalAccessException e) {
            StateUtil.setAuthorityError(res);
        } catch (NullPointerException e) {
            StateUtil.setNullObjectError(res);
        }
        return res;
    }

    @RequestMapping(value = "/modify",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> modify(HttpServletRequest request,@RequestBody Project p){
        Map<String,Object> res = new HashMap<>();
        try{
            String username = (String)tokenUtil.getDataFromPayLoad(request.getHeader("token"),"username");
            String projectId = p.get_id();
            String teamId = p.getTeamId();
            Project oldProject = projectService.findByProjectId(projectId);

            boolean canModifyProjectInfo = authorityService.findByTeamIdAndProjectIdAndUsername(teamId,projectId,username).getModifyProjectInfo();
            if(!canModifyProjectInfo)
                throw new IllegalAccessException();

            Log log = logService.log(teamId,"",username,"");

            if(isChanged(oldProject.getProjectName(),p.getProjectName())) {
                projectService.setManagerByProjectId(projectId, p.getProjectManager());
                log.setDetail("将项目名称改为"+p.getProjectName());
                logService.save(log);
            }
            if(isChanged(oldProject.getDescription(),p.getDescription())) {
                projectService.setDescription(projectId, p.getDescription());
                log.setDetail("将项目介绍改为"+p.getDescription());
                logService.save(log);
            }

            StateUtil.setSuccess(res);
        } catch (UnsupportedEncodingException e) {
            StateUtil.setTokenError(res);
        } catch (IllegalAccessException e) {
            StateUtil.setAuthorityError(res);
        } catch (NullPointerException e) {
            StateUtil.setNullObjectError(res);
        }

        return res;
    }


    private boolean isChanged(Object o1,Object o2){
        return o1==null||!o1.equals(o2);
    }
}
