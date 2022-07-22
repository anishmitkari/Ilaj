package com.userdoctor.ui.common.activity.Home_Activity.Activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.userdoctor.R;
import com.userdoctor.ui.common.utils.SharedPrefManager;
import com.userdoctor.ui.common.utils.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.userdoctor.ui.common.utils.URLs.Doctore_Support;

public class ActivitySupport extends AppCompatActivity {
ImageView iv_back;
int user_id;
    EditText discription, title;
    Button submit;
    String id;
    SharedPrefManager sharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        iv_back=findViewById(R.id.iv_back);

        discription = findViewById(R.id.discription);
        title = findViewById(R.id.title);
        submit = findViewById(R.id.submit);


        sharedPrefManager = new SharedPrefManager(this);
        if (sharedPrefManager != null) {
            user_id = sharedPrefManager.getUser().getId();
        }
        id = String.valueOf(user_id);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
       startActivity(new Intent(ActivitySupport.this, ActivitySetting.class));
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mStrDiscription = discription.getText().toString();
                String mStrTitle = title.getText().toString();
                if (!mStrDiscription.isEmpty() && !mStrTitle.isEmpty()) {
                    ApiSupportCall(id,mStrDiscription,mStrTitle);
                } else {
                    Toast.makeText(ActivitySupport.this, "Please enter title and discription", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }





    private void ApiSupportCall(final String id, final String mStrDiscription, final String mStrTitle) {
        final ProgressDialog progressDialog=new ProgressDialog(ActivitySupport.this);
        progressDialog.setTitle("Please Wait..");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Doctore_Support,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.print("check response detail " + response);
                        try {
                            progressDialog.dismiss();
                            JSONObject obj = new JSONObject(response);
                            String result = obj.getString("result");
                            String msg = obj.getString("msg");
                            if (result.equalsIgnoreCase("true")) {
                                Toast.makeText(ActivitySupport.this, "Your Query Submitted Successfully Submitted", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ActivitySupport.this, ActivitySetting.class));
                            } else {
                                Toast.makeText(getApplicationContext(), " notaccept", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("user_id", id);
                params.put("title", mStrDiscription);
                params.put("msg_query", mStrTitle);

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

}
