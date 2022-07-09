package com.example.projectk.model;

public class Phone_Model {

    String phone_no,teacher_name,id,admin;

    public Phone_Model() {
    }

    public Phone_Model(String phone_no, String teacher_name, String id, String admin) {
        this.phone_no = phone_no;
        this.teacher_name = teacher_name;
        this.id = id;
        this.admin = admin;
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
}
