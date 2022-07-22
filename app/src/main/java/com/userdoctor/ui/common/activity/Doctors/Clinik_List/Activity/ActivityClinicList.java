   package com.userdoctor.ui.common.activity.Doctors.Clinik_List.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.userdoctor.R;
import com.userdoctor.ui.common.activity.Doctors.Clinik_List.Fragment.FragmentClinikMap;
import com.userdoctor.ui.common.activity.Home_Activity.ActivityHome;
import com.userdoctor.ui.common.activity.Doctors.Clinik_List.Fragment.FragmentClinicList;

   public class ActivityClinicList extends AppCompatActivity implements View.OnClickListener {


    private ImageView iv_back,searchbar;
    private TextView tv_clinic_list, tv_clinic_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_list);
        initView();
        clickListner();
    }



    @SuppressLint("WrongConstant")
    private void initView() {
        searchbar=findViewById(R.id.searchbar);
        tv_clinic_list = findViewById(R.id.tv_clinic_list);
        tv_clinic_map = findViewById(R.id.tv_clinic_map);
        iv_back = findViewById(R.id.iv_back);
        tv_clinic_list.setBackgroundResource(R.drawable.selection_bottom_line_bg);
        tv_clinic_map.setBackgroundResource(R.drawable.edittext_bottom_line);
        FragmentClinicList fdr = new FragmentClinicList();
        replaceFragment(fdr, false, R.id.frame_layout);
    }

    private void clickListner() {
        searchbar.setOnClickListener(this);
        tv_clinic_list.setOnClickListener(this);
        tv_clinic_map.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_clinic_list:
                tv_clinic_list.setBackgroundResource(R.drawable.selection_bottom_line_bg);
                tv_clinic_map.setBackgroundResource(R.drawable.edittext_bottom_line);
                FragmentClinicList fdr = new FragmentClinicList();
                replaceFragment(fdr, false, R.id.frame_layout);
                break;
            case R.id.searchbar:
                startActivity(new Intent(ActivityClinicList.this, SearchClinikList.class));
                break;

            case R.id.iv_back:
                startActivity(new Intent(ActivityClinicList.this, ActivityHome.class));
                break;


            case R.id.tv_clinic_map:
                tv_clinic_map.setBackgroundResource(R.drawable.selection_bottom_line_bg);
                tv_clinic_list.setBackgroundResource(R.drawable.edittext_bottom_line);
                FragmentClinikMap mapdr = new FragmentClinikMap();
                replaceFragment(mapdr, false, R.id.frame_layout);

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
