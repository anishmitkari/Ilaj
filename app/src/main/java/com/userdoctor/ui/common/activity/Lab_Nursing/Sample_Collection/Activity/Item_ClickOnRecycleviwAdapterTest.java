package com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Activity;

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
import com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Sample.DetailPage;
import com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Adapter.SlidingImage_Adapter;
import com.userdoctor.ui.common.utils.APIClient;
import com.userdoctor.ui.common.utils.NetworkUtil;
import com.userdoctor.ui.common.utils.PrefrenceManager;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;

public class Item_ClickOnRecycleviwAdapterTest extends AppCompatActivity {
    String value ;
    String Test_Id;
    Button book_now;
    TextView title,desc,certified,injection,e_reports,price;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    ImageView iv_back;
   // private static final Integer[] IMAGES = {R.drawable.first_slide_image, R.drawable.second_slider_image, R.drawable.third_slider_image};
    SlidingImage_Adapter slidingImage_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_vaccination_detail);
        init();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("id");
            System.out.println("check is "+ value);
        }
        NetworkUtil.isNetworkConnected(Item_ClickOnRecycleviwAdapterTest.this);

        mFunApiCallDetail(value);

        book_now=findViewById(R.id.book_now);
        iv_back=findViewById(R.id.iv_back);



        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             onBackPressed();
            }
        });
        book_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Item_ClickOnRecycleviwAdapterTest.this,Activity_Sample_collection.class);
                intent.putExtra("test_id",Test_Id);
                startActivity(intent);
            }
        });

    }


    public void mFunApiCallDetail(final String value)
    { Api_Call apiInterface = APIClient.getClient().create(Api_Call.class);
        Call<DetailPage> call = apiInterface.getDtail(value);
        call.enqueue(new Callback<DetailPage>() {
            @Override
            public void onResponse(Call<DetailPage> call, retrofit2.Response<DetailPage> response) {
                try {
                    if (response != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                             String mStrTitle=response.body().getData().getSubcatName();
                             String mStrDesc=response.body().getData().getDescription();
                             String mStrPrice=response.body().getData().getPrice();
                            Test_Id=response.body().getData().getId();
                            title.setText(mStrTitle);
                            desc.setText(mStrDesc);
                            price.setText("USD "+mStrPrice);

                            PrefrenceManager.setSampleIdItem(Item_ClickOnRecycleviwAdapterTest.this,Test_Id);
                            PrefrenceManager.setSampleTestNameItem(Item_ClickOnRecycleviwAdapterTest.this,mStrTitle);



                            slidingImage_adapter = new SlidingImage_Adapter(response.body().getBanners(),Item_ClickOnRecycleviwAdapterTest.this);
                            mPager.setAdapter(slidingImage_adapter);
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
                            Toast.makeText(Item_ClickOnRecycleviwAdapterTest.this, "No list found", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    Log.e("error catch", e.getMessage());
                }
            }
            @Override
            public void onFailure(Call<DetailPage> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }


    private void init() {
        mPager = (ViewPager) findViewById(R.id.pager);
        title = findViewById(R.id.title);
        desc = findViewById(R.id.description);
        certified = findViewById(R.id.certifiedlabs);
        e_reports = findViewById(R.id.reports);
        price = findViewById(R.id.price); }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
