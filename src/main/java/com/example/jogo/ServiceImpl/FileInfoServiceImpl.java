package com.example.jogo.ServiceImpl;

import com.example.jogo.Entity.FileInfo;
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
import java.util.List;

@Service
public class FileInfoServiceImpl implements FileInfoService {
    @Resource
    private FileInfoRepository fileInfoRepository;
    /* Call env.getProper() to get variable defined in application.properties. */
    @Resource
    private Environment env;
    private static final int BUFF_SIZE = 1024;
    private static final Logger logger = LogManager.getLogger(FileInfoServiceImpl.class);

    @Override
    public boolean deleteAllByTeamIdAndProjectId(String teamId, String projectId) {
        return fileInfoRepository.deleteAllByTeamIdAndProjectId(teamId,projectId);
    }

    @Override
    public boolean deleteByTeamIdAndProjectIdAndFileName(String teamId, String projectId, String fileName) {
        return fileInfoRepository.deleteByTeamIdAndProjectIdAndFileName(teamId,projectId,fileName);
    }

    @Override
    public boolean deleteAFile(FileInfo fileInfo) {
        String path,rootPath=env.getProperty("rootPath");
        if(fileInfo.getProjectId().equals(""))
            path = rootPath+"/"+fileInfo.getTeamId()+"/"+fileInfo.getFileName();
        else path = rootPath+"/"+fileInfo.getTeamId()+"/"+fileInfo.getProjectId()+"/"+fileInfo.getFileName();

        File file = new File(path);
        if(!file.exists())
            return false;

        return file.delete();
    }

    @Override
    public boolean deleteAllFiles(String teamId, String projectId) {
        String path,rootPath=env.getProperty("rootPath");
        if(projectId.equals(""))
            path = rootPath+"/"+teamId;
        else path = rootPath+"/"+teamId+"/"+projectId;

        boolean res= true;
        File file = new File(path);
        for(File f:file.listFiles()){
            if(f.isFile())
                res &= f.delete();
            else
                for(File childFile: f.listFiles())
                    res &= childFile.delete();
        }


        return res;
    }

    @Override
    public List<FileInfo> findAllByTeamIdAndProjectId(String teamId, String projectId) {
        return fileInfoRepository.findAllByTeamIdAndProjectId(teamId,projectId);
    }

    @Override
    public FileInfo findByTeamIdAndProjectIdAndFileName(String teamId, String projectId, String fileName) {
        return fileInfoRepository.findByTeamIdAndProjectIdAndFileName(teamId,projectId,fileName);
    }

    @Override
    public boolean save(FileInfo fileInfo) {
        if(fileInfoRepository.findByTeamIdAndProjectIdAndFileName(fileInfo.getTeamId(),fileInfo.getTeamId(),fileInfo.getFileName())!=null)
            return false;
        fileInfoRepository.save(fileInfo);
        return true;
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

        if(projectId.equals(""))
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
