package com.userdoctor.ui.common.activity.Doctors.Home_Doctor_list.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.userdoctor.R;
import com.userdoctor.ui.common.activity.OTHER_Activity.ActivityPersonalInfo;
import com.userdoctor.ui.common.utils.DialogUtil;
import com.userdoctor.ui.common.utils.URLs;
import com.userdoctor.ui.common.utils.VolleySingleton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.userdoctor.ui.common.activity.Home_Activity.ActivityHome.Lattitude;
import static com.userdoctor.ui.common.activity.Home_Activity.ActivityHome.Longitude;

public class ActivityDrDetail extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout ll_personalInfo, ll_ratting, contact_us;
    private ImageView iv_back;
    private Button tv_bookAppointMent;
    String Intent_,hospital_address, id, distance, profile_img, dr_id, abut_self, address, contact_no, degree_name, time, cat_name, dr_name, rating, hospital_name;
    TextView mTextFee,mTextName, mTextSpeciallist, mTextAddress, mTextDistance, mTextRating, mTexthospital_name;
    RatingBar ratingBar;
    CircleImageView userImage;
    Button call;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dr_detail);

        initView();
        clickListner();
        if (getIntent()!=null)
        {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                dr_id = extras.getString("id");
                Intent_=extras.getString("INTENT");
                if (Intent_.equals("CLINIK"))
                {
                    mFunUserDetailApiCall();
                }

                if (Intent_.equals("HOME"))
                {
                    mFunUserDetailApiCall2();
                }


            }


        }




       // mFunUserDetailApiCall();

    }


    public void mFunUserDetailApiCall() {

        DialogUtil.show(ActivityDrDetail.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_URL + "view_doctor_profile",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        DialogUtil.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);

                            System.out.println(" dr detail check api " + obj);

                            boolean responsedata = obj.getBoolean("result");
                            JSONObject data = obj.getJSONObject("data");


                            id = data.getString("id");
                            String category_id = data.getString("category_id");
                            profile_img = data.getString("profile_img");
                            dr_name = data.getString("dr_name");
                            cat_name = data.getString("cat_name");
                            hospital_name = data.getString("hospital_name");
                            hospital_address = data.getString("hospital_address");
                            distance = data.getString("distance");
                            rating = data.getString("avg_rating");


                            degree_name = data.getString("degree_name");
                            contact_no = data.getString("contact_no");
                            String dr_email = data.getString("dr_email");
                            String lat = data.getString("lat");
                            String lng = data.getString("lng");
                            address = data.getString("address");
                            //  String date=data.getString("date");
                            // time=data.getString("time");



                            String registration_proof = data.getString("registration_proof");
                            String qualification_proof = data.getString("qualification_proof");
                            String id_proof = data.getString("id_proof");
                            String clinic_fee = data.getString("clinic_fee");
                            String home_fee = data.getString("home_fee");
                            String emergency_fee = data.getString("emergency_fee");


                            String clinic_name = data.getString("clinic_name");
                            String clinic_address = data.getString("clinic_address");



                            mTextName.setText(clinic_name);
                            mTextSpeciallist.setText(clinic_address);
                            mTexthospital_name.setText(hospital_name);
                            mTextFee.setText(clinic_fee);
                            mTextDistance.setText(distance);





                            abut_self = data.getString("abut_self");
                            System.out.println("<><><>dr name " + dr_name);
                            System.out.println("<><><>dr name " + cat_name);

                            if (rating.equals("null")) {
                                mTextRating.setText("");
                                ratingBar.setRating(Float.parseFloat("0.0"));
                            } else {
                                mTextRating.setText(rating);
                                ratingBar.setRating(Float.parseFloat(rating));
                            }

                           /* mTextName.setText(dr_name);
                            mTextSpeciallist.setText(cat_name);
                            mTexthospital_name.setText(hospital_name);*/
                            Picasso.with(ActivityDrDetail.this).load(URLs.IMAGE_DOCTORLIST + profile_img).fit().centerCrop().placeholder(R.drawable.ic_home_doc).error(R.drawable.ic_home_doc).into(userImage);

                        } catch (JSONException e) {
                            System.out.println("<><><>" + e.getMessage().toString());
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ActivityDrDetail.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("dr_id", dr_id);
                params.put("lat", Lattitude);
                params.put("lng", Longitude);
                return params;
            }
        };

        VolleySingleton.getInstance(ActivityDrDetail.this).addToRequestQueue(stringRequest);
    }




    public void mFunUserDetailApiCall2() {

        DialogUtil.show(ActivityDrDetail.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_URL + "view_doctor_profile",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        DialogUtil.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);

                            System.out.println(" dr detail check api " + obj);

                            boolean responsedata = obj.getBoolean("result");
                            JSONObject data = obj.getJSONObject("data");


                            id = data.getString("id");
                            String category_id = data.getString("category_id");
                            profile_img = data.getString("profile_img");
                            dr_name = data.getString("dr_name");
                            cat_name = data.getString("cat_name");
                            hospital_name = data.getString("hospital_name");
                            hospital_address = data.getString("hospital_address");
                            distance = data.getString("distance");
                            rating = data.getString("avg_rating");


                            degree_name = data.getString("degree_name");
                            contact_no = data.getString("contact_no");
                            String dr_email = data.getString("dr_email");
                            String lat = data.getString("lat");
                            String lng = data.getString("lng");
                            address = data.getString("address");
                            //  String date=data.getString("date");
                            // time=data.getString("time");



                            String registration_proof = data.getString("registration_proof");
                            String qualification_proof = data.getString("qualification_proof");
                            String id_proof = data.getString("id_proof");
                            String clinic_fee = data.getString("clinic_fee");
                            String home_fee = data.getString("home_fee");
                            String emergency_fee = data.getString("emergency_fee");


                            String clinic_name = data.getString("clinic_name");
                            String clinic_address = data.getString("clinic_address");



                            mTextName.setText(dr_name);
                            mTextSpeciallist.setText(cat_name);

                            mTexthospital_name.setText(hospital_name);
                            mTextFee.setText("Home Fee :"+home_fee);
                            mTextDistance.setText(distance);





                            abut_self = data.getString("abut_self");
                            System.out.println("<><><>dr name " + hospital_name);
                            System.out.println("<><><>dr name " + cat_name);

                            if (rating.equals("null")) {
                                mTextRating.setText("");
                                ratingBar.setRating(Float.parseFloat("0.0"));
                            } else {
                                mTextRating.setText(rating);
                                ratingBar.setRating(Float.parseFloat(rating));
                            }

                           /* mTextName.setText(dr_name);
                            mTextSpeciallist.setText(cat_name);
                            mTexthospital_name.setText(hospital_name);*/
                            Picasso.with(ActivityDrDetail.this).load(URLs.IMAGE_DOCTORLIST + profile_img).fit().centerCrop().placeholder(R.drawable.ic_home_doc).error(R.drawable.ic_home_doc).into(userImage);

                        } catch (JSONException e) {
                            System.out.println("<><><>" + e.getMessage().toString());
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ActivityDrDetail.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("dr_id", dr_id);
                params.put("lat", Lattitude);
                params.put("lng", Longitude);
                return params;
            }
        };

        VolleySingleton.getInstance(ActivityDrDetail.this).addToRequestQueue(stringRequest);
    }





    private void initView() {
        call=findViewById(R.id.call);
        contact_us = findViewById(R.id.contact_us);
        mTextName = findViewById(R.id.mTextName);
        mTextSpeciallist = findViewById(R.id.mTextSpecialist);
        // mTextAddress=findViewById(R.id.mTextAddress);
        mTexthospital_name = findViewById(R.id.hospital_name);
        mTextDistance = findViewById(R.id.mTextDistance);
        mTextRating = findViewById(R.id.mTextRating);
        ratingBar = findViewById(R.id.ratingBar);
        userImage = findViewById(R.id.img_profile);
        iv_back = findViewById(R.id.iv_back);
        ll_personalInfo = findViewById(R.id.ll_personalInfo);
        ll_ratting = findViewById(R.id.ll_ratting);
        tv_bookAppointMent = findViewById(R.id.tv_bookAppointMent);
        mTextFee=findViewById(R.id.mTextFee);
    }

    private void clickListner() {
        iv_back.setOnClickListener(this);
        ll_personalInfo.setOnClickListener(this);
        ll_ratting.setOnClickListener(this);
        tv_bookAppointMent.setOnClickListener(this);
        call.setOnClickListener(this::onClick);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_personalInfo:
                intent = new Intent(ActivityDrDetail.this, ActivityPersonalInfo.class);
                intent.putExtra("contact_no", contact_no);
                intent.putExtra("hospital_address", hospital_address);
                intent.putExtra("degree_name", degree_name);
                startActivity(intent);
                break;

            case R.id.ll_ratting:
                intent = new Intent(ActivityDrDetail.this, ActivityRattingReview.class);
                intent.putExtra("id", id);
                startActivity(intent);
                break;

            case R.id.tv_bookAppointMent:
                intent = new Intent(ActivityDrDetail.this, ActivityBookNow_drTopSpec.class);
                intent.putExtra("dr_name", dr_name);
                intent.putExtra("specialist", cat_name);
                intent.putExtra("dr_id", dr_id);
                intent.putExtra("profile_img", profile_img);
                startActivity(intent);
                break;


            case R.id.iv_back:
                onBackPressed();
                break;



            case R.id.call:
                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                 call_action(); }
                break;

        }
    }




    @RequiresApi(api = Build.VERSION_CODES.M)
    private void call_action() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);

        callIntent.setData(Uri.parse("tel:" + contact_no));
        if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        startActivity(callIntent);

    }




    public  boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }
    }








}
