package com.userdoctor.ui.common.activity.Doctors.Clinik_List.Fragment;


import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.userdoctor.R;
import com.userdoctor.ui.common.activity.Doctors.Home_Doctor_list.Activity.ActivityDrDetail;
import com.userdoctor.ui.common.gps.GPSTracker;
import com.userdoctor.ui.common.model.ClinikListData;
import com.userdoctor.ui.common.utils.DialogUtil;
import com.userdoctor.ui.common.utils.NetworkUtil;
import com.userdoctor.ui.common.utils.URLs;
import com.userdoctor.ui.common.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.userdoctor.ui.common.activity.Home_Activity.ActivityHome.Lattitude;
import static com.userdoctor.ui.common.activity.Home_Activity.ActivityHome.Longitude;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentClinikMap extends Fragment implements OnMapReadyCallback, LocationListener,GoogleMap.OnMarkerClickListener{
    View view;
    Geocoder geocoder;
    private JSONArray result;
    private GoogleMap mMap;
    private GPSTracker tracker;
    double lat,lng;
    String id;
    String address;
    private List<ClinikListData> clinikList=new ArrayList<>();





    public FragmentClinikMap() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_fragment_clinik_map, container, false);


        tracker = new GPSTracker(getActivity());
        NetworkUtil.isNetworkConnected(getActivity());


        GPSTracker GTracker = new GPSTracker(getActivity());
        if (tracker.canGetLocation() == true) {
            lat = tracker.getLatitude();
            lng = tracker.getLongitude();
            Log.e("current_lat ", " " + String.valueOf(lat));
            Log.e("current_Lon ", " " + String.valueOf(lng));
            //getAddress(lat,lng);
        } else if (tracker.canGetLocation() == false) {
            tracker.showSettingsAlert();
        }



        List<Address> addresses = new ArrayList<>();
        geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(lat, lng, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
            System.out.println("address is "+address);
            // current_location.setText(address);
            // current_address.setText(address);
        } catch (IOException e) {
            e.printStackTrace();
        }

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            return;
        }
        mMap.setMyLocationEnabled(true);
        DialogUtil.show(getActivity());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_URL + "doctors_list_at_clinic",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        DialogUtil.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            boolean responsedata = obj.getBoolean("result");
                            System.out.println("call nearby doctor" + obj);
                            if (responsedata == true) {
                                JSONArray jsonArray = obj.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    System.out.println("<><><> dr home FragmentClinikMap  --   " + jsonObject);
                                    if (jsonObject != null) {

                                        ClinikListData hero = new ClinikListData(
                                                jsonObject.getString("id"),
                                                jsonObject.getString("category_id"),
                                                jsonObject.getString("dr_name"),
                                                jsonObject.getString("profile_img"),
                                                jsonObject.getString("contact_no"),
                                                jsonObject.getString("distance"),
                                                jsonObject.getString("clinic_name"),
                                                jsonObject.getString("clinic_address"),
                                                jsonObject.getString("avg_rating"),
                                                jsonObject.getString("lat"),
                                                jsonObject.getString("lng"),
                                                jsonObject.getString("type")


                                                );





                                        mMap.addMarker(new MarkerOptions()
                                                .position(new LatLng(Double.parseDouble(jsonObject.getString("lat")) , Double.parseDouble(jsonObject.getString("lng"))))
                                                // .title(Double.valueOf(Lattitude).toString() + "," + Double.valueOf(Longitude).toString())
                                                .title(jsonObject.getString("clinic_name"))
                                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))
                                        );
                                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng), 6.0f));




                                        clinikList.add(hero);
                                    } else {
                                        Toast.makeText(getActivity(), "List have no data", Toast.LENGTH_SHORT).show();
                                    }
                                }



                               // recycler_view.setAdapter(mAdapter);

                            } else {
                                Toast.makeText(getActivity(), "fragment have no data", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            System.out.println("<><><> catch exc" + e.getMessage().toString());
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("check error  ..."+error);
                    }
                })
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("lat", Lattitude);
                params.put("lng", Longitude);
                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);






        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                final Dialog dialog1 = new Dialog(getActivity(), R.style.bottom_Dialog);
                Window window = dialog1.getWindow();
                window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.BOTTOM; // Here you can set window top or bottom
                wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
                wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                window.setAttributes(wlp);
                View view1 = inflater.inflate(R.layout.map_dialog_dr_show, null);
                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog1.setContentView(view1);
                dialog1.setCanceledOnTouchOutside(true);
                dialog1.getWindow().setGravity(Gravity.BOTTOM);
                dialog1.setCancelable(true);
                TextView tv_name=view1.findViewById(R.id.tv_name);
                TextView  mTextAddress=view1.findViewById(R.id.mTextAddress);
                TextView mdistance=view1.findViewById(R.id.mTextDistance);
                RatingBar ratingBar=view1.findViewById(R.id.ratingBar);


                for (int i = 0; i<clinikList.size(); i++) {
                    if (marker.getTitle().equals(clinikList.get(i).getClinic_name())) {
                        String UserName = clinikList.get(i).getClinic_name();
                        String Clinikaddress=clinikList.get(i).getClinic_address();
                        String distance=clinikList.get(i).getDistance();
                        String rating=clinikList.get(i).getAvg_rating();
                          if (rating.equals("null"))
                          {
                              ratingBar.setRating(Float.parseFloat("0.0"));
                          }
                          else
                          {
                              ratingBar.setRating(Float.parseFloat(rating));
                          }
                        float f1 = Float.parseFloat(distance);
                        id=clinikList.get(i).getId();
                        mTextAddress.setText(Clinikaddress);
                        tv_name.setText(UserName);
                        mdistance.setText(new DecimalFormat("##.##").format(f1)+ "  Km");
                      //  mdistance.setText(distance);
                        break;
                    }
                }
                Button btn_book_confirm=view1.findViewById(R.id.btn_book_confirm);
                LinearLayout ll_dr_profile=view1.findViewById(R.id.ll_dr_profile);

                btn_book_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ActivityDrDetail.class);
                        intent.putExtra("id",id);
                        intent.putExtra("INTENT","CLINIK");
                        startActivity(intent);
                    }
                });
                dialog1.show();
                return false;
            }
        });




    }
}
