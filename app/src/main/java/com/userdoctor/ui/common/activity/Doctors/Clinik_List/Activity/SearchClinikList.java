package com.userdoctor.ui.common.activity.Doctors.Clinik_List.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.userdoctor.R;
import com.userdoctor.ui.common.activity.Doctors.Clinik_List.Adapter.ClinicListAdapter;
import com.userdoctor.ui.common.model.ClinikListData;
import com.userdoctor.ui.common.utils.DialogUtil;
import com.userdoctor.ui.common.utils.URLs;
import com.userdoctor.ui.common.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.userdoctor.ui.common.activity.Home_Activity.ActivityHome.Lattitude;
import static com.userdoctor.ui.common.activity.Home_Activity.ActivityHome.Longitude;

public class SearchClinikList extends AppCompatActivity {
    ImageView iv_back;
    SearchView searchView;
    Context context;
    private RecyclerView recycler_view;
    private LinearLayout ll_no_record;
    private ClinicListAdapter mAdapter;
    private List<ClinikListData> clinikList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_clinik_list);
        initView();
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mFunCallClinikFragmentList();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

    @SuppressLint("WrongConstant")
    private void initView() {
        context = SearchClinikList.this;
        searchView = findViewById(R.id.search_bar);
        iv_back = findViewById(R.id.iv_back);
        recycler_view = findViewById(R.id.recycler_view);

        mAdapter = new ClinicListAdapter(clinikList, context);

        RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(SearchClinikList.this);
        recycler_view.setLayoutManager(mLayoutManger);
        recycler_view.setLayoutManager(new LinearLayoutManager(SearchClinikList.this, LinearLayoutManager.VERTICAL, false));
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        mAdapter.notifyDataSetChanged();


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void filterSearch(String newText) {
        ArrayList<ClinikListData> doctore_list_data = new ArrayList<ClinikListData>();
        for (ClinikListData smodel : clinikList) {
            if (smodel.getClinic_name().toLowerCase().startsWith(newText.toLowerCase())) {
                doctore_list_data.clear();
                doctore_list_data.add(smodel);
                Log.e("<><><> ", doctore_list_data.toString());
            }
        }
        mAdapter.setFilter(doctore_list_data);
        if (doctore_list_data != null) {
            recycler_view.setAdapter(mAdapter);
        }
    }

    public void mFunCallClinikFragmentList() {
        DialogUtil.show(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_URL + "doctors_list_at_clinic",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        DialogUtil.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            boolean responsedata = obj.getBoolean("result");
                            System.out.println("<><><>" + obj);

                            if (responsedata == true) {
                                JSONArray jsonArray = obj.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    System.out.println("<><><>" + jsonObject);
                                    if (jsonObject != null) {
                                        ClinikListData hero = new ClinikListData(
                                                jsonObject.getString("id"),
                                                jsonObject.getString("category_id"),
                                                jsonObject.getString("dr_name"),
                                                jsonObject.getString("profile_img"),
                                                jsonObject.getString("contact_no"),
                                                jsonObject.getString("distance"),
                                                jsonObject.getString("clinic_name"),
                                                jsonObject.getString("clinic_address"),
                                                jsonObject.getString("avg_rating"),
                                                jsonObject.getString("lat"),
                                                jsonObject.getString("lng"),
                                                jsonObject.getString("type")

                                                );
                                        clinikList.add(hero);
                                    } else {
                                        Toast.makeText(context, "List have no data", Toast.LENGTH_SHORT).show();
                                    }


                                }
                                recycler_view.setAdapter(mAdapter);
                            } else {
                                Toast.makeText(context, "List have no data", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            System.out.println("<><><>" + e.getMessage().toString());
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("lat", Lattitude);
                params.put("lng", Longitude);
                return params;
            }
        };


        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }
}