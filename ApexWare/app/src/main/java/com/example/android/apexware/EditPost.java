package com.example.android.apexware;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;

public class EditPost extends AppCompatActivity {
    EditText body;
    Button post;
    String beforeEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_comment_activity);
        post=findViewById(R.id.postcomment);
        TextView title=findViewById(R.id.activitytitle);
        title.setText("Edit post");
        Gson gson = new Gson();
        String postAsString = getIntent().getStringExtra("postToDisplay");
        final Post post1 = gson.fromJson(postAsString, Post.class);
         beforeEdit=post1.getTextPostcontent();
         body=findViewById(R.id.addCommenttopost);
         body.setText(beforeEdit);
        if(!TextUtils.isEmpty(body.getText())){post.setEnabled(false);}
        ImageButton cancel=findViewById(R.id.cancelcomment);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelcomment(v);
            }
        });

    }
    public void cancelcomment(View v)
    { if(body.getText().toString()== beforeEdit){finish(); }
        else if(!TextUtils.isEmpty(body.getText())){post.setEnabled(false);}
        else
      {
            new AlertDialog.Builder(EditPost.this)
                    .setTitle("Discard changes?")
                    .setMessage("Do you want to discard your changes?")
                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton("DISCARD", new DialogInterface.OnClickListener() {
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
}
