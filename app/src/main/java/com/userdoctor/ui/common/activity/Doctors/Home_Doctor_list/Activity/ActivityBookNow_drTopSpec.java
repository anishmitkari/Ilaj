package com.userdoctor.ui.common.activity.Doctors.Home_Doctor_list.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.userdoctor.R;
import com.userdoctor.ui.common.Session.Session;
import com.userdoctor.ui.common.activity.Doctors.Home_Doctor_list.Adapter.BookNow_DrTopSpecialAdapter;
import com.userdoctor.ui.common.activity.Doctors.Home_Doctor_list.Adapter.ScheduyleTimeDataAdapter;
import com.userdoctor.ui.common.activity.Doctors.Home_Doctor_list.Model.GetAvailableTimeData;
import com.userdoctor.ui.common.activity.Doctors.Home_Doctor_list.Model.ScheduleTimeData;
import com.userdoctor.ui.common.utils.NetworkUtil;
import com.userdoctor.ui.common.utils.URLs;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.userdoctor.ui.common.utils.URLs.GetAvailableList;
import static com.userdoctor.ui.common.utils.URLs.GetTimeArray;

public class ActivityBookNow_drTopSpec extends AppCompatActivity implements ScheduyleTimeDataAdapter.OnShareClickedListener{

    RecyclerView recyclerView,recycleview_schedule;
    LinearLayout current_date;
    TextView date,dr_name,specialist;
    String DR_Name,Specialist,DR_id,DR_Image;
    Context context;
    String day_id;
    ImageView dr_image;
    CardView view_profile;
    ScheduyleTimeDataAdapter scheduyleTimeDataAdapter;
    List<ScheduleTimeData> scheduleTimeDataList=new ArrayList<>();
    ImageView iv_back;
Session session;

    BookNow_DrTopSpecialAdapter bookNow_drTopSpecialAdapter;
    List<GetAvailableTimeData> getAvailableTimeDataArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_now_dr_top_spec);
        session=new Session(getApplicationContext());
        initView();
       if(getIntent()!=null)
       {
           DR_Name = getIntent().getStringExtra("dr_name");
           Specialist=getIntent().getStringExtra("specialist");
           DR_id=getIntent().getStringExtra("dr_id");
           DR_Image=getIntent().getStringExtra("profile_img");
       }


        Picasso.with(ActivityBookNow_drTopSpec.this).load(URLs.IMAGE_DOCTORLIST + DR_Image).fit().centerCrop().placeholder(R.drawable.ic_home_doc).error(R.drawable.ic_home_doc).into(dr_image);

        view_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });




        System.out.println("check specialist "+DR_id);
        dr_name.setText(DR_Name);
        specialist.setText(Specialist);
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String Today_Date = df.format(c);

        NetworkUtil.isNetworkConnected(ActivityBookNow_drTopSpec.this);
        getShowingSchedule(DR_id);


        System.out.println("check day id "+ day_id);

    }





    public void getShowingSchedule(final String DR_id) {

        final ProgressDialog progressDialog = new ProgressDialog(context, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GetAvailableList,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            progressDialog.dismiss();

                            JSONObject obj = new JSONObject(response);
                            String result = obj.getString("result");
                            String msg = obj.getString("msg");
                            System.out.println("response===ppppp " + response);
                            if (result.equals("true")) {


                                JSONArray jsonArray = obj.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    ScheduleTimeData scheduleTimeData =new ScheduleTimeData(
                                            jsonObject.getString("id"),
                                            jsonObject.getString("dr_id"),
                                            jsonObject.getString("day_id"),
                                            jsonObject.getString("day"),
                                            jsonObject.getString("day_shedule")
                                    );


                                    scheduleTimeDataList.add(scheduleTimeData);
                                }
                            }
                            else {
                                Toast.makeText(context, " " + msg, Toast.LENGTH_SHORT).show();
                                System.out.println("false" + msg);
                            }

                            scheduyleTimeDataAdapter = new ScheduyleTimeDataAdapter(scheduleTimeDataList, ActivityBookNow_drTopSpec.this);
                             LinearLayoutManager manager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
                           // recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
                            recycleview_schedule.setLayoutManager(manager);
                            scheduyleTimeDataAdapter.setOnShareClickedListener(ActivityBookNow_drTopSpec.this);
                            recycleview_schedule.setAdapter(scheduyleTimeDataAdapter);

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
                params.put("dr_id", DR_id);
                return params;

            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);


    }






    public void initView()
    {
        iv_back=findViewById(R.id.iv_back);
        view_profile=findViewById(R.id.view_profile);
        dr_image=findViewById(R.id.dr_image);
        context=ActivityBookNow_drTopSpec.this;
        dr_name=findViewById(R.id.dr_name);
        specialist=findViewById(R.id.mTextSpecialist);
        current_date=findViewById(R.id.current_date);
        date=findViewById(R.id.date);
        recyclerView=findViewById(R.id.recycler_view);
        recycleview_schedule=findViewById(R.id.recycleview_schedule);

    }


    @Override
    public void ShareClicked(String data) {
         this.day_id=data;
         getTimeApiCall(DR_id,data);
    }


    public void getTimeApiCall(final String dr_id,final String data)
    {
        final ProgressDialog progressDialog = new ProgressDialog(context, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GetTimeArray,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            getAvailableTimeDataArrayList.clear();
                            progressDialog.dismiss();

                            JSONObject obj = new JSONObject(response);
                            String result = obj.getString("result");
                            String msg = obj.getString("msg");
                            String mStrdate = obj.getString("date");


                            if (result.equals("true")) {
                                date.setText(mStrdate);
                                System.out.println("DATE -------      "+mStrdate);
                                session.setBookingDate(mStrdate);
                                System.out.println("response " + response);
                                JSONArray jsonArray = obj.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    GetAvailableTimeData availableTimeData =new GetAvailableTimeData(
                                            jsonObject.getString("avail_time_id"),
                                            jsonObject.getString("day_id"),
                                            jsonObject.getString("day"),
                                            jsonObject.getString("avail_start_tym"),
                                            jsonObject.getString("avail_end_tym")
                                    );


                                    getAvailableTimeDataArrayList.add(availableTimeData);
                                }
                            }
                            else {
                                Toast.makeText(context, " " + msg, Toast.LENGTH_SHORT).show();
                                System.out.println("false" + msg);
                            }

                            bookNow_drTopSpecialAdapter = new BookNow_DrTopSpecialAdapter(getAvailableTimeDataArrayList, ActivityBookNow_drTopSpec.this,dr_id,DR_Name,Specialist,DR_Image);
                             recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
                            recyclerView.setAdapter(bookNow_drTopSpecialAdapter);

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
                params.put("day_id", data);
                return params;

            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);








    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
