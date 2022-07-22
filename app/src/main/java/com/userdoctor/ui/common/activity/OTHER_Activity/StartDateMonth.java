package com.userdoctor.ui.common.activity.OTHER_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.utils.DateUtils;
import com.userdoctor.R;
import com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Activity.Activity_Vaccination;
import com.userdoctor.ui.common.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StartDateMonth extends AppCompatActivity {
    TextView mtextstartdate, mtextenddate;
    Button done;
    String value;
    LinearLayout startdate, enddate;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_date_month);
        NetworkUtil.isNetworkConnected(StartDateMonth.this);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("days_id");
        }




        startdate = findViewById(R.id.start_date);
        enddate = findViewById(R.id.end_date);
        mtextstartdate = findViewById(R.id.mtextstartdate);
        mtextenddate = findViewById(R.id.enddate);
        done = findViewById(R.id.done);


        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(StartDateMonth.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                mtextstartdate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                //   Toast.makeText(StartDateMonth.this, dayOfMonth + "-" + (monthOfYear + 1) + "-" + year, Toast.LENGTH_SHORT).show();


                            }
                        }, mYear, mMonth, mDay);
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


                DatePickerDialog datePickerDialog = new DatePickerDialog(StartDateMonth.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                mtextenddate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                //  Toast.makeText(StartDateMonth.this, dayOfMonth + "-" + (monthOfYear + 1) + "-" + year, Toast.LENGTH_SHORT).show();


                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();


            }
        });


        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);

        calendarView.setOnForwardPageChangeListener(() ->

                Toast.makeText(getApplicationContext(), "Forward", Toast.LENGTH_SHORT).show());

        calendarView.setOnPreviousPageChangeListener(() ->
                Toast.makeText(getApplicationContext(), "Previous", Toast.LENGTH_SHORT).show());

        calendarView.setSelectedDates(getSelectedDays());

        List<EventDay> events = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 7);
        events.add(new EventDay(cal, R.mipmap.ic_launcher));

        calendarView.setEvents(events);


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (Calendar calendar : calendarView.getSelectedDates()) {

                    System.out.println("check date " + calendar.getTime());

                    Toast.makeText(getApplicationContext(),
                            calendar.getTime().toString(),
                            Toast.LENGTH_SHORT).show();


                }


                startActivity(new Intent(StartDateMonth.this, Activity_Vaccination.class));


            }
        });


    }


    private List<Calendar> getSelectedDays() {
        List<Calendar> calendars = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Calendar calendar = DateUtils.getCalendar();
            calendar.add(Calendar.DAY_OF_MONTH, i);
            calendars.add(calendar);
        }

        return calendars;
    }

}
