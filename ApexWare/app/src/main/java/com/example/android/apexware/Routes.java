package com.example.android.apexware;
/**
 * This Interface contain all backend routes
 */
public interface Routes {
    //All variables are default to public,static,final
    boolean active_mock=true;

    //Server Base Address
    String serverAddress="http://34.66.175.211//api/";

    //Account routes
    String deleteMessage=serverAddress+"del_msg?";//post
    String readMessage=serverAddress+"read_msg?";//post
    String me=serverAddress+"me?";//post
    String updatePrefrence=serverAddress+"updateprefs?";//patch
    String prefrence=serverAddress+"prefs?";//post
    String information=serverAddress+"info?";//post
    String karma=serverAddress+"Karma?";
    String signOut=serverAddress+"sign_out?";
    String signUp=serverAddress+"sign_up?";
    String signIn=serverAddress+"sign_in?";
    String mailVerification=serverAddress+"mail_verify?";
    String checkCode=serverAddress+"check_code?";

    //Administrations
    String deleteAccount=serverAddress+"del_account?";//delete
    String deleteUser=serverAddress+"del_user?";//delete
    String addModerator=serverAddress+"add_moderator?";//post

    //Links,Comments and Posts
    String comment=serverAddress+"comment?";//post
    String deleteComment=serverAddress+"delete?";//delete
    String editComment=serverAddress+"edit?";//patch
    String reportComment=serverAddress+"report?";//post
    String voteForComment=serverAddress+"vote?";//post
    String lockPost=serverAddress+"lock_post?";//post
    String hidePost=serverAddress+"Hide?";//post
    String savePost=serverAddress+"save?";//post
    String moreComments=serverAddress+"moreComments?";//post

    //User
    String blockUser=serverAddress+"block_user?";//post
    String compose=serverAddress+"compose?";//post
    String userData=serverAddress+"user_data?";//post

    //Moderation
    String block=serverAddress+"block?";//post
    String reportAction=serverAddress+"report_action?";//post
    String reviewReports=serverAddress+"review_reports?";//post

    //Apexcom
    String about=serverAddress+"about?";//post&get
    String submit_post=serverAddress+"submit_post?";//post
    String subscribeApexcom=serverAddress+"subscribe?";//post
    String siteAdmin=serverAddress+"site_admin?";//post

    //General
    String sortPosts=serverAddress+"sort_posts?";//post&get
    String searchForApexcom=serverAddress+"search?";//post&get
    String getSubscribers=serverAddress+"get_subscribers?";//post&get
    String getApexNames=serverAddress+"Apex_names?";

}
