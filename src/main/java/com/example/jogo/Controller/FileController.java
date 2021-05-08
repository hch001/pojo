package com.example.jogo.Controller;

import com.example.jogo.Entity.Authority;
import com.example.jogo.Service.AuthorityService;
import com.example.jogo.Service.FileInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/project/file")
public class FileController {
    @Resource
    private FileInfoService fileInfoService;
    @Resource
    private AuthorityService authorityService;

    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> storeFile(HttpServletRequest httpServletRequest, @RequestParam String teamId,@RequestParam String projectId,@RequestParam String username)  throws IOException {
        System.out.println("teamId:"+teamId+",projectId:"+projectId+",username:"+username);
        fileInfoService.storeFile("team_id","project_id",httpServletRequest);
        return null;
    }


}
