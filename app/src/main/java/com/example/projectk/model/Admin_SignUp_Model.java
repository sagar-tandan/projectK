package com.example.projectk.model;

public class Admin_SignUp_Model {

    String email,password,who;

    public Admin_SignUp_Model() {
    }

    public Admin_SignUp_Model(String email, String password, String who) {
        this.email = email;
        this.password = password;
        this.who = who;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }
}
