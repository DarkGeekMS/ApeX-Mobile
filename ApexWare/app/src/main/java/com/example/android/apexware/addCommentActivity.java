package com.example.android.apexware;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class addCommentActivity extends AppCompatActivity {
    EditText comment;
    Button postcomment;
    User user = SharedPrefmanager.getInstance(addCommentActivity.this).getUser();
    final String token=user.getToken();
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** set the view */
        setContentView(R.layout.add_comment_activity);
        postcomment=findViewById(R.id.postcomment);
        Window window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorGray));
        Gson gson = new Gson();
        final String ID = getIntent().getStringExtra("postID");
        //postid will be used in the request
        final String postid = gson.fromJson(ID, String.class);
        comment=findViewById(R.id.addCommenttopost) ;
        ImageButton cancel=findViewById(R.id.cancelcomment);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelcomment(v);
            }
        });
        postcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(comment.getText()) )
                    Toast.makeText(getApplicationContext(),"please enter a message", Toast.LENGTH_SHORT).show();
                else {
                    if(Routes.active_mock)
                    {
                        Comment newcmnt=new Comment(ID) ;
                    newcmnt.setId("516516");
                    //
                    Date currentTime = Calendar.getInstance().getTime();
                    String currendate="20"+Integer.toString(currentTime.getYear()-100);
                    if(currentTime.getMonth()>9){
                        currendate+="-"+Integer.toString(currentTime.getMonth());
                    }
                    else{
                        currendate+="-"+"0"+Integer.toString(currentTime.getMonth()+1);
                    }
                    currendate+="-"+Integer.toString(currentTime.getDate())+" ";
                    currendate+=Integer.toString(currentTime.getHours());
                    currendate+=":"+Integer.toString(currentTime.getMinutes());
                    //
                    newcmnt.setCommentCreateDate(currendate);
                    newcmnt.setCommentOwner(user.getUsername());
                    newcmnt.setCommentContent(comment.getText().toString());
                    Intent i=new Intent();
                    Gson gson = new Gson();
                    String newnew = gson.toJson(newcmnt);
                    i.putExtra("newcomment",newnew);
                    setResult(RESULT_OK,i);
                    finish();
                    }else{
                        getResponse(Request.Method.POST, Routes.ADD,null , new  VolleyCallback(){
                                @Override
                                public void onSuccessResponse(String result) {
                                    try {
                                        JSONObject response = new JSONObject(result);
                                       String  value=response.getString("comment");
                                        if(value!=null)
                                        {
                                            Comment newcmnt=new Comment(ID) ;
                                            newcmnt.setId(value);
                                            //
                                            Date currentTime = Calendar.getInstance().getTime();
                                            String currendate="20"+Integer.toString(currentTime.getYear()-100);
                                            if(currentTime.getMonth()>9){
                                                currendate+="-"+Integer.toString(currentTime.getMonth());
                                            }
                                            else{
                                                currendate+="-"+"0"+Integer.toString(currentTime.getMonth()+1);
                                            }
                                            currendate+="-"+Integer.toString(currentTime.getDate())+" ";
                                            currendate+=Integer.toString(currentTime.getHours());
                                            currendate+=":"+Integer.toString(currentTime.getMinutes());
                                            //
                                            newcmnt.setCommentCreateDate(currendate);
                                            newcmnt.setCommentOwner(user.getUsername());
                                            newcmnt.setCommentContent(comment.getText().toString());
                                            Intent newcomment=new Intent();
                                            Gson gson = new Gson();
                                            String newnew = gson.toJson(newcmnt);
                                            newcomment.putExtra("newcomment",newnew);
                                            setResult(RESULT_OK,newcomment);
                                            finish();
                                        }
                                        else  Toast.makeText(getApplicationContext(),"Error,fuck this shit am out!",Toast.LENGTH_SHORT).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },token,
                            ID,
                            comment.getText().toString());
                }}
            }
        });

    }

    public void cancelcomment(View v)
    {
        if(!TextUtils.isEmpty(comment.getText()) )
        {
            new AlertDialog.Builder(addCommentActivity.this)
                    .setMessage("Discard comment?")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton("DISCARD", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with delete operation
                            finish();
                        }
                    })
                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton("EDIT", null)
                    .show();
        }
        else finish();
    }




    public void getResponse(int method, String url, JSONObject jsonValue, final VolleyCallback callback, final  String token, final String parent,final String content) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                callback.onSuccessResponse(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                NetworkResponse networkResponse = error.networkResponse;
                                String errorMessage = "Unknown error";
                                if (networkResponse == null) {
                                    if (error.getClass().equals(TimeoutError.class)) {
                                        errorMessage = "Request timeout";
                                    } else if (error.getClass().equals(NoConnectionError.class)) {
                                        errorMessage = "Failed to connect server";
                                    }
                                } else {
                                    String result = new String(networkResponse.data);
                                    try {
                                        JSONObject response = new JSONObject(result);
                                        String status = response.getString("status");
                                        String message = response.getString("message");

                                        Log.e("Error Status", status);
                                        Log.e("Error Message", message);

                                        if (networkResponse.statusCode == 404) {
                                            errorMessage = "Resource not found";
                                        } else if (networkResponse.statusCode == 401) {
                                            errorMessage = message+" Please login again";
                                        } else if (networkResponse.statusCode == 400) {
                                            errorMessage = message+ " Check your inputs";
                                        } else if (networkResponse.statusCode == 500) {
                                            errorMessage = message+" Something is getting wrong";
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("content",content);
                        params.put("parent",parent);
                        params.put("token", token);
                        return params;
                    }
                };
        VolleySingleton.getInstance(addCommentActivity.this).addToRequestQueue(stringRequest);
    }

}
