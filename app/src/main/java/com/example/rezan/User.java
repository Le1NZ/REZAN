package com.example.rezan;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public Integer Score;
    public String Name;

    public User() {
    }

    public User(Integer score, String name) {
        Score = score;
        Name = name;
    }
}
