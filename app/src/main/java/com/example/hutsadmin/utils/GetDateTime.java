package com.example.hutsadmin.utils;

import android.app.Activity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class GetDateTime {


    Activity activity;
    //    String Url ="https://www.timeapi.io/api/Time/current/coordinate?latitude=22.5726&longitude=88.3639";
    String Url = "https://www.timeapi.io/api/Time/current/coordinate?latitude=24.8607&longitude=67.0011";
    RequestQueue requestQueue;


    public GetDateTime(Activity activity) {
        this.activity = activity;


        requestQueue = Volley.newRequestQueue(activity);

    }



    public void getCurrentDateTime(TimeCallBack timeCallBack)
    {
        JSONObject jsonObject =new JSONObject();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    timeCallBack.getDateTime(response.getString("date"), response.getString("time"));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonObjectRequest);


    }

    public interface TimeCallBack {
        void getDateTime(String date , String time);

    }

}

