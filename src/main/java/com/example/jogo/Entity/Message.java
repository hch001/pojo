package com.example.jogo.Entity;

import com.example.jogo.Utils.IDUtil;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;

@Document(value = "Message")
public class Message implements Serializable {
    @MongoId
    private String _id;
    private String teamId;
    private String projectId;
    private String from;
    private String to;
    private String description;
    private String state; // waiting accepted refused

//    public static int TTL = 3*60*60*1000; // time-unit:mills
    public static int MAX_LENGTH = 30; // max length of description

    public Message(){
        this._id = IDUtil.generateID();
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
