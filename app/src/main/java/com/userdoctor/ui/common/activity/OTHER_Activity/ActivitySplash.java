package com.userdoctor.ui.common.activity.OTHER_Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.userdoctor.R;
import com.userdoctor.ui.common.activity.Home_Activity.ActivityHome;
import com.userdoctor.ui.common.services.Config;
import com.userdoctor.ui.common.utils.Constant;
import com.userdoctor.ui.common.utils.PrefrenceManager;
import com.userdoctor.ui.common.utils.SharedPrefManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ActivitySplash extends AppCompatActivity {
    //private Session session;
    private PrefrenceManager sharedPreferences;
    protected Context context;
    private String android_id;
    private ImageView splash_logo_image;
    public static boolean isLoggedIn = false;
    Context mContext;
    public static String regId;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    private static final int REQUEST_CODE_PERMISSION = 2;
    String[] mPermission = {
            Manifest.permission.INTERNET,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext=ActivitySplash.this;
        FirebaseApp.initializeApp(mContext);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                           // Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            System.out.println("========"+task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                       // String msg = getString(R.string.msg_token_fmt, token);
                      //  Log.d(TAG, msg);
                        System.out.println("====token===="+token);
                        Constant.fcm=token;
                       // Toast.makeText(ActivitySplash.this, token, Toast.LENGTH_SHORT).show();
                    }
                });


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");


                }
            }
        };
        //session = new Session(this);
        //splash_logo_image = findViewById(R.id.splash_logo_image);
        sharedPreferences=new PrefrenceManager(ActivitySplash.this);
        printHashKey();

        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission[0])
                    != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this, mPermission[1])
                            != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this, mPermission[2])
                            != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this, mPermission[3])
                            != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this, mPermission[4])
                            != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this, mPermission[5])
                            != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this, mPermission[6])
                            != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        mPermission, REQUEST_CODE_PERMISSION);

                // If any permission aboe not allowed by user, this condition will execute every tim, else your else part will work
            } else {



                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {



                        if (SharedPrefManager.getInstance(ActivitySplash.this).isLoggedIn()) {
                            startActivity(new Intent(ActivitySplash.this, ActivityHome.class));
                             finish();
                        }
                        else
                        {
                            Intent intent = null;
                            intent = new Intent(ActivitySplash.this, ActivityWelcome.class);
                            Animatoo.animateFade(ActivitySplash.this);
                            startActivity(intent);
                            finish();
                        }








                    }
                }, 4000);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("exception", "" + e);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e("Req Code", "" + requestCode);
        System.out.println(grantResults[0] == PackageManager.PERMISSION_GRANTED);
        System.out.println(grantResults[1] == PackageManager.PERMISSION_GRANTED);
        System.out.println(grantResults[2] == PackageManager.PERMISSION_GRANTED);
        System.out.println(grantResults[3] == PackageManager.PERMISSION_GRANTED);
        System.out.println(grantResults[4] == PackageManager.PERMISSION_GRANTED);
        System.out.println(grantResults[5] == PackageManager.PERMISSION_GRANTED);
        System.out.println(grantResults[6] == PackageManager.PERMISSION_GRANTED);


        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length == 7 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[2] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[3] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[4] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[5] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[6] == PackageManager.PERMISSION_GRANTED
            ) {




                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = null;
                        intent = new Intent(ActivitySplash.this, ActivityWelcome.class);
                        //intent = new Intent(ActivityActivitySplash.this, ActivityNavigation.class);
                        Animatoo.animateFade(ActivitySplash.this);
                        /*if (session.isLoggedIn()) {

                            if (session.getUser().user_id != null && !session.getUser().user_id.equalsIgnoreCase("")) {

                                intent = new Intent(ActivitySplash.this, MainActivity.class);
                                //intent = new Intent(ActivityActivitySplash.this, ActivityLogin.class);

                                Animatoo.animateFade(ActivitySplash.this);
                            } else ToastClass.showToast(ActivitySplash.this, "user not exist !");

                        } else {
                            intent = new Intent(ActivitySplash.this, ActivityLogin.class);
                            //intent = new Intent(ActivityActivitySplash.this, ActivityNavigation.class);
                            Animatoo.animateFade(ActivitySplash.this);
                        }*/

                        startActivity(intent);
                        finish();

                    }
                }, 4000);
            }

        }
    }

    public void printHashKey() {
        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.urdoctor",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));

            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
        Log.e("check splash", "Firebase reg id: " + regId);
       // Constants.fcm=regId;
       // Constant.fcm=regId;
    }
}
