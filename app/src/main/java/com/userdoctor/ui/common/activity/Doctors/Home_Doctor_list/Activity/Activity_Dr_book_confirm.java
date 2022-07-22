package com.userdoctor.ui.common.activity.Doctors.Home_Doctor_list.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.userdoctor.R;
import com.userdoctor.ui.common.activity.Home_Activity.ActivityHome;

import java.util.Locale;

public class Activity_Dr_book_confirm extends AppCompatActivity {
    private static int SPLASH_SCREEN_TIME_OUT = 2500;
    Button btn_done;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__dr_book_confirm);

        //btn_done=findViewById(R.id.btn_done);
        /*NetworkUtil.isNetworkConnected(Activity_Dr_book_confirm.this);

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Activity_Dr_book_confirm.this, ActivityHome.class);
                startActivity(intent);
                finish();
            }
        });
*/
        String languagename = Locale.getDefault().getDisplayLanguage();
        String country = Locale.getDefault().getCountry();




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(Activity_Dr_book_confirm.this, ActivityHome.class);
                startActivity(i);
                finish();
            }

        }, SPLASH_SCREEN_TIME_OUT);
    }

}
