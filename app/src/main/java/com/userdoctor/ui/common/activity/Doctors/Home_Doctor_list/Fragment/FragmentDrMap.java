package com.userdoctor.ui.common.activity.Doctors.Home_Doctor_list.Fragment;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.userdoctor.R;
import com.userdoctor.ui.common.gps.GPSTracker;
import com.userdoctor.ui.common.model.DoctorListData;
import com.userdoctor.ui.common.utils.DialogUtil;
import com.userdoctor.ui.common.utils.URLs;
import com.userdoctor.ui.common.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDrMap extends Fragment /*implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener*/ {
    SupportMapFragment mapFragment;
    private View view;
    private Context context;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    private GoogleMap mMap;
    private GPSTracker tracker;
    private Double lat;
    private Double lng;
    private String country;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 101;
    private List<DoctorListData> drList=new ArrayList<>();

    public FragmentDrMap() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dr_map, container, false);
        tracker = new GPSTracker(context);
        //if location is enable
        if (tracker.canGetLocation() == true) {
            lat = tracker.getLatitude();
            lng = tracker.getLongitude();
            Log.e("current_lat ", " " + String.valueOf(lat));
            Log.e("current_Lon ", " " + String.valueOf(lng));
            //getAddress(lat,lng);

        } else if (tracker.canGetLocation() == false) {

            tracker.showSettingsAlert();
        }

        initView();
        return view;
    }

    private void initView() {

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

         mapFragment = (SupportMapFragment)
                getChildFragmentManager()
                        .findFragmentById(R.id.map);




         mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                mMap.clear(); //clear old markers

                CameraPosition googlePlex = CameraPosition.builder()
                        .target(new LatLng(37.4219999, -122.0862462))
                        .zoom(10)
                        .bearing(0)
                        .tilt(45)
                        .build();

                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 10000, null);

                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(37.4219999, -122.0862462))
                        .title("Spider Man"));
                        //.icon(bitmapDescriptorFromVector(getActivity(), R.drawable.ic_profile)));

                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(37.4629101, -122.2449094))
                        .title("Iron Man")
                        .snippet("His Talent : Plenty of money"));

                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(37.3092293, -122.1136845))
                        .title("Captain America"));
            }
        });




    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


    //////////////////////////////// map  start////////////////////////////
    /*@Override
    public void onMapReady(GoogleMap googleMap) {
        *//*LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        //MarkerOptions are used to create a new Marker.You can specify location, title etc with MarkerOptions
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("You are Here");
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        //Adding the created the marker on the map
        googleMap.addMarker(markerOptions);*//*

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        //mMap.setPadding(10,200,10,20);  // for copyright google
        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }


        *//*CameraPosition googlePlex = CameraPosition.builder()
                .target(new LatLng(22.7520030, 75.9045550))
                .zoom(10)
                .bearing(0)
                .tilt(45)
                .build();

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.7520030, 75.9045550))
                .title("cccc")
                .snippet("His Talent : Plenty of money"));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.7520030, 75.9045550))
                .title("ind"));*//*


        //MarkerInfoWindowAdapter markerInfoWindowAdapter = new MarkerInfoWindowAdapter(context);
        //mMap.setInfoWindowAdapter(markerInfoWindowAdapter);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                LayoutInflater inflater = LayoutInflater.from(context);
                final Dialog dialog1 = new Dialog(context, R.style.bottom_Dialog);
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

               Button btn_book_confirm=view1.findViewById(R.id.btn_book_confirm);
               LinearLayout ll_dr_profile=view1.findViewById(R.id.ll_dr_profile);

                btn_book_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ActivityBookNow_drTopSpec.class);
                        startActivity(intent);

                    }
                });


                dialog1.show();


                return false;
            }
        });

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        //Showing Current Location Marker on Map
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);


        LocationManager locationManager = (LocationManager)
                context.getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(new Criteria(), true);
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Location locations = locationManager.getLastKnownLocation(provider);
        List<String> providerList = locationManager.getAllProviders();
        if (null != locations && null != providerList && providerList.size() > 0) {
            double longitude = locations.getLongitude();
            double latitude = locations.getLatitude();
            Geocoder geocoder = new Geocoder(context,
                    Locale.getDefault());
            try {
                List<Address> listAddresses = geocoder.getFromLocation(latitude,
                        longitude, 1);
                if (null != listAddresses && listAddresses.size() > 0) {
                    String state = listAddresses.get(0).getAdminArea();
                    country = listAddresses.get(0).getCountryName();
                    String subLocality = listAddresses.get(0).getSubLocality();
                    markerOptions.title("" + latLng + "," + subLocality + "," + state
                            + "," + country);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        mCurrLocationMarker = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,
                    this);
        }

    }


    @SuppressLint("RestrictedApi")
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, this);

        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }*/

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(context,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                           // buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(context, "permission denied",
                            Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    //////////////////////////////// map  end////////////////////////////







    public void mFunCallDrFragmentList() {
        DialogUtil.show(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_URL + "Doctor_list_AtHome",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        DialogUtil.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            boolean responsedata = obj.getBoolean("result");
                            System.out.println("<><><>" + obj);

                            if (responsedata == true) {

                                JSONArray jsonArray = obj.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    System.out.println("<><><> dr home" + jsonObject);

                                    if (jsonObject != null) {
                                        DoctorListData hero = new DoctorListData(
                                                jsonObject.getString("id"),
                                                jsonObject.getString("category_id"),
                                                jsonObject.getString("dr_name"),
                                                jsonObject.getString("profile_img"),
                                                jsonObject.getString("contact_no"),
                                                jsonObject.getString("dr_email"),
                                                jsonObject.getString("lab_name"),
                                                jsonObject.getString("lab_address"),
                                                jsonObject.getString("hospital_name"),
                                                jsonObject.getString("hospital_address"),
                                                jsonObject.getString("mci_member"),
                                                jsonObject.getString("national_id"),
                                                jsonObject.getString("gender"),
                                                jsonObject.getString("degree_id"),
                                                jsonObject.getString("passing_year"),
                                                jsonObject.getString("experience"),
                                                jsonObject.getString("phone_no"),
                                                jsonObject.getString("clinic_fee"),
                                                jsonObject.getString("home_fee"),
                                                jsonObject.getString("emergency_fee"),
                                                jsonObject.getString("address"),
                                                jsonObject.getString("countryId"),
                                                jsonObject.getString("stateId"),
                                                jsonObject.getString("city_id"),
                                                jsonObject.getString("home_availability"),
                                                jsonObject.getString("emergency"),
                                                jsonObject.getString("lat"),
                                                jsonObject.getString("lng"),
                                                jsonObject.getString("registration_proof"),
                                                jsonObject.getString("qualification_proof"),
                                                jsonObject.getString("id_proof"),
                                                jsonObject.getString("status"),
                                                jsonObject.getString("clinic_open_hours"),
                                                jsonObject.getString("clinic_close_hours"),
                                                jsonObject.getString("delete_status")
                                        );



                                        drList.add(hero);
                                    } else {
                                        Toast.makeText(getActivity(), "List have no data", Toast.LENGTH_SHORT).show();
                                    }


                                }
                               // recycler_view.setAdapter(mAdapter);
                            } else {
                                Toast.makeText(getActivity(), "fragment have no data", Toast.LENGTH_SHORT).show();
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
                        // Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }); /*{

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("lat", Lattitude);
                params.put("lng", Longitude);
                return params;
            }
        };*/

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

















    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
