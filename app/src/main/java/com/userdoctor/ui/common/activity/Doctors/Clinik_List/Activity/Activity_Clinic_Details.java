package com.userdoctor.ui.common.activity.Doctors.Clinik_List.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.userdoctor.R;
import com.userdoctor.ui.common.activity.Doctors.Home_Doctor_list.Activity.ActivityBookNow_drTopSpec;
import com.userdoctor.ui.common.utils.NetworkUtil;

public class Activity_Clinic_Details extends AppCompatActivity implements View.OnClickListener{

    Button btn_book_clinic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_profile_);

        btn_book_clinic = findViewById(R.id.btn_book_clinic);
        btn_book_clinic.setOnClickListener(this);
        NetworkUtil.isNetworkConnected(Activity_Clinic_Details.this);


    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
        case R.id.btn_book_clinic:

        //**************intent page same but new activity make in dynamic for clinic**
        intent = new Intent(Activity_Clinic_Details.this, ActivityBookNow_drTopSpec.class);
        startActivity(intent);
        break;
    }
    }
}
