package com.userdoctor.ui.common.activity.OTHER_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.userdoctor.R;
import com.userdoctor.ui.common.activity.Home_Activity.ActivityHome;
import com.userdoctor.ui.common.utils.NetworkUtil;

public class ActivitySampleConfirmDone extends AppCompatActivity {

Button btn_confirm_main;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_confirm_done);
        NetworkUtil.isNetworkConnected(ActivitySampleConfirmDone.this);

        btn_confirm_main=findViewById(R.id.btn_confirm_main);

        btn_confirm_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(ActivitySampleConfirmDone.this, ActivityHome.class);
                startActivity(intent);
                finish();
            }
        });


    }
}
