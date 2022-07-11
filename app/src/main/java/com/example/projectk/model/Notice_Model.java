package com.example.projectk.model;

public class Notice_Model {

    String imageUrl,id,notice,link,hashtag,date,time,admin,id_with_admin,key;

    public Notice_Model() {
    }

    public Notice_Model(String imageUrl, String id, String notice, String link,
                        String hashtag, String date, String time, String admin, String id_with_admin, String key) {
        this.imageUrl = imageUrl;
        this.id = id;
        this.notice = notice;
        this.link = link;
        this.hashtag = hashtag;
        this.date = date;
        this.time = time;
        this.admin = admin;
        this.id_with_admin = id_with_admin;
        this.key = key;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
