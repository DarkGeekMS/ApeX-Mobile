package com.example.android.apexware;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class NewPass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pass);
    }

    public void save(View view) {
        //todo edit user's password and login
        Intent i=new Intent(getApplicationContext(), HomePage.class);
        startActivity(i);
    }
}
