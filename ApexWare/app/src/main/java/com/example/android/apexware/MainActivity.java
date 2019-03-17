package com.example.android.apexware;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    Button login;
    Button signup;
    Button forgot_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (Button) findViewById(R.id.login_btn);

        signup = (Button) findViewById(R.id.signup_btn);

        forgot_pass = (Button) findViewById(R.id.forgot_pass_btn);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity_sign_up();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_home_activity();
            }
        });
    }
    /*
     * opens the activity sign up on pressing the button sign up
    */
    public void openActivity_sign_up() {
        Intent intent = new Intent(this, activity_sign_up.class);
        startActivity(intent);
    }

    /*
     * opens the activity home on pressing the log in button
     */
    public void open_home_activity() {
        Intent intent = new Intent(this, listOfPostsClass.class);
        startActivity(intent);
    }
}