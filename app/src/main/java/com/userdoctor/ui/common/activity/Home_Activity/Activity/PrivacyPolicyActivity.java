package com.userdoctor.ui.common.activity.Home_Activity.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.userdoctor.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.userdoctor.ui.common.utils.URLs.PrivacyPolicy;

public class PrivacyPolicyActivity extends AppCompatActivity {
TextView privacypolicy;
ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
                privacypolicy=findViewById(R.id.privacyp);
        back=findViewById(R.id.iv_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PrivacyPolicyActivity.this, ActivitySetting.class));
            }
        });
        privacydata();
    }

    public void privacydata(){



        RequestQueue queue = Volley.newRequestQueue(PrivacyPolicyActivity.this);
       // String  url = "http://logicaltest.in/Urdoctors/UsersApi/Terms";
        StringRequest postRequest = new StringRequest(Request.Method.POST, PrivacyPolicy,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        System.out.println("====response==="+response);
                        Log.d("Response", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONObject data=obj.getJSONObject("data");
                            String content=data.getString("content");

                            privacypolicy.setText(content);
                            System.out.println("===content===="+content);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println("==response==="+response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        System.out.println("==error==="+error.fillInStackTrace());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("type", "user");


                return params;
            }
        };
        queue.add(postRequest);
    }
}