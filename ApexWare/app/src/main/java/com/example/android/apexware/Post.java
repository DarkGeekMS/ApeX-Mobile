package com.example.android.apexware;

import android.app.DownloadManager;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;

public class Post  implements Parcelable {

  protected Post(Parcel in) {
    postType = in.readInt();
    postId = in.readInt();
    ApexcomLogo = in.readString();
    apexcomName = in.readString();
    postOwner = in.readString();
    postCreateDate = in.readInt();
    postTitle = in.readString();
    videoURL = in.readString();
    ImageURL = in.readString();
    textPostcontent = in.readString();
  }

  public static final Creator<Post> CREATOR = new Creator<Post>() {
    @Override
    public Post createFromParcel(Parcel in) {
      return new Post(in);
    }

    @Override
    public Post[] newArray(int size) {
      return new Post[size];
    }
  };

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(postType);
    dest.writeInt(postId);
    dest.writeString(ApexcomLogo);
    dest.writeString(apexcomName);
    dest.writeString(postOwner);
    dest.writeInt(postCreateDate);
    dest.writeString(postTitle);
    dest.writeString(videoURL);
    dest.writeString(ImageURL);
    dest.writeString(textPostcontent);
  }

  public void setPostId(int postId) {
    this.postId = postId;
  }

  /**
   *
   *
   * <h1>Post Class</h1>
   *
   * This class hold all post attributes and function of setters and getters and other function to
   * deal with them
   */
  public int postType;
  public int postId;

  public int getPostId() {
    return postId;
  }

  public String ApexcomLogo = null;
  public String apexcomName = null;
  public String postOwner = null;
  public int postCreateDate = 0;

  public String postTitle = null;
  public String videoURL = null;
  public String ImageURL = null;
  public String textPostcontent = null;
  /** this is default constructor to create post */
  public Post() {}
  /** this is method to get path of apexcomlogo attribute */
  public String getApexcomLogo() {
    return ApexcomLogo;
  }
  /** this method return apexcom name */
  public String getApexcomName() {
    return apexcomName;
  }
  /** this method return uploaded image url */
  public String getImageURL() {
    return ImageURL;
  }
  /**
   * \ this method return intger aas indication for post type type=0 means text post type=1 means
   * image post type=2 means link of video
   */
  public int getPostType() {
    return postType;
  }
  /** this method return post creation date as intger with 24 Hr format */
  public int getPostCreateDate() {
    return postCreateDate;
  }
  /** this method return the link (URL) of the video */
  public String getVideoURL() {

    return videoURL;
  }
  /** this method return name of post creator */
  public String getPostOwner() {
    return postOwner;
  }
  /** thid method return string with title of the post */
  public String getPostTitle() {
    return postTitle;
  }
  /** this method return string contaon text of post */
  public String getTextPostcontent() {
    return textPostcontent;
  }
  /**
   * this method set image path for apexcomlogo
   *
   * @param apexcomLogo string caontain path for the image
   */
  public void setApexcomLogo(String apexcomLogo) {
    ApexcomLogo = apexcomLogo;
  }
  /**
   * this method set name oof apexcom
   *
   * @param apexcomName string contain name of apexcom
   */
  public void setApexcomName(String apexcomName) {
    this.apexcomName = apexcomName;
  }
  /**
   * this method set path of the uploaded photo
   *
   * @param imageURL string conatain path of the image
   */
  public void setImageURL(String imageURL) {
    ImageURL = imageURL;
  }
  /**
   * this method set date of creation post
   *
   * @param postCreateDate int (0~23) to set the date if the param isnot within range it wont be
   *     assigned
   */
  public void setPostCreateDate(int postCreateDate) {
    if (postCreateDate >= 0 && postCreateDate <= 23) this.postCreateDate = postCreateDate;
  }
  /**
   * this method to set the name of post creator
   *
   * @param postOwner string contain post creator
   */
  public void setPostOwner(String postOwner) {
    this.postOwner = postOwner;
  }
  /**
   * this method set titlte of the post
   *
   * @param postTitle string contin title of the post
   */
  public void setPostTitle(String postTitle) {
    this.postTitle = postTitle;
  }
  /**
   * this method set type of the post the param must be within correct range otherwise ot wont be
   * assigned
   *
   * @param postType int (0,1,2) 0 for the text post 1 for the image post 2 for the video links
   */
  public void setPostType(int postType) {
    if (postType == 1 || postType == 2 || postType == 0) this.postType = postType;
  }
  /**
   * this method set url for video links
   *
   * @param videoURL string contain url for videos
   */
  public void setVideoURL(String videoURL) {

    this.videoURL = videoURL;
  }
  /**
   * this method set text for type textpost typr
   *
   * @param textPostTitle String contain text for the post
   */
  public void setTextPostTitle(String textPostTitle) {
    this.textPostcontent = textPostTitle;
  }
  /**
   *
   *
   * <h1>Return the id's of videos </h1>
   *
   * This is a public function that return the id of video from given links either that video in
   * playlist or not
   *
   * @param url is the pased url in string form to this function
   * @author Mazen Amr Fawzy @Version 1.0 @Date 16/3/2019
   */
  public String getVideoId(String url) {
    String id;
    int startIndex = getCharacterIndex(url, '=');
    int endIndex = getCharacterIndex(url, '&');
    if (endIndex == -1) id = url.substring(startIndex, url.length());
    else id = url.substring(startIndex, endIndex - 1);
    return id;
  }
  /**
   *
   *
   * <h1>This function return index of char</h1>
   *
   * this function returns index of the char after the given char
   *
   * @param url this is the string to search into
   * @param toFind given char to search for the it
   * @author Mazen Amr Fawzy
   * @version 1.0 @Date 16/3/2019
   */
  private int getCharacterIndex(String url, char toFind) {
    for (int i = 0; i < url.length(); i++) if (url.charAt(i) == toFind) return i + 1;
    return -1;
  }
}
