package com.userdoctor.ui.common.activity.OTHER_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.userdoctor.R;
import com.userdoctor.ui.common.utils.NetworkUtil;

public class ActivityPersonalInfo extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_back;
    String dr_id, abut_self, address, contact_no, degree_name, time;
    TextView about, mTextaddress, mtexttime, phone, certificate, degree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);


        NetworkUtil.isNetworkConnected(ActivityPersonalInfo.this);




        Bundle b = getIntent().getExtras();
        address = b.getString("hospital_address");
        contact_no = b.getString("contact_no");
        degree_name = b.getString("degree_name");



        initView();
        clickListner();




        phone.setText(contact_no);
        mTextaddress.setText(address);
        degree.setText(degree_name);



    }


    private void initView() {
        iv_back = findViewById(R.id.iv_back);
        mTextaddress = findViewById(R.id.address);
        phone = findViewById(R.id.phone);
        degree = findViewById(R.id.degree);


    }

    private void clickListner() {
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }
}
