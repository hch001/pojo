package com.example.jogo.Controller;

import com.example.jogo.Entity.Authority;
import com.example.jogo.Entity.FileConfig;
import com.example.jogo.Entity.FileInfo;
import com.example.jogo.Entity.Project;
import com.example.jogo.Service.*;
import com.example.jogo.ServiceImpl.FileInfoServiceImpl;
import com.example.jogo.Utils.StateUtil;
import com.example.jogo.Utils.TokenUtil;
import com.sun.el.parser.Token;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/project/file")
public class ProjectFileController {
    @Resource
    private FileInfoService fileInfoService;
    @Resource
    private AuthorityService authorityService;
    @Resource
    private ProjectService projectService;
    @Resource
    private Environment env;
    @Resource
    private TokenUtil tokenUtil;
    @Resource
    private LogService logService;
    @Resource
    private FileConfigService fileConfigService;

    /**
     * can not user @RequestBody
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> upload(HttpServletRequest httpServletRequest)  {
        Map<String,Object> res = new HashMap<>();
        try{
            String username = (String)tokenUtil.getDataFromPayLoad(httpServletRequest.getHeader("token"),"username");
            String projectId = httpServletRequest.getHeader("projectId");
            MultipartFile file = ((MultipartHttpServletRequest)httpServletRequest).getFile("file");
            String fileName = file.getOriginalFilename();
            double size = new BigDecimal(file.getSize()).divide(new BigDecimal(1024*1024)).doubleValue();

            String teamId = projectService.findByProjectId(projectId).getTeamId();
            FileConfig fileConfig = fileConfigService.findByTeamIdAndProjectId(teamId,projectId);

            if(!fileConfigService.hasEnoughSpace(teamId,projectId,size)){
                res.put("code","403");
                res.put("msg","存储空间不足");
                return res;
            }

            if(fileConfig.getMaxSizePerFile()<size){
                res.put("code","403");
                res.put("msg","文件过大");
                return res;
            }

            boolean match = false;
            for(String t:fileConfig.getAllowedTypes()){
                if(fileName.endsWith(t)){
                    match = true;
                    break;
                }
            }
            if(!match){
                res.put("code","403");
                res.put("msg","文件类型不允许");
                return res;
            }

            if(!authorityService.hasAuthority(teamId,projectId,username,"upload"))
                throw new IllegalAccessException();

            if(fileInfoService.findByTeamIdAndProjectIdAndFileName(teamId,projectId,fileName)!=null){
                res.put("code","403");
                res.put("msg","文件已经存在");
                return res;
            }

            fileInfoService.storeFile(teamId,projectId,httpServletRequest);
            fileInfoService.save(fileInfoService.fileInfo(teamId,projectId,fileName,username,size,0));
            fileConfigService.setUsed(teamId,projectId,fileConfig.getUsed()+size);

            logService.save(logService.log(teamId,projectId,username,"上传了文件<"+fileName+">"));
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

    @RequestMapping(value = "/download",method = RequestMethod.GET)
    public void download(HttpServletRequest request,HttpServletResponse response,@RequestParam String fileName,@RequestParam String teamId,@RequestParam String projectId){
        String path;
        if(projectId.equals(""))
            path = env.getProperty("rootPath")+"/"+teamId+"/"+fileName;
        else path = env.getProperty("rootPath")+"/"+teamId+"/"+projectId+"/"+fileName;
        System.out.println("path:"+path);

        try{
            String username = (String)tokenUtil.getDataFromPayLoad(request.getHeader("token"),"username");

            if(!authorityService.hasAuthority(teamId,projectId,username,"download"))
                throw new IllegalAccessException();

            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);

            response.reset();
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("utf-8");
            response.setContentLength((int) file.length());
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName );

            OutputStream fos = response.getOutputStream();
            byte[] bytes = new byte[1024];
            int len;
            while((len=fis.read(bytes))!=-1){
                fos.write(bytes,0,len);
            }
            fis.close();
            fos.close();

            fileInfoService.increaseDownloads(teamId,projectId,fileName);
            logService.save(logService.log(teamId,projectId,username,"下载了文件<"+fileName+">"));
        }catch (Exception e) {
            e.printStackTrace();
            response.setStatus(404);
        }
        response.setStatus(200);
    }

    @RequestMapping(value = "/get",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getFiles(HttpServletRequest request,@RequestBody Map<String,String> params) {
        Map<String,Object> res = new HashMap<>();
        try{
            String username = (String)tokenUtil.getDataFromPayLoad(request.getHeader("token"),"username");
            String projectId = params.get("projectId");
            Project project = projectService.findByProjectId(projectId);
            FileConfig fileConfig = fileConfigService.findByTeamIdAndProjectId(project.getTeamId(), projectId);

            if(!project.getMembers().contains(username))
                throw new IllegalAccessException();

            List<FileInfo> files = fileInfoService.findAllByTeamIdAndProjectId(project.getTeamId(), projectId);
            res.put("files",files);
            res.put("allowedTypes", fileConfig.getAllowedTypes());
            res.put("size",FileConfigService.MAX_SIZE);
            res.put("teamId",project.getTeamId());

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
