package com.example.android.apexware;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Messages {
    private String id;
    private String subject;
    private String content;
    private int isRead;
    private String sender;
    private String createdAgo ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int isRead() {
        return isRead;
    }

    public void setRead(int read) {
        isRead = read;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getCreatedAgo() {
        return createdAgo;
    }

    public void setFormat(String time) {
        this.createdAgo = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
