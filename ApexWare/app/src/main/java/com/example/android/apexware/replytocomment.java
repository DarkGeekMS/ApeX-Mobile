package com.example.android.apexware;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;

public class replytocomment extends AppCompatActivity {
    EditText reply;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.replytocomment);
        reply=findViewById(R.id.yourREply);
        Window window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.myblue));
        /*
        * get comment info*/
        Gson gson = new Gson();
        String comnt = getIntent().getStringExtra("Comment");
        final Comment commenttoreply = gson.fromJson(comnt, Comment.class);
        final TextView commentownername=findViewById(R.id.commentownernametoreply);
        commentownername.setText(commenttoreply.getCommentOwner());
        final TextView commentcontent=findViewById(R.id.commentcontenttoreply);
        commentcontent.setText(commenttoreply.getCommentContent());


        ImageButton cancel=findViewById(R.id.cancelreplytocomment);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelcomment(v);
            }
        });
    }
    public void cancelcomment(View v)
    {

        if(!TextUtils.isEmpty(reply.getText()) )
        {
            new AlertDialog.Builder(replytocomment.this)
                    .setMessage("Discard Reply?")

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
}
