package com.example.projectk.model;

public class Phone_Model {

    String phone_no,teacher_name,id,admin,id_with_admin,key;

    public Phone_Model() {
    }

    public Phone_Model(String phone_no, String teacher_name, String id, String admin, String id_with_admin, String key) {
        this.phone_no = phone_no;
        this.teacher_name = teacher_name;
        this.id = id;
        this.admin = admin;
        this.id_with_admin = id_with_admin;
        this.key = key;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
