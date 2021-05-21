package com.example.jogo.Controller;

import com.example.jogo.Entity.FileConfig;
import com.example.jogo.Entity.Project;
import com.example.jogo.Service.AuthorityService;
import com.example.jogo.Service.FileConfigService;
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

@RequestMapping(value = "/project/fileConfig")
@Controller
public class ProjectFileConfigController {

    @Resource
    private TokenUtil tokenUtil;
    @Resource
    private ProjectService projectService;
    @Resource
    private FileConfigService fileConfigService;

    @RequestMapping(value = "/get",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getFileConfig(HttpServletRequest request, @RequestBody Map<String,String> params){
        Map<String, Object> res = new HashMap<>();
        String projectId = params.get("projectId");
        try{
            String username = (String)tokenUtil.getDataFromPayLoad(request.getHeader("token"),"username");

            if(projectId==null)
                throw new NullPointerException();

            Project project = projectService.findByProjectId(projectId);

            if(!username.equals(project.getProjectManager()))
                throw new IllegalAccessException();

            FileConfig fileConfig = fileConfigService.findByTeamIdAndProjectId(project.getTeamId(), projectId);
            res.put("allowedTypes",fileConfig.getAllowedTypes());
            res.put("maxSizePerFile",fileConfig.getMaxSizePerFile());

            StateUtil.setSuccess(res);
        } catch (UnsupportedEncodingException e) {
            StateUtil.setTokenError(res);
        } catch (NullPointerException e) {
            StateUtil.setNullObjectError(res);
        } catch (IllegalAccessException e) {
            StateUtil.setAuthorityError(res);
        }

        return res;
    }

    @RequestMapping(value = "/modify",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> modify(HttpServletRequest request,@RequestBody FileConfig fileConfig) {
        Map<String, Object> res = new HashMap<>();
        String projectId = fileConfig.get_id();
        Project project = projectService.findByProjectId(projectId);
        try {
            String username = (String) tokenUtil.getDataFromPayLoad(request.getHeader("token"), "username");
            String teamId = projectService.findByProjectId(projectId).getTeamId();

            if (projectId == null)
                throw new NullPointerException();

            if (!username.equals(project.getProjectManager()))
                throw new IllegalAccessException();

            fileConfigService.setAllowedTypes(teamId,projectId,fileConfig.getAllowedTypes());
            fileConfigService.setMaxSizePerFile(teamId,projectId,fileConfig.getMaxSizePerFile());

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
