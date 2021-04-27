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
    private String team_id;
    @Inoperable
    private String project_id;
    @Inoperable
    private String member_id;
    private Boolean upload;
    private Boolean download;
    private Boolean removeFile;
    private Boolean addMember;
    private Boolean removeMember;
    private Boolean modifyTeamInfo;
    private Boolean modifyProjectInfo;
    private Boolean modifyTask;
    private Boolean modifyNotice;
    private Boolean assessMember;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
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

    public Boolean getAssessMember() {
        return assessMember;
    }

    public void setAssessMember(Boolean assessMember) {
        this.assessMember = assessMember;
    }
}
