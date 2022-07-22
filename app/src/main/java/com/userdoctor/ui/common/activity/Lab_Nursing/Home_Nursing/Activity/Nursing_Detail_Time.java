package com.userdoctor.ui.common.activity.Lab_Nursing.Home_Nursing.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.userdoctor.R;
import com.userdoctor.ui.common.Interface.Api_Call;
import com.userdoctor.ui.common.activity.Home_Activity.ActivityHome;
import com.userdoctor.ui.common.activity.Lab_Nursing.Home_Nursing.Model.NursingDetailRequest;
import com.userdoctor.ui.common.model.UserData;
import com.userdoctor.ui.common.utils.APIClient;
import com.userdoctor.ui.common.utils.DialogUtil;
import com.userdoctor.ui.common.utils.NetworkUtil;
import com.userdoctor.ui.common.utils.SharedPrefManager;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

import static com.userdoctor.ui.common.activity.Home_Activity.ActivityHome.Lattitude;
import static com.userdoctor.ui.common.activity.Home_Activity.ActivityHome.Longitude;
import static com.userdoctor.ui.common.activity.Home_Activity.ActivityHome.lat;
import static com.userdoctor.ui.common.activity.Home_Activity.ActivityHome.lng;

public class Nursing_Detail_Time extends AppCompatActivity implements View.OnClickListener {
    EditText patient_name, investigation, age, number, address, landmark;
    TextView visit_time, current_address;
    ImageView iv_back;
    RadioButton radioMale, radioFemale;
    String mStrGender, mStrInvestigation, mStrPatient_name, mStrAge, mStrNumber, mStrEmail, mStrAddress, mStrLandmark;
    Button btn_next;
    String mStrTimeList, mStrstartdate;
//    mStrenddate = " ";
    Geocoder geocoder;
   // String m_StrEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nursing__detail__time);
        NetworkUtil.isNetworkConnected(Nursing_Detail_Time.this);
        initView();


        Bundle extras2 = getIntent().getExtras();
        if (extras2 != null) {
            mStrPatient_name = extras2.getString("patient_name");
            mStrInvestigation = extras2.getString("investigation");
            mStrAge = extras2.getString("age");
            mStrNumber = extras2.getString("number");
            mStrEmail = extras2.getString("email");
            mStrAddress = extras2.getString("address");
            mStrLandmark = extras2.getString("landmark");
            mStrstartdate = extras2.getString("start_date");
          //  mStrenddate = extras2.getString("end_date");
            mStrTimeList = extras2.getString("selct_time");
        }


        try {

            if (!mStrPatient_name.isEmpty()) {
                patient_name.setText(mStrPatient_name);
            }
            if (!mStrInvestigation.isEmpty()) {
                investigation.setText(mStrInvestigation);
            }

            if (!mStrAge.isEmpty()) {
                age.setText(mStrAge);
            }
            if (!mStrNumber.isEmpty()) {
                number.setText(mStrNumber);
            }

          /*  if (!mStrEmail.isEmpty()) {
                email_id.setText(mStrEmail);
            }*/


            if (!mStrAddress.isEmpty()) {
                address.setText(mStrAddress);
            }


            if (!mStrLandmark.isEmpty()) {
                landmark.setText(mStrLandmark);
            }

            if (!mStrstartdate.isEmpty()) {
                visit_time.setText(mStrstartdate);
            }


        } catch (Exception e) {
            Log.e("check error ", e.toString());
        }


        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        System.out.println("Current formattedDate => " + formattedDate);


    }

    public void initView() {
        patient_name = findViewById(R.id.patient_name);
        investigation = findViewById(R.id.investigation);
        age = findViewById(R.id.age);
        number = findViewById(R.id.number);
        // email_id = findViewById(R.id.email_id);
        address = findViewById(R.id.address);
        landmark = findViewById(R.id.landmark);
        radioFemale = findViewById(R.id.female);
        radioMale = findViewById(R.id.male);

        btn_next = findViewById(R.id.btn_next);
        visit_time = findViewById(R.id.visit_time);
        iv_back = findViewById(R.id.iv_back);
        current_address = findViewById(R.id.current_address);

        iv_back.setOnClickListener(this);
        visit_time.setOnClickListener(this);
        btn_next.setOnClickListener(this);


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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_back:

                startActivity(new Intent(Nursing_Detail_Time.this, ActivityHome.class));

                break;
            case R.id.visit_time:


                mStrPatient_name = patient_name.getText().toString();
                mStrInvestigation = investigation.getText().toString();
                mStrAge = age.getText().toString();
                mStrNumber = number.getText().toString();
                //  mStrEmail = email_id.getText().toString();
                mStrAddress = address.getText().toString();
                mStrLandmark = landmark.getText().toString();


                Intent intent = new Intent(Nursing_Detail_Time.this, Visit_Time_Activity.class);
                intent.putExtra("patient_name", mStrPatient_name);
                intent.putExtra("investigation", mStrInvestigation);
                intent.putExtra("age", mStrAge);
                intent.putExtra("number", mStrNumber);
                intent.putExtra("email", mStrEmail);
                intent.putExtra("address", mStrAddress);
                intent.putExtra("landmark", mStrLandmark);
                startActivity(intent);

                break;


            case R.id.btn_next:


                UserData user = SharedPrefManager.getInstance(Nursing_Detail_Time.this).getUser();
                int mStrUser_id = user.getId();
                System.out.println("check user id " + mStrUser_id);
                String tmpStr10 = String.valueOf(mStrUser_id);
                System.out.println("check user id str " + tmpStr10);


                String mgetStrPatient_name = patient_name.getText().toString();
                if (!TextUtils.isEmpty(mgetStrPatient_name)) {
                } else {
                    patient_name.setError("Please enter Patient Name");
                    return;
                }


                String m_Investigation_name = investigation.getText().toString();
                if (!TextUtils.isEmpty(m_Investigation_name)) {
                } else {
                    investigation.setError("Please enter Investigation");
                    return;
                }

                String mgetStrAge = age.getText().toString();
                if (!TextUtils.isEmpty(mgetStrAge)) {
                } else {
                    age.setError("Please enter Age");
                    return;
                }

                String m_StrNumber = number.getText().toString();
                if (!TextUtils.isEmpty(m_StrNumber)) {
                } else {
                    number.setError("Please enter number");
                    return;
                }

               /* String m_StrEmail = email_id.getText().toString();
                if (m_StrEmail.isEmpty()) {
                    email_id.setError("Please enter email address");
                    return;
                } else if (!email_id.getText().toString().trim().matches(emailPattern)) {
                    email_id.setError("Please enter valide email address");
                    return;
                }
*/


                String m_StrAddress = address.getText().toString();
                if (!TextUtils.isEmpty(m_StrAddress)) {
                } else {
                    address.setError("Please enter address");
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


                String m_StrLat = Lattitude;
                String m_StrLng = Longitude;

             /*   if (mStrenddate != null) {
                    m_StrEndDate = mStrenddate;

                } else {
                    m_StrEndDate = " ";
                }*/

                String m_StrTime = mStrTimeList;


                if (radioMale.isChecked()) {
                    mStrGender = "male";
                } else {
                    mStrGender = "female";
                }
                System.out.println("check " + mStrUser_id + " " + mgetStrPatient_name + " " + m_Investigation_name + " " + mgetStrAge + " " + mStrNumber + " " + " " + m_StrAddress + " " + mgetStrLandmark + " " + mStrstartdate + " " + m_StrLat + "" + m_StrLng + " " + mStrGender + " " + ""+ " " + m_StrTime);
                mFunFormRequest(mStrUser_id, mgetStrPatient_name, m_Investigation_name, mgetStrAge, m_StrNumber, m_StrAddress, mgetStrLandmark, mStrstartdate, m_StrLat, m_StrLng, mStrGender,  m_StrTime);
                break;
        }
    }
    public void mFunFormRequest(final int mStrUser_id, final String mStrPatient_name, final String mStrinvestigation, final String mStrAge, final String mStrMobile, final String mStrAddress, final String mStrLandmark, final String mStrStartDate, final String mStrlat, final String mStrLng, final String mStrGender,  final String mStrTime) {// public void mFunApiCall(final String test_id, final int mStrUser_id, final String mStrPatient_name, final String mStrinvestigation, final String mStrAge, final String mStrMobile, final String mStrEmail, final String mStrAddress, final String mStrLandmark, final String mStrGender, final String mStrDate, final String mStrTime, final String mStrLat, final String mStrLng) {
        DialogUtil.show(Nursing_Detail_Time.this);
        Api_Call apiInterface = APIClient.getClient().create(Api_Call.class);
        Call<NursingDetailRequest> call = apiInterface.getNursingRequest(
                String.valueOf(mStrUser_id),
                mStrPatient_name, mStrinvestigation,
                mStrAge, mStrMobile, mStrAddress,
                mStrLandmark, mStrStartDate, mStrlat, mStrLng, mStrGender,  mStrTime);
        call.enqueue(new Callback<NursingDetailRequest>() {
            @Override
            public void onResponse(Call<NursingDetailRequest> call, retrofit2.Response<NursingDetailRequest> response) {
                try {
                    if (response != null) {
                        System.out.println("check appointmnet time " + response.body().getMsg());
                        String msg = response.body().getMsg();
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            DialogUtil.dismiss();

                            Intent intent = new Intent(Nursing_Detail_Time.this, Succesfully_Done_Nursing_Request.class);
                            startActivity(intent);

                        } else {
                            DialogUtil.dismiss();
                            Toast.makeText(Nursing_Detail_Time.this, "" + msg, Toast.LENGTH_SHORT).show();
                        }


                    }
                } catch (Exception e) {
                    DialogUtil.dismiss();
                    Log.e("error catch", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<NursingDetailRequest> call, Throwable t) {
                DialogUtil.dismiss();
                Log.e("error fail", t.getMessage());
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(Nursing_Detail_Time.this, ActivityHome.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);


    }
}