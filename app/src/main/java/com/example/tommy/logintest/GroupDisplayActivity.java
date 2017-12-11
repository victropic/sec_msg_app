package com.example.tommy.logintest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by oldepiper on 12/9/2017.
 */

public class GroupDisplayActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_select);
        Bundle extras = getIntent().getExtras();
        final String sessionId= extras.getString("sessionId");
        Log.d("session", sessionId);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    String success = object.getString("success");
                    JSONArray groups = object.getJSONArray("groups");


                    ArrayList<String> groupNames = new ArrayList<>();
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(GroupDisplayActivity.this, android.R.layout.simple_list_item_1, groupNames);

                    for(int i = 0; i < groups.length(); i++) {
                        String temp = groups.getJSONObject(i).getString("name");
                        groupNames.add(temp);
                    }

                    ListView lvGroups = (ListView)findViewById(R.id.lvGroups);
                    lvGroups.setAdapter(adapter);
                }
                catch(Exception e) {
                    Log.e("Error", e.getMessage());
                }

            }
        };

        GroupListRequest request = new GroupListRequest(responseListener, sessionId);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

        ListView lvGroups = (ListView)findViewById(R.id.lvGroups);

    }
}
