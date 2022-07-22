package com.userdoctor.ui.common.activity.OTHER_Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.ToxicBakery.viewpager.transforms.ZoomOutSlideTransformer;
import com.userdoctor.R;
import com.userdoctor.ui.common.activity.Login_Section.ActivityLogin;
import com.userdoctor.ui.common.adapter.WelcomePagerAdapter;
import com.userdoctor.ui.common.utils.NetworkUtil;


public class ActivityWelcome extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    ViewPager mViewPager;
    private WelcomePagerAdapter mAdapter;
    private LinearLayout viewPagerCountDots;
    private int dotsCount;
    private ImageView[] dots;
    private Context mContext;
    //int[] mResources = {R.drawable.slider_image1, R.drawable.slider_image2, R.drawable.slider_image3};
    int[] mResources = {R.drawable.slider1, R.drawable.slider2, R.drawable.slider3};

    private TextView tv_login;
    private TextView tv_skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_well_come);
        NetworkUtil.isNetworkConnected(ActivityWelcome.this);
        intView();
        clickListner();
        mContext = ActivityWelcome.this;

        tv_login = findViewById(R.id.tv_login);
        mViewPager = findViewById(R.id.slider_pager);
        viewPagerCountDots = findViewById(R.id.linear_layout);

        mAdapter = new WelcomePagerAdapter(ActivityWelcome.this, mContext, mResources);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setPageTransformer(true, new ZoomOutSlideTransformer());
        mViewPager.setCurrentItem(0);
        mViewPager.setOnPageChangeListener(this);
        setPageViewIndicator();


        //***********************************************************************
    }



    private void intView() {
        tv_skip = findViewById(R.id.tv_skip);
        tv_login =  findViewById(R.id.tv_login);

    }

    private void clickListner() {
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ActivityLogin.class);
                startActivity(intent);
            }
        });

        tv_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ActivityLogin.class);
                startActivity(intent);
            }
        });
    }

    private void setPageViewIndicator() {

        Log.d("###setPageViewIndicator", " : called");
        dotsCount = mAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(mContext);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.circle_inactive));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    20,
                    20
            );

            params.setMargins(4, 0, 4, 0);

            final int presentPosition = i;
            dots[presentPosition].setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    mViewPager.setCurrentItem(presentPosition);
                    return true;
                }

            });


            viewPagerCountDots.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.circle_active));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        Log.e("###onPageSelected, pos ", String.valueOf(position));
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.circle_inactive));
        }

        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.circle_active));

        if (position + 1 == dotsCount) {
            tv_skip.setText("Get Start");

        } else {
            tv_skip.setText("Skip");

        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void scrollPage(int position) {
        mViewPager.setCurrentItem(position);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // clickDone();

    }

//    public void clickDone() {
//        new AlertDialog.Builder(this)
//                .setIcon(R.mipmap.ic_launcher)
//                .setTitle(getString(R.string.title_dialog))
//                .setMessage(getString(R.string.msg_dialog))
//                .setPositiveButton(getString(R.string.yes_dialog), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                        Intent i = new Intent();
//                        i.setAction(Intent.ACTION_MAIN);
//                        i.addCategory(Intent.CATEGORY_HOME);
//                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(i);
//                        finish();
//                    }
//                })
//                .setNegativeButton(getString(R.string.no_dialog), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                })
//                .show();
//    }


}
