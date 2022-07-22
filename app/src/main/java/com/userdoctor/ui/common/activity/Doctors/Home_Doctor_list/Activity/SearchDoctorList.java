package com.userdoctor.ui.common.activity.Doctors.Home_Doctor_list.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.userdoctor.R;
import com.userdoctor.ui.common.activity.Doctors.Home_Doctor_list.Adapter.DrListAdapter;
import com.userdoctor.ui.common.model.DoctorListData;
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

public class SearchDoctorList extends AppCompatActivity implements View.OnClickListener {
    SearchView searchView;
    ImageView iv_back;
    RecyclerView recycler_view;
    private DrListAdapter mAdapter;
    private List<DoctorListData> drList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_doctor_list);
        initView();

        mFunCallDrFragmentList();


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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_back:
                startActivity(new Intent(SearchDoctorList.this, ActivityDoctorList.class));
                finish();
                break;


        }


    }

    @SuppressLint("WrongConstant")
    public void initView() {
        searchView = findViewById(R.id.search_bar);
        iv_back = findViewById(R.id.iv_back);
        recycler_view = findViewById(R.id.recycler_view);
         iv_back.setOnClickListener(this);
        mAdapter = new DrListAdapter(drList, SearchDoctorList.this);

        RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(SearchDoctorList.this);
        recycler_view.setLayoutManager(mLayoutManger);
        recycler_view.setLayoutManager(new LinearLayoutManager(SearchDoctorList.this, LinearLayoutManager.VERTICAL, false));
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        mAdapter.notifyDataSetChanged();

    }



    //Doctor_list_AtHome
    public void mFunCallDrFragmentList() {
        DialogUtil.show(SearchDoctorList.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_URL + "all_doctor_list_at_home",
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
                                    //getting the json object of the particular index inside the array
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    System.out.println("<><><> dr home SearchDoctorList-      " + jsonObject);

                                    if (jsonObject != null) {
                                        DoctorListData hero = new DoctorListData(
                                                jsonObject.getString("id"),
                                                jsonObject.getString("category_id"),
                                                jsonObject.getString("dr_name"),
                                                jsonObject.getString("profile_img"),
                                                jsonObject.getString("contact_no"),
                                                jsonObject.getString("dr_email"),
                                                jsonObject.getString("lab_name"),
                                                jsonObject.getString("lab_address"),
                                                jsonObject.getString("hospital_name"),
                                                jsonObject.getString("hospital_address"),
                                                jsonObject.getString("mci_member"),
                                                jsonObject.getString("national_id"),
                                                jsonObject.getString("gender"),
                                                jsonObject.getString("degree_id"),
                                                jsonObject.getString("passing_year"),
                                                jsonObject.getString("experience"),
                                                jsonObject.getString("phone_no"),
                                                jsonObject.getString("clinic_fee"),
                                                jsonObject.getString("home_fee"),
                                                jsonObject.getString("emergency_fee"),
                                                jsonObject.getString("address"),
                                                jsonObject.getString("countryId"),
                                                jsonObject.getString("stateId"),
                                                jsonObject.getString("city_id"),
                                                jsonObject.getString("home_availability"),
                                                jsonObject.getString("emergency"),
                                                jsonObject.getString("lat"),
                                                jsonObject.getString("lng"),
                                                jsonObject.getString("registration_proof"),
                                                jsonObject.getString("qualification_proof"),
                                                jsonObject.getString("id_proof"),
                                                jsonObject.getString("status"),
                                                jsonObject.getString("clinic_open_hours"),
                                                jsonObject.getString("clinic_close_hours"),
                                                jsonObject.getString("delete_status"),
                                                jsonObject.getString("avg_rating"),
                                                jsonObject.getString("distance"),
                                                jsonObject.getString("type")

                                        );
                                        drList.add(hero);

                                    } else {
                                        Toast.makeText(SearchDoctorList.this, "List have no data", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                //   recycler_view.setAdapter(mAdapter);
                            } else {
                                Toast.makeText(SearchDoctorList.this, "fragment have no data", Toast.LENGTH_SHORT).show();
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
                        // Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("lat", Lattitude);
                params.put("lng", Longitude);
                return params;
            }
        };

        VolleySingleton.getInstance(SearchDoctorList.this).addToRequestQueue(stringRequest);
    }


    private void filterSearch(String newText) {
        ArrayList<DoctorListData> doctore_list_data = new ArrayList<DoctorListData>();
        for (DoctorListData smodel : drList) {
            if (smodel.getDr_name().toLowerCase().startsWith(newText.toLowerCase())) {
                doctore_list_data.clear();
                doctore_list_data.add(smodel);
                Log.e("<><><> ", doctore_list_data.toString());
            }
        }
        mAdapter.setFilter(doctore_list_data);
        if (doctore_list_data!=null) {
            recycler_view.setAdapter(mAdapter);
        }


    }


}
