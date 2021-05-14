package com.example.jogo.Entity;

import com.example.jogo.MyAnnotation.Inoperable;
import com.example.jogo.Utils.IDUtil;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Document(value = "Project")
public class Project implements Serializable {
    @MongoId
    private String _id;
    private String teamId;
    @Inoperable
    private Date time;
    private String projectManager;
    private String projectName;
    private List<String> members;
//    private List<FileInfo> files;
//    private FileConfig fileConfig;
//    private List<Authority> authorities;
//    private List<Log> logs;
//    private List<Notice> notices;
    private String description;
//    private List<Assessment> assessments;
//    private List<Task> tasks;

    public Project(){
        this.time = new Date();
        this._id = IDUtil.generateID();
    }

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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }
//
//    public List<Log> getLogs() {
//        return logs;
//    }
//
//    public void setLogs(List<Log> logs) {
//        this.logs = logs;
//    }
//
//    public List<Notice> getNotices() {
//        return notices;
//    }
//
//    public void setNotices(List<Notice> notices) {
//        this.notices = notices;
//    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public List<Assessment> getAssessments() {
//        return assessments;
//    }
//
//    public void setAssessments(List<Assessment> assessments) {
//        this.assessments = assessments;
//    }
//
//    public List<Task> getTasks() {
//        return tasks;
//    }
//
//    public void setTasks(List<Task> tasks) {
//        this.tasks = tasks;
//    }
//
//    public List<FileInfo> getFiles() {
//        return files;
//    }
//
//    public void setFiles(List<FileInfo> files) {
//        this.files = files;
//    }
//
//    public FileConfig getFileConfig() {
//        return fileConfig;
//    }
//
//    public void setFileConfig(FileConfig fileConfig) {
//        this.fileConfig = fileConfig;
//    }
//
//    public List<Authority> getAuthorities() {
//        return authorities;
//    }
//
//    public void setAuthorities(List<Authority> authorities) {
//        this.authorities = authorities;
//    }
}
