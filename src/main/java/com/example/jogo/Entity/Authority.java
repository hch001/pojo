package com.example.jogo.Entity;

import com.example.jogo.MyAnnotation.Inoperable;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;

@Document(value = "Authority")
public class Authority implements Serializable {
    @MongoId
    private String _id;
    @Inoperable
    private String teamId;
    @Inoperable
    private String projectId;
    @Inoperable
    private String username;

    private Boolean upload;
    private Boolean download;
    private Boolean removeFile;

    private Boolean addMember;
    private Boolean removeMember;

    private Boolean modifyTeamInfo;
    private Boolean modifyProjectInfo;

    private Boolean modifyTask;

    private Boolean modifyNotice;

    private Boolean assess;

    public boolean getAuthority(String field){
        switch (field){
            case ("upload"):
                return notNullAndTrue(getUpload());
            case ("download"):
                return notNullAndTrue(getDownload());
            case ("addMember"):
                return notNullAndTrue(getAddMember());
            case ("removeMember"):
                return notNullAndTrue(getRemoveMember());
            case ("modifyTeamInfo"):
                return notNullAndTrue(getModifyTeamInfo());
            case ("modifyProjectInfo"):
                return notNullAndTrue(getModifyProjectInfo());
            case ("modifyTask"):
                return notNullAndTrue(getModifyTask());
            case ("modifyNotice"):
                return notNullAndTrue(getModifyNotice());
            case ("assess"):
                return notNullAndTrue(notNullAndTrue(getAssess()));
        }
        return false;
    }

    private boolean notNullAndTrue(Boolean b) {return b!=null&&b;}

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getUpload() {
        return upload;
    }

    public void setUpload(Boolean upload) {
        this.upload = upload;
    }

    public Boolean getDownload() {
        return download;
    }

    public void setDownload(Boolean download) {
        this.download = download;
    }

    public Boolean getRemoveFile() {
        return removeFile;
    }

    public void setRemoveFile(Boolean removeFile) {
        this.removeFile = removeFile;
    }

    public Boolean getAddMember() {
        return addMember;
    }

    public void setAddMember(Boolean addMember) {
        this.addMember = addMember;
    }

    public Boolean getRemoveMember() {
        return removeMember;
    }

    public void setRemoveMember(Boolean removeMember) {
        this.removeMember = removeMember;
    }

    public Boolean getModifyTeamInfo() {
        return modifyTeamInfo;
    }

    public void setModifyTeamInfo(Boolean modifyTeamInfo) {
        this.modifyTeamInfo = modifyTeamInfo;
    }

    public Boolean getModifyProjectInfo() {
        return modifyProjectInfo;
    }

    public void setModifyProjectInfo(Boolean modifyProjectInfo) {
        this.modifyProjectInfo = modifyProjectInfo;
    }

    public Boolean getModifyTask() {
        return modifyTask;
    }

    public void setModifyTask(Boolean modifyTask) {
        this.modifyTask = modifyTask;
    }

    public Boolean getModifyNotice() {
        return modifyNotice;
    }

    public void setModifyNotice(Boolean modifyNotice) {
        this.modifyNotice = modifyNotice;
    }

    public Boolean getAssess() {
        return assess;
    }

    public void setAssess(Boolean assess) {
        this.assess = assess;
    }


}
