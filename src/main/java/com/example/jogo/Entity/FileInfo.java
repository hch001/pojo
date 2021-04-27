package com.example.jogo.Entity;

import com.example.jogo.MyAnnotation.FieldName;
import com.example.jogo.MyAnnotation.Inoperable;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;
import java.util.Date;

@Document(value = "FileInfo")
public class FileInfo implements Serializable {
    @MongoId
    private String _id;
    @Inoperable
    private String fileName;
    @Inoperable
    private String typeName;
    @Inoperable
    private String path;
    @Inoperable
    private String uploader;
    @Inoperable
    private Integer downloads;
    @Inoperable
    private Integer size;
    @Inoperable
    private Date time;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
