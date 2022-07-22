package com.userdoctor.ui.common.activity.OTHER_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.userdoctor.R;
import com.userdoctor.ui.common.activity.Home_Activity.ActivityHome;
import com.userdoctor.ui.common.utils.DialogUtil;
import com.userdoctor.ui.common.utils.NetworkUtil;
import com.userdoctor.ui.common.utils.SharedPrefManager;
import com.userdoctor.ui.common.utils.URLs;
import com.userdoctor.ui.common.utils.VolleySingleton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityProfile extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;
    CircleImageView Profile_image;
    int user_id;
    String id, user_image;
    TextView mTextusercontact,mTextUsername, mTextUserage, mTextuserlocation, mTextuserEmail, mTextUsergender, mTextBloodGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        NetworkUtil.isNetworkConnected(ActivityProfile.this);

        sharedPrefManager = new SharedPrefManager(this);
        if (sharedPrefManager != null) {
            user_id = sharedPrefManager.getUser().getId();
            user_image = sharedPrefManager.getUser().getImage();
        }

        System.out.println("chck user image "+user_image);
        id = String.valueOf(user_id);

        initview();
        Clicklistner();
        if (user_image != null && !user_image.isEmpty()) {
            Picasso.with(ActivityProfile.this).load(user_image).into(Profile_image);
        }
        else
        {

            Toast.makeText(this, "no imag", Toast.LENGTH_SHORT).show();
            Picasso.with(ActivityProfile.this).load(R.drawable.ic_profile).into(Profile_image);


        }
        mFunProfileApiCall();


    }

    private void initview() {
        mTextusercontact=findViewById(R.id.mTextusercontact);
        mTextUsername = findViewById(R.id.mTextusername);
       // mTextUserage = findViewById(R.id.mTextUserage);
     //   mTextuserlocation = findViewById(R.id.mTextuserlocation);
        mTextuserEmail = findViewById(R.id.mTextuserEmail);
      //  mTextUsergender = findViewById(R.id.mTextUsergender);
       // mTextBloodGroup = findViewById(R.id.mTextBloodGroup);
        Profile_image=findViewById(R.id.profile_image);
    }


    private void Clicklistner() {
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityProfile.this, ActivityHome.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.tv_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityProfile.this, ActivityEditProfile.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void mFunProfileApiCall() {
  DialogUtil.show(ActivityProfile.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_URL + "get_userProfile",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        DialogUtil.dismiss();
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            boolean res = obj.getBoolean("result");
                            if (res == true) {

                                JSONObject data = obj.getJSONObject("data");
                                System.out.println("check dtaa   "+data);


                                String id = data.getString("id");
                                String mStrFirstName = data.getString("fname");
                                String mStrContactNo = data.getString("contact_no");
                                String mStrEmail = data.getString("email");
                                String mStrPassword = data.getString("password");
                                String mStrImage = data.getString("image");
                                String mStrGender = data.getString("gender");
                                String mStrAge = data.getString("age");
                                String mStrBloodGroup = data.getString("blood_group");
                                String mStrLocation = data.getString("location");
                                String mStrCityId = data.getString("city_id");
                                String mStrLattitude = data.getString("lat");
                                String mStrLongitude = data.getString("lng");


                                System.out.println("check dtaa"+mStrFirstName);

                                mTextUsername.setText(mStrFirstName);
                               // mTextUserage.setText("Age " + mStrAge + " yrs");
                             //   mTextuserlocation.setText(mStrLocation);
                                mTextuserEmail.setText(mStrEmail);
                              //  mTextUsergender.setText(mStrGender);
                             //   mTextBloodGroup.setText(mStrBloodGroup);
                                mTextusercontact.setText(mStrContactNo);

                                if (mStrImage != null && !mStrImage.isEmpty()) {
                                    Picasso.with(ActivityProfile.this).load(URLs.IMAGE_BASE_URL+mStrImage).into(Profile_image);
                                }





                            } else {

                                Toast.makeText(ActivityProfile.this, "else condition", Toast.LENGTH_SHORT).show();
                                //String msg = obj.getString("msg");
                               // Toast.makeText(ActivityProfile.this, msg, Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("check error", e.getMessage().toString());
                            System.out.print("check" + e.getMessage());
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
                params.put("user_id", id);

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ActivityProfile.this, ActivityHome.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}