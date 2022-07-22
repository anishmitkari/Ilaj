package com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.userdoctor.R;
import com.userdoctor.ui.common.Interface.Api_Call;
import com.userdoctor.ui.common.activity.Home_Activity.ActivityHome;
import com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Vaccination_Model.Post_Vaccination_Request.PostVaccinationRequest;
import com.userdoctor.ui.common.model.UserData;
import com.userdoctor.ui.common.utils.APIClient;
import com.userdoctor.ui.common.utils.DialogUtil;
import com.userdoctor.ui.common.utils.PrefrenceManager;
import com.userdoctor.ui.common.utils.SharedPrefManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

import static com.userdoctor.ui.common.activity.Home_Activity.ActivityHome.Lattitude;
import static com.userdoctor.ui.common.activity.Home_Activity.ActivityHome.Longitude;


import static com.userdoctor.ui.common.activity.Home_Activity.ActivityHome.lat;
import static com.userdoctor.ui.common.activity.Home_Activity.ActivityHome.lng;

public class Vaccination_Next_Page extends AppCompatActivity {
    TextView vaccination_time,current_address;
    EditText patient_name, age, number,  address, landmark;
    RadioButton radioMale, radioFemale;
    Button btn_next;
    ImageView iv_back;
    String id_Vaccination, mStrGender, mgetStrPatient_name, mgetStrAge, mgetStrNumber,  mgetStrAddress, mgetStrLandmark, mStrTimeList, mStrDateList;
    String mStrstartdate, mStrenddate, mSpinnerId, mStrPatient_name, mStrAge, mStrNumber, mStrEmail, mStrAddress, mStrLandmark;
    Geocoder geocoder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination__next__page);
        initView();


        Bundle extras2 = getIntent().getExtras();
        if (extras2 != null) {
            mgetStrPatient_name = extras2.getString("patient_name");
            mgetStrAge = extras2.getString("age");
            mgetStrNumber = extras2.getString("number");
            mgetStrAddress = extras2.getString("address");
            mgetStrLandmark = extras2.getString("landmark");
            mStrTimeList = extras2.getString("selct_time");
            mStrstartdate = extras2.getString("start_date");
            mStrenddate = extras2.getString("end_date");
            mSpinnerId = extras2.getString("spinner_id");
            vaccination_time.setText(mStrstartdate+" - "+mStrenddate);

        }


        try {

            if (!mgetStrPatient_name.isEmpty()) {
                patient_name.setText(mgetStrPatient_name);
            }

            if (!mgetStrAge.isEmpty()) {
                age.setText(mgetStrAge);
            }

            if (!mgetStrNumber.isEmpty()) {
                number.setText(mgetStrNumber);
            }

            if (!mgetStrAddress.isEmpty()) {
                address.setText(mgetStrAddress);
            }

            if (!mgetStrLandmark.isEmpty()) {
                landmark.setText(mgetStrLandmark);
            }

        } catch (Exception e) {
            Log.e("check error ", e.toString());
        }


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserData user = SharedPrefManager.getInstance(Vaccination_Next_Page.this).getUser();
                int mStrUser_id = user.getId();
                String mgetStrPatient_name = patient_name.getText().toString();
                System.out.println("inside button" + mgetStrPatient_name);
                if (!TextUtils.isEmpty(mgetStrPatient_name)) {
                } else {
                    patient_name.setError("Please enter Patient Name");
                    return;
                }


                String mgetStrAge = age.getText().toString();
                if (!TextUtils.isEmpty(mgetStrAge)) {
                } else {
                    age.setError("Please enter Investigation");
                    return;
                }

                String mgetStrNumber = number.getText().toString();
                if (!TextUtils.isEmpty(mgetStrNumber)) {
                } else {
                    number.setError("Please enter patient age");
                    return;
                }





                String mgetStrAddress = address.getText().toString();
                if (!TextUtils.isEmpty(mgetStrAddress)) {
                } else {
                    address.setError("Please enter Mobile number");
                    return;
                }


                String mgetStrLandmark = landmark.getText().toString();
                if (!TextUtils.isEmpty(mgetStrLandmark)) {
                } else {
                    landmark.setError("Please enter landmark number");
                    return;
                }

                if (radioMale.isChecked()) {
                    mStrGender = "male";
                } else {
                    mStrGender = "female";

                }




                String mStrLat = Lattitude;
                String mStrLng = Longitude;

                String course_id = PrefrenceManager.getVaccinationDaysId(Vaccination_Next_Page.this);
                String vaccination_type_id = PrefrenceManager.getVaccination_Main_Category_ID(Vaccination_Next_Page.this);


                System.out.println(

                                 "check not found " +
                                " course_id " + course_id +
                                " user_id " + mStrUser_id +
                                " patient_name " + mgetStrPatient_name +
                                " patient_age " + mgetStrAge +
                                " mobile " + mgetStrNumber +
                                " address " + mgetStrAddress +
                                " landmark " + mgetStrLandmark +
                                " from_date " + mStrstartdate +
                                " lat " + Lattitude +
                                " lng " + Longitude +
                                " patient_gender " + mStrGender +
                                " time_interval_id " + mSpinnerId +
                                " vaccination_type_id " + vaccination_type_id +
                                " to_date " + mStrenddate +
                                " times " + mStrTimeList


                );


                openConfirmDialog();
            }
        });


        vaccination_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mStrPatient_name = patient_name.getText().toString();
                mStrAge = age.getText().toString();
                mStrNumber = number.getText().toString();

                mStrAddress = address.getText().toString();
                mStrLandmark = landmark.getText().toString();


                Intent intent = new Intent(Vaccination_Next_Page.this, Vaccination_Time_Page.class);
                intent.putExtra("patient_name", mStrPatient_name);
                intent.putExtra("age", mStrAge);
                intent.putExtra("number", mStrNumber);
                intent.putExtra("address", mStrAddress);
                intent.putExtra("landmark", mStrLandmark);
                startActivity(intent);
            }
        });




        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           startActivity(new Intent(Vaccination_Next_Page.this,Activity_Vaccination.class));
            }
        });


    }


    @SuppressLint("WrongConstant")
    private void openConfirmDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setContentView(R.layout.sample_dialog_confirm);
        TextView tv_done = dialog.findViewById(R.id.tv_done);
        TextView text_detail = dialog.findViewById(R.id.text_dailoge);
        String mgetStrAge = age.getText().toString();

        if (radioMale.isChecked()) {
            mStrGender = "male";
        } else {
            mStrGender = "female";
        }


        text_detail.setText("Vaccination Test for  " +  mgetStrAge  + " years");
        tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                UserData user = SharedPrefManager.getInstance(Vaccination_Next_Page.this).getUser();
                int mStrUser_id = user.getId();

                String course_id = PrefrenceManager.getVaccinationDaysId(Vaccination_Next_Page.this);
                String vaccination_type_id = PrefrenceManager.getVaccination_Main_Category_ID(Vaccination_Next_Page.this);


                System.out.println(

                        "check not found " +
                                " course_id " + course_id +
                                " user_id " + mStrUser_id +
                                " patient_name " + mgetStrPatient_name +
                                " patient_age " + mgetStrAge +
                                " mobile " + mgetStrNumber +
                                " address " + mgetStrAddress +
                                " landmark " + mgetStrLandmark +
                                " from_date " + mStrstartdate +
                                " lat " + Lattitude +
                                " lng " + Longitude +
                                " patient_gender " + mStrGender +
                                " time_interval_id " + mSpinnerId +
                                " vaccination_type_id " + vaccination_type_id +
                                " to_date " + mStrenddate +
                                " times " + mStrTimeList


                );
                mFunApiCall(course_id, mStrUser_id, mgetStrPatient_name, mgetStrAge, mgetStrNumber,  mgetStrAddress, mgetStrLandmark, mStrstartdate, Lattitude, Longitude, mStrGender, mSpinnerId, vaccination_type_id, mStrenddate, mStrTimeList);
                dialog.dismiss();


            }
        });

        dialog.show();

    }


    public void mFunApiCall(final String test_id, final int mStrUser_id, final String mStrPatient_name, final String mStrAge, final String mStrMobile,  final String mStrAddress, final String mStrLandmark, final String todate, final String mStrLat, final String mStrLng, final String mStrGender, final String mStrTimeIntervalId, final String mStrVaccinationTypeId, final String mStrto_date, final String mStrTimes) {
        DialogUtil.show(Vaccination_Next_Page.this);
        Api_Call apiInterface = APIClient.getClient().create(Api_Call.class);
        Call<PostVaccinationRequest> call = apiInterface.PostVaccinationRequest(test_id, String.valueOf(mStrUser_id), mStrPatient_name, mStrAge, mStrMobile,  mStrAddress, mStrLandmark, todate, mStrLat, mStrLng, mStrGender, mStrTimeIntervalId, mStrVaccinationTypeId, mStrto_date, mStrTimes);
        call.enqueue(new Callback<PostVaccinationRequest>() {
            @Override
            public void onResponse(Call<PostVaccinationRequest> call, retrofit2.Response<PostVaccinationRequest> response) {
                try {

                    if (response != null) {
                        System.out.println("check appointmnet time " + response.body().getMsg());
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            String msg = response.body().getMsg();
                            DialogUtil.dismiss();
                            Toast.makeText(Vaccination_Next_Page.this, "Your Requested Submitted Successfully" + msg, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Vaccination_Next_Page.this, ActivityHome.class));
                        } else {
                            DialogUtil.dismiss();

                            Toast.makeText(Vaccination_Next_Page.this, "some parameter missing", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    DialogUtil.dismiss();
                    Log.e("error", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<PostVaccinationRequest> call, Throwable t) {
                DialogUtil.dismiss();
                Log.e("error", t.getMessage());
            }
        });

    }


    public void initView() {


        current_address=findViewById(R.id.current_address);
        vaccination_time = findViewById(R.id.vaccination_time);
        btn_next = findViewById(R.id.btn_next);
        patient_name = findViewById(R.id.patient_name);
        age = findViewById(R.id.age);
        number = findViewById(R.id.number);
        address = findViewById(R.id.address);
        landmark = findViewById(R.id.landmark);
        radioFemale = findViewById(R.id.female);
        radioMale = findViewById(R.id.male);
        iv_back=findViewById(R.id.iv_back);


        List<Address> addresses = new ArrayList<>();
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(lat, lng, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
            current_address.setText(address);
        } catch (IOException e) {
            e.printStackTrace();
        }




    }




}
