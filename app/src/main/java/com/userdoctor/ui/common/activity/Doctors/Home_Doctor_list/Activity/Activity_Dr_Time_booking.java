package com.userdoctor.ui.common.activity.Doctors.Home_Doctor_list.Activity;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.userdoctor.R;
import com.userdoctor.ui.common.Session.Session;
import com.userdoctor.ui.common.utils.Constant;
import com.userdoctor.ui.common.utils.NetworkUtil;
import com.userdoctor.ui.common.utils.SharedPrefManager;
import com.userdoctor.ui.common.utils.URLs;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.userdoctor.ui.common.activity.Home_Activity.ActivityHome.Lattitude;
import static com.userdoctor.ui.common.activity.Home_Activity.ActivityHome.Longitude;
import static com.userdoctor.ui.common.utils.URLs.ConfirmBooking;


public class Activity_Dr_Time_booking extends AppCompatActivity {

    LinearLayout ll_confirm_done;
    Button btn_dr_spec_book_now;
    String time_id, start_time, end_time, dr_id, dr_name,DR_Image, dr_specialist;
    Context context;
    SharedPrefManager sharedPrefManager;
    EditText visit_time, name, address, mobile, age;
    int user_id;
    String user_image,
            user_email, user_location, user_contact, user_age, user_firstname;
    String date,DATE;
    RadioButton radio_btn_female, getRadio_btn_male;
    RadioGroup bookgrup;
    String id, Gender;
    TextView mTextdr_name, mTextspecialist, mTexttime,txtdate;
    ImageView iv_back,dr_image;

    private int CalendarHour, CalendarMinute;
    String format, mVisit_Time;
    Calendar calendar;
    TimePickerDialog timepickerdialog;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__dr__time_booking);
        session=new Session(getApplicationContext());
        date=session.getBookingDate();
        initView();
        System.out.println("date---     "+date);

        sharedPrefManager = new SharedPrefManager(this);
        if (sharedPrefManager != null) {
            user_id = sharedPrefManager.getUser().getId();
            user_firstname = sharedPrefManager.getUser().getFname();
            user_age = sharedPrefManager.getUser().getAge();
            user_contact = sharedPrefManager.getUser().getContact_no();
            user_location = sharedPrefManager.getUser().getLocation();
            user_email = sharedPrefManager.getUser().getEmail();
            user_image = sharedPrefManager.getUser().getImage();

            System.out.println("user_firstname   "+user_firstname+"  "+user_age+"  "+user_contact+"  "+user_location);
        }

        id = String.valueOf(user_id);


        if (getIntent() != null) {
            time_id = getIntent().getStringExtra("time_id");
            start_time = getIntent().getStringExtra("start_time");
            end_time = getIntent().getStringExtra("end_time");
            dr_id = getIntent().getStringExtra("dr_id");
            dr_name = getIntent().getStringExtra("dr_name");
            dr_specialist = getIntent().getStringExtra("dr_specialist");
            DR_Image=getIntent().getStringExtra("dr_image");


            System.out.println("time---   "+time_id+"  "+start_time+"  "+end_time );
        }
        Picasso.with(Activity_Dr_Time_booking.this).load(URLs.IMAGE_DOCTORLIST + DR_Image).fit().centerCrop().placeholder(R.drawable.ic_home_doc).error(R.drawable.ic_home_doc).into(dr_image);


        mTextdr_name.setText(dr_name);
        mTextspecialist.setText(dr_specialist);
        mTexttime.setText(start_time + " - " + end_time);



        date = date.replace("-","/");
        SimpleDateFormat input = new SimpleDateFormat("dd/MM/yy");
        SimpleDateFormat output = new SimpleDateFormat("yyyy/MM/dd");
        try {
           Date oneWayTripDate = input.parse(date);                 // parse input
              DATE=output.format(oneWayTripDate);
            System.out.println("<><><><><><><DATE     "+DATE);
         } catch (ParseException e) {
            e.printStackTrace();
        }

        bookgrup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if(checkedId == R.id.rb_myselfbook) {

                    name.setText(user_firstname);
                           mobile.setText(user_contact);
                } else if(checkedId == R.id.rb_bookfamily) {
                    name.getText().clear();
                    mobile.getText().clear();

                } else {

                }
            }

        });






        btn_dr_spec_book_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mStrName = name.getText().toString();
                String mStrmobile = mobile.getText().toString();
                String mStrAddress = address.getText().toString();
                String mStrAge = age.getText().toString();

                if (radio_btn_female.isChecked()) {
                    Gender = "Female";
                } else {
                    Gender = "Male";
                }

                if (!radio_btn_female.isChecked() && !getRadio_btn_male.isChecked()) {
                    Toast.makeText(context, "please select gender", Toast.LENGTH_SHORT).show();
                    return;
                }


                if ( !mStrName.isEmpty() && !mStrAddress.isEmpty() && !mStrmobile.isEmpty() && !mStrAge.isEmpty()  ) {
                    System.out.println("check paarams " + mStrAge + " time id " + Gender + " id " + id + " mstrmob " + mStrmobile + " email " + mStrAddress + " name " + mStrName + "   " + Lattitude + " " + Longitude);

                    SubmitApiCall(dr_id, time_id, id, mStrName, mStrmobile, mStrAddress, mStrAge, Gender,mStrAddress);

                } else {
                    System.out.println("check fields " + mStrName + " " + mStrAddress + " " + mStrmobile+" ");
                    Toast.makeText(context, "All fields are required ", Toast.LENGTH_SHORT).show();

                }


            }
        });


        NetworkUtil.isNetworkConnected(Activity_Dr_Time_booking.this);
        visit_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calendar = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);


                timepickerdialog = new TimePickerDialog(context,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                if (hourOfDay == 0) {
                                    hourOfDay += 12;
                                    format = "AM";
                                } else if (hourOfDay == 12) {
                                    format = "PM";
                                } else if (hourOfDay > 12) {
                                    hourOfDay -= 12;
                                    format = "PM";
                                } else {
                                    format = "AM";
                                }
                                mVisit_Time=hourOfDay + ":" + minute + format;

                          visit_time.setText(hourOfDay + ":" + minute + format);
                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();


            }
        });


    }

    private void initView() {
        dr_image=findViewById(R.id.dr_image);
        radio_btn_female = findViewById(R.id.female);
        getRadio_btn_male = findViewById(R.id.male);
        mTextdr_name = findViewById(R.id.dr_name);
        mTextspecialist = findViewById(R.id.specialist);
        age = findViewById(R.id.age);
        mTexttime = findViewById(R.id.time);
        txtdate = findViewById(R.id.txtdate);
        ll_confirm_done = findViewById(R.id.ll_confirm_done);
        btn_dr_spec_book_now = findViewById(R.id.btn_dr_spec_book_now);
        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        mobile = findViewById(R.id.mobile);
        iv_back = findViewById(R.id.iv_back);
        visit_time = findViewById(R.id.visit_time);
        bookgrup = findViewById(R.id.bookgrup);
        txtdate = findViewById(R.id.txtdate);
        context = Activity_Dr_Time_booking.this;

    }

    public void SubmitApiCall(String dr_id, String time_id, String id, String mStrName, String mStrmobile, String mStrEmail, String age, String gender, String mStrAddress) {
        final ProgressDialog progressDialog = new ProgressDialog(context, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfirmBooking,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressDialog.dismiss();
                            JSONObject obj = new JSONObject(response);
                            String result = obj.getString("result");
                            String msg = obj.getString("msg");
                            System.out.println("response " + response);
                            if (result.equals("true")) {
                                session.setAdBookingType("");
                                Intent intent = new Intent(Activity_Dr_Time_booking.this, Activity_Dr_book_confirm.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(context, " " + msg, Toast.LENGTH_SHORT).show();
                                System.out.println("false" + msg);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("dr_id", dr_id);
                params.put("avail_time_id", time_id);
                params.put("user_id", id);
                params.put("name", mStrName);
                params.put("email", mStrEmail);
                params.put("mobile", mStrmobile);
                params.put("gender", gender);
                params.put("age", age);
                params.put("date",DATE);
                params.put("lat", Lattitude);
                params.put("lng", Longitude);
                params.put("address", mStrAddress);
                params.put("visit_time","");
                params.put("type", session.getAdBookingType());
                System.out.println("===param==="+params);
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
