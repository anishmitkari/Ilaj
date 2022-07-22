package com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Activity;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.userdoctor.R;
import com.userdoctor.ui.common.Interface.Api_Call;
import com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Adapter.DateAdapter;
import com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Adapter.TimeAdapter;
import com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Sample.Current_Date_Model.CurrentDate;
import com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Sample.Current_Date_Model.DateModel;
import com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Sample.GetDate_TimeModel.GetdayWisetime;
import com.userdoctor.ui.common.utils.APIClient;
import com.userdoctor.ui.common.utils.DialogUtil;
import com.userdoctor.ui.common.utils.NetworkUtil;

import retrofit2.Call;
import retrofit2.Callback;

import static com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Adapter.DateAdapter.dateset;
import static com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Adapter.SubTimeAdapter.timeselect;

public class Activity_Sample_Book_Time extends AppCompatActivity {
    LinearLayout ll_same;
    RecyclerView recyclerView, recycler_view2;
    DateAdapter dateAdapter;
    TimeAdapter timeAdapter;
    TextView current_date;
    Button submit;
    ImageView iv_back;
    String GlobalDate, mStrPatient_name, mStrAddress, mStrinvestigation, mStrNumber,  mStrLandmark, mStrAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__sample__book__time);
        init();

        Bundle extras2 = getIntent().getExtras();
        if (extras2 != null) {

            mStrPatient_name = extras2.getString("patient_name");
            mStrinvestigation = extras2.getString("investigation");
            mStrAge = extras2.getString("age");
            mStrNumber = extras2.getString("number");
            mStrAddress = extras2.getString("address");
            mStrLandmark = extras2.getString("landmark");

        }


        NetworkUtil.isNetworkConnected(Activity_Sample_Book_Time.this);

        mFunCallDateList();
        // mFunTimeList();


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mSelectDate = dateset.toString().replace("[", "").replace("]", "");
                String mSelectTime = timeselect.toString().replace("[", "").replace("]", "");


                if (!mSelectDate.isEmpty() && !mSelectTime.isEmpty()) {
                    Intent intent = new Intent(Activity_Sample_Book_Time.this, Activity_Sample_collection.class);
                    intent.putExtra("patient_name", mStrPatient_name);
                    intent.putExtra("investigation", mStrinvestigation);
                    intent.putExtra("age", mStrAge);
                    intent.putExtra("number", mStrNumber);
                    intent.putExtra("address", mStrAddress);
                    intent.putExtra("landmark", mStrLandmark);
                    intent.putExtra("date", mSelectDate);
                    intent.putExtra("time", mSelectTime);
                    startActivity(intent);
                } else {
                    Toast.makeText(Activity_Sample_Book_Time.this, "please select date time both", Toast.LENGTH_SHORT).show();
                }
            }


        });


    }


    public void init() {
        iv_back=findViewById(R.id.iv_back);
        recyclerView = findViewById(R.id.recycler_view);
        current_date = findViewById(R.id.current_date);
        recycler_view2 = findViewById(R.id.recycler_view2);
        submit = findViewById(R.id.submit);
    }

    public void mFunCallDateList() {
        Api_Call apiInterface = APIClient.getClient().create(Api_Call.class);
        Call<DateModel> call = apiInterface.getDate();
        call.enqueue(new Callback<DateModel>() {
            @Override
            public void onResponse(Call<DateModel> call, retrofit2.Response<DateModel> response) {
                try {
                    if (response != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            CurrentDate currentDate = response.body().getCurrentDate();
                            String formated_date = currentDate.getFormated();
                            current_date.setText("Today " + formated_date);
                            dateAdapter = new DateAdapter(response.body().getDates(), Activity_Sample_Book_Time.this);
                            LinearLayoutManager manager = new LinearLayoutManager(Activity_Sample_Book_Time.this, RecyclerView.HORIZONTAL, false);
                            recyclerView.setLayoutManager(manager);
                            recyclerView.setAdapter(dateAdapter);
                            // dateAdapter.notifyDataSetChanged();


                        } else {
                            Toast.makeText(Activity_Sample_Book_Time.this, "No list found", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    Log.e("error", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<DateModel> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }

    public void yourDesiredMethod(String value) {
        mDateFunApi(value);
    }

    public void mDateFunApi(final String date) {
        DialogUtil.show(Activity_Sample_Book_Time.this);
        Api_Call apiInterface = APIClient.getClient().create(Api_Call.class);
        Call<GetdayWisetime> call = apiInterface.getDayWiseTime(date);
        call.enqueue(new Callback<GetdayWisetime>() {
            @Override
            public void onResponse(Call<GetdayWisetime> call, retrofit2.Response<GetdayWisetime> response) {
                try {
                    if (response != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            DialogUtil.dismiss();


                            timeAdapter = new TimeAdapter(response.body().getDatum(), Activity_Sample_Book_Time.this);
                            LinearLayoutManager manager = new LinearLayoutManager(Activity_Sample_Book_Time.this, RecyclerView.VERTICAL, false);
                            recycler_view2.setLayoutManager(manager);
                            recycler_view2.setAdapter(timeAdapter);


                        } else {
                            DialogUtil.dismiss();
                            Toast.makeText(Activity_Sample_Book_Time.this, "No list found", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    Log.e("catch error", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<GetdayWisetime> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
