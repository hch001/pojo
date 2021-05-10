package com.example.jogo.Controller;

import com.example.jogo.Entity.Authority;
import com.example.jogo.Service.AuthorityService;
import com.example.jogo.Service.FileInfoService;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/project/file")
public class FileController {
    @Resource
    private FileInfoService fileInfoService;
    @Resource
    private AuthorityService authorityService;
    @Resource
    private Environment env;

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> upload(HttpServletRequest httpServletRequest, @RequestParam String teamId,@RequestParam String projectId,@RequestParam String username)  throws IOException {
        Map<String,String> res = new HashMap<>();
        if(fileInfoService.storeFile(teamId,projectId,httpServletRequest)){
            res.put("code","200");
            res.put("msg","上传成功");
        }
        else {
            res.put("code","403");
            res.put("msg","上传文件失败");
        }

        return res;
    }

    @RequestMapping(value = "/download",method = RequestMethod.GET)
    public void download(HttpServletResponse response,@RequestParam String fileName,@RequestParam String teamId,@RequestParam String projectId){
        String path;
        if(projectId.equals(""))
            path = env.getProperty("rootPath")+"/"+teamId+"/"+fileName;
        else path = env.getProperty("rootPath")+"/"+teamId+"/"+projectId+"/"+fileName;
        System.out.println("path:"+path);

        try{
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
        }catch (IOException e){
            response.setStatus(404);
        }
        response.setStatus(200);
    }

}
