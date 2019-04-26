package com.example.android.apexware;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * this class has the information of the community
 */
public class CommunityInfo implements Parcelable {

    private String communityName;
    private String communityTagName;
    private int numOfFollowers;
    private String communityLogo;
    private String Background;

    public String getBackground() {
        return Background;
    }

    public void setBackground(String background) {
        Background = background;
    }

    public CommunityInfo() {
    }

    public CommunityInfo(String communityName, String communityTagName, int numOfFollowers, String communityLogo,String Background) {
        this.communityName = communityName;
        this.communityTagName = communityTagName;
        this.numOfFollowers = numOfFollowers;
        this.communityLogo = communityLogo;
        this.Background=Background;
    }

    protected CommunityInfo(Parcel in) {
        communityName = in.readString();
        communityTagName = in.readString();
        numOfFollowers = in.readInt();
        communityLogo = in.readString();
        Background=in.readString();
    }

    public static final Creator<CommunityInfo> CREATOR = new Creator<CommunityInfo>() {
        @Override
        public CommunityInfo createFromParcel(Parcel in) {
            return new CommunityInfo(in);
        }

        @Override
        public CommunityInfo[] newArray(int size) {
            return new CommunityInfo[size];
        }
    };

    public String getCommunityLogo() {
        return communityLogo;
    }

    public void setCommunityLogo(String communityLogo) {
        this.communityLogo = communityLogo;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getCommunityTagName() {
        return communityTagName;
    }

    public void setCommunityTagName(String communityTagName) {
        this.communityTagName = communityTagName;
    }

    public int getnumOfFollowers() {
        return numOfFollowers;
    }

    public void setnumOfFollowers(int numOfFollowers) {
        this.numOfFollowers = numOfFollowers;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(communityName);
        dest.writeString(communityTagName);
        dest.writeInt(numOfFollowers);
        dest.writeString(communityLogo);
        dest.writeString(Background);
    }
}
