package com.example.projectk.model;

public class Assessment_Model {

    String name,id,fileUrl,dueDate,admin;

    public Assessment_Model() {
    }

    public Assessment_Model(String name, String id, String fileUrl, String dueDate, String admin) {
        this.name = name;
        this.id = id;
        this.fileUrl = fileUrl;
        this.dueDate = dueDate;
        this.admin = admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }
}
