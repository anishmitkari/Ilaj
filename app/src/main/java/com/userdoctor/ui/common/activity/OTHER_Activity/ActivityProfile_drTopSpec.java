package com.userdoctor.ui.common.activity.OTHER_Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.userdoctor.R;
import com.userdoctor.ui.common.utils.NetworkUtil;

public class ActivityProfile_drTopSpec extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_dr_top_spec);
        NetworkUtil.isNetworkConnected(ActivityProfile_drTopSpec.this);


    }
}
