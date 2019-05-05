package com.example.android.apexware;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.android.apexware.atv.model.TreeNode;
import com.example.android.apexware.atv.view.AndroidTreeView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.GONE;

public class Test extends AppCompatActivity {
    TreeNode root;
    String value;
    int position;
    int mSelected = -1;
    List<Comment> CommentsList;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorGray));
        /*get post info from previous activity*/
        Gson gson = new Gson();
        String postAsString = getIntent().getStringExtra("postToDisplay");
        position=getIntent().getIntExtra("itemPos",0);
        final Post post1 = gson.fromJson(postAsString, Post.class);

        /*mock active!!!!!!!!!!*/
        Comment comment1 = new Comment("1");
        comment1.setId("1");
        comment1.setCommentCreateDate("1265");
        comment1.setCommentOwner("Omar229");
        comment1.setCommentContent(
                "lknlscnsln jjcnsljncl slxiklm pscmscm skcskc scjosijcoskn ss;l,pokpsm79 s98cu9sjcosjnci cih9sjoksc sisisisisis ");

        View post=getLayoutInflater().inflate(R.layout.homepgaelistview, null);

        setContentView(R.layout.activity_test);

        /**/

        ImageView logo = findViewById(R.id.apexcomlogo);
        Picasso.get().load(post1.getApexcomLogo()).resize(50, 50).into(logo);
        // *apex community name*//*
        TextView comName = findViewById(R.id.apexcomName);
        comName.setText(post1.getApexcomName());
        // *creator name and date created*//*
        TextView postOwnerAndCreatedTime = (TextView) findViewById(R.id.apexcomOwnerNameAndTimeCreated);
        postOwnerAndCreatedTime.setText(
                "posted by " + post1.getPostOwner() + "." + post1.getPostCreateDate());
        TextView Title = findViewById(R.id.PostTitle);
        Title.setText(post1.getPostTitle());
        TextView textPost = (TextView) findViewById(R.id.TextPostBody);
        WebView videoLinkView = (WebView) findViewById(R.id.videoWebView);
        ImageView uploadedImageView = (ImageView) findViewById(R.id.imageUploadedView);
        textPost.setText(post1.getTextPostcontent());
        /** handling which view will be shown */
        if (post1.getPostType() == 0) {
            textPost.setVisibility(View.VISIBLE);
            textPost.setText(post1.getTextPostcontent());
            videoLinkView.setVisibility(GONE);
            uploadedImageView.setVisibility(GONE);
        } else if (post1.getPostType() == 1) {
            textPost.setVisibility(GONE);
            videoLinkView.setVisibility(GONE);
            uploadedImageView.setVisibility(View.VISIBLE);
            Picasso.get().load(post1.getImageURL()).into(uploadedImageView);
        } else if (post1.getPostType() == 2) {
            textPost.setVisibility(GONE);
            uploadedImageView.setVisibility(GONE);
            videoLinkView.setVisibility(View.VISIBLE);
            setuoVideoSetting(videoLinkView, post1.getVideoURL());
        }
        /** setting 1 view to be shown */
        if (post1.getPostType() == 0) {
            // set body of the post
            TextView postBody = findViewById(R.id.TextPostBody);
            postBody.setText(post1.getTextPostcontent());
            postBody.getText();
        } else if (post1.getPostType() == 1) {
            // set image of the post
            ImageView uploadedImage = (ImageView) findViewById(R.id.imageUploadedView);
            Picasso.get().load(post1.getImageURL()).into(uploadedImage);
        } else if (post1.getPostType() == 2) {
            // set VideoLinks
            WebView viewVideoLinks = (WebView) findViewById(R.id.videoWebView);
            setuoVideoSetting(viewVideoLinks, post1.getVideoURL());
        }
        final Button up = findViewById(R.id.upvote);
        final Button down = findViewById(R.id.downvote);
        final TextView counter = findViewById(R.id.votecounter);
        /** up vote */
        up.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int currentvotes = Integer.parseInt(counter.getText().toString());
                        upvote(post1.getPostId(), Request.Method.POST, null,
                                new  VolleyCallback(){
                                    @Override
                                    public void onSuccessResponse(String result) {
                                        try {
                                            JSONObject response = new JSONObject(result);
                                            value=response.getString("votes");
                                            counter.setText(value);
                                            int newvotes=Integer.parseInt(value);
                                            if(newvotes>currentvotes){
                                                if(newvotes==currentvotes+2)//was downvoted and upvote clicked
                                                { down.setTextColor(Color.GRAY);
                                                    post1.setDownvoted(false);}
                                                up.setTextColor(Color.BLUE);
                                                post1.setUpvoted(true);}
                                            else if (newvotes<currentvotes)//was upvoted & upvote clicked
                                            {
                                                up.setTextColor(Color.GRAY);
                                                post1.setUpvoted(false);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                    }
                });

        /** downVote */
        down.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int currentvotes = Integer.parseInt(counter.getText().toString());
                        downvote(post1.getPostId(),Request.Method.GET, null,
                                new  VolleyCallback(){
                                    @Override
                                    public void onSuccessResponse(String result) {
                                        try {
                                            JSONObject response = new JSONObject(result);
                                            value=response.getString("votes");
                                            counter.setText(value);
                                            int newvotes=Integer.parseInt(value);
                                            if(newvotes<currentvotes){
                                                if(newvotes==currentvotes-2)//was upvoted and downvote clicked
                                                { up.setTextColor(Color.GRAY);
                                                    post1.setUpvoted(false);}
                                                down.setTextColor(Color.RED);
                                                post1.setDownvoted(true);}
                                            else if (newvotes>currentvotes)//was downvoted & downvote clicked(cancel downvote)
                                            {
                                                down.setTextColor(Color.GRAY);
                                                post1.setDownvoted(false);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                    }
                });
        TextView addcomment=findViewById(R.id.addcomment);
        addcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Test.this , AddCommentActivity.class);
                Gson gson = new Gson();
                String ID = gson.toJson(post1.getPostId());
                intent.putExtra("postID", ID); // sending the post to next activity
                startActivityForResult(intent,9);
            }
        });

        try{
            //Root
            root = TreeNode.root();
            //Parent
            MyHolder.IconTreeItem Item = new MyHolder.IconTreeItem(R.drawable.ic_arrow_drop_down, "Parent");
            TreeNode parent = new TreeNode(Item).setViewHolder(new MyHolder(getApplicationContext(), true, MyHolder.DEFAULT, MyHolder.DEFAULT));
            //Child
            TreeNode child = new TreeNode(Item).setViewHolder(new MyHolder(getApplicationContext(), false, R.layout.child, 25));
            //Sub Child
            TreeNode subChild = new TreeNode(Item).setViewHolder(new MyHolder(getApplicationContext(), false, R.layout.child, 50));
            //Sub Child
            TreeNode subChild2 = new TreeNode(Item).setViewHolder(new MyHolder(getApplicationContext(), false, R.layout.child, 75));
            TreeNode child3 = new TreeNode(Item).setViewHolder(new MyHolder(getApplicationContext(), false, R.layout.child, 25));
            //Add sub child.
            child.addChild(subChild);
            subChild.addChild(subChild2);
            //Add child.
            parent.addChildren(child);
            parent.addChildren(child3);
            root.addChild(parent);

            //Add AndroidTreeView into view.
            AndroidTreeView tView = new AndroidTreeView(getApplicationContext(), root);
            ((LinearLayout) findViewById(R.id.ll_parent)).addView(tView.getView());


            Button button=(Button)findViewById(R.id.testButton);
            button.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Parent
                            MyHolder.IconTreeItem nodeItem44 =
                                    new MyHolder.IconTreeItem(R.drawable.ic_arrow_drop_down, "Parent");
                            TreeNode parent2 =
                                    new TreeNode(nodeItem44)
                                            .setViewHolder(
                                                    new MyHolder(
                                                            getApplicationContext(), true, MyHolder.DEFAULT, MyHolder.DEFAULT));
                            root.addChild(parent2);
                            TreeNode.BaseNodeViewHolder.tView.expandNode(root);
                        }
                    });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void setuoVideoSetting(WebView temp, String url) {
        String frameVideo =
                "<html><body><br><iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/"
                        + url
                        + "\" frameborder=\"0\" allowfullscreen></iframe></body></html>";

        WebView displayYoutubeVideo = (WebView) temp.findViewById(R.id.videoWebView);
        displayYoutubeVideo.setWebViewClient(
                new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        return false;
                    }
                });
        WebSettings webSettings = displayYoutubeVideo.getSettings();
        webSettings.setJavaScriptEnabled(true);
        displayYoutubeVideo.loadData(frameVideo, "text/html", "utf-8");
        displayYoutubeVideo.getSettings().setLoadWithOverviewMode(true);
        displayYoutubeVideo.getSettings().setUseWideViewPort(true);
    }
    /**
     * upvote post request
     * @param postID
     * @param method
     * @param jsonValue
     * @param callback
     */
    public void upvote(String postID,int method, JSONObject jsonValue, final VolleyCallback callback){
        final String postId=postID;
        User user = SharedPrefmanager.getInstance(getApplicationContext()).getUser();
        final String token=user.getToken();
        StringRequest stringRequest =
                new StringRequest(
                        Request.Method.POST,
                        Routes.vote,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    // converting response to json object
                                    JSONObject obj = new JSONObject(response);
                                    // if no error in response
                                    if (response != null) {
                                        callback.onSuccessResponse(response);
                                        Toast.makeText(getApplicationContext(), "you upvoted the post", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(
                                                getApplicationContext(),
                                                "something went wrong.. try again",
                                                Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(
                                        getApplicationContext(),
                                        "something went wrong with the connection",
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("name", postId);
                        params.put("dir", "1");
                        params.put("token", token);
                        return params;
                    }
                };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
    /**
     * downvote post request
     * @param postID
     * @param method
     * @param jsonValue
     * @param callback
     */
    public void downvote(String postID,int method, JSONObject jsonValue, final VolleyCallback callback){
        final String postId=postID;
        User user = SharedPrefmanager.getInstance(getApplicationContext()).getUser();
        final String token=user.getToken();
        StringRequest stringRequest =
                new StringRequest(
                        Request.Method.POST,
                        Routes.vote,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    // converting response to json object
                                    JSONObject obj = new JSONObject(response);
                                    // if no error in response
                                    if (response != null) {
                                        callback.onSuccessResponse(response);
                                        Toast.makeText(getApplicationContext(), "you downvoted the post", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(
                                                getApplicationContext(),
                                                "something went wrong.. try again",
                                                Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(
                                        getApplicationContext(),
                                        "something went wrong with the connection",
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("name", postId);
                        params.put("dir", "-1");
                        params.put("token", token);
                        return params;
                    }
                };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
    /**
     * save post request
     * @param postID
     * @param method
     * @param jsonValue
     * @param callback
     */
    public void savePost(String postID,int method, JSONObject jsonValue, final VolleyCallback callback){
        final String postId=postID;
        User user = SharedPrefmanager.getInstance(getApplicationContext()).getUser();
        final String token=user.getToken();
        StringRequest stringRequest =
                new StringRequest(
                        Request.Method.POST,
                        Routes.savePost,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    // converting response to json object
                                    JSONObject obj = new JSONObject(response);
                                    // if no error in response
                                    if (response != null) {
                                        callback.onSuccessResponse(response);
                                        Toast.makeText(getApplicationContext(), "Post is saved ", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(
                                                getApplicationContext(),
                                                "something went wrong.. try again",
                                                Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                String er=error.getMessage();
                                Toast.makeText(
                                        getApplicationContext(),
                                        "something went wrong with the connection",
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("ID", postId);
                        params.put("token", token);
                        return params;
                    }
                };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
    /**
     * hide post request
     * @param postID
     * @param method
     * @param jsonValue
     * @param callback
     */
    public void hidePost(String postID,int method, JSONObject jsonValue, final VolleyCallback callback){
        final String postId=postID;
        User user = SharedPrefmanager.getInstance(getApplicationContext()).getUser();
        final String token=user.getToken();
        StringRequest stringRequest =
                new StringRequest(
                        Request.Method.POST,
                        Routes.hidePost,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    // converting response to json object
                                    JSONObject obj = new JSONObject(response);
                                    // if no error in response
                                    if (response != null) {
                                        callback.onSuccessResponse(response);
                                    } else {
                                        Toast.makeText(
                                                getApplicationContext(),
                                                "something went wrong.. try again",
                                                Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(
                                        getApplicationContext(),
                                        "something went wrong with the connection",
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("name", postId);
                        params.put("token", token);
                        return params;
                    }
                };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
    public void reportPost(String postID, int method, JSONObject jsonValue, final VolleyCallback callback, final String reason){
        final String postId=postID;
        User user = SharedPrefmanager.getInstance(getApplicationContext()).getUser();
        final String token=user.getToken();
        StringRequest stringRequest =
                new StringRequest(
                        Request.Method.POST,
                        Routes.report,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    // converting response to json object
                                    JSONObject obj = new JSONObject(response);
                                    // if no error in response
                                    if (response != null) {
                                        callback.onSuccessResponse(response);
                                    } else {
                                        Toast.makeText(
                                                getApplicationContext(),
                                                "something went wrong.. try again",
                                                Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(
                                        getApplicationContext(),
                                        "something went wrong with the connection",
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("name", postId);
                        params.put("token", token);
                        params.put("content",reason);
                        return params;
                    }
                };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
    /**
     * upvote comment of a post
     * @param v
     */
    public void upvotecomment(View v) {
        TextView counter = findViewById(R.id.votecounterforcomment);
        int i = Integer.parseInt(counter.getText().toString());
        Button up = findViewById(R.id.upvotecomment);
        Button down = findViewById(R.id.downvotecomment);
        if (down.getCurrentTextColor() == Color.RED) {
            down.setTextColor(Color.GRAY);
        }
        up.setTextColor(Color.BLUE);
        i++;
        counter.setText(Integer.toString(i));
    }
    /**
     * downvote comment of a post
     * @param v
     */
    public void downvotecomment(View v) {
        TextView counter = findViewById(R.id.votecounterforcomment);
        int i = Integer.parseInt(counter.getText().toString());
        Button down = findViewById(R.id.downvotecomment);
        Button up = findViewById(R.id.upvotecomment);
        if (up.getCurrentTextColor() == Color.BLUE) {
            up.setTextColor(Color.GRAY);
        }
        down.setTextColor(Color.RED);
        i--;
        counter.setText(Integer.toString(i));
    }
    /**
     * upvote reply of a comment
     * @param v
     */
    public void upvotereply(View v) {
        TextView counter = findViewById(R.id.votecounterforreply);
        int i = Integer.parseInt(counter.getText().toString());
        Button up = findViewById(R.id.upvotereply);
        Button down = findViewById(R.id.downvotereply);
        if (down.getCurrentTextColor() == Color.RED) {
            down.setTextColor(Color.GRAY);
        }
        up.setTextColor(Color.BLUE);
        i++;
        counter.setText(Integer.toString(i));
    }
    /**
     * downvote reply of a comment
     * @param v
     */
    public void downvotereply(View v) {
        TextView counter = findViewById(R.id.votecounterforreply);
        int i = Integer.parseInt(counter.getText().toString());
        Button down = findViewById(R.id.downvotereply);
        Button up = findViewById(R.id.upvotereply);
        if (up.getCurrentTextColor() == Color.BLUE) {
            up.setTextColor(Color.GRAY);
        }
        down.setTextColor(Color.RED);
        i--;
        counter.setText(Integer.toString(i));
    }

    public void getResponse(int method, String url, JSONObject jsonValue, final VolleyCallback callback,final  String token,final String id) {
        StringRequest stringRequest =
                new StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                callback.onSuccessResponse(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                int x=0;
                                Toast.makeText(
                                        Test.this, "Server Error", Toast.LENGTH_SHORT)
                                        .show();
                                error.getMessage();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("token", token);
                        params.put("parent",id);
                        return params;
                    }
                };
        VolleySingleton.getInstance(Test.this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==9&&resultCode==RESULT_OK)
        {
            Gson gson = new Gson();
            String newcom = data.getStringExtra("newcomment");
            final Comment Comment1 = gson.fromJson(newcom, Comment.class);
            if(Comment1!= null)
            {CommentsList.add(Comment1);
                }
           /* if(id!="300000")
            {
                for(int i = 0 ; i < postArrayList.size() ; i++){
                    if(id.equalsIgnoreCase(postArrayList.get(i).getPostId())){
                        postArrayList.remove(i);
                    }
                }
                adapter.notifyDataSetChanged();
                Toast.makeText(getContext(),"post is hidden",Toast.LENGTH_SHORT).show();
            }
            else  Toast.makeText(getContext(),"what tf",Toast.LENGTH_SHORT).show();*/

        }
    }
    //onActivityResult


    /**/



    }


