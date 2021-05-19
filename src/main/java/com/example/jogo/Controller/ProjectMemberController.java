package com.example.jogo.Controller;

import com.example.jogo.Entity.*;
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

@Controller
@RequestMapping(value = "/project/member")
public class ProjectMemberController {

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
    private InvitationService invitationService;
    @Resource
    private TokenUtil tokenUtil;

    @RequestMapping(value = "/get",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getMembers(HttpServletRequest request, @RequestBody Map<String,String> params){
        Map<String,Object> res = new HashMap<>();
        try{
            String username  = (String)tokenUtil.getDataFromPayLoad(request.getHeader("token"),"username");
            String projectId = params.get("projectId");
            Project project = projectService.findByProjectId(projectId);

            if(!project.getMembers().contains(username))
                throw new IllegalAccessException();

            if(project.getProjectManager().equals(username))
                res.put("isManager",true);
            else res.put("isManager",false);

            List<String> memberUsernames = project.getMembers();
            List<Member> members = new ArrayList<>();
            memberUsernames.forEach((name)->{
                Member m = memberService.findByUsername(name);
                /* hide the private information */
                m.setPassword(null);
                m.setProjectIds(null);
                members.add(m);
            });
            res.put("members",members);
            res.put("code","200");
        } catch (UnsupportedEncodingException | IllegalAccessException |NullPointerException e){
            res.put("code","403");
        }
        return res;
    }

    @RequestMapping(value = "/removeMember",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> removeMember(HttpServletRequest request,@RequestBody Map<String,String> params) {
        Map<String,Object> res = new HashMap<>();
        try{
            String username = (String)tokenUtil.getDataFromPayLoad(request.getHeader("token"),"username");
            String memberUsername = params.get("name");
            String projectId = params.get("projectId");
            Project project = projectService.findByProjectId(projectId);

            /* can not delete manager itself */
            if(memberUsername.equals(username))
                throw new IllegalAccessException();

            if(!project.getMembers().contains(memberUsername))
                throw new NullPointerException();

            if(!authorityService.hasAuthority(project.getTeamId(), projectId,username,"removeMember"))
                throw new IllegalAccessException();

            projectService.removeMemberByProjectId(projectId,memberUsername);
            memberService.leaveProject(memberUsername,projectId);
            authorityService.deleteByTeamIdAndProjectIdAndUsername(project.getTeamId(), projectId,memberUsername);

            Log log = logService.log(project.getTeamId(), projectId,username,"移出了成员<"+memberService.findByUsername(memberUsername).getNickName()+">("+memberUsername+")");
            logService.save(log);

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

    @RequestMapping(value = "/getSimilarNickName",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getSimilarNickName(@RequestBody Map<String,String> params){
        String nickName = params.get("nickName");

        Map<String,Object> res = new HashMap<>();
        List<Map<String,String>> list = new ArrayList<>();
        List<Member> members = memberService.findAllByNickNameStartsWith(nickName);
        if(members==null){
            res.put("code","403");
            return res;
        }

        members.forEach(member -> {
            list.add(new HashMap<>(){{
                put("value",member.getUsername());
                put("nickName",member.getNickName());
                put("gender",member.getGender());
                put("phone",member.getPhone());
                put("email",member.getEmail());
            }});
        });

        res.put("code","200");
        res.put("members",list);

        return res;
    }

    @RequestMapping(value = "/addMember",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> addMember(HttpServletRequest request,@RequestBody Map<String,String> params){
        Map<String,Object> res = new HashMap<>();
        String addMemberUsername = params.get("addMemberUsername");
        String projectId = params.get("projectId");
        String description = params.get("description");
        try{
            String username = (String) tokenUtil.getDataFromPayLoad(request.getHeader("token"), "username");
            Project project = projectService.findByProjectId(projectId);
            String teamId = project.getTeamId();

            if(null==memberService.findByUsername(addMemberUsername) || project.getMembers().contains(addMemberUsername))
                throw new NullPointerException();

            if(!authorityService.hasAuthority(teamId,projectId,username,"addMember"))
                throw new IllegalAccessException();

            Invitation invitation = invitationService.invitation(teamId,projectId,username,addMemberUsername,description);
            invitationService.save(invitation);

            StateUtil.setSuccess(res);
        }catch (UnsupportedEncodingException e) {
            StateUtil.setTokenError(res);
        }catch (IllegalAccessException e) {
            StateUtil.setAuthorityError(res);
        } catch (NullPointerException e){
            res.put("code","403");
            res.put("msg","用户不存在或者已在项目中");
        }

        return res;
    }

//    private void hidePassword(List<Member> members){
//        members.forEach((member)->{
//            member.setPassword(null);
//        });
//    }

    private boolean isChanged(Object o1,Object o2){
        return o1==null||!o1.equals(o2);
    }
}
