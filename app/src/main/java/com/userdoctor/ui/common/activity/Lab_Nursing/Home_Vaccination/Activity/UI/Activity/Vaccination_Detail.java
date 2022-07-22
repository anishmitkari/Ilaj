package com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.userdoctor.R;
import com.userdoctor.ui.common.Interface.Api_Call;
import com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Adapter.SlidingImageVaccination_Adapter;
import com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Vaccination_Model.VaccinationDetail;
import com.userdoctor.ui.common.utils.APIClient;
import com.userdoctor.ui.common.utils.NetworkUtil;
import com.userdoctor.ui.common.utils.PrefrenceManager;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;

public class Vaccination_Detail extends AppCompatActivity {
    String value, Test_Id;
    TextView title, description, certified, injection, e_reports, price;
    SlidingImageVaccination_Adapter slidingImageVaccination_adapter;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
      Button book_now;
      ImageView iv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination_detail);
        initView();


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Vaccination_Detail.this,Activity_Vaccination_Main.class));
            }
        });



        book_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Vaccination_Detail.this,Activity_Vaccination.class);
                intent.putExtra("Vaccination_ID",Test_Id);
                startActivity(intent);
              //  startActivity(new Intent(Vaccination_Detail.this,Activity_Vaccination.class));
            }
        });

        value=  PrefrenceManager.getVaccination_Main_Category_ID(Vaccination_Detail.this);
        NetworkUtil.isNetworkConnected(Vaccination_Detail.this);
        mFunApiCallDetail(value);


        System.out.println("check value"+value);


    }


    public void initView() {
        price=findViewById(R.id.price);
        iv_back=findViewById(R.id.iv_back);
        book_now=findViewById(R.id.book_now);
        mPager = (ViewPager) findViewById(R.id.pager);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
    }


    public void mFunApiCallDetail(final String value) {
        Api_Call apiInterface = APIClient.getClient().create(Api_Call.class);
        Call<VaccinationDetail> call = apiInterface.getVaccinationDetail(value);
        call.enqueue(new Callback<VaccinationDetail>() {
            @Override
            public void onResponse(Call<VaccinationDetail> call, retrofit2.Response<VaccinationDetail> response) {
                try {
                    if (response != null) {

                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            String mStrTitle = response.body().getData().getName();
                            String mStrPrice = response.body().getData().getPrice();


                            System.out.println("check the response " + response.body().getData().getName());
                            System.out.println("check the banner " + response.body().getBanners());

                             Log.e("check the banners",response.body().getBanners().toString());


                            Test_Id = response.body().getData().getId();

                            title.setText(response.body().getData().getName());
                        //    description.setText(response.body().getData().getDescription());
                            price.setText("USD "+response.body().getData().getPrice());

                            PrefrenceManager.setVaccinationIdItem(Vaccination_Detail.this, Test_Id);
                            PrefrenceManager.setVaccinationNameItem(Vaccination_Detail.this, mStrTitle);


                            slidingImageVaccination_adapter = new SlidingImageVaccination_Adapter(response.body().getBanners(), Vaccination_Detail.this);
                            mPager.setAdapter(slidingImageVaccination_adapter);
                            CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);
                            indicator.setViewPager(mPager);
                            final float density = getResources().getDisplayMetrics().density;
                            indicator.setRadius(5 * density);
                            NUM_PAGES = response.body().getBanners().size();
                            final Handler handler = new Handler();
                            final Runnable Update = new Runnable() {

                                public void run() {
                                    if (currentPage == NUM_PAGES) {
                                        currentPage = 0;
                                    }
                                    mPager.setCurrentItem(currentPage++, true);
                                }
                            };
                            Timer swipeTimer = new Timer();
                            swipeTimer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    handler.post(Update);
                                }
                            }, 3000, 3000);

                            // Pager listener over indicator
                            indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                                @Override
                                public void onPageSelected(int position) {
                                    currentPage = position;
                                }

                                @Override
                                public void onPageScrolled(int pos, float arg1, int arg2) {
                                }

                                @Override
                                public void onPageScrollStateChanged(int pos) {
                                }
                            });
                        } else {
                            Toast.makeText(Vaccination_Detail.this, "No list found", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    Log.e("error catch", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<VaccinationDetail> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }


}
