package com.example.android.apexware;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VolleyConnection extends AppCompatActivity {
    final String[] s = new String[1];
    final static Post post=new Post();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**
         * this class to create post and put them in list and represent  them in the proper  list view
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testconnection);
       final TextView textView = (TextView) findViewById(R.id.testconnection);
        // ...
        // Instantiate the RequestQueue.
        try {
            RequestQueue queue = Volley.newRequestQueue(this);
            // Request a string response from the provided URL.
            String url = "http://localhost:8000/api/Sign_in?";
            // prepare the Request
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            // response
                            Log.d("Response", response);
                            try {
                                JSONObject rep = new JSONObject(response);
                                post.setApexcomLogo(rep.getString("token"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            int x=0;
                            Log.d("Error.Response", String.valueOf(error));
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("username","Monda Talaat");
                    params.put("password", "monda21");
                    //params.put("email","mazen9030@hotmail.com");
                    return params;
                }
            };
            queue.add(postRequest);
            Toast.makeText(this,post.getApexcomLogo(),Toast.LENGTH_LONG).show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
