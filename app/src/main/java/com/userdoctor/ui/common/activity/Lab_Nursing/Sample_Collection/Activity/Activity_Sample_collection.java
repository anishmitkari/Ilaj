package com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Activity;

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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.userdoctor.R;
import com.userdoctor.ui.common.Interface.Api_Call;
import com.userdoctor.ui.common.activity.Home_Activity.ActivityHome;
import com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Sample.SampleCollection;
import com.userdoctor.ui.common.model.UserData;
import com.userdoctor.ui.common.utils.APIClient;
import com.userdoctor.ui.common.utils.DialogUtil;
import com.userdoctor.ui.common.utils.NetworkUtil;
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

public class Activity_Sample_collection extends AppCompatActivity {

    TextView appointment_time, current_address;
    String format, test_id, mStrGender, mStrTimeList, mStrDateList;
    String storeid;
    Button btn_next;
    String id_Sample, test_name;
    EditText patient_name, investigation, age, number,  address, landmark;
    RadioGroup Gender;
    RadioButton radioMale, radioFemale;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Geocoder geocoder;
    String mgetStrPatient_name, mgetStrinvestigation, mgetStrAge, mgetStrNumber, mgetStrEmail, mgetStrAddress, mgetStrLandmark;
    String mStrPatient_name, mStrinvestigation, mStrAge, mStrNumber, mStrEmail, mStrAddress, mStrLandmark;
    ImageView iv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__sample_collection);
        init();






        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });




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


        Bundle extras2 = getIntent().getExtras();
        if (extras2 != null) {
            mgetStrPatient_name = extras2.getString("patient_name");
            mgetStrinvestigation = extras2.getString("investigation");
            mgetStrAge = extras2.getString("age");
            mgetStrNumber = extras2.getString("number");
            mgetStrAddress = extras2.getString("address");
            mgetStrLandmark = extras2.getString("landmark");
            mStrTimeList = extras2.getString("time");
            mStrDateList = extras2.getString("date");
            System.out.println("return back value  " + mgetStrPatient_name);
            System.out.println("return back value " + mgetStrEmail);
            System.out.println("return date  " + mStrTimeList);
            System.out.println("return time  " + mStrDateList);
            appointment_time.setText(mStrDateList);
        }

        try {

            if (!mgetStrPatient_name.isEmpty()) {
                patient_name.setText(mgetStrPatient_name);
            }
            if (!mgetStrinvestigation.isEmpty()) {
                investigation.setText(mgetStrinvestigation);
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


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            test_id = extras.getString("test_id");

        }
        NetworkUtil.isNetworkConnected(Activity_Sample_collection.this);


        appointment_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStrPatient_name = patient_name.getText().toString();
                mStrinvestigation = investigation.getText().toString();
                mStrAge = age.getText().toString();
                mStrNumber = number.getText().toString();
                mStrAddress = address.getText().toString();
                mStrLandmark = landmark.getText().toString();
                Intent i = new Intent(Activity_Sample_collection.this, Activity_Sample_Book_Time.class);
                i.putExtra("patient_name", mStrPatient_name);
                i.putExtra("investigation", mStrinvestigation);
                i.putExtra("age", mStrAge);
                i.putExtra("number", mStrNumber);
                i.putExtra("address", mStrAddress);
                i.putExtra("landmark", mStrLandmark);
                startActivity(i);
            }
        });


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                UserData user = SharedPrefManager.getInstance(Activity_Sample_collection.this).getUser();
                int mStrUser_id = user.getId();
                String mgetStrPatient_name = patient_name.getText().toString();
                System.out.println("inside button" + mgetStrPatient_name);
                if (!TextUtils.isEmpty(mgetStrPatient_name)) {
                } else {
                    patient_name.setError("Please enter Patient Name");
                    return;
                }


                String mgetStrinvestigation = investigation.getText().toString();
                if (!TextUtils.isEmpty(mgetStrinvestigation)) {
                } else {
                    investigation.setError("Please enter Investigation");
                    return;
                }

                String mgetStrAge = age.getText().toString();
                if (!TextUtils.isEmpty(mgetStrAge)) {
                } else {
                    age.setError("Please enter patient age");
                    return;
                }

                String mgetStrNumber = number.getText().toString();
                if (!TextUtils.isEmpty(mgetStrNumber)) {
                } else {
                    number.setError("Please enter Mobile number");
                    return;
                }


             /*   String mgetStrEmail = email.getText().toString();
                if (mgetStrEmail.isEmpty()) {
                    email.setError("Please enter emailaddress");
                    return;
                } else if (!email.getText().toString().trim().matches(emailPattern)) {
                    email.setError("Please enter valide email address");
                    return;
                }*/


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
                }
                else {
                    mStrGender = "female";
                }

                String mStrLat = Lattitude;
                String mStrLng = Longitude;
                System.out.println("check not found " + id_Sample + " " + mStrUser_id + " " + mgetStrPatient_name + " " + mgetStrinvestigation + " " + mgetStrAge + " " + mgetStrNumber + " " + mgetStrEmail + " " + mgetStrAddress + " " + mgetStrLandmark + " " + mStrGender + " " + mStrDateList + " " + mStrTimeList + " " + mStrLat + " " + mStrLng);
                openConfirmDialog();


            }
        });


    }

    public void mFunApiCall(final String test_id, final int mStrUser_id, final String mStrPatient_name, final String mStrinvestigation, final String mStrAge, final String mStrMobile,  final String mStrAddress, final String mStrLandmark, final String mStrGender, final String mStrDate, final String mStrTime, final String mStrLat, final String mStrLng) {
        DialogUtil.show(Activity_Sample_collection.this);
        Api_Call apiInterface = APIClient.getClient().create(Api_Call.class);
        Call<SampleCollection> call = apiInterface.getSampleCollection(test_id, String.valueOf(mStrUser_id), mStrPatient_name, mStrinvestigation, mStrAge, mStrMobile,  mStrAddress, mStrLandmark, mStrGender, mStrDate, mStrTime, mStrLat, mStrLng);


        call.enqueue(new Callback<SampleCollection>() {
            @Override
            public void onResponse(Call<SampleCollection> call, retrofit2.Response<SampleCollection> response) {
                try {
                    if (response != null) {
                        System.out.println("check appointmnet time " + response.body().getMsg());

                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            String msg = response.body().getMsg();
                            DialogUtil.dismiss();
                            Toast.makeText(Activity_Sample_collection.this, "Your Requested Submitted Successfully" + msg, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Activity_Sample_collection.this, ActivityHome.class));

                        } else {
                            DialogUtil.dismiss();
                            Toast.makeText(Activity_Sample_collection.this, "Please select Date and Time", Toast.LENGTH_SHORT).show();
                        }


                    }
                } catch (Exception e) {
                    DialogUtil.dismiss();
                    Log.e("error", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<SampleCollection> call, Throwable t) {
                DialogUtil.dismiss();
                Log.e("error", t.getMessage());
            }
        });

    }

    @SuppressLint("WrongViewCast")
    public void init() {

        id_Sample = PrefrenceManager.getSampleIdItem(Activity_Sample_collection.this);
        test_name = PrefrenceManager.getSampleTestNameItem(Activity_Sample_collection.this);
        Log.e("check sampple id is ", id_Sample + "  " + test_name);

        iv_back=findViewById(R.id.iv_back);
        current_address = findViewById(R.id.current_address);
        radioMale = findViewById(R.id.radioMale);
        radioFemale = findViewById(R.id.radioFemale);
        appointment_time = findViewById(R.id.appointment_time);
        btn_next = findViewById(R.id.btn_next);
        patient_name = findViewById(R.id.patientname);
        investigation = findViewById(R.id.investigation);
        age = findViewById(R.id.age);
        number = findViewById(R.id.number);
      //  email = findViewById(R.id.email_id);
        address = findViewById(R.id.address);
        landmark = findViewById(R.id.landmark);
        Gender = findViewById(R.id.radioGroup);


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

        text_detail.setText("Home Sample Collection For " + test_name + " for " + mStrGender + " " + mgetStrAge + " years");
        tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mStrLat = Lattitude;
                String mStrLng = Longitude;
                UserData user = SharedPrefManager.getInstance(Activity_Sample_collection.this).getUser();
                int mStrUser_id = user.getId();
                mFunApiCall(id_Sample, mStrUser_id, mgetStrPatient_name, mgetStrinvestigation, mgetStrAge, mgetStrNumber,  mgetStrAddress, mgetStrLandmark, mStrGender, mStrDateList, mStrTimeList, mStrLat, mStrLng);
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(Activity_Sample_collection.this,
                Item_ClickOnRecycleviwAdapterTest.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);


    }
}
