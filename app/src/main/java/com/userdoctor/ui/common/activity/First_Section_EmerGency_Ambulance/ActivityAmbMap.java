package com.userdoctor.ui.common.activity.First_Section_EmerGency_Ambulance;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;

import com.userdoctor.R;
import com.userdoctor.ui.common.activity.Home_Activity.ActivityHome;
import com.userdoctor.ui.common.activity.Doctors.Home_Doctor_list.Activity.Activity_Dr_book_confirm;
import com.userdoctor.ui.common.gps.GPSTracker;
import com.userdoctor.ui.common.model.AmbulanceModel;
import com.userdoctor.ui.common.utils.Constant;
import com.userdoctor.ui.common.utils.DialogUtil;
import com.userdoctor.ui.common.utils.NetworkUtil;
import com.userdoctor.ui.common.utils.SharedPrefManager;
import com.userdoctor.ui.common.utils.URLs;
import com.userdoctor.ui.common.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;



public class ActivityAmbMap extends FragmentActivity  /*implements , GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener*/ {

    HashMap<Integer, AmbulanceModel> h1 = new HashMap<>();
    public MaterialSearchBar materialSearchBar, searchBarDestination;
    //EditText currentLocation,Destinationl;
    SharedPrefManager sharedPrefManager;
    Button book_now;
    ImageView back;
    GoogleMap googleMap;
    private GPSTracker tracker;
    private Double lat;
    private Double lng;
    double dest_lattitude, dest_longitude;
    String lattitude, longitude,oaddress,daddress;
    AmbulanceModel ambulanceModel;
    ArrayList<AmbulanceModel> arrayList;
    int user_id;
    int[] Stored_Ids;
    int MAP_BUTTON_REQUEST_CODE = 100;
    private final float DEFAULT_ZOOM = 15;
    String address;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlacesClient placesClient;
    private List<AutocompletePrediction> predictionList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amb_map);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(ActivityAmbMap.this);
        Places.initialize(ActivityAmbMap.this, "AIzaSyBVbQq3xiusXdf7RxV0SP2kH6Qvl5ZRTkk");
        placesClient = Places.createClient(this);

        sharedPrefManager = new SharedPrefManager(this);
        System.out.println("=====userid======"+sharedPrefManager.getUser().getId());

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

    private void initView() {
        book_now = findViewById(R.id.book_now);
        back= findViewById(R.id.tool_left_title);
        materialSearchBar= findViewById(R.id.destination);
        searchBarDestination= findViewById(R.id.searchBar);
       // currentLocation= findViewById(R.id.current_location);
      //  Destinationl= findViewById(R.id.destination_location);
        final AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();
        materialSearchBar.enableSearch();
        searchBarDestination.enableSearch();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //checkLocationPermission();
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
                        .zoom(18)
                        .bearing(0)
                        .tilt(55)
                        .build();

                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 1000, null);
                NetworkUtil.isNetworkConnected(ActivityAmbMap.this);

                //apicall();
             /*   currentadressfromllatlng();
                currentLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            getAddress();
                    }
                });

                Destinationl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getdesAddress();
                    }
                });
*/

            /*   materialSearchBar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });*/
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(ActivityAmbMap.this, Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation(lat, lng, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    oaddress = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName();
searchBarDestination.setText(oaddress);
                    System.out.println("====origin===="+oaddress);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
                    @Override
                    public void onSearchStateChanged(boolean enabled) {
                    }

                    @Override
                    public void onSearchConfirmed(CharSequence text) {
                        startSearch(text.toString(), true, null, true);
                    }

                    @Override
                    public void onButtonClicked(int buttonCode) {
                        if (buttonCode == MaterialSearchBar.BUTTON_NAVIGATION) {
                            //opening or closing a navigation drawer
                        } else if (buttonCode == MaterialSearchBar.BUTTON_BACK) {
                            materialSearchBar.disableSearch();
                        }
                    }
                });
                materialSearchBar.addTextChangeListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        FindAutocompletePredictionsRequest predictionsRequest = FindAutocompletePredictionsRequest.builder()
                                .setTypeFilter(TypeFilter.ADDRESS)
                                .setCountry("in")
                                .setSessionToken(token)
                                .setQuery(s.toString())
                                .build();
                        placesClient.findAutocompletePredictions(predictionsRequest).addOnCompleteListener(new OnCompleteListener<FindAutocompletePredictionsResponse>() {
                            @Override
                            public void onComplete(@NonNull Task<FindAutocompletePredictionsResponse> task) {
                                if (task.isSuccessful()) {
                                    FindAutocompletePredictionsResponse predictionsResponse = task.getResult();
                                    if (predictionsResponse != null) {
                                        predictionList = predictionsResponse.getAutocompletePredictions();
                                        List<String> suggestionsList = new ArrayList<>();
                                        for (int i = 0; i < predictionList.size(); i++) {
                                            AutocompletePrediction prediction = predictionList.get(i);
                                            suggestionsList.add(prediction.getFullText(null).toString());
                                        }
                                        materialSearchBar.updateLastSuggestions(suggestionsList);
                                        if (!materialSearchBar.isSuggestionsVisible()) {
                                            materialSearchBar.showSuggestionsList();
                                        }
                                    }
                                } else {
                                    Log.i("mytag", "prediction fetching task unsuccessful");
                                }
                            }
                        });
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });







                materialSearchBar.setSuggstionsClickListener(new SuggestionsAdapter.OnItemViewClickListener() {
                    @Override
                    public void OnItemClickListener(int position, View v) {
                        if (position >= predictionList.size()) {
                            return;
                        }
                        AutocompletePrediction selectedPrediction = predictionList.get(position);
                        String suggestion = materialSearchBar.getLastSuggestions().get(position).toString();
                        materialSearchBar.setText(suggestion);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                materialSearchBar.clearSuggestions();
                            }
                        }, 1000);
                        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        if (imm != null)
                            imm.hideSoftInputFromWindow(materialSearchBar.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
                        final String placeId = selectedPrediction.getPlaceId();
                        List<Place.Field> placeFields = Arrays.asList(Place.Field.LAT_LNG);

                        FetchPlaceRequest fetchPlaceRequest = FetchPlaceRequest.builder(placeId, placeFields).build();
                        placesClient.fetchPlace(fetchPlaceRequest).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
                            @Override
                            public void onSuccess(FetchPlaceResponse fetchPlaceResponse) {
                                Place place = fetchPlaceResponse.getPlace();
                                lat = place.getLatLng().latitude;
                                lng = place.getLatLng().longitude;

                                if (lat != 0.0 && lng != 0.0) {
                                    if (dest_longitude != 0.0 && dest_lattitude != 0.0) {
                                       // sendRequest();
                                    }
                                }
                                Log.i("mytag", "Place found: " + place.getName());


                                Geocoder geocoder;
                                List<Address> addresses;
                                geocoder = new Geocoder(ActivityAmbMap.this, Locale.getDefault());

                                try {
                                    addresses = geocoder.getFromLocation(place.getLatLng().latitude, place.getLatLng().longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                                     daddress = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                                    String city = addresses.get(0).getLocality();
                                    String state = addresses.get(0).getAdminArea();
                                    String country = addresses.get(0).getCountryName();
                                    String postalCode = addresses.get(0).getPostalCode();
                                    String knownName = addresses.get(0).getFeatureName();

                                    System.out.println("====adddddressss===="+daddress);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }







                                System.out.println("=========placename===="+"llllllllllllllllll"+place.getLatLng().longitude);
                                LatLng latLngOfPlace = place.getLatLng();
                                if (latLngOfPlace != null) {
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngOfPlace, DEFAULT_ZOOM));
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if (e instanceof ApiException) {
                                    ApiException apiException = (ApiException) e;
                                    apiException.printStackTrace();
                                    int statusCode = apiException.getStatusCode();
                                    Log.i("mytag", "place not found: " + e.getMessage());
                                    Log.i("mytag", "status code: " + statusCode);
                                }
                            }
                        });
                    }

                    @Override
                    public void OnItemDeleteListener(int position, View v) {

                    }
                });








           searchBarDestination.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
                @Override
                public void onSearchStateChanged(boolean enabled) {

                }

                @Override
                public void onSearchConfirmed(CharSequence text) {
                    startSearch(text.toString(), true, null, true);
                }

                @Override
                public void onButtonClicked(int buttonCode) {
                    if (buttonCode == MaterialSearchBar.BUTTON_NAVIGATION) {
                    } else if (buttonCode == MaterialSearchBar.BUTTON_BACK) {
                        searchBarDestination.disableSearch();
                    }
                }
            });
                searchBarDestination.addTextChangeListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        FindAutocompletePredictionsRequest predictionsRequest = FindAutocompletePredictionsRequest.builder()
                                .setTypeFilter(TypeFilter.ADDRESS)
                                .setCountry("in")
                                .setSessionToken(token)
                                .setQuery(s.toString())
                                .build();
                        placesClient.findAutocompletePredictions(predictionsRequest).addOnCompleteListener(new OnCompleteListener<FindAutocompletePredictionsResponse>() {
                            @Override
                            public void onComplete(@NonNull Task<FindAutocompletePredictionsResponse> task) {
                                if (task.isSuccessful()) {
                                    FindAutocompletePredictionsResponse predictionsResponse = task.getResult();
                                    if (predictionsResponse != null) {
                                        predictionList = predictionsResponse.getAutocompletePredictions();
                                        List<String> suggestionsList = new ArrayList<>();
                                        for (int i = 0; i < predictionList.size(); i++) {
                                            AutocompletePrediction prediction = predictionList.get(i);
                                            suggestionsList.add(prediction.getFullText(null).toString());
                                        }
                                        searchBarDestination.updateLastSuggestions(suggestionsList);
                                        if (!searchBarDestination.isSuggestionsVisible()) {
                                            searchBarDestination.showSuggestionsList();
                                        }
                                    }
                                } else {
                                    Log.i("mytag", "prediction fetching task unsuccessful");
                                }
                            }
                        });





                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                searchBarDestination.setSuggstionsClickListener(new SuggestionsAdapter.OnItemViewClickListener() {
                    @Override
                    public void OnItemClickListener(int position, View v) {
                        if (position >= predictionList.size()) {
                            return;
                        }

                        AutocompletePrediction selectedPrediction = predictionList.get(position);
                        String suggestion = searchBarDestination.getLastSuggestions().get(position).toString();
                        searchBarDestination.setText(suggestion);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                searchBarDestination.clearSuggestions();
                            }
                        }, 1000);
                        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        if (imm != null)
                            imm.hideSoftInputFromWindow(searchBarDestination.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
                        final String placeId = selectedPrediction.getPlaceId();
                        List<Place.Field> placeFields = Arrays.asList(Place.Field.LAT_LNG);

                        FetchPlaceRequest fetchPlaceRequest = FetchPlaceRequest.builder(placeId, placeFields).build();
                        placesClient.fetchPlace(fetchPlaceRequest).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
                            @Override
                            public void onSuccess(FetchPlaceResponse fetchPlaceResponse) {
                                Place place = fetchPlaceResponse.getPlace();
                                dest_lattitude = place.getLatLng().latitude;
                                dest_longitude = place.getLatLng().longitude;
                                if (lat != 0.0 && lng != 0.0) {
                                    if (dest_longitude != 0.0 && dest_lattitude != 0.0) {
                                      //  sendRequest();
                                        apicall();
                                    }
                                }

                                Geocoder geocoder;
                                List<Address> addresses;
                                geocoder = new Geocoder(ActivityAmbMap.this, Locale.getDefault());

                                try {
                                    addresses = geocoder.getFromLocation(place.getLatLng().latitude, place.getLatLng().longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                                    oaddress = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                                    String city = addresses.get(0).getLocality();
                                    String state = addresses.get(0).getAdminArea();
                                    String country = addresses.get(0).getCountryName();
                                    String postalCode = addresses.get(0).getPostalCode();
                                    String knownName = addresses.get(0).getFeatureName();

                                    System.out.println("====origin===="+oaddress);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }




                                System.out.println("=====latlong======="+place.getLatLng().longitude+"jjjjjjjj"+place.getLatLng().latitude);
                                Log.i("mytag", "Place found: " + place.getName());
                                LatLng latLngOfPlace = place.getLatLng();
                                if (latLngOfPlace != null) {
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngOfPlace, DEFAULT_ZOOM));
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if (e instanceof ApiException) {
                                    ApiException apiException = (ApiException) e;
                                    apiException.printStackTrace();
                                    int statusCode = apiException.getStatusCode();
                                    Log.i("mytag", "place not found: " + e.getMessage());
                                    Log.i("mytag", "status code: " + statusCode);
                                }
                            }
                        });
                    }

                    @Override
                    public void OnItemDeleteListener(int position, View v) {
                    }
                });

                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(ActivityAmbMap.this, ActivityHome.class));

                    }
                });


                book_now.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LayoutInflater inflater = LayoutInflater.from(ActivityAmbMap.this);
                        final Dialog dialog1 = new Dialog(ActivityAmbMap.this, R.style.bottom_Dialog);
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
                                // Confirm_dialog();
                                RequestQueue queue = Volley.newRequestQueue(ActivityAmbMap.this);
                              String  url = "http://logicaltest.in/Urdoctors/UsersApi/book_ambulance";
                                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                                        new Response.Listener<String>()
                                        {
                                            @Override
                                            public void onResponse(String response) {
                                                // response
                                                Log.d("Response", response);
                                                System.out.println("====rsepppooo====="+response);

                                                AlertDialog.Builder builder1 = new AlertDialog.Builder(ActivityAmbMap.this);
                                                builder1.setMessage("Ambulance Booked");
                                                builder1.setCancelable(true);

                                                builder1.setPositiveButton(
                                                        "Yes",
                                                        new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {
                                                                dialog.cancel();
                                                                startActivity(new Intent(ActivityAmbMap.this, ActivityHome.class));
                                                            }
                                                        });
                                                AlertDialog alert11 = builder1.create();
                                                alert11.show();
                                            }
                                        },
                                        new Response.ErrorListener()
                                        {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                // error
                                                System.out.println("======response===="+error.fillInStackTrace());
                                               // Log.d("Error.Response", response);
                                            }
                                        }
                                ) {
                                    @Override
                                    protected Map<String, String> getParams()
                                    {
                                        Map<String, String>  params = new HashMap<String, String>();
                                        params.put("user_id", String.valueOf(sharedPrefManager.getUser().getId()));
                                        params.put("lat", String.valueOf(lat));
                                        params.put("lng", String.valueOf(lng));
                                        params.put("origin", oaddress);
                                        params.put("destination", daddress);


System.out.println("====params========"+params);


                                        return params;
                                    }
                                };
                                queue.add(postRequest);






                            }

                        });


                        dialog1.show();


                    }

                });

            }
        });
    }
    public void apicall() {

       // DialogUtil.show(ActivityAmbMap.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_URL + "ambulance_availability",
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
                                        Toast.makeText(ActivityAmbMap.this, "List have no data", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                Stored_Ids = new int[arrayList.size()];

                                Log.e("check size", String.valueOf(Stored_Ids.length));
                                //   recycler_view.setAdapter(adapter);
                            } else {
                                Toast.makeText(ActivityAmbMap.this, "List have no data", Toast.LENGTH_SHORT).show();
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

        VolleySingleton.getInstance(ActivityAmbMap.this).addToRequestQueue(stringRequest);


    }
    protected Marker createMarker(double latitude, double longitude, String title, String snippet, int iconResID) {
        return googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(snippet)
                .icon(BitmapDescriptorFactory.fromResource(iconResID)));
    }





    private void Confirm_dialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this).setTitle("Confirm Appointment")
                .setMessage("Appointment for ER doctor with Dr.Suresh. After confirm the doctor will contact with you.");

        dialog.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {



                dialog.dismiss();
                Intent intent = new Intent(ActivityAmbMap.this, Activity_Dr_book_confirm.class);
                startActivity(intent);

            }


        });
        final AlertDialog alert = dialog.create();
        alert.show();

    }

/*    private void getAddress() {
        Intent locationPickerIntent = new LocationPickerActivity.Builder()
                .withLocation(lat, lng)
                //.withLocation(latitude, longitude)
                .withGoogleTimeZoneEnabled()
                .withGeolocApiKey("AIzaSyCdDubVfzwR-1UsiCs0sDGsLjspuDLxSr4")
                .withSearchZone("hi_IN")
                // .withSearchZone(SearchZoneRect(LatLng(latitude, longitude), LatLng(latitude, longitude)))
                // .withDefaultLocaleSearchZone()
                .shouldReturnOkOnBackPressed()
                .withGooglePlacesApiKey("AIzaSyCdDubVfzwR-1UsiCs0sDGsLjspuDLxSr4")
                .build(getApplicationContext());


        locationPickerIntent.putExtra("test", "this is a test");
        startActivityForResult(locationPickerIntent, MAP_BUTTON_REQUEST_CODE);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            Log.e("RESULT****", "OK" + requestCode);
            if (requestCode == 1) {
                String latitude = String.valueOf(data.getDoubleExtra(LATITUDE, 0.0));
                Log.e("LATITUDE****", latitude.toString());
                String longitude = String.valueOf(data.getDoubleExtra(LONGITUDE, 0.0));
                Log.e("LONGITUDE****", longitude.toString());
                String address = data.getStringExtra(LOCATION_ADDRESS);
                Log.e("ADDRESS****", address.toString());
                String postalcode = data.getStringExtra(ZIPCODE);
                Log.e("POSTALCODE****", postalcode.toString());
                String bundle = String.valueOf(data.getBundleExtra(TRANSITION_BUNDLE));
                Log.e("BUNDLE TEXT****", bundle);
                String fullAddress = data.getStringExtra(ADDRESS);
                if (fullAddress != null) {
                    Log.e("FULL ADDRESS****", fullAddress.toString());
                }
                String timeZoneId = data.getStringExtra(TIME_ZONE_ID);
                Log.e("TIME ZONE ID****", timeZoneId);
                String timeZoneDisplayName = data.getStringExtra(TIME_ZONE_DISPLAY_NAME);
                Log.e("TIME ZONE NAME****", timeZoneDisplayName);
            } else if (requestCode == 100) {
                // mlatitude = String.valueOf(data.getDoubleExtra(LATITUDE, 0.0));
                lat = (data.getDoubleExtra(LATITUDE, lat));
                // Log.e("LATITUDE****", mlatitude.toString());
                Log.e("lat", lat.toString());
                // mlongitude = String.valueOf(data.getDoubleExtra(LONGITUDE, 0.0));
                lng =(data.getDoubleExtra(LONGITUDE, lng));
                Log.e("LONGITUDE****", lng.toString());
                address = data.getStringExtra(LOCATION_ADDRESS);
                currentLocation.setText(address);
                Log.e("ADDRESS****", address.toString());
            }
        }

        if (resultCode == Activity.RESULT_CANCELED) {
            Log.e("RESULT****", "CANCELLED");
        }
    }




    private void getdesAddress() {
        Intent locationPickerIntent = new LocationPickerActivity.Builder()
                .withLocation(lat, lng)
                //.withLocation(latitude, longitude)
                .withGoogleTimeZoneEnabled()
                .withGeolocApiKey("AIzaSyCdDubVfzwR-1UsiCs0sDGsLjspuDLxSr4")
                .withSearchZone("hi_IN")
                // .withSearchZone(SearchZoneRect(LatLng(latitude, longitude), LatLng(latitude, longitude)))
                // .withDefaultLocaleSearchZone()
                .shouldReturnOkOnBackPressed()
                .withGooglePlacesApiKey("AIzaSyCdDubVfzwR-1UsiCs0sDGsLjspuDLxSr4")
                .build(getApplicationContext());


        locationPickerIntent.putExtra("test", "this is a test");
        startActivityForResult(locationPickerIntent, MAP_BUTTON_REQUEST_CODE);
    }

public void currentadressfromllatlng(){

    Geocoder geocoder;
    List<Address> addresses;
    geocoder = new Geocoder(this, Locale.getDefault());

    try {
        addresses = geocoder.getFromLocation(lat, lng, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName();
        currentLocation.setText(address);
        System.out.println("=====address===="+address+city+state+country+postalCode+knownName);


    } catch (IOException e) {
        e.printStackTrace();
    }


}*/


}



