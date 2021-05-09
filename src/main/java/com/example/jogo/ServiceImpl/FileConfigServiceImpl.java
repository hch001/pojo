package com.example.jogo.ServiceImpl;

import com.example.jogo.Entity.FileConfig;
import com.example.jogo.Service.FileConfigService;
import com.example.jogo.repository.FileConfigRepository;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileConfigServiceImpl implements FileConfigService {
    @Resource
    private FileConfigRepository fileConfigRepository;
    @Resource
    private Environment env;
    private static final int MAX_SIZE = 100; // MB
    private static final List<String> DEFAULT_ALLOWED_TYPES = new ArrayList<>(){{
        add("doc");
        add("docx");
        add("pdf");
        add("jpg");
        add("png");
    }};
    private static final int DEFAULT_SIZE_PER_SIZE = 10; // MB

    @Override
    public boolean init(String teamId, String projectId) {
        /* already existed */
        if(fileConfigRepository.findByTeamIdAndProjectId(teamId,projectId)!=null)
            return false;

        String rootPath = env.getProperty("rootPath"),path;
        if(projectId==null || projectId.equals("")) path = rootPath+"/"+teamId;
        else path = rootPath+"/"+teamId+"/"+projectId;

        File file = new File(path);
        boolean res = file.mkdirs();

        FileConfig fileConfig = new FileConfig();
        fileConfig.setUsed(0);
        fileConfig.setAllowedTypes(DEFAULT_ALLOWED_TYPES);
        fileConfig.setMaxSizePerFile(DEFAULT_SIZE_PER_SIZE);

        fileConfigRepository.save(fileConfig);
        return res;
    }

    @Override
    public boolean isAllowedType(String teamId, String projectId, String type) {
        FileConfig fileConfig = fileConfigRepository.findByTeamIdAndProjectId(teamId,projectId);
        if(fileConfig==null)
            return false;
        List<String> allowedTypes = fileConfig.getAllowedTypes();

        return allowedTypes!=null && allowedTypes.contains(type);
    }

    @Override
    public boolean hasEnoughSpace(String teamId, String projectId, int fileSize) {
        FileConfig fileConfig = fileConfigRepository.findByTeamIdAndProjectId(teamId,projectId);
        if(fileConfig==null)
            return false;
        return fileConfig.getUsed()!=null && (MAX_SIZE - fileConfig.getUsed()) >= fileSize;
    }

    @Override
    public FileConfig findByTeamIdAndProjectId(String teamId,String projectId){
        return fileConfigRepository.findByTeamIdAndProjectId(teamId,projectId);
    }

    @Override
    public boolean deleteAllByTeamIdAndProjectId(String teamId, String projectId){
        return fileConfigRepository.deleteAllByTeamIdAndProjectId(teamId,projectId);
    }

    @Override
    public boolean setMaxSizePerFile(String teamId,String projectId,Integer newSize){
        return fileConfigRepository.setMaxSizePerFile(teamId,projectId,newSize);
    }

    @Override
    public boolean setAllowedTypes(String teamId,String projectId,List<String> types){
        return fileConfigRepository.setAllowedTypes(teamId,projectId,types);
    }

    @Override
    public boolean setUsed(String teamId,String projectId,int newUsed){
        return fileConfigRepository.setUsed(teamId,projectId,newUsed);
    }

}
