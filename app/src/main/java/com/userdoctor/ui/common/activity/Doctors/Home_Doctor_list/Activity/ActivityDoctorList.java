package com.userdoctor.ui.common.activity.Doctors.Home_Doctor_list.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.userdoctor.R;
import com.userdoctor.ui.common.activity.Home_Activity.ActivityHome;
import com.userdoctor.ui.common.activity.Doctors.Home_Doctor_list.Fragment.FragmentDrList;
import com.userdoctor.ui.common.activity.Doctors.Home_Doctor_list.Fragment.FragmentDrMap2;

public class ActivityDoctorList extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_back,searchbar;
    private TextView tv_dr_list, tv_dr_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);
        initView();
        clickListner();
    }

    private void clickListner() {

        tv_dr_list.setOnClickListener(this);
        tv_dr_map.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        searchbar.setOnClickListener(this);

    }

    @SuppressLint("WrongConstant")
    private void initView() {
        tv_dr_list = findViewById(R.id.tv_dr_list);
        tv_dr_map = findViewById(R.id.tv_dr_map);
        iv_back = findViewById(R.id.iv_back);
        searchbar=findViewById(R.id.searchbar);
        tv_dr_list.setBackgroundResource(R.drawable.selection_bottom_line_bg);
        tv_dr_map.setBackgroundResource(R.drawable.edittext_bottom_line);
        FragmentDrList fdr = new FragmentDrList();
        replaceFragment(fdr, false, R.id.frame_layout);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_dr_list:
                tv_dr_list.setBackgroundResource(R.drawable.selection_bottom_line_bg);
                tv_dr_map.setBackgroundResource(R.drawable.edittext_bottom_line);
                FragmentDrList fdr = new FragmentDrList();
                replaceFragment(fdr, false, R.id.frame_layout);
                break;

            case R.id.tv_dr_map:
                tv_dr_map.setBackgroundResource(R.drawable.selection_bottom_line_bg);
                tv_dr_list.setBackgroundResource(R.drawable.edittext_bottom_line);
                FragmentDrMap2 mapdr = new FragmentDrMap2();
                replaceFragment(mapdr, false, R.id.frame_layout);
                break;

            case R.id.searchbar:
                startActivity(new Intent(ActivityDoctorList.this,SearchDoctorList.class));
                break;

            case R.id.iv_back:
                startActivity(new Intent(ActivityDoctorList.this, ActivityHome.class));

                break;


        }

    }

    /*....................replaceFragment()....................................*/
    @SuppressLint("WrongConstant")
    public void replaceFragment(Fragment fragment, boolean addToBackStack, int containerId) {
        String backStackName = fragment.getClass().getName();
        FragmentManager fm = getSupportFragmentManager();
        int i = fm.getBackStackEntryCount();
        while (i > 0) {
            fm.popBackStackImmediate();
            i--;
        }
        boolean fragmentPopped = getFragmentManager().popBackStackImmediate(backStackName, 0);
        if (!fragmentPopped) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(containerId, fragment, backStackName).setTransition(FragmentTransaction.TRANSIT_UNSET);
            if (addToBackStack)
                transaction.addToBackStack(backStackName);
            transaction.commit();
        }
    }

    public void addFragment(Fragment fragment, boolean addToBackStack, int containerId) {
        String backStackName = fragment.getClass().getName();
        boolean fragmentPopped = getFragmentManager().popBackStackImmediate(backStackName, 0);
        if (!fragmentPopped) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(containerId, fragment, backStackName).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            if (addToBackStack)
                transaction.addToBackStack(backStackName);
            transaction.commit();
        }
    }

}
