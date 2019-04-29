package com.example.android.apexware;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WriteMessage extends AppCompatActivity {
    EditText editTextReciever;
    EditText editTextSubject;
    EditText editTextContent;
    //User user = SharedPrefmanager.getInstance(this).getUser();
   //final String token=user.getToken();
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_message);

        Window window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.myblue));

        //get edit text value
        editTextReciever=(EditText)findViewById(R.id.username_reciever_userinput);
        editTextSubject=(EditText)findViewById(R.id.subject_userinput);
        editTextContent=(EditText)findViewById(R.id.message_content);

        Toolbar toolbar =findViewById(R.id.WriteMessagesToolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MessageFragment.class));
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.send_option, menu);
        return true;
    }
    public void sendMessage (MenuItem item){
        Toast.makeText(this,"you pressed send button",Toast.LENGTH_SHORT).show();
    }
    /*public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.:
                getResponse(Request.Method.POST,
                        Routes.InboxMessages,
                        null,
                        new VolleyCallback(){
                            @Override
                            public void onSuccessResponse(String response) {
                                try {
                                    // converting response to json object
                                    JSONObject obj = new JSONObject(response);

                                    // if no error in response
                                    if (response != null) {
                                        // getting the result from the response
                                        String temp=obj.getString("id");
                                        if(temp!="Receiver id is not found"){
                                            Toast.makeText(getApplicationContext(),"Successfuly sent",Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(
                                                getApplicationContext(), "Unsuccessful operation", Toast.LENGTH_SHORT)
                                                .show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },editTextReciever.getText().toString().trim(),
                          editTextSubject.getText().toString().trim(),
                          editTextContent.getText().toString().trim(),token);
                return true;
                }
        return super.onOptionsItemSelected(item);
    }*/
    public void getResponse(
            int method,
            String url,
            JSONObject jsonValue,
            final VolleyCallback callback,
            final String username,
            final String subject,
            final String content,
            final String token) {
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
                                Toast.makeText(
                                        getApplicationContext(), "Server Error", Toast.LENGTH_SHORT)
                                        .show();
                                error.getMessage();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("username", username);
                        params.put("subject", subject);
                        params.put("content", content);
                        params.put("token",token);
                        return params;
                    }
                };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
