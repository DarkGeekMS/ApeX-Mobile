package com.example.android.apexware;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.gson.Gson;

public class addCommentActivity extends AppCompatActivity {
    EditText comment;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** set the view */
        setContentView(R.layout.add_comment_activity);
        Window window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorGray));
        Gson gson = new Gson();
        String ID = getIntent().getStringExtra("postID");
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
}
