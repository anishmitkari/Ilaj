package com.userdoctor.ui.common.activity.Doctors.Clinik_List.Fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentClinicList extends Fragment {
    private Context context;
    private View view;
    private RecyclerView recycler_view;
    private LinearLayout ll_no_record;
    private ClinicListAdapter mAdapter;
    private List<ClinikListData> clinikList;
    LinearLayout  ll_top10, ll_near_by, ll_top_rating;


    public FragmentClinicList() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_clinic_list, container, false);
        initView();
        mFunCallClinikFragmentList();
        return view;

    }


    public void mFunCallClinikFragmentList() {
        DialogUtil.show(getActivity());
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
                                        Toast.makeText(getActivity(), "List have no data", Toast.LENGTH_SHORT).show();
                                    }


                                }
                                recycler_view.setAdapter(mAdapter);
                            } else {
                                Toast.makeText(getActivity(), "List have no data", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
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


        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    @SuppressLint("WrongConstant")
    private void initView() {
        clinikList = new ArrayList<>();
        recycler_view = view.findViewById(R.id.recycler_view);
        mAdapter = new ClinicListAdapter(clinikList, context);


        ll_top10 = view.findViewById(R.id.ll_top10);
        ll_near_by = view.findViewById(R.id.ll_near_by);
        ll_top_rating = view.findViewById(R.id.ll_top_rating);


        RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(context);
        recycler_view.setLayoutManager(mLayoutManger);
        recycler_view.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        mAdapter.notifyDataSetChanged();







        ll_top10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_top10.setBackgroundResource(R.color.colorPrimary);
                ll_near_by.setBackgroundResource(R.color.light_white_bg);
                ll_top_rating.setBackgroundResource(R.color.light_white_bg);
                mFunCallTop10List();

            }
        });

        ll_near_by.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_top10.setBackgroundResource(R.color.light_white_bg);
                ll_near_by.setBackgroundResource(R.color.colorPrimary);
                ll_top_rating.setBackgroundResource(R.color.light_white_bg);
                mFunCallNearByList();

            }
        });

        ll_top_rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_top10.setBackgroundResource(R.color.light_white_bg);
                ll_near_by.setBackgroundResource(R.color.light_white_bg);
                ll_top_rating.setBackgroundResource(R.color.colorPrimary);
                mFunCallDrRatingList();
            }
        });

    }

    private void mFunCallDrRatingList() {
            clinikList.clear();
            DialogUtil.show(getActivity());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_URL + "doctor_at_clinic_by_rating",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            DialogUtil.dismiss();
                            try {
                                JSONObject obj = new JSONObject(response);
                                boolean responsedata = obj.getBoolean("result");
                                System.out.println("call nearby doctor" + obj);
                                if (responsedata == true) {
                                    JSONArray jsonArray = obj.getJSONArray("data");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        System.out.println("<><><> dr home FragmentCLininclist      " + jsonObject);
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
                                            Toast.makeText(getActivity(), "List have no data", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    recycler_view.setAdapter(mAdapter);
                                } else {
                                    Toast.makeText(getActivity(), "fragment have no data", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                System.out.println("<><><> catch exc" + e.getMessage().toString());
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("check error  ..."+error);
                        }
                    })
            {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("lat", Lattitude);
                    params.put("lng", Longitude);
                    return params;
                }
            };

            VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

        }

    private void mFunCallNearByList() {
        clinikList.clear();
        DialogUtil.show(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_URL + "doctors_list_at_clinic",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        DialogUtil.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            boolean responsedata = obj.getBoolean("result");
                            System.out.println("call nearby doctor" + obj);
                            if (responsedata == true) {
                                JSONArray jsonArray = obj.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    System.out.println("<><><> dr home" + jsonObject);
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
                                        Toast.makeText(getActivity(), "List have no data", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                recycler_view.setAdapter(mAdapter);
                            } else {
                                Toast.makeText(getActivity(), "fragment have no data", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            System.out.println("<><><> catch exc" + e.getMessage().toString());
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("check error  ..."+error);
                    }
                })
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("lat", Lattitude);
                params.put("lng", Longitude);
                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);



}

    private void mFunCallTop10List() {
        clinikList.clear();
            DialogUtil.show(getActivity());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_URL + "doctor_top_clinic_list",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            DialogUtil.dismiss();
                            try {
                                JSONObject obj = new JSONObject(response);
                                boolean responsedata = obj.getBoolean("result");
                                System.out.println("call nearby doctor" + obj);
                                if (responsedata == true) {
                                    JSONArray jsonArray = obj.getJSONArray("data");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        System.out.println("<><><> dr home" + jsonObject);
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
                                            Toast.makeText(getActivity(), "List have no data", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    recycler_view.setAdapter(mAdapter);
                                } else {
                                    Toast.makeText(getActivity(), "fragment have no data", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                System.out.println("<><><> catch exc" + e.getMessage().toString());
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("check error  ..."+error);
                        }
                    })
            {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("lat", Lattitude);
                    params.put("lng", Longitude);
                    return params;
                }
            };

            VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

        }








    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
