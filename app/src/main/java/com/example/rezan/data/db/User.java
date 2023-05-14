package com.example.rezan.data.db;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Map;

@IgnoreExtraProperties
public class User {

    private Integer Score;
    private String Name;

    public User() {
    }

    public User(Integer score, String name) {
        Score = score;
        Name = name;
    }

    public Integer getScore() {
        return Score;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
