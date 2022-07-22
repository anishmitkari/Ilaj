package com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Activity;

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
import com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Adapter.SampleAdapter;
import com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Sample.SampleDataModel;
import com.userdoctor.ui.common.activity.Home_Activity.ActivityHome;
import com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Sample.TestList;
import com.userdoctor.ui.common.utils.APIClient;
import com.userdoctor.ui.common.utils.NetworkUtil;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;

public class Activity_Sample_Collection_Main extends AppCompatActivity {
    TextView book_now;
    RecyclerView recyclerView;
    SampleAdapter sampleAdapter;
    ImageView iv_back;
    androidx.appcompat.widget.SearchView search_hint;
    List<TestList> testListArrayList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__sample__collection__main);

        iv_back=findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Activity_Sample_Collection_Main.this, ActivityHome.class));
            }
        });

        recyclerView = findViewById(R.id.recycler_view);
        search_hint=findViewById(R.id.search_hint);



        search_hint.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        NetworkUtil.isNetworkConnected(Activity_Sample_Collection_Main.this);
        mFunCallDrFragmentList();
    }

    private void mFunCallDrFragmentList() {
        Api_Call apiInterface = APIClient.getClient().create(Api_Call.class);
        Call<SampleDataModel> call = apiInterface.getTestList();

        call.enqueue(new Callback<SampleDataModel>() {
            @Override
            public void onResponse(Call<SampleDataModel> call, retrofit2.Response<SampleDataModel> response) {

                try {

                    testListArrayList=response.body().gettestlist();
                    if (response != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {

                            sampleAdapter = new SampleAdapter(testListArrayList,Activity_Sample_Collection_Main.this);
                            LinearLayoutManager manager = new LinearLayoutManager(Activity_Sample_Collection_Main.this,  RecyclerView.VERTICAL, false);
                            recyclerView.setLayoutManager(manager);
                            recyclerView.setAdapter(sampleAdapter);
                        } else {
                              Toast.makeText(Activity_Sample_Collection_Main.this, "No list found", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    Log.e("recipe", e.getMessage());
                }
            }
            @Override
            public void onFailure(Call<SampleDataModel> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }

    private void filterSearch(String newText) {
        ArrayList<TestList> _list_data = new ArrayList<TestList>();

        for (TestList smodel : testListArrayList) {

            if (smodel.getTestName().toLowerCase().startsWith(newText.toLowerCase())) {
               // _list_data.clear();
                _list_data.add(smodel);
                Log.e("<><><> ", _list_data.toString());
            }
        }

        sampleAdapter.setFilter(_list_data);
        if (_list_data!=null) {
            recyclerView.setAdapter(sampleAdapter);
        }


    }

}
