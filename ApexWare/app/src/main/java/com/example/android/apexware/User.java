package com.example.android.apexware;

/**
 * this class is to save the info of the logged user
 */
public class User {

    private String id, username, email, token;

    /**
     * This Constructor take a token when user do a successful login
     *
     * @param token
     */
    public User(String token,String username) {
        this.token = token;
        this.username=username;
    }
    public User(String token) {
        this.token = token;
    }
    /**
     * This Constructor take id,email,user when user fo a successful signup
     *
     * @param id
     * @param username
     * @param email
     */
    public User(String id, String username, String email, String token) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.token = token;
    }
    public User(){

    }
    /** This Method return id of user */
    public String getId() {
        return id;
    }
    /** This Method return username of user */
    public String getUsername() {
        return username;
    }
    /** This Method return Email of User */
    public String getEmail() {
        return email;
    }
    /** This Method return token of user */
    public String getToken() {
        return token;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
