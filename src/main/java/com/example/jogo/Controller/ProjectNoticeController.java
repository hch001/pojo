package com.example.jogo.Controller;

import com.example.jogo.Entity.Notice;
import com.example.jogo.Entity.Project;
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
import java.util.HashMap;
import java.util.Map;

@RequestMapping(value = "/project/notice")
@Controller
public class ProjectNoticeController {

    @Resource
    private TokenUtil tokenUtil;
    @Resource
    private LogService logService;
    @Resource
    private MemberService memberService;
    @Resource
    private ProjectService projectService;
    @Resource
    private NoticeService noticeService;
    @Resource
    private AuthorityService authorityService;

    @RequestMapping(value = "/get")
    @ResponseBody
    public Map<String,Object> getNotice(HttpServletRequest request,@RequestBody Map<String,String> params) {
        Map<String,Object> res = new HashMap<>();
        try{
            String username = (String)tokenUtil.getDataFromPayLoad(request.getHeader("token"),"username");
            String projectId = params.get("projectId");
            Project project = projectService.findByProjectId(projectId);

            /* project or project.getMembers() might cause NullPointerException */
            if(!project.getMembers().contains(username))
                throw new IllegalAccessException();

            res.put("notices",noticeService.findAllByTeamIdAndProjectId(project.getTeamId(),projectId));
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

    @RequestMapping(value = "/publish",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> publishNotice(HttpServletRequest request,@RequestBody Map<String,String> params) {
        Map<String,Object> res = new HashMap<>();
        try{
            String username = (String)tokenUtil.getDataFromPayLoad(request.getHeader("token"),"username");
            String projectId = params.get("projectId");
            Project project = projectService.findByProjectId(projectId);

            if(!authorityService.hasAuthority(project.getTeamId(),projectId,username,"modifyNotice"))
                throw new IllegalAccessException();

            Notice notice = noticeService.notice(project.getTeamId(),projectId,params.get("title"),params.get("content"),Integer.parseInt(params.get("priority")));

            noticeService.save(notice);
            logService.save(logService.log(project.getTeamId(),projectId,username,"发布了公告<"+params.get("title")+">"));
            StateUtil.setSuccess(res);
        } catch (UnsupportedEncodingException e) {
            StateUtil.setTokenError(res);
        } catch (IllegalAccessException e) {
            StateUtil.setAuthorityError(res);
        } catch (IllegalArgumentException e) {
            StateUtil.setWrongArgumentError(res);
        } catch (NullPointerException e) {
            StateUtil.setNullObjectError(res);
        }

        return res;
    }

//    @RequestMapping(value = "/remove")
//    @ResponseBody
//    public Map<String,Object> remove(HttpServletRequest request,@RequestBody Map<String,String> params) {
//        Map<String,Object> res = new HashMap<>();
//        try{
//            String username = (String)tokenUtil.getDataFromPayLoad(request.getHeader("token"),"username");
//            String projectId = params.get("projectId");
//            String noticeId = params.get("noticeId");
//            Notice notice = noticeService
//            Project project = projectService.findByProjectId(projectId);
//
//            if(!authorityService.hasAuthority(project.getTeamId(),projectId,username,"modifyNotice"))
//                throw new IllegalAccessException();
//
//            noticeService.deleteBy_id(noticeId);
//
//            logService.save(logService.log(project.getTeamId(),projectId,username,"移除了通知("+noticeId+")"));
//            StateUtil.setSuccess(res);
//        } catch (UnsupportedEncodingException e) {
//            StateUtil.setTokenError(res);
//        } catch (IllegalAccessException e) {
//            StateUtil.setAuthorityError(res);
//        } catch (NullPointerException e) {
//            StateUtil.setNullObjectError(res);
//        }
//
//
//        return res;
//    }
}
