package com.example.projectk.model;

public class Assessment_Model {

    String name,id,fileUrl,dueDate,admin,id_with_admin,key;

    public Assessment_Model() {
    }

    public Assessment_Model(String name, String id, String fileUrl, String dueDate, String admin, String id_with_admin, String key) {
        this.name = name;
        this.id = id;
        this.fileUrl = fileUrl;
        this.dueDate = dueDate;
        this.admin = admin;
        this.id_with_admin = id_with_admin;
        this.key = key;
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

    public String getId_with_admin() {
        return id_with_admin;
    }

    public void setId_with_admin(String id_with_admin) {
        this.id_with_admin = id_with_admin;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
