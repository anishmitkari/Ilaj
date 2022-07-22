package com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.userdoctor.R;
import com.userdoctor.ui.common.Interface.Api_Call;
import com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Adapter.VaccinationTimeAdapter;
import com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Vaccination_Model.Vaccination_Time_Slots.VaccinationDay;
import com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Vaccination_Model.Vaccination_Time_Slots.VaccinationTimeSlots;
import com.userdoctor.ui.common.utils.APIClient;
import com.userdoctor.ui.common.utils.DialogUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;

import static com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Adapter.SubTimeVaccinationAdapter.timeselect_vacc;


public class Vaccination_Time_Page extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {
    Button done;
    Spinner spinner;
    String spin_id;
    List<VaccinationDay> vaccinationDayslist;
    ImageView iv_back;
    String Select_Spinner_id;
    String mStrStartDate, mStrendDate;
    TextView mtextstartdate, mtextenddate;
    LinearLayout startdate, enddate;
    RecyclerView recycler_view2;
    private int mYear, mMonth, mDay, mHour, mMinute;
    VaccinationTimeAdapter vaccinationTimeAdapter;


    String mgetStrPatient_name,mgetStrAge,mgetStrNumber,mgetStrEmail,mgetStrAddress,mgetStrLandmark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination__time__page);
        initView();




        Bundle extras2 = getIntent().getExtras();
        if (extras2 != null) {
            mgetStrPatient_name = extras2.getString("patient_name");
            mgetStrAge = extras2.getString("age");
            mgetStrNumber = extras2.getString("number");
            mgetStrAddress = extras2.getString("address");
            mgetStrLandmark = extras2.getString("landmark");

        }

        spinner.setOnItemSelectedListener(this);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mStrStartDate != null ) {
                    Toast.makeText(Vaccination_Time_Page.this, " " + mStrStartDate, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Vaccination_Time_Page.this, " Please Choose Date", Toast.LENGTH_SHORT).show();
                   return;
                }
                if (Select_Spinner_id.equals("0"))
                {
                   Select_Spinner_id="";
                }
                else
                {
                  //  Toast.makeText(Vaccination_Time_Page.this, "spin "+Select_Spinner_id, Toast.LENGTH_SHORT).show();
                }

                String mSelectTime = timeselect_vacc.toString().replace("[", "").replace("]", "");


                if (mSelectTime.isEmpty())
                {
                    Toast.makeText(Vaccination_Time_Page.this, "please select time ", Toast.LENGTH_SHORT).show();
                    return;
                }






                Intent intent=new Intent(Vaccination_Time_Page.this,Vaccination_Next_Page.class);
                intent.putExtra("patient_name", mgetStrPatient_name);
                intent.putExtra("age", mgetStrAge);
                intent.putExtra("number", mgetStrNumber);
                intent.putExtra("address", mgetStrAddress);
                intent.putExtra("landmark", mgetStrLandmark);
                intent.putExtra("start_date",mStrStartDate);
                intent.putExtra("end_date",mStrendDate);
                intent.putExtra("spinner_id",Select_Spinner_id);
                intent.putExtra("selct_time",mSelectTime);
                startActivity(intent);



                // startActivity(new Intent(Vaccination_Time_Page.this, Vaccination_Next_Page.class));


            }
        });


        mDateTimeFunApi();


        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(Vaccination_Time_Page.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                mStrStartDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                mtextstartdate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                //   Toast.makeText(StartDateMonth.this, dayOfMonth + "-" + (monthOfYear + 1) + "-" + year, Toast.LENGTH_SHORT).show();


                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

                datePickerDialog.show();


            }
        });

        enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Vaccination_Time_Page.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                mStrendDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                mtextenddate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                //  Toast.makeText(StartDateMonth.this, dayOfMonth + "-" + (monthOfYear + 1) + "-" + year, Toast.LENGTH_SHORT).show();
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

                datePickerDialog.show();


            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Vaccination_Time_Page.this,Vaccination_Next_Page.class));


            }
        });




    }

    public void initView() {
        iv_back=findViewById(R.id.iv_back);
        mtextstartdate = findViewById(R.id.mtextstartdate);
        mtextenddate = findViewById(R.id.enddate);
        startdate = findViewById(R.id.start_date);
        enddate = findViewById(R.id.end_date);
        recycler_view2 = findViewById(R.id.recycler_view2);
        done = findViewById(R.id.done);
        spinner = findViewById(R.id.spinner);
    }

    public void mDateTimeFunApi() {

        DialogUtil.show(Vaccination_Time_Page.this);
        Api_Call apiInterface = APIClient.getClient().create(Api_Call.class);
        Call<VaccinationTimeSlots> call = apiInterface.getCurrentTime();
        call.enqueue(new Callback<VaccinationTimeSlots>() {
            @Override
            public void onResponse(Call<VaccinationTimeSlots> call, retrofit2.Response<VaccinationTimeSlots> response) {
                try {
                    if (response != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            DialogUtil.dismiss();
                            vaccinationDayslist = response.body().getVaccinationDays();
                            List<String> listSpinner = new ArrayList<String>();
                             /* add value in 0 index in model */

                            VaccinationDay vaccinationDay = new VaccinationDay("Select", "0");
                            vaccinationDayslist.add(0, vaccinationDay);

                            for (int i = 0; i < vaccinationDayslist.size(); i++) {
                                spin_id = vaccinationDayslist.get(i).getId();
                                listSpinner.add(vaccinationDayslist.get(i).getRoutin());
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(Vaccination_Time_Page.this, android.R.layout.simple_spinner_item, listSpinner);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                            vaccinationTimeAdapter = new VaccinationTimeAdapter(response.body().getVaccinationTimeDatum(), Vaccination_Time_Page.this);
                            LinearLayoutManager manager = new LinearLayoutManager(Vaccination_Time_Page.this, RecyclerView.VERTICAL, false);
                            recycler_view2.setLayoutManager(manager);
                            recycler_view2.setAdapter(vaccinationTimeAdapter);


                        } else {
                            DialogUtil.dismiss();
                            Toast.makeText(Vaccination_Time_Page.this, "No list found", Toast.LENGTH_SHORT).show();
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


    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        try {
            Select_Spinner_id = vaccinationDayslist.get(position).getId();
        } catch (Exception e) {
            Toast.makeText(this, "e" + e, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


}
