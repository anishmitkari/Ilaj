package com.userdoctor.ui.common.activity.TestBookings;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
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
import com.bumptech.glide.Glide;
import com.userdoctor.R;
import com.userdoctor.ui.common.activity.Home_Activity.ActivityHome;
import com.userdoctor.ui.common.adapter.TimeAdapterUpcoming;
import com.userdoctor.ui.common.gps.GPSTracker;
import com.userdoctor.ui.common.model.UpComing_Detail.Time_Date;
import com.userdoctor.ui.common.utils.SharedPrefManager;
import com.userdoctor.ui.common.utils.VolleySingleton;
import com.rxandroidnetworking.RxAndroidNetworking;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.userdoctor.ui.common.Interface.Api.Imageurl;
import static com.userdoctor.ui.common.utils.PathUtils.bitmapToFile;
import static com.userdoctor.ui.common.utils.URLs.Upload_reciept;

public class Upcomiing_Detail_Page extends AppCompatActivity implements OnClickListener {
    TextView tv_name, dr_id, date, et_address, Appointment_Type, call, appointment_time;
    Button status, complete, upload_reciept;
    Context context;
    String booking_id, mStrmobile, mSTrstatus, id,nurse_id;
    double mStrlat, mStrlng;
    TextView tv_date1, view_map, tv_date;
    RecyclerView recycler_view;
    ImageView img_profile;
    ProgressDialog mProgressDialog;
    int user_id;
    GPSTracker gps;
    public static Double lat, lng;
    SharedPrefManager sharedPrefManager;
    TimeAdapterUpcoming timeAdapterUpcoming;
    private List<Time_Date> list_data;
    File imgFile1;
    LinearLayout Complete_Layout;

    private static final String JSON_DETAILS = "http://logicaltest.in/Urdoctors/NurseApi/user_booking_detail";
    private static final String JSON_COMPLETE = "http://logicaltest.in/Urdoctors/UsersApi/user_complete_booking";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upcoming_detail_page);

        initview();

        sharedPrefManager = new SharedPrefManager(this);
        if (sharedPrefManager != null) {
            user_id = sharedPrefManager.getUser().getId();
        }

        id = String.valueOf(user_id);

        isPermissionGranted();
        gps = new GPSTracker(this);

        if (gps.canGetLocation() == true) {
            lat = gps.getLatitude();
            lng = gps.getLongitude();
            Log.e("current_lat ", " " + String.valueOf(lat));
            Log.e("current_Lon ", " " + String.valueOf(lng));
            //getAddress(lat,lng);
        } else if (gps.canGetLocation() == false) {
            gps.showSettingsAlert();
        }

        if (getIntent() != null) {
            Intent intent = getIntent();
            booking_id = intent.getStringExtra("booking_id");
            nurse_id=intent.getStringExtra("nurse_id");
        }
        Details();

    }

    public boolean isPermissionGranted() {

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission is granted");
                return true;
            } else {

                Log.v("TAG", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG", "Permission is granted");
            return true;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //  Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                    call_action();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }


    public void initview() {
        context = Upcomiing_Detail_Page.this;
        Appointment_Type=findViewById(R.id.Appointment_Type);
        tv_name = findViewById(R.id.tv_name);
        dr_id = findViewById(R.id.patient_id);
        appointment_time = findViewById(R.id.appointment_time);
        tv_date = findViewById(R.id.appointment_date);
        date = findViewById(R.id.date);
        tv_date1 = findViewById(R.id.tv_date);
        // service_type=findViewById(R.id.service_type);
        complete = findViewById(R.id.complete);
        // call = findViewById(R.id.call);
        et_address = findViewById(R.id.et_address);
        view_map = findViewById(R.id.view_map);
        recycler_view = findViewById(R.id.recycler_view);
        img_profile = findViewById(R.id.img_profile);
        status = findViewById(R.id.status);
        list_data = new ArrayList<>();
        upload_reciept = findViewById(R.id.upload_receipt);
        Complete_Layout=findViewById(R.id.Complete_Layout);

        upload_reciept.setOnClickListener(this);
        complete.setOnClickListener(this);
        view_map.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.view_map:
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" + lat + "," + lng + "&daddr=" + mStrlat + "," + mStrlng));
                startActivity(intent);
                break;

            case R.id.upload_receipt:
                selectImage1();
                break;

            case R.id.complete:
                ratingDialoge();
                break;

        }


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void call_action() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);

        callIntent.setData(Uri.parse("tel:" + mStrmobile));
        if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        startActivity(callIntent);
    }


    public void UPLOAD_RECIEPT(final String User_Id, final String nurse_id, final String user_booking_id) {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
        RxAndroidNetworking.upload(Upload_reciept)
                .addMultipartParameter("user_id", User_Id)
                .addMultipartParameter("user_booking_id", user_booking_id)
                .addMultipartFile("recipt", imgFile1)
                .build()
                .getJSONObjectObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {
                        // do anything onComplete
                        mProgressDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // handle error
                        mProgressDialog.dismiss();
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("rx_error", e.getMessage());
                    }


                    @Override
                    public void onNext(JSONObject response) {
                        //do anything with response
                        try {
                            mProgressDialog.dismiss();
                            Log.e("response check ", response.toString());
                            String res = response.getString("result");
                            String msg = response.getString("msg");
                            {
                                if (res.equals("true")) {
                                    Toast.makeText(context, "Submitted Data Successfully", Toast.LENGTH_SHORT).show();

                                } else {
                                    System.out.println("check msg " + msg);
                                    Toast.makeText(context, " " + msg, Toast.LENGTH_SHORT).show();
                                }

                            }

                        } catch (Exception e) {
                            Toast.makeText(context, "error " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();

                        }

                    }
                });


    }


    private void Details() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_DETAILS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar.setVisibility(View.GONE);
                        Log.e("check response ", response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String trueStr = jsonObject.getString("result");

                            Log.e("id is inside", booking_id);
                            System.out.println("check " + trueStr);

                            System.out.println("check " + jsonObject);

                            if (trueStr.equals("true")) {

                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                                String user_booking_id = jsonObject1.getString("user_booking_id");
                                String user_id = jsonObject1.getString("user_id");
                                String nurse_id = jsonObject1.getString("nurse_id");
                                String Strpatient_id = jsonObject1.getString("patient_id");
                                String ns_name = jsonObject1.getString("ns_name");
                                String patient_name = jsonObject1.getString("patient_name");
                                String Strinvestigation = jsonObject1.getString("investigation");
                                String patient_age = jsonObject1.getString("patient_age");
                                mStrmobile = jsonObject1.getString("mobile");
                                String email = jsonObject1.getString("email");
                                String address = jsonObject1.getString("address");
                                String landmark = jsonObject1.getString("landmark");
                                String from_date = jsonObject1.getString("from_date");
                                String to_date = jsonObject1.getString("to_date");
                                String patient_gender = jsonObject1.getString("patient_gender");
                                mSTrstatus = jsonObject1.getString("status");
                                String home_fee = jsonObject1.getString("home_fee");
                                String date_time = jsonObject1.getString("date");
                                String mStrstatus = jsonObject1.getString("status");

                                String mStrType = jsonObject1.getString("type");


                                Appointment_Type.setText(mStrType);

                                mStrlat = jsonObject1.getDouble("lat");
                                mStrlng = jsonObject1.getDouble("lng");
                                if (mStrstatus.equals("Accepted")) {
                                    complete.setVisibility(View.GONE);
                                }

                                if (mStrstatus.equals("Pending"))
                                {

                                    upload_reciept.setVisibility(View.GONE);
                                }
                                status.setText(mStrstatus);

                                System.out.println("double data " + mStrlat + " " + mStrlng);
                                System.out.println("mSTrstatus.... " + mSTrstatus);

                                JSONArray jsonArray = jsonObject1.getJSONArray("times");


                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject ob = jsonArray.getJSONObject(i);
                                    String from_time = ob.getString("from_time");
                                    String to_time = ob.getString("to_time");
                                    String sentinel = ob.getString("sentinel");
                                    Time_Date time_date = new Time_Date(from_time, to_time, sentinel);
                                    list_data.add(time_date);
                                }

                                recycler_view.setLayoutManager(new LinearLayoutManager(context));
                                timeAdapterUpcoming = new TimeAdapterUpcoming(list_data);
                                recycler_view.setAdapter(timeAdapterUpcoming);

                                tv_date1.setText(date_time);
                                if(!ns_name.equals("null"))
                                {
                                    tv_name.setText(ns_name);
                                }

                                et_address.setText(address);
                                dr_id.setText(nurse_id);

                                if (to_date != null&&!to_date.equals("00/00/0000")) {
                                    tv_date.setText(from_date + " to " + to_date);
                                } else {
                                    tv_date.setText(from_date);
                                }



                                if (mSTrstatus.equals("Confirmed")) {
                                    Complete_Layout.setVisibility(View.VISIBLE);
                                }


                                et_address.setText(address);

                                Glide.with(context)
                                        .load(Imageurl + "f-user.png")
                                        .override(300, 200)
                                        .into(img_profile);


                            }


                        } catch (Exception e) {


                            System.out.println("check catch" + e.toString());
                            // Toast.makeText(Apointment_NurseDetails_Activity.this, " " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("check fail " + error.toString());
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_booking_id", booking_id);

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


    }


    public void ratingDialoge() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.ratingdialoge);
        dialog.setTitle("Please Give Rating ...");
       // dialog.setCancelable(false);
       // dialog.setCanceledOnTouchOutside(false);


        View v = getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);

        // set the custom dialog components - text, image and button

        RatingBar ratingBar = (RatingBar) dialog.findViewById(R.id.ratingBar);
        EditText reviews = (EditText) dialog.findViewById(R.id.reviews);


        Button dialogButton = (Button) dialog.findViewById(R.id.submit);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String rating = String.valueOf(ratingBar.getRating());
                String mStrReview = reviews.getText().toString();
                // dialog.dismiss();
                if (rating.equals("0.0")) {
                    Toast.makeText(Upcomiing_Detail_Page.this, "Please give atleast rating", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    CompleteApiCall(rating, mStrReview);
                    dialog.dismiss();
                }


            }
        });

        dialog.show();
    }


    public void CompleteApiCall(final String rating, final String mStrReview) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_COMPLETE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar.setVisibility(View.GONE);
                        Log.e("check response ", response);

                        try {
                            //converting response to json object

                            JSONObject jsonObject = new JSONObject(response);
                            String trueStr = jsonObject.getString("result");

                            if (trueStr.equals("true")) {

                                String status = jsonObject.getString("msg");
                                startActivity(new Intent(context, ActivityHome.class));
                                Toast.makeText(Upcomiing_Detail_Page.this, "Thank For Your Valuable Feedback and Your Appointment is complete Now", Toast.LENGTH_SHORT).show();


                            }


                        } catch (Exception e) {


                            System.out.println("check catch" + e.toString());
                            // Toast.makeText(Apointment_NurseDetails_Activity.this, " " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("check fail " + error.toString());
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("user_id", id);
                params.put("user_booking_id", booking_id);
                params.put("rating", rating);
                params.put("reviews", mStrReview);

                // params.put("date_time",formattedDate);

                return params;
            }
        };


        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


    }

    private void selectImage1() {
        final PickImageDialog dialog = PickImageDialog.build(new PickSetup());
        dialog.setOnPickCancel(new IPickCancel() {
            @Override
            public void onCancelClick() {
                dialog.dismiss();
            }
        }).setOnPickResult(new IPickResult() {
            @Override
            public void onPickResult(PickResult r) {

                if (r.getError() == null) {
                    imgFile1 = bitmapToFile(context, r.getBitmap());
                    String filename = imgFile1.getName();
                    System.out.println("check id is "+id+" "+nurse_id+" "+booking_id);
                    UPLOAD_RECIEPT(id,nurse_id,booking_id);

                } else {
                     Toast.makeText(context, r.getError().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

        }).show((FragmentActivity) context);
    }

}




