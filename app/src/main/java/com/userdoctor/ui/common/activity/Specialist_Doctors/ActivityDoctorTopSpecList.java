package com.userdoctor.ui.common.activity.Specialist_Doctors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.userdoctor.R;
import com.userdoctor.ui.common.adapter.DoctorTopSpecListAdapter;
import com.userdoctor.ui.common.model.ModelDoctorTopSpecList;
import com.userdoctor.ui.common.utils.DialogUtil;
import com.userdoctor.ui.common.utils.NetworkUtil;
import com.userdoctor.ui.common.utils.URLs;
import com.userdoctor.ui.common.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityDoctorTopSpecList extends AppCompatActivity {

    ImageView iv_back;
    TextView specialist;
    Button btn_dr_spec_profile, btn_dr_spec_book_now;
    String id,special;
    RecyclerView recycler_view;
    DoctorTopSpecListAdapter adapter;
    List<ModelDoctorTopSpecList> heroList;
    SearchView search_hint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_top_spec_list);

        NetworkUtil.isNetworkConnected(ActivityDoctorTopSpecList.this);


        initview();
        clicklistner();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getString("id");
            special=extras.getString("SPECIAL");
            specialist.setText(special);
        }



        heroList = new ArrayList<>();
        adapter = new DoctorTopSpecListAdapter(heroList, ActivityDoctorTopSpecList.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setAdapter(adapter);






        mFunCallDrList();


    }





    public void mFunCallDrList() {
        DialogUtil.show(ActivityDoctorTopSpecList.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_URL + "DoctorList_OnCatId",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        DialogUtil.dismiss();
                        try { JSONObject obj = new JSONObject(response);
                            boolean responsedata = obj.getBoolean("result");
                            System.out.println("<><><>"+obj);

                            if (responsedata == true) {
                                JSONArray jsonArray = obj.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    System.out.println("<><><>"+jsonObject);

                                    if (jsonObject!=null)
                                    {
                                          ModelDoctorTopSpecList hero = new ModelDoctorTopSpecList(
                                                  jsonObject.getString("id"),
                                                  jsonObject.getString("category_id"),
                                                  jsonObject.getString("dr_name"),
                                                  jsonObject.getString("profile_img"),
                                                  jsonObject.getString("contact_no"),
                                                  jsonObject.getString("dr_email"),
                                                  jsonObject.getString("password"),
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
                                                  jsonObject.getString("type"),
                                                  jsonObject.getString("cat_name"));
                                                  heroList.add(hero);
                                    }
                                    else
                                    {
                                        Toast.makeText(ActivityDoctorTopSpecList.this, "List have no data", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                recycler_view.setAdapter(adapter);
                            } else {
                                Toast.makeText(ActivityDoctorTopSpecList.this, "List have no data", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            System.out.println("<><><>"+e.getMessage().toString());
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("cat_id", id);
                 return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }




    public void initview() {
        recycler_view=findViewById(R.id.mRecycleview);
        iv_back = findViewById(R.id.iv_back);
        btn_dr_spec_profile = findViewById(R.id.btn_dr_spec_profile);
        btn_dr_spec_book_now = findViewById(R.id.btn_dr_spec_book_now);
        specialist=findViewById(R.id.specialist);
        search_hint=findViewById(R.id.search_hint);

    }

    public void clicklistner() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
      /*  btn_dr_spec_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }




    private void filterSearch(String newText) {
        ArrayList<ModelDoctorTopSpecList> doctore_list_data = new ArrayList<ModelDoctorTopSpecList>();
        for (ModelDoctorTopSpecList smodel : heroList) {
            if (smodel.getDr_name().toLowerCase().startsWith(newText.toLowerCase())) {
                doctore_list_data.clear();
                doctore_list_data.add(smodel);
                Log.e("<><><> ", doctore_list_data.toString());
            }
        }
        adapter.setFilter(doctore_list_data);



      /*  if (doctore_list_data!=null) {
            recycler_view.setAdapter(mAdapter);
        }*/


    }









}
