package com.example.android.apexware;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
/** This class take all information about message */
public class Messages implements Parcelable {
  private String id;
  private String subject;
  private String content;
  private int isRead;
  private String sender;
  private String createdAgo;

  protected Messages(Parcel in) {
    id = in.readString();
    subject = in.readString();
    content = in.readString();
    isRead = in.readInt();
    sender = in.readString();
    createdAgo = in.readString();
  }

  public static final Creator<Messages> CREATOR =
      new Creator<Messages>() {
        @Override
        public Messages createFromParcel(Parcel in) {
          return new Messages(in);
        }

        @Override
        public Messages[] newArray(int size) {
          return new Messages[size];
        }
      };

  public Messages() {}

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

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(id);
    dest.writeString(subject);
    dest.writeString(content);
    dest.writeInt(isRead);
    dest.writeString(sender);
    dest.writeString(createdAgo);
  }
}
