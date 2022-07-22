package com.userdoctor.ui.common.activity.OTHER_Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.userdoctor.R;
import com.userdoctor.ui.common.adapter.CheckUpListAdapter;
import com.userdoctor.ui.common.model.CheckUpData;
import com.userdoctor.ui.common.utils.DateFarmateChange;
import com.userdoctor.ui.common.utils.NetworkUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ActivityBookAppointMent extends AppCompatActivity {
    private String serverDate;
    private TextView tv_date, tv_time, tv_bookAppointMent;
    private LinearLayout ll_checkup, ll_date, ll_time;
     String format;
    private RecyclerView recycler_view;
    private CheckUpListAdapter mAdapter;
    private List<CheckUpData> checkUpList;
    private Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appoint_ment);
        NetworkUtil.isNetworkConnected(ActivityBookAppointMent.this);

        initView();
        ClickListner();
    }

    @SuppressLint("WrongConstant")
    private void initView() {
        checkUpList = new ArrayList<>();
        tv_date = findViewById(R.id.tv_date);
        tv_time = findViewById(R.id.tv_time);
        ll_date = findViewById(R.id.ll_date);
        ll_time = findViewById(R.id.ll_time);
        ll_checkup = findViewById(R.id.ll_checkup);
        tv_bookAppointMent = findViewById(R.id.tv_bookAppointMent);


    }

    private void ClickListner() {
        ll_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDate();
            }
        });

        ll_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTime();
            }
        });

        ll_checkup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCheckUpDialog();
            }
        });

        tv_bookAppointMent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openConfirmDialog();
            }
        });
    }


    private void getTime() {
        Calendar mcurrentTime = Calendar.getInstance();
        // int hour, minute;
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        /*if (tv_time.getText().toString().trim().equals("")) {
            // Get Current Date
            Calendar c = Calendar.getInstance();
            hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            minute = mcurrentTime.get(Calendar.MINUTE);

        } else {
            //when edit then open calender select time
            String[] nameArray = tv_time.getText().toString().split(":");
            hour = Integer.parseInt(nameArray[1]);
            minute = Integer.parseInt(nameArray[0]);

            hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            minute = mcurrentTime.get(Calendar.MINUTE);
        }*/

        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(ActivityBookAppointMent.this, R.style.calender_dialog_theme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                if (selectedHour == 0) {

                    selectedHour += 12;

                    format = "AM";
                } else if (selectedHour == 12) {

                    format = "PM";

                } else if (selectedHour > 12) {

                    selectedHour -= 12;

                    format = "PM";

                } else {

                    format = "AM";
                }


                int hour = selectedHour % 12;
                tv_time.setText(String.format("%02d:%02d %s", hour == 0 ? 12 : hour,
                        selectedMinute, selectedHour < 12 ? "am" : "pm"));


                // tv_time.setText(selectedHour + ":" + selectedMinute  + " "+ format);

                //tv_time.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }

    private void getDate() {

        int day, month, year;

        if (tv_date.getText().toString().trim().equals("")) {
            // Get Current Date
            Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);

        } else {
            //when edit then open calender select date
            String[] nameArray = tv_date.getText().toString().split("-");
            year = Integer.parseInt(nameArray[2]);
            month = Integer.parseInt(nameArray[1]) - 1;
            day = Integer.parseInt(nameArray[0]);
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.calender_dialog_theme, datePicker, year, month, day);
        datePickerDialog.show();
    }

    private DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {
        // the callback received when the user "sets" the Date in the
        // DatePickerDialog
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {

            selectedMonth += 1;
            String date = selectedDay + "-" + selectedMonth + "-" + selectedYear;

            //change date farmate 1/1/2018 to 01/01/2018
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date d_date = null;
            try {
                d_date = dateFormat.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String strDate = dateFormat.format(d_date);

            Log.e("strDate=", strDate);
            //change date farmate dd-MM-yyyy to yyyy-MM-dd
            try {
                serverDate = DateFarmateChange.convertddMMyyyyToyyyyMMdd(strDate);
                Log.e("serverDate=", serverDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            tv_date.setText(strDate);
            /*if (isDateApply) {
                tv_date.setText(strDate);
            } else {
                tv_end_date.setText(strDate);
            }*/

        }
    };

    @SuppressLint("WrongConstant")
    private void openCheckUpDialog() {
        final Dialog dialog = new Dialog(this, R.style.bottom_Dialog);

        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM; // Here you can set window top or bottom
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);


        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        //dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        //dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setContentView(R.layout.dialog_checkup);

        recycler_view = dialog.findViewById(R.id.recycler_view);
        // et_forgot_email = dialog.findViewById(R.id.et_forgot_email);
        btn_submit = dialog.findViewById(R.id.btn_submit);


        mAdapter = new CheckUpListAdapter(checkUpList, this);
        RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(mLayoutManger);
        recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }


    @SuppressLint("WrongConstant")
    private void openConfirmDialog() {
        final Dialog dialog = new Dialog(this);

        /*Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM; // Here you can set window top or bottom
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);*/

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        //dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        //dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setContentView(R.layout.dialog_confirm);

        TextView tv_done = dialog.findViewById(R.id.tv_done);
        TextView tv_cancle = dialog.findViewById(R.id.tv_cancle);


        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }


}
