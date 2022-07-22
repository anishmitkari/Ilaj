package com.userdoctor.ui.common.activity.Home_Activity.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.userdoctor.R;
import com.userdoctor.ui.common.activity.Home_Activity.ActivityHome;
import com.userdoctor.ui.common.utils.NetworkUtil;
import com.userdoctor.ui.common.utils.SharedPrefManager;
import com.userdoctor.ui.common.utils.VolleyMultipartRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.userdoctor.ui.common.utils.URLs.User_Change_Password;

public class ActivitySetting extends AppCompatActivity {
    CardView change_password;
    Context context;
    CardView support;
    ImageView back;
    String id;
    int user_id;
    TextView termandcondition,about,privacypolicy;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        support = findViewById(R.id.support);
        back = findViewById(R.id.iv_back);
        termandcondition = findViewById(R.id.term);
        privacypolicy = findViewById(R.id.privacypolicy);
        about = findViewById(R.id.aboutus);
        context = ActivitySetting.this;
        change_password = findViewById(R.id.change_password);
        NetworkUtil.isNetworkConnected(ActivitySetting.this);

        sharedPrefManager = new SharedPrefManager(this);
        if (sharedPrefManager != null) {
            user_id = sharedPrefManager.getUser().getId();
        }
        id = String.valueOf(user_id);


        System.out.println("check id " + id);

        termandcondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivitySetting.this, TermAndConditionActivity.class));
            }
        });

        privacypolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivitySetting.this, PrivacyPolicyActivity.class));
            }
        });
     about.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             startActivity(new Intent(ActivitySetting.this, AboutusActivity.class));
         }
     });
back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(ActivitySetting.this, ActivityHome.class));

    }
});
        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Change_Password_Dialoge();
            }
        });

        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ActivitySetting.this, ActivitySupport.class));

            }
        });


    }


    private void Change_Password_Dialoge() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.user_change_password_dialoge);
        final EditText old_password = dialog.findViewById(R.id.old_password);
        final EditText new_password = dialog.findViewById(R.id.new_password);
        final EditText confirm_password = dialog.findViewById(R.id.confirm_password);
        Button dialogButton = (Button) dialog.findViewById(R.id.update_btn);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mStroldpassword = old_password.getText().toString();
                final String mStrnewPassword = new_password.getText().toString();
                final String mStrConfirmPassword = confirm_password.getText().toString();


                if (!mStroldpassword.isEmpty() && !mStrnewPassword.isEmpty() && !mStrConfirmPassword.isEmpty()) {

                    if (mStrnewPassword.equals(mStrConfirmPassword)) {

                        System.out.println("new " + mStrnewPassword);
                        System.out.println("old " + mStrConfirmPassword);

                        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, User_Change_Password,
                                new Response.Listener<NetworkResponse>() {
                                    @Override
                                    public void onResponse(NetworkResponse response) {
                                        Log.e("mmi", response.toString());
                                        System.out.println("check response " + response.toString());
                                        try {
                                            //getting the whole json object from the response
                                            JSONObject obj = new JSONObject(new String(response.data));
                                            String result = obj.getString("result");
                                            String msg = obj.getString("msg");

                                            if (result.equalsIgnoreCase("true")) {
                                                Toast.makeText(getApplicationContext(), " Your Password Successfully Changed", Toast.LENGTH_LONG).show();
                                                dialog.dismiss();
                                            } else {

                                                Toast.makeText(getApplicationContext(), " Your Old Password is Wrong", Toast.LENGTH_LONG).show();
                                            }


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.e("jklm", error.toString());
                                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();

                                params.put("id", id);
                                params.put("oldpassword", mStroldpassword);
                                params.put("newpassword", mStrnewPassword);
                                return params;
                            }


                        };

                        Volley.newRequestQueue(context).add(volleyMultipartRequest);

                    } else {
                        Toast.makeText(context, "Confirm Password is not matched with New Password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "all fields are requreid", Toast.LENGTH_SHORT).show();
                }


                //  Dr_ChangePassword(mStroldpassword,mStrnewPassword);
                // dialog.dismiss();
            }
        });

        dialog.show();
    }


}
