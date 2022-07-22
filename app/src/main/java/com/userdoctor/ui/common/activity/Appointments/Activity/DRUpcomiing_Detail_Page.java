package com.userdoctor.ui.common.activity.Appointments.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
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

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.userdoctor.ui.common.utils.PathUtils.bitmapToFile;
import static com.userdoctor.ui.common.utils.URLs.DRUpload_reciept;

public class DRUpcomiing_Detail_Page extends AppCompatActivity implements View.OnClickListener {

    TextView appointment_time,tv_name, mTextdr_id, date, et_address, Appointment_Type, call;
    Button status, complete, upload_reciept;
    Context context;
    String booking_id, mStrmobile, mSTrstatus, id,nurse_id;
    double mStrlat, mStrlng;
    TextView appointment_date, view_map, tv_date;
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
    ImageView iv_back;
    private static final String JSON_DETAILS = "http://logicaltest.in/Urdoctors/DoctorApi/user_appointment_detail";
    private static final String JSON_COMPLETE = "http://logicaltest.in/Urdoctors/UsersApi/user_complete_doctor_booking";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drupcomiing__detail__page);

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
        }

        Details();

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

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

                    call_action();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }


    public void initview() {
        context = DRUpcomiing_Detail_Page.this;
        tv_name = findViewById(R.id.tv_name);
        Complete_Layout=findViewById(R.id.Complete_Layout);
        mTextdr_id = findViewById(R.id.patient_id);
        appointment_time = findViewById(R.id.appointment_time);
        tv_date = findViewById(R.id.tv_date);
        appointment_date = findViewById(R.id.appointment_date);
        iv_back = findViewById(R.id.iv_back);

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


        upload_reciept.setOnClickListener(this);
        complete.setOnClickListener(this);
        view_map.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.view_map:
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=" + lat + "," + lng + "&daddr=" + mStrlat + "," + mStrlng));
                startActivity(intent);
                break;
            case R.id.upload_receipt:
                selectImage1(booking_id);
                break;

            case R.id.complete:
                ratingDialoge();
                break;


            case R.id.iv_back:
                onBackPressed();
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




    private void Details() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_DETAILS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar.setVisibility(View.GONE);
                        Log.e("dr upcoming response ", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String trueStr = jsonObject.getString("result");
                            Log.e("id is inside", booking_id);
                            System.out.println("check " + trueStr);
                            System.out.println("check response  " + jsonObject);
                            if (trueStr.equals("true")) {
                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                 booking_id = jsonObject1.getString("booking_id");

                                String booking_id = jsonObject1.getString("booking_id");
                                String user_id=jsonObject1.getString("user_id");
                                String appoint_ID = jsonObject1.getString("appoint_ID");
                                String dr_id = jsonObject1.getString("dr_id");
                                String dr_name = jsonObject1.getString("dr_name");
                                String profile_img = jsonObject1.getString("profile_img");
                                String contact_no = jsonObject1.getString("contact_no");
                                String gender = jsonObject1.getString("gender");
                                String home_availability = jsonObject1.getString("home_availability");
                                 mStrlat = jsonObject1.getDouble("lat");
                                 mStrlng = jsonObject1.getDouble("lng");
                                String home_fee = jsonObject1.getString("home_fee");
                                String mstrdate = jsonObject1.getString("date");
                                String avail_start_tym=jsonObject1.getString("avail_start_tym");
                                String avail_end_tym=jsonObject1.getString("avail_end_tym");
                                mSTrstatus = jsonObject1.getString("status");
                                String recipt = jsonObject1.getString("recipt");
                                String image = jsonObject1.getString("image");

                                appointment_time.setText(avail_start_tym+" - "+avail_end_tym);

                                tv_date.setText(mstrdate);
                                appointment_date.setText(mstrdate);

                                if (mSTrstatus.equals("Confirmed")) {
                                    Complete_Layout.setVisibility(View.VISIBLE);
                                }

                                status.setText(mSTrstatus);
                                tv_name.setText(dr_name);
                                mTextdr_id.setText(dr_id);





                            }


                        } catch (Exception e) {
                            System.out.println("check catch" + e.toString());
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
                params.put("booking_id", booking_id);
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
      //  dialog.setCanceledOnTouchOutside(false);


        View v = getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);
        RatingBar ratingBar = (RatingBar) dialog.findViewById(R.id.ratingBar);
        EditText reviews = (EditText) dialog.findViewById(R.id.reviews);


        Button dialogButton = (Button) dialog.findViewById(R.id.submit);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rating = String.valueOf(ratingBar.getRating());
                String mStrReview = reviews.getText().toString();
                // dialog.dismiss();
                if (!rating.equals("0.0") && !mStrReview.isEmpty()) {
                    System.out.println("response is  "+rating+" "+mStrReview+" "+user_id+" "+booking_id);
                    CompleteApiCall(rating, mStrReview);
                    dialog.dismiss();
                }
                else {

                    Toast.makeText(DRUpcomiing_Detail_Page.this, "Please give rating and review both", Toast.LENGTH_SHORT).show();



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
                            JSONObject jsonObject = new JSONObject(response);
                            String trueStr = jsonObject.getString("result");

                            if (trueStr.equals("true")) {

                                String status = jsonObject.getString("msg");
                                startActivity(new Intent(context, ActivityHome.class));
                                Toast.makeText(DRUpcomiing_Detail_Page.this, "Thank For Your Valuable Feedback and Your Appointment is complete Now", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(context, "false", Toast.LENGTH_SHORT).show();
                            }


                        } catch (Exception e) {
                            System.out.println("check catch" + e.toString());
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
                params.put("booking_id", booking_id);
                params.put("rating", rating);
                params.put("review", mStrReview);

                // params.put("date_time",formattedDate);

                return params;
            }
        };


        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


    }
    private void selectImage1(String userBookingId) {

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

                    System.out.println("check id is " + "recipt " + imgFile1 + " " + " " + id + " "  + " " + userBookingId);

                    UPLOAD_RECIEPT(id,  userBookingId);

                } else {

                    Toast.makeText(context, r.getError().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

        }).show((FragmentActivity) context);
    }
    public void UPLOAD_RECIEPT(final String User_Id, final String user_booking_id) {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
        RxAndroidNetworking.upload(DRUpload_reciept)
                .addMultipartParameter("user_id", User_Id)
                .addMultipartParameter("booking_id", user_booking_id)
                .addMultipartFile("recipt", imgFile1)
                .build()
                .getJSONObjectObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {
                        mProgressDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mProgressDialog.dismiss();
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("rx_error", e.getMessage());
                    }


                    @Override
                    public void onNext(JSONObject response) {

                        try {
                            mProgressDialog.dismiss();
                            Log.e("response check ", response.toString());
                            String res = response.getString("result");
                            String msg = response.getString("msg");
                            {
                                if (res.equals("true")) {
                                    Toast.makeText(context, "Submitted Data Successfully", Toast.LENGTH_SHORT).show();
                                    onBackPressed();
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
