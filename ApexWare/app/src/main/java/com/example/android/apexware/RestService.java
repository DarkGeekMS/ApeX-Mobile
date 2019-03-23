package com.example.android.apexware;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RestService extends AppCompatActivity implements DataSupplier {
    @Override
    public boolean login_interface(String username, String password) {
        RequestQueue queue = (RequestQueue) Volley.newRequestQueue(this);
        // Request a string response from the provided URL.
        String url = "http://localhost:8000/api/Sign_in?";
        // prepare the Request
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        int x = 0;
                        Log.d("Error.Response", String.valueOf(error));
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", "Monda Talaat");
                params.put("password", "monda21");
                //params.put("email","mazen9030@hotmail.com");
                return params;
            }
        };
        queue.add(postRequest);
        queue.start();
        return false;
    }
}
