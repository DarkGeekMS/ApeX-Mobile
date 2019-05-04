package com.example.android.apexware;
/**
 * This Interface contain all backend routes
 */
public interface Routes {
    //All variables are default to public,static,final
    boolean active_mock=false;
    //Server Base Address
    String serverAddress="http://35.232.3.8//api/";

    //Account routes
    String deleteMessage=serverAddress+"DeleteMessage?";//post
    String readMessage=serverAddress+"ReadMessage?";//post
    String me=serverAddress+"Me?";//post
    String updatePrefrence=serverAddress+"UpdatePreferences?";//patch
    String prefrence=serverAddress+"GetPreferences?";//post
    String blockList=serverAddress+"BlockList";//post
    String information=serverAddress+"ProfileInfo?";//post
    String InboxMessages=serverAddress+"InboxMessages?";//post
    String karma=serverAddress+"Karma?";
    String signOut=serverAddress+"SignOut?";
    String signUp=serverAddress+"SignUp?";
    String signIn=serverAddress+"SignIn?";
    String mailVerification=serverAddress+"MailVerification?";
    String checkCode=serverAddress+"CheckCode?";
    String changePassword=serverAddress+"ChangePassword";
    String getUserData=serverAddress+"UserData";

    //Administrations
    String deleteAccount=serverAddress+"del_account?";//delete
    String deleteApexcom=serverAddress+"DeleteApexcom?";//delete
    String deleteUser=serverAddress+"DeleteUser?";//delete
    String addModerator=serverAddress+"AddModerator?";//post

    //Links,Comments and Posts
    String ADD=serverAddress+"AddReply?";//add
    String comment=serverAddress+"comment?";//post
    String delete=serverAddress+"Delete?";//delete
    String edit=serverAddress+"EditText?";//patch
    String report=serverAddress+"Report?";//post
    String vote=serverAddress+"Vote?";//post
    String lockPost=serverAddress+"LockPost?";//post
    String hidePost=serverAddress+"Hide?";//post
    String savePost=serverAddress+"Save?";//post
    String moreComments=serverAddress+"RetrieveComments?";//post
    String addReplay=serverAddress+"AddReply?";

    //User
    String blockUser=serverAddress+"/BlockUser?";//post
    String compose=serverAddress+"ComposeMessage?";//post
    String userData=serverAddress+"UserData?";//post

    //Moderation
    String block=serverAddress+"ApexcomBlockUser?";//post
    String reportAction=serverAddress+"IgnoreReport?";//post
    String reviewReports=serverAddress+"ReviewReports?";//post

    //Apexcom
    String getApexcom=serverAddress+"GetApexcoms?";//post&get
    String about=serverAddress+"AboutApexcom?";//post&get
    String submit_post=serverAddress+"SubmitPost?";//post
    String subscribeApexcom=serverAddress+"Subscribe?";//post
    String siteAdmin=serverAddress+"SiteAdmin?";//post

    //General
    String sortPosts=serverAddress+"SortPosts?";//post&get
    String searchForApexcom=serverAddress+"Search?";//post&get
    String getSubscribers=serverAddress+"GetSubscribers?";//post&get
    String getApexNames=serverAddress+"ApexComs?";

}
