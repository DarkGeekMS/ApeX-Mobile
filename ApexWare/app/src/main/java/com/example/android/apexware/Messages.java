package com.example.android.apexware;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Messages {
    private String id;
    private String subject;
    private String content;
    private boolean isRead;
    private String sender;
    private SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

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

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public SimpleDateFormat getFormat() {
        return format;
    }

    public void setFormat(SimpleDateFormat format) {
        this.format = format;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
