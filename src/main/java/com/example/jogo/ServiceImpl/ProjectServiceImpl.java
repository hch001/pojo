package com.example.jogo.ServiceImpl;

import com.example.jogo.Entity.Project;
import com.example.jogo.Service.ProjectService;
import com.example.jogo.repository.ProjectRepository;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Bean(name = "projectCollection")
    public MongoCollection<Document> mongoCollection(MongoTemplate mongoTemplate){
        return mongoTemplate.getCollection("Project");
    }

    @Resource
    private ProjectRepository projectRepository;
    @Resource(name = "projectCollection")
    private MongoCollection<Document> mongoCollection;

    @Override
    public void save(Project project) {
        projectRepository.save(project);
    }

    @Override
    public Project findByProjectId(String projectId) {
        return projectRepository.findBy_id(projectId);
    }

    @Override
    public List<Project> findAllByTeamId(String teamId) {
        return projectRepository.findAllByTeamId(teamId);
    }

    @Override
    public boolean deleteByProjectId(String projectId) {
        if(projectRepository.findBy_id(projectId)==null)
            return false;
        projectRepository.deleteBy_id(projectId);
        return true;
    }

    @Override
    public boolean setProjectNameByProjectId(String projectId, String newProjectName) {
        Bson filter = Filters.eq("_id",projectId);
        Document operations = new Document("$set",new Document("projectName",newProjectName));
        UpdateResult result = mongoCollection.updateOne(filter,operations);

        return (int)result.getModifiedCount() > 0;
    }

    @Override
    public boolean setDescription(String projectId,String description) {
        Bson filter = Filters.eq("_id",projectId);
        Document operations = new Document("$set",new Document("description",description));
        UpdateResult result = mongoCollection.updateOne(filter,operations);

        return (int)result.getModifiedCount() > 0;
    }

    @Override
    public boolean setManagerByProjectId(String projectId, String username) {
        Bson filter = Filters.eq("_id",projectId);
        Document operations = new Document("$set",new Document("projectManager",username));
        UpdateResult result = mongoCollection.updateOne(filter,operations);

        return (int)result.getModifiedCount() > 0;
    }

    @Override
    public boolean removeMemberByProjectId(String projectId, String username) {
        Bson filter = Filters.eq("_id",projectId);
        Document operations = new Document("$pull",new Document("members",username));
        UpdateResult result = mongoCollection.updateOne(filter,operations);

        return (int)result.getModifiedCount() > 0;
    }

    @Override
    public boolean addMemberByProjectId(String projectId, String username) {
        Bson filter = Filters.eq("_id",projectId);
        Document operations = new Document("$push",new Document("members",username));
        UpdateResult result = mongoCollection.updateOne(filter,operations);

        return (int)result.getModifiedCount() > 0;
    }
}
