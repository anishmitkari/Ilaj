package com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.userdoctor.R;
import com.userdoctor.ui.common.Interface.Api_Call;
import com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Adapter.VaccinationDays_Adapter;
import com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Vaccination_Model.Vaccination_Days_Model.VaccinationDays;
import com.userdoctor.ui.common.utils.APIClient;

import retrofit2.Call;
import retrofit2.Callback;

public class Activity_Vaccination extends AppCompatActivity  {

    Button btn_vacc_confirm;
    RecyclerView recyclerView;
    String value;
     ImageView iv_back;
    VaccinationDays_Adapter vaccinationDays_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity__vaccination);

        recyclerView = findViewById(R.id.recycler_view);

        iv_back=findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_Vaccination.this,Vaccination_Detail.class));
            }
        });
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("id");
        }
        System.out.println("check id is "+value);
        mApiVaccinationDays();
    }


    public void mApiVaccinationDays() {
        Api_Call apiInterface = APIClient.getClient().create(Api_Call.class);
        Call<VaccinationDays> call = apiInterface.getVaccinationDays(value);

        call.enqueue(new Callback<VaccinationDays>() {
            @Override
            public void onResponse(Call<VaccinationDays> call, retrofit2.Response<VaccinationDays> response) {
                try {
                    if (response != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {

                            vaccinationDays_adapter = new VaccinationDays_Adapter(response.body().getData(), Activity_Vaccination.this);
                            LinearLayoutManager manager = new LinearLayoutManager(Activity_Vaccination.this, RecyclerView.VERTICAL, false);
                            recyclerView.setLayoutManager(manager);
                            recyclerView.setAdapter(vaccinationDays_adapter);

                        } else {
                            Toast.makeText(Activity_Vaccination.this, "No list found", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    Log.e("error ", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<VaccinationDays> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });


    }



}
