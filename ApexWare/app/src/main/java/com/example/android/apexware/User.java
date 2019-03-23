package com.example.android.apexware;

public class User {

    private String id, username, email, gender, token;

    public User(String token) {
        //this.id = id;
        //this.username = username;
        this.token = token;
        //this.gender = gender;
    }

    public User(String id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }
}
