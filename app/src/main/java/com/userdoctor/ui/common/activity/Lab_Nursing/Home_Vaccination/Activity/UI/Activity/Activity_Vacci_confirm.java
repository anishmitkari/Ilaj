package com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.userdoctor.R;
import com.userdoctor.ui.common.activity.Home_Activity.ActivityHome;

public class Activity_Vacci_confirm extends AppCompatActivity {
    Button btn_main_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__vacci_confirm);

        btn_main_menu=findViewById(R.id.btn_main_menu);

        btn_main_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Activity_Vacci_confirm.this, ActivityHome.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
