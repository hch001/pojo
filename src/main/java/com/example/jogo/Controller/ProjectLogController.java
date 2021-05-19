package com.example.jogo.Controller;

import com.example.jogo.Entity.Project;
import com.example.jogo.Service.LogService;
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
import java.util.HashMap;
import java.util.Map;

@RequestMapping(value = "/project/log")
@Controller
public class ProjectLogController {
    @Resource
    private TokenUtil tokenUtil;
    @Resource
    private ProjectService projectService;
    @Resource
    private LogService logService;

    @RequestMapping(value = "/get",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getLogs(HttpServletRequest request, @RequestBody Map<String,String> params){
        Map<String,Object> res = new HashMap<>();
        try{
            String username = (String)tokenUtil.getDataFromPayLoad(request.getHeader("token"),"username");
            String projectId = params.get("projectId");
            Project project = projectService.findByProjectId(projectId);

            if(!project.getProjectManager().equals(username))
                throw new IllegalAccessException();

            res.put("logs",logService.findAllByTeamIdAndProjectId(project.getTeamId(),projectId));
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
}
