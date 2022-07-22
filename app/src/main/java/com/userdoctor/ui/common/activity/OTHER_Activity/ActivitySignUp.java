package com.userdoctor.ui.common.activity.OTHER_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.userdoctor.R;
import com.userdoctor.ui.common.activity.Home_Activity.ActivityHome;
import com.userdoctor.ui.common.activity.Login_Section.ActivityLogin;
import com.userdoctor.ui.common.model.UserData;
import com.userdoctor.ui.common.utils.Constant;
import com.userdoctor.ui.common.utils.DialogUtil;
import com.userdoctor.ui.common.utils.NetworkUtil;
import com.userdoctor.ui.common.utils.SharedPrefManager;
import com.userdoctor.ui.common.utils.URLs;
import com.userdoctor.ui.common.utils.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActivitySignUp extends AppCompatActivity {

    EditText Ed_first_name,Ed_Email,Ed_password,Ed_PhoneNumber;
    CheckBox checkBox;
    Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mDialog=new Dialog(ActivitySignUp.this);
        NetworkUtil.isNetworkConnected(ActivitySignUp.this);

        initView();


           findViewById(R.id.tv_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivitySignUp.this, ActivityLogin.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.iv_signUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



              mFunCallSignUp();
            }
        });




    }
    private void initView()
    {
        Ed_first_name=findViewById(R.id.et_name);
        Ed_Email=findViewById(R.id.et_email);
        Ed_password=findViewById(R.id.et_password);
        Ed_PhoneNumber=findViewById(R.id.et_phone);
        checkBox=findViewById(R.id.ch_terms);
    }

    public void mFunCallSignUp()
    {
        final String StrUsername = Ed_first_name.getText().toString().trim();
        final String StrEmail = Ed_Email.getText().toString().trim();
        final String StrPassword = Ed_password.getText().toString().trim();
        final String StrPhoneNumber = Ed_PhoneNumber.getText().toString().trim();
        if (TextUtils.isEmpty(StrUsername)) {
            Ed_first_name.setError("Please enter username");
            Ed_first_name.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(StrEmail)) {
            Ed_Email.setError("Please enter your email");
            Ed_Email.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(StrEmail).matches()) {
            Ed_Email.setError("Enter a valid email");
            Ed_Email.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(StrPassword)) {
            Ed_password.setError("Enter a password");
            Ed_password.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(StrPhoneNumber)) {
            Ed_PhoneNumber.setError("Enter a phoneNumber");
            Ed_PhoneNumber.requestFocus();
            return;
        }


        if (!checkBox.isChecked()){
           // checkBox.setError("Please Check the checkbox");
            Toast.makeText(ActivitySignUp.this, "Please check terms and condition", Toast.LENGTH_SHORT).show();
            return;
        }

        DialogUtil.show(ActivitySignUp.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_URL+"UserSingUp",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        DialogUtil.dismiss();


                        try {
                            //converting response to json object
                              JSONObject obj = new JSONObject(response);
                              boolean res=obj.getBoolean("result");
                             if (res==true)
                             {

                                 JSONObject data = obj.getJSONObject("data");
                                 System.out.println("<><><>< "+data);
                                 String id=data.getString("id");
                                 String mStrFirstName=data.getString("fname");
                                 String mStrContactNo=data.getString("contact_no");
                                 String mStrEmail=data.getString("email");
                                 String mStrPassword=data.getString("password");
                                 String mStrImage=data.getString("image");
                                 String mStrGender=data.getString("gender");
                                 String mStrAge=data.getString("age");
                                 String mStrBloodGroup=data.getString("blood_group");
                                 String mStrLocation=data.getString("location");
                                 String mStrCityId=data.getString("city_id");
                                 String mStrLattitude=data.getString("lat");
                                 String mStrLongitude=data.getString("lng");


                                 UserData user = new UserData(
                                         data.getInt("id"),
                                         data.getString("fname"),
                                         data.getString("contact_no"),
                                         data.getString("email"),
                                         data.getString("image")

                                 );
                                 SharedPrefManager.getInstance(ActivitySignUp.this).userLogin(user);
                                 Intent intent = new Intent(ActivitySignUp.this, ActivityHome.class);
                                 startActivity(intent);
                                 finish();


                             }
                             else
                             {
                                 String msg=obj.getString("msg");
                                 Toast.makeText(ActivitySignUp.this, msg, Toast.LENGTH_SHORT).show();


                             //    Toast.makeText(ActivitySignUp.this, "Email id or Contact No. already exist", Toast.LENGTH_SHORT).show();

                             }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            System.out.print("check"+e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("fname", StrUsername);
                params.put("email", StrEmail);
                params.put("password", StrPassword);
                params.put("contact_no", StrPhoneNumber);
                params.put("fcm_id", Constant.fcm);
                System.out.println("===param==="+params);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);







    }






}
