package com.userdoctor.ui.common.activity.OTHER_Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.userdoctor.R;
import com.userdoctor.ui.common.activity.Home_Activity.ActivityHome;
import com.userdoctor.ui.common.activity.Appointments.Fragments.FragmentDRCompleteList;
import com.userdoctor.ui.common.activity.Appointments.Fragments.FragmentDRUpcomingList;
import com.userdoctor.ui.common.utils.NetworkUtil;

public class ActivityTestBooking extends AppCompatActivity {
Context context;
    TextView tv_upcoming_list,tv_complete_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_booking);
        NetworkUtil.isNetworkConnected(ActivityTestBooking.this);

        initView();


    }

    private void initView() {

        context=ActivityTestBooking.this;

        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityHome.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });


        tv_upcoming_list = findViewById(R.id.tv_upcoming_list);
        tv_complete_list = findViewById(R.id.tv_complete_list);


        //first time call
        tv_upcoming_list.setBackgroundResource(R.drawable.selection_bottom_line_bg);
        tv_complete_list.setBackgroundResource(R.drawable.edittext_bottom_line);
        //call fragment
        FragmentDRUpcomingList fdr = new FragmentDRUpcomingList();
        replaceFragment(fdr, false, R.id.frame_layout2);


        tv_upcoming_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //referece = "1";
                // tv_dr_list.setTextColor(Color.parseColor("#FFFFFF"));
                //tv_skill.setTextColor(Color.parseColor("#ed1256"));
                //tv_degree.setBackgroundResource(R.color.colorAccent);
                //tv_degree.setBackgroundResource(R.drawable.squere_corner_border_bg);
                tv_upcoming_list.setBackgroundResource(R.drawable.selection_bottom_line_bg);
                tv_complete_list.setBackgroundResource(R.drawable.edittext_bottom_line);


                FragmentDRUpcomingList fdr = new FragmentDRUpcomingList();
                replaceFragment(fdr, false, R.id.frame_layout2);

                /*if (NetworkUtil.isNetworkConnected(this)) {
                    try {
                        //degree_url = API.BASE_URL + "contractor_degree_list.php";
                        DegreeListApi(degree_url);

                    } catch (NullPointerException e) {
                        ToastClass.showToast(this, getString(R.string.too_slow));
                    }
                } else {
                    ToastClass.showToast(this, getString(R.string.no_internet_access));
                }*/


            }
        });

        tv_complete_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // referece = "2";
                /*tv_dr_map.setTextColor(Color.parseColor("#FFFFFF"));
                tv_degree.setTextColor(Color.parseColor("#ed1256"));
                tv_skill.setBackgroundResource(R.color.colorAccent);
                 tv_skill.setBackgroundResource(R.drawable.squere_corner_border_bg);
                tv_degree.setBackgroundResource(R.drawable.squere_border_red);*/


                tv_complete_list.setBackgroundResource(R.drawable.selection_bottom_line_bg);
                tv_upcoming_list.setBackgroundResource(R.drawable.edittext_bottom_line);

                FragmentDRCompleteList mapdr = new FragmentDRCompleteList();
                replaceFragment(mapdr, false, R.id.frame_layout2);

                /*if (NetworkUtil.isNetworkConnected(this)) {
                    try {
                        skill_url = API.BASE_URL + "contractor_skill_list.php";
                        SkillsListApi(skill_url);

                    } catch (NullPointerException e) {
                        ToastClass.showToast(this, getString(R.string.too_slow));
                    }
                } else {
                    ToastClass.showToast(this, getString(R.string.no_internet_access));
                }*/


            }
        });

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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ActivityTestBooking.this, ActivityHome.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}

