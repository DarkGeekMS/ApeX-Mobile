package com.example.android.apexware;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
/** This class handle edit post by user */
public class EditPost extends AppCompatActivity {
  EditText body;
  Button post;
  String beforeEdit;
  User user = SharedPrefmanager.getInstance(EditPost.this).getUser();
  final String token = user.getToken();
  Post post1 = new Post();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.add_comment_activity);
    post = findViewById(R.id.postcomment);
    TextView title = findViewById(R.id.activitytitle);
    title.setText("Edit post");
    Gson gson = new Gson();
    String postAsString = getIntent().getStringExtra("postToEdit");
    post1 = gson.fromJson(postAsString, Post.class);
    beforeEdit = post1.getTextPostcontent();
    body = findViewById(R.id.addCommenttopost);
    body.setText(beforeEdit);
    ImageButton cancel = findViewById(R.id.cancelcomment);
    cancel.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            cancelcomment(v);
          }
        });

    post.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {

            getResponse(
                Request.Method.PATCH,
                Routes.edit,
                null,
                new VolleyCallback() {
                  @Override
                  public void onSuccessResponse(String result) {
                    try {
                      JSONObject response = new JSONObject(result);
                      String value = response.getString("value");
                      if (value.equals("true")) {
                        Intent edit = new Intent();
                        edit.putExtra("edited", body.getText().toString());
                        edit.putExtra("postid", post1.getPostId());
                        setResult(RESULT_OK, edit);
                        finish();
                      } else
                        Toast.makeText(
                                getApplicationContext(),
                                "Error,post isn`t hidden",
                                Toast.LENGTH_SHORT)
                            .show();

                    } catch (JSONException e) {
                      e.printStackTrace();
                    }
                  }
                },
                token,
                post1.getPostId(),
                body.getText().toString());
          }
        });
  }
  /** This function handle cancel edit post */
  public void cancelcomment(View v) {
    if (body.getText().toString() == beforeEdit) {
      finish();
    } else if (!TextUtils.isEmpty(body.getText())) {
      post.setEnabled(false);
    } else {
      new AlertDialog.Builder(EditPost.this)
          .setTitle("Discard changes?")
          .setMessage("Do you want to discard your changes?")
          // Specifying a listener allows you to take an action before dismissing the dialog.
          // The dialog is automatically dismissed when a dialog button is clicked.
          .setPositiveButton(
              "DISCARD",
              new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                  // Continue with delete operation
                  finish();
                }
              })
          // A null listener allows the button to dismiss the dialog and take no further action.
          .setNegativeButton("KEEP EDITING", null)
          .show();
    }
  }
  /** This function take updated changes to user */
  public void getResponse(
      int method,
      String url,
      JSONObject jsonValue,
      final VolleyCallback callback,
      final String token,
      final String id,
      final String content) {
    StringRequest stringRequest =
        new StringRequest(
            Request.Method.PATCH,
            url,
            new Response.Listener<String>() {
              @Override
              public void onResponse(String response) {
                callback.onSuccessResponse(response);
                Toast.makeText(EditPost.this.getApplicationContext(), "Edited", Toast.LENGTH_SHORT)
                    .show();
              }
            },
            new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                int x = 0;
                Toast.makeText(
                        EditPost.this.getApplicationContext(), "Server Error", Toast.LENGTH_SHORT)
                    .show();
                error.getMessage();
              }
            }) {
          @Override
          protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<>();
            params.put("name", post1.getPostId());
            params.put("token", token);
            params.put("content", content);
            return params;
          }
        };
    VolleySingleton.getInstance(EditPost.this).addToRequestQueue(stringRequest);
  }
}
