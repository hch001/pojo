package com.example.jogo.Entity;

import com.example.jogo.MyAnnotation.Inoperable;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;
import java.util.List;

@Document(value = "FileConfig")
public class FileConfig implements Serializable {
    @MongoId
    private String _id;
    private String teamId;
    private String projectId;

    private Integer maxSizePerFile;
    private List<String> allowedTypes;
    @Inoperable
    private Integer used;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Integer getMaxSizePerFile() {
        return maxSizePerFile;
    }

    public void setMaxSizePerFile(Integer maxSizePerFile) {
        this.maxSizePerFile = maxSizePerFile;
    }

    public List<String> getAllowedTypes() {
        return allowedTypes;
    }

    public void setAllowedTypes(List<String> allowedTypes) {
        this.allowedTypes = allowedTypes;
    }

    public Integer getUsed() {
        return used;
    }

    public void setUsed(Integer used) {
        this.used = used;
    }
}
