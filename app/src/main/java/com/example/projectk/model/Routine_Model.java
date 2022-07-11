package com.example.projectk.model;

public class Routine_Model {
    String routine, key, id, admin, id_with_admin, time, date,update;

    public Routine_Model() {
    }

    public Routine_Model(String routine, String key, String id, String admin, String id_with_admin, String time, String date, String update) {
        this.routine = routine;
        this.key = key;
        this.id = id;
        this.admin = admin;
        this.id_with_admin = id_with_admin;
        this.time = time;
        this.date = date;
        this.update = update;
    }

    public String getRoutine() {
        return routine;
    }

    public void setRoutine(String routine) {
        this.routine = routine;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }
}
