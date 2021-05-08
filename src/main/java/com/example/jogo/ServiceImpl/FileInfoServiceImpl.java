package com.example.jogo.ServiceImpl;

import com.example.jogo.Service.FileInfoService;
import com.example.jogo.repository.FileInfoRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;

@Service
public class FileInfoServiceImpl implements FileInfoService {
    @Resource
    private FileInfoRepository fileInfoRepository;
    @Resource
    private Environment env;
    private static final int BUFF_SIZE = 1024;
    private static final Logger logger = LogManager.getLogger(FileInfoServiceImpl.class);

    @Override
    public boolean deleteAllByTeamIdAndProjectId(String teamId, String projectId) {
        return fileInfoRepository.deleteAllByTeamIdAndProjectId(teamId,projectId);
    }

    @Override
    public boolean storeFile(String teamId, String projectId, HttpServletRequest httpServletRequest) {
        if(!(httpServletRequest instanceof MultipartHttpServletRequest)){
            return  false;
        }
        MultipartHttpServletRequest request = (MultipartHttpServletRequest)httpServletRequest;
        String rootPath = env.getProperty("rootPath"),path;
        MultipartFile multipartFile = request.getFile("file");

        if(multipartFile==null) return false;

        if(projectId==null||projectId.equals(""))
            path=rootPath+"/"+teamId+"/"+multipartFile.getOriginalFilename();
        else path = rootPath+"/"+teamId+"/"+projectId+"/"+multipartFile.getOriginalFilename();

        try{
            FileInputStream fis = (FileInputStream) multipartFile.getInputStream();
            FileOutputStream fos = new FileOutputStream(new File(path));
            byte[] bytes = new byte[BUFF_SIZE];
            int len;
            while((len=fis.read(bytes))!=-1){
                fos.write(bytes,0,len);
            }
            fis.close();
            fos.close();
        }catch (FileNotFoundException e){
            logger.error("文件上传错误，文件不能创建、打开或者文件名是目录");
            return false;
        }
        catch (IOException e){
            logger.error("I/O error");
            return false;
        }

        return true;
    }

}
