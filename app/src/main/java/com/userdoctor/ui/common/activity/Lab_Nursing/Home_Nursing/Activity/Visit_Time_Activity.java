package com.userdoctor.ui.common.activity.Lab_Nursing.Home_Nursing.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.userdoctor.R;
import com.userdoctor.ui.common.Interface.Api_Call;
import com.userdoctor.ui.common.activity.Lab_Nursing.Home_Nursing.Adapter.NursingTimeAdapter;
import com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Vaccination_Model.Vaccination_Time_Slots.VaccinationTimeSlots;
import com.userdoctor.ui.common.utils.APIClient;
import com.userdoctor.ui.common.utils.DialogUtil;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;

import static com.userdoctor.ui.common.activity.Lab_Nursing.Home_Nursing.Adapter.SubTimeNursingAdapter.timeselect_nursing;

public class Visit_Time_Activity extends AppCompatActivity implements View.OnClickListener {
    String mStrEndDate,mStrStartDate,mgetInvestigation, mgetStrPatient_name, mgetStrAge, mgetStrNumber, mgetStrEmail, mgetStrAddress, mgetStrLandmark, mStrTimeList, mStrDateList;
    RecyclerView recyclerView;
    LinearLayout start_date,end_date;
    Button button_done;
    DatePickerDialog datePickerDialog1;
    ImageView iv_back;
    TextView mtextstartdate,mtextenddate;
    private int mYear, mMonth, mDay, mHour, mMinute;
    NursingTimeAdapter nursingTimeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit__home_);
        initView();
        Bundle extras2 = getIntent().getExtras();
        if (extras2 != null) {
            mgetStrPatient_name = extras2.getString("patient_name");
            mgetInvestigation = extras2.getString("investigation");
            mgetStrAge = extras2.getString("age");
            mgetStrNumber = extras2.getString("number");
            mgetStrEmail = extras2.getString("email");
            mgetStrAddress = extras2.getString("address");
            mgetStrLandmark = extras2.getString("landmark");
        }

        mTimeFunApi();
    }

    public void mTimeFunApi() {
        DialogUtil.show(Visit_Time_Activity.this);
        Api_Call apiInterface = APIClient.getClient().create(Api_Call.class);
        Call<VaccinationTimeSlots> call = apiInterface.getCurrentTime();
        call.enqueue(new Callback<VaccinationTimeSlots>() {
            @Override
            public void onResponse(Call<VaccinationTimeSlots> call, retrofit2.Response<VaccinationTimeSlots> response) {
                try {
                    if (response != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            DialogUtil.dismiss();
                            nursingTimeAdapter = new NursingTimeAdapter(response.body().getVaccinationTimeDatum(), Visit_Time_Activity.this);
                            LinearLayoutManager manager = new LinearLayoutManager(Visit_Time_Activity.this, RecyclerView.VERTICAL, false);
                            recyclerView.setLayoutManager(manager);
                            recyclerView.setAdapter(nursingTimeAdapter);
                        } else {
                            DialogUtil.dismiss();
                            Toast.makeText(Visit_Time_Activity.this, "No list found", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    Log.e("catch error", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<VaccinationTimeSlots> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });

    }


    public void initView()
    {
        iv_back=findViewById(R.id.iv_back);
        recyclerView=findViewById(R.id.recycler_view);
        start_date=findViewById(R.id.start_date);
      //  end_date=findViewById(R.id.end_date);
        button_done=findViewById(R.id.button_done);
        mtextenddate=findViewById(R.id.enddate);
        mtextstartdate=findViewById(R.id.mtextstartdate);
         iv_back.setOnClickListener(this);
        start_date.setOnClickListener(this);
     //   end_date.setOnClickListener(this);
        button_done.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.start_date:
                final Calendar c1 = Calendar.getInstance();
                mYear = c1.get(Calendar.YEAR);
                mMonth = c1.get(Calendar.MONTH);
                mDay = c1.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog1 = new DatePickerDialog(Visit_Time_Activity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {


                                mStrStartDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                mtextstartdate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                //   Toast.makeText(StartDateMonth.this, dayOfMonth + "-" + (monthOfYear + 1) + "-" + year, Toast.LENGTH_SHORT).show();


                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog1.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

                datePickerDialog1.show();

                break;

          /*  case R.id.end_date:
                final Calendar c2 = Calendar.getInstance();
                mYear = c2.get(Calendar.YEAR);
                mMonth = c2.get(Calendar.MONTH);
                mDay = c2.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog2 = new DatePickerDialog(Visit_Time_Activity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                mStrEndDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                mtextenddate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                //  Toast.makeText(StartDateMonth.this, dayOfMonth + "-" + (monthOfYear + 1) + "-" + year, Toast.LENGTH_SHORT).show();
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog2.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog2.show();
                break;*/

            case R.id.button_done:
                if (mStrStartDate != null ) {
                } else {
                    Toast.makeText(Visit_Time_Activity.this, " Please Choose Date", Toast.LENGTH_SHORT).show();
                    return;
                }


                String mSelectTime = timeselect_nursing.toString().replace("[", "").replace("]", "");


                if (mSelectTime.isEmpty())
                {
                    Toast.makeText(Visit_Time_Activity.this, "please select time ", Toast.LENGTH_SHORT).show();
                    return;
                }


                Intent intent=new Intent(Visit_Time_Activity.this, Nursing_Detail_Time.class);
                intent.putExtra("patient_name", mgetStrPatient_name);
                intent.putExtra("investigation",mgetInvestigation);
                intent.putExtra("age", mgetStrAge);
                intent.putExtra("number", mgetStrNumber);
                intent.putExtra("email", mgetStrEmail);
                intent.putExtra("address", mgetStrAddress);
                intent.putExtra("landmark", mgetStrLandmark);
                intent.putExtra("start_date",mStrStartDate);
               // intent.putExtra("end_date",mStrEndDate);
                intent.putExtra("selct_time",mSelectTime);
                startActivity(intent);
                break;





            case R.id.iv_back:
                onBackPressed();
                break;
        }
        }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
