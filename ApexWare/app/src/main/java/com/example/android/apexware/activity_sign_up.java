package com.example.android.apexware;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class activity_sign_up extends AppCompatActivity {
    Button login;
    Button create_acc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        login = (Button) findViewById(R.id.login_instead_btn);

        create_acc = (Button) findViewById(R.id.signup_btn);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity_login();
            }
        });

    }
/*
* opens the activity login on pressing the button log in instead
*/
    public void openActivity_login() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
