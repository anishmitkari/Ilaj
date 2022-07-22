package com.userdoctor.ui.common.activity.First_Section_EmerGency_Ambulance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.userdoctor.R;
import com.userdoctor.ui.common.activity.Home_Activity.ActivityHome;
import com.userdoctor.ui.common.gps.GPSTracker;
import com.userdoctor.ui.common.model.AmbulanceModel;
import com.userdoctor.ui.common.utils.DialogUtil;
import com.userdoctor.ui.common.utils.NetworkUtil;
import com.userdoctor.ui.common.utils.URLs;
import com.userdoctor.ui.common.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActivityEmrMap extends AppCompatActivity {
ImageView iv_back;

    HashMap<Integer, AmbulanceModel> h1 = new HashMap<>();
    EditText current_location, destination_location;
    Button book_now;
    GoogleMap googleMap;
    private GPSTracker tracker;
    private Double lat;
    private Double lng;
    String lattitude, longitude;
    AmbulanceModel ambulanceModel;
    ArrayList<AmbulanceModel> arrayList;
    int user_id;
    int[] Stored_Ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emr_map);
        iv_back=findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });



        tracker = new GPSTracker(this);
        if (tracker.canGetLocation() == true) {
            lat = tracker.getLatitude();
            lng = tracker.getLongitude();
            Log.e("current_lat ", " " + String.valueOf(lat));
            Log.e("current_Lon ", " " + String.valueOf(lng));
            //getAddress(lat,lng);
        } else if (tracker.canGetLocation() == false) {
            tracker.showSettingsAlert();
        }
        lattitude = String.valueOf(lat);
        longitude = String.valueOf(lng);
        arrayList = new ArrayList<AmbulanceModel>();


        initView();


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



    private void initView() {
        book_now = findViewById(R.id.book_now);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        }
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                mMap.clear(); //clear old markers
                CameraPosition googlePlex = CameraPosition.builder()
                        .target(new LatLng(lat, lng))
                        .zoom(10)
                        .bearing(0)
                        .tilt(55)
                        .build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 10000, null);
                NetworkUtil.isNetworkConnected(ActivityEmrMap.this);
                apicall();
                book_now.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LayoutInflater inflater = LayoutInflater.from(ActivityEmrMap.this);
                        final Dialog dialog1 = new Dialog(ActivityEmrMap.this, R.style.bottom_Dialog);
                        Window window = dialog1.getWindow();
                        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        WindowManager.LayoutParams wlp = window.getAttributes();
                        wlp.gravity = Gravity.BOTTOM; // Here you can set window top or bottom
                        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
                        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                        window.setAttributes(wlp);
                        View view1 = inflater.inflate(R.layout.dialoge_confirm_now, null);
                        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog1.setContentView(view1);
                        dialog1.setCanceledOnTouchOutside(true);
                        dialog1.getWindow().setGravity(Gravity.BOTTOM);
                        dialog1.setCancelable(true);
                        TextView mTextCunsultingFee = view1.findViewById(R.id.mTextCunsultingFee);
                        Button confirm_now = view1.findViewById(R.id.confirm_now);

                        confirm_now.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(ActivityEmrMap.this, ActivityHome.class));
                            }

                        });


                        dialog1.show();


                    }

                });

            }
        });
    }
    public void apicall() {

        DialogUtil.show(ActivityEmrMap.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_URL + "emergency_doctors_list",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        DialogUtil.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            boolean responsedata = obj.getBoolean("result");

                            if (responsedata == true) {
                                final JSONArray jsonArray = obj.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    user_id = jsonObject.getInt("id");

                                    String mStrAmbulanceOwner = jsonObject.getString("amb_owner");
                                    double mStrLattitude = jsonObject.getDouble("lat");
                                    double mStrLongitude = jsonObject.getDouble("lng");
                                    String mStrContactNo = jsonObject.getString("amb_contact_no");
                                    String mStrOwnerName = jsonObject.getString("amb_owner");
                                    String mStrOwnerAddress = jsonObject.getString("address");

                                    createMarker(mStrLattitude, mStrLongitude, "Owner Name: " + mStrAmbulanceOwner, "Contact No.: " + mStrContactNo, R.drawable.ambulance_location3);


                                    if (jsonObject != null) {
//
                                        ambulanceModel = new AmbulanceModel(
                                                jsonObject.getString("id"),
                                                jsonObject.getString("amb_no"),
                                                jsonObject.getString("email"),
                                                jsonObject.getString("amb_owner"),
                                                jsonObject.getString("amb_contact_no"),
                                                jsonObject.getString("driving_license"),
                                                jsonObject.getString("national_id"),
                                                jsonObject.getString("emrgncy_availability"),
                                               // jsonObject.getString("amb_img"),
                                                jsonObject.getString("experience"),
                                                jsonObject.getString("address"),
                                                jsonObject.getDouble("lat"),
                                                jsonObject.getDouble("lng"),
                                                jsonObject.getString("status"),
                                                jsonObject.getString("distance"));

                                        // ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String,String>>();


                                        h1.put(user_id, ambulanceModel);
                                        arrayList.add(ambulanceModel);


                                    } else {
                                        Toast.makeText(ActivityEmrMap.this, "List have no data", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                Stored_Ids = new int[arrayList.size()];

                                Log.e("check size", String.valueOf(Stored_Ids.length));
                                //   recycler_view.setAdapter(adapter);
                            } else {
                                Toast.makeText(ActivityEmrMap.this, "List have no data", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            System.out.println("<><><>" + e.getMessage().toString());
                            e.printStackTrace();
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
                params.put("lat", lattitude);
                params.put("lng", longitude);
                return params;
            }
        };

        VolleySingleton.getInstance(ActivityEmrMap.this).addToRequestQueue(stringRequest);


    }
    protected Marker createMarker(double latitude, double longitude, String title, String snippet, int iconResID) {
        return googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(snippet)
                .icon(BitmapDescriptorFactory.fromResource(iconResID)));
    }













}
