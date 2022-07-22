package com.userdoctor.ui.common.activity.Lab_Nursing.Home_Nursing.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.userdoctor.R;
import com.userdoctor.ui.common.activity.Home_Activity.ActivityHome;

public class Succesfully_Done_Nursing_Request extends AppCompatActivity {
Button btn_main_menu;
ImageView iv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succesfully__done__nursing__request);
        iv_back=findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        btn_main_menu=findViewById(R.id.btn_main_menu);
        btn_main_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Succesfully_Done_Nursing_Request.this, ActivityHome.class));

            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
