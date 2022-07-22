package com.userdoctor.ui.common.activity.Home_Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.userdoctor.R;
import com.userdoctor.ui.common.Session.Session;
import com.userdoctor.ui.common.activity.OTHER_Activity.ActivityAppointMentList;
import com.userdoctor.ui.common.activity.OTHER_Activity.ActivityDeliveryDrug;
import com.userdoctor.ui.common.activity.OTHER_Activity.ActivityMedicalRecord;
import com.userdoctor.ui.common.activity.OTHER_Activity.ActivityOrder;
import com.userdoctor.ui.common.activity.OTHER_Activity.ActivityPayment;
import com.userdoctor.ui.common.activity.OTHER_Activity.ActivityProfile;
import com.userdoctor.ui.common.activity.OTHER_Activity.ActivityReminder;
import com.userdoctor.ui.common.activity.Home_Activity.Activity.ActivitySetting;
import com.userdoctor.ui.common.activity.OTHER_Activity.ActivityTestBooking;
import com.userdoctor.ui.common.activity.OTHER_Activity.Activity_More_Dr;
import com.userdoctor.ui.common.activity.First_Section_EmerGency_Ambulance.ActivityAmbMap;
import com.userdoctor.ui.common.activity.First_Section_EmerGency_Ambulance.ActivityEmrMap;
import com.userdoctor.ui.common.activity.Lab_Nursing.Home_Nursing.Activity.Nursing_Detail_Time;
import com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Activity.Activity_Vaccination_Main;
import com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Activity.Activity_Sample_Collection_Main;
import com.userdoctor.ui.common.activity.Doctors.Clinik_List.Activity.ActivityClinicList;
import com.userdoctor.ui.common.activity.Doctors.Home_Doctor_list.Activity.ActivityDoctorList;
import com.userdoctor.ui.common.adapter.HomeAdapter;
import com.userdoctor.ui.common.adapter.LeftDrawerAdapter;
import com.userdoctor.ui.common.gps.GPSTracker;
import com.userdoctor.ui.common.model.DrawerItem;
import com.userdoctor.ui.common.model.TopHomeSpeciallist;
import com.userdoctor.ui.common.utils.NetworkUtil;
import com.userdoctor.ui.common.utils.SharedPrefManager;
import com.userdoctor.ui.common.utils.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.userdoctor.ui.common.utils.URLs.IMAGE_BASE_URL;

public class ActivityHome extends AppCompatActivity implements View.OnClickListener {

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 101;
    DrawerLayout mDrawerLayout;
    Toolbar toolbar;
    private ListView mDrawerList;
    private ImageView iv_drawer;
    private TextView tv_name;
    LeftDrawerAdapter leftDrawerAdapter;
    public ArrayList<DrawerItem> List_Item;
    public int ScreenPos = 0;
    DrawerItem drawerItem;
    boolean doubleBackToExitPressedOnce = false;
    private GPSTracker tracker;
    public  static  Double lat;
    public static Double lng;
    public ImageView nav_img_profile;
    public LinearLayout ll_nav_header, ll_sam1;
    private TextView nav_tv_name, nav_tv_viewProfile, tv_find_more_dr;
    private LinearLayout ll_clinic_list, ll_doc, ll_delivery;
    CardView card_emergency_dr_book, card_ambulance_book, card_vacination, card_sample_collect,home_nursing;
    SharedPrefManager sharedPrefManager;
    int user_id;
    String user_firstname, fcm_id, User_image;
    RecyclerView recyclerView;
    HomeAdapter adapter;
    public static String Lattitude,Longitude;
    private LinearLayoutManager linearLayoutManager;
    private List<Object> objectList = new ArrayList<>();
Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        session=new Session(getApplicationContext());
        sharedPrefManager = new SharedPrefManager(this);
        if (sharedPrefManager != null) {
            user_id = sharedPrefManager.getUser().getId();
            user_firstname = sharedPrefManager.getUser().getFname();
            User_image=sharedPrefManager.getUser().getImage();
        }

        tracker = new GPSTracker(this);
        NetworkUtil.isNetworkConnected(ActivityHome.this);

        initView();
        clickListner();
        nav_tv_name.setText(user_firstname);

        Glide.with(ActivityHome.this).load(IMAGE_BASE_URL+User_image).error(R.drawable.ic_user).into(nav_img_profile);




        System.out.println("check first name"+User_image);


        adapter = new HomeAdapter(objectList);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        TopSpecialistDrList();

    }

    private void TopSpecialistDrList() {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.BASE_URL+"Get_Specialist",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);
                            System.out.println("check response  "+obj);

                            boolean responsedata=obj.getBoolean("result");
                            if (responsedata==true)
                            {
                                JSONArray jsonArray=obj.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String id=jsonObject.getString("id");

                                    System.out.println("id "+id);

                                    TopHomeSpeciallist hero = new TopHomeSpeciallist(jsonObject.getString("id"),jsonObject.getString("cat_name"), jsonObject.getString("icon"));
                                    objectList.add(hero);
                                }
                                recyclerView.setAdapter(adapter);
                            }
                            else
                            {
                                Toast.makeText(ActivityHome.this, "data not found", Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
    }
    private void initView() {
        List_Item = new ArrayList<>();
        // iv_emergency_map = findViewById(R.id.iv_emergency_map);
        // iv_amb_map = findViewById(R.id.iv_amb_map);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerList = findViewById(R.id.left_drawer);
        iv_drawer = findViewById(R.id.iv_drawer);
        ll_clinic_list = findViewById(R.id.ll_clinic_list);
        ll_doc = findViewById(R.id.ll_doc);
        ll_delivery = findViewById(R.id.ll_delivery);
        tv_find_more_dr = findViewById(R.id.tv_find_more_dr);
        card_ambulance_book = findViewById(R.id.card_ambulance_book);
        card_emergency_dr_book = findViewById(R.id.card_emergency_dr_book);
        home_nursing=findViewById(R.id.home_nursing);
        card_vacination = findViewById(R.id.card_vacination);
        card_sample_collect = findViewById(R.id.card_sample_collect);
        ll_sam1 = findViewById(R.id.ll_sam1);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        //mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        View header = getLayoutInflater().inflate(R.layout.nav_header_activity_home, null);
        ll_nav_header = header.findViewById(R.id.ll_nav_header);
        nav_img_profile = header.findViewById(R.id.nav_img_profile);
        nav_tv_name = header.findViewById(R.id.nav_tv_name);
        nav_tv_viewProfile = header.findViewById(R.id.nav_tv_viewProfile);
        recyclerView = findViewById(R.id.recycler_view);




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

        Lattitude=String.valueOf(lat);
         Longitude=String.valueOf(lng);
          System.out.println("latt"+Lattitude+"  "+Longitude);
        mDrawerList.addHeaderView(header);
        DrawerItem();
        SetupDrawer();

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

    }

    private void clickListner() {

        iv_drawer.setOnClickListener(this);
        ll_nav_header.setOnClickListener(this);
        ll_clinic_list.setOnClickListener(this);
        ll_doc.setOnClickListener(this);
        ll_delivery.setOnClickListener(this);
        tv_find_more_dr.setOnClickListener(this);
        card_emergency_dr_book.setOnClickListener(this);
        home_nursing.setOnClickListener(this);
        card_ambulance_book.setOnClickListener(this);
        card_vacination.setOnClickListener(this);
        card_sample_collect.setOnClickListener(this);
    }


    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(this,
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
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        /*if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);*/
                    }
                } else {
                    Toast.makeText(this, "permission denied",
                            Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    //Drawer Item Array
    public void DrawerItem() {

        List_Item.clear();

        List_Item.add(new DrawerItem(R.drawable.ic_testbooking, "Test Bookings", R.drawable.ic_next_gray));
        List_Item.add(new DrawerItem(R.drawable.ic_booking, "AppointMents", R.drawable.ic_next_gray));
        List_Item.add(new DrawerItem(R.drawable.ic_order, "Orders", R.drawable.ic_next_gray));
        //List_Item.add(new DrawerItem(R.drawable.ic_next_gray,"My Doctors", R.drawable.ic_next_gray));
        List_Item.add(new DrawerItem(R.drawable.ic_medicalrecord, "Medical Records", R.drawable.ic_next_gray));
        List_Item.add(new DrawerItem(R.drawable.ic_reminders, "Reminder", R.drawable.ic_next_gray));
        List_Item.add(new DrawerItem(R.drawable.ic_payment, "Payment", R.drawable.ic_next_gray));
        //List_Item.add(new DrawerItem(R.drawable.ic_next_gray,"Blogs", R.drawable.ic_next_gray));
        List_Item.add(new DrawerItem(R.drawable.ic_newsettings, "Setting", R.drawable.ic_next_gray));
        List_Item.add(new DrawerItem(R.drawable.ic_newhelp, "Help", R.drawable.ic_next_gray));
        List_Item.add(new DrawerItem(R.drawable.ic_menu_share, "Share App", R.drawable.ic_next_gray));
        List_Item.add(new DrawerItem(R.drawable.icon_logout, "LogOut", R.drawable.ic_next_gray));

    }


    //Navigation Drawer Method
    public void SetupDrawer() {

        //Drawer Adapter

        leftDrawerAdapter = new LeftDrawerAdapter(ActivityHome.this, List_Item);

        //Set Adapter
        mDrawerList.setAdapter(leftDrawerAdapter);

        //Call first bydefault Fragment
        /*fragment = new FragmentHome();
        titletxt.setText("Home");
        //  imgheader.setVisibility(View.GONE);
        FragmentManager frgManager = getFragmentManager();
        frgManager.beginTransaction().replace(R.id.content_id, fragment).commit();*/

        //ListView click Listner
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

                int position = pos - 1;
                if (position >= 0) {
                    //Call open Fragment
                    SelectOption(position);
                }
            }
        });

    }

    public void SelectOption(int pos) {
        ScreenPos = pos;
        leftDrawerAdapter.setSelectedIndex(pos);
        drawerItem = List_Item.get(pos);
        Log.e("Position......", String.valueOf(pos));
        String Item_Name = drawerItem.getItemName();
        Log.e("Position......", drawerItem.getItemName());

        if (Item_Name.equals("AppointMents")) {

            Intent intent = new Intent(this, ActivityTestBooking.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            mDrawerLayout.closeDrawer(mDrawerList);

        } else if (Item_Name.equals("Test Bookings")) {

            Intent intent = new Intent(this, ActivityAppointMentList.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        } else if (Item_Name.equals("Orders")) {

            Intent intent = new Intent(this, ActivityOrder.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            mDrawerLayout.closeDrawer(mDrawerList);


        }/* else if (Item_Name.equals("My Doctors")) {
            Intent intent = new Intent(this, ActivityAppointMentList.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            //overridePendingTransition(R.anim.slide_top, R.anim.slide_stay);
            mDrawerLayout.closeDrawer(mDrawerList);

        } */ else if (Item_Name.equals("Medical Records")) {
            Intent intent = new Intent(this, ActivityMedicalRecord.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            //overridePendingTransition(R.anim.slide_top, R.anim.slide_down);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else if (Item_Name.equals("Reminder")) {
            Intent intent = new Intent(this, ActivityReminder.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            //overridePendingTransition(R.anim.slide_top, R.anim.slide_down);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else if (Item_Name.equals("Payment")) {
            Intent intent = new Intent(this, ActivityPayment.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            //overridePendingTransition(R.anim.slide_top, R.anim.slide_down);
            mDrawerLayout.closeDrawer(mDrawerList);
        }/* else if (Item_Name.equals("Blogs")) {
            Intent intent = new Intent(this, ActivityAppointMentList.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            //overridePendingTransition(R.anim.slide_top, R.anim.slide_down);
            mDrawerLayout.closeDrawer(mDrawerList);
        }*/
        else if (Item_Name.equals("Setting")) {

            Intent intent=new Intent(ActivityHome.this, ActivitySetting.class);
            startActivity(intent);


//            Intent intent = new Intent(this, ActivityAppointMentList.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
            //overridePendingTransition(R.anim.slide_top, R.anim.slide_down);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else if (Item_Name.equals("Help")) {
//            Intent intent = new Intent(this, ActivityAppointMentList.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
            //overridePendingTransition(R.anim.slide_top, R.anim.slide_down);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else if (Item_Name.equals("Share App")) {
            shareApp();
        } else if (Item_Name.equals("LogOut")) {
            LogOut();
        }

    }

    private void LogOut() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this).setTitle("UrDoctor")
                .setMessage("Are you sure, you want to logout this app");
        dialog.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                // mGoogleSignInClient.signOut();
                // LoginManager.getInstance().logOut();
                //session.logout();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
                finish();
                dialog.dismiss();
            }


        });
        final AlertDialog alert = dialog.create();
        alert.show();
    }

    private void shareApp() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        share.putExtra(Intent.EXTRA_SUBJECT, "UrDoctor");
        share.putExtra(Intent.EXTRA_TEXT, "Welcome to UrDoctor! You can download app from Play Store:- https://play.google.com/store/apps/");
        startActivity(Intent.createChooser(share, "Share link!"));

    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.iv_drawer:
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {

                    mDrawerLayout.closeDrawer(GravityCompat.START);
                } else {

                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
                break;

            case R.id.ll_nav_header:
                intent = new Intent(ActivityHome.this, ActivityProfile.class);
                startActivity(intent);

                break;
            case R.id.ll_doc:
                session.setAdBookingType("Home Booking");
                intent = new Intent(ActivityHome.this, ActivityDoctorList.class);
                startActivity(intent);

                break;

            case R.id.ll_clinic_list:
                session.setAdBookingType("Clinik Booking");

                intent = new Intent(ActivityHome.this, ActivityClinicList.class);
                startActivity(intent);

                break;

            case R.id.card_emergency_dr_book:
                intent = new Intent(ActivityHome.this, ActivityEmrMap.class);
                startActivity(intent);

                break;

            case R.id.card_ambulance_book:
                intent = new Intent(ActivityHome.this, ActivityAmbMap.class);
                startActivity(intent);

                break;


            case R.id.ll_delivery:
                intent = new Intent(ActivityHome.this, ActivityDeliveryDrug.class);
                startActivity(intent);
                break;


            case R.id.tv_find_more_dr:
                intent = new Intent(ActivityHome.this, Activity_More_Dr.class);
                startActivity(intent);
                break;



            case R.id.card_vacination:
                intent = new Intent(ActivityHome.this, Activity_Vaccination_Main.class);
                startActivity(intent);
                break;

            case R.id.card_sample_collect:
                intent = new Intent(ActivityHome.this, Activity_Sample_Collection_Main.class);
                startActivity(intent);
                break;

            case R.id.home_nursing:

                intent = new Intent(ActivityHome.this, Nursing_Detail_Time.class);
                startActivity(intent);
                break;


        }
    }


    @Override
    public void onBackPressed() {

        Log.e("pos", "" + ScreenPos);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else if (ScreenPos == 0) {

            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please Press Back again to exit the app", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {

                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);

        } else {

            //Call home fragment
            SelectOption(0);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
