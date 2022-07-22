package com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.userdoctor.R;
import com.userdoctor.ui.common.Interface.Api_Call;
import com.userdoctor.ui.common.activity.Home_Activity.ActivityHome;
import com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Adapter.Vaccination_Adapter_Main;
import com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Vaccination_Model.VaccinationCollection;
import com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Vaccination_Model.Vaccination_Datum;
import com.userdoctor.ui.common.utils.APIClient;
import com.userdoctor.ui.common.utils.NetworkUtil;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;

public class Activity_Vaccination_Main extends AppCompatActivity {
  TextView book_now;
  TextView click;
  RecyclerView recyclerView;
    Vaccination_Adapter_Main vaccination_adapter_main;
   ImageView iv_back;
   SearchView search_hint;
    List<Vaccination_Datum> vaccination_datalist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__vaccination__main);
        init();

        NetworkUtil.isNetworkConnected(Activity_Vaccination_Main.this);
        Vaccination_call();

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_Vaccination_Main.this, ActivityHome.class));
            }
        });



        search_hint.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filterSearch(newText);
                return false;
            }
        });












    }


    public void Vaccination_call()
    {
        Api_Call apiInterface = APIClient.getClient().create(Api_Call.class);
        Call<VaccinationCollection> call = apiInterface.getVaccinationList();
        call.enqueue(new Callback<VaccinationCollection>() {
            @Override
            public void onResponse(Call<VaccinationCollection> call, retrofit2.Response<VaccinationCollection> response) {
                try {

                    if (response != null) {
                        vaccination_datalist=response.body().getData();
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            vaccination_adapter_main = new Vaccination_Adapter_Main(vaccination_datalist, Activity_Vaccination_Main.this);
                            LinearLayoutManager manager = new LinearLayoutManager(Activity_Vaccination_Main.this,  RecyclerView.VERTICAL, false);
                            recyclerView.setLayoutManager(manager);
                            recyclerView.setAdapter(vaccination_adapter_main);
                        } else {
                            Toast.makeText(Activity_Vaccination_Main.this, "No list found", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    Log.e("recipe", e.getMessage());
                }
            }
            @Override
            public void onFailure(Call<VaccinationCollection> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }


    public void init()
    {   search_hint=findViewById(R.id.search_hint);
        iv_back=findViewById(R.id.iv_back);

        recyclerView=findViewById(R.id.recycler_view);
    }

    private void filterSearch(String newText) {
        ArrayList<Vaccination_Datum> _list_data = new ArrayList<Vaccination_Datum>();

        for (Vaccination_Datum smodel : vaccination_datalist) {

            if (smodel.getName().toLowerCase().startsWith(newText.toLowerCase())) {
                // _list_data.clear();
                _list_data.add(smodel);
                Log.e("<><><> ", _list_data.toString());
            }
        }

        vaccination_adapter_main.setFilter(_list_data);
        if (_list_data!=null) {
            recyclerView.setAdapter(vaccination_adapter_main);
        }


    }
}
