package com.example.rezan.data.db;

import java.util.HashMap;

public class Achievement {

    private String name;
    private String photo;
    private Integer score;
    private String mapObject;
    private HashMap<String, Boolean> users;
    private String header;
    private Boolean isDone;

    public void setHeader(String header) {
        this.header = header;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public Boolean getDone() {
        return isDone;
    }

    public String getHeader() {
        return header;
    }

    public Achievement() {
    }

    public Achievement(String name, String photo, Integer score, Boolean isDone) {
        this.name = name;
        this.photo = photo;
        this.score = score;
        this.isDone = isDone;
    }


    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }

    public Integer getScore() {
        return score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void setMapObject(String mapObject) {
        this.mapObject = mapObject;
    }

    public HashMap<String, Boolean> getUsers() {
        return users;
    }

    public void setUsers(HashMap<String, Boolean> users) {
        this.users = users;
    }

    public String getMapObject() {
        return mapObject;
    }
}
