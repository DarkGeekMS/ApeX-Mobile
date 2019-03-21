package com.example.android.apexware;

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
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class VolleyConnection extends AppCompatActivity {
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
    RequestQueue queue = Volley.newRequestQueue(this);
    // Request a string response from the provided URL.
        String url = "http://127.0.0.1:8000/api/review_reports";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("login.java response", response.toString());

                        int l=  response.length();
                        try {
                            if(l>1){

                                Toast.makeText(getApplicationContext(), response.getString("valid"), Toast.LENGTH_LONG).show();
                            }

                            else{

                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("error", "Error: " + error.getMessage());
                        // hide the progress dialog
                        Toast.makeText(getApplicationContext(), "Server Error, try again", Toast.LENGTH_LONG).show();
                    }
                });

        // Adding request to request queue
        queue.add(jsonObjReq);
    }
}
