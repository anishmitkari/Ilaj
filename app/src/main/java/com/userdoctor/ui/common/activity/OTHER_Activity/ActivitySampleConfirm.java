package com.userdoctor.ui.common.activity.OTHER_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.userdoctor.R;
import com.userdoctor.ui.common.utils.NetworkUtil;

public class ActivitySampleConfirm extends AppCompatActivity {

    Button btn_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_confirm);

        NetworkUtil.isNetworkConnected(ActivitySampleConfirm.this);

        btn_confirm=findViewById(R.id.btn_confirm);

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(ActivitySampleConfirm.this,ActivitySampleConfirmDone.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
