package com.userdoctor.ui.common.activity.Appointments.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.userdoctor.R;
import com.userdoctor.ui.common.Interface.Api_Call;
import com.userdoctor.ui.common.activity.Appointments.Adapter.AdapterDRUpcomingList;
import com.userdoctor.ui.common.activity.Home_Activity.Model.DrUpcomingModel;
import com.userdoctor.ui.common.model.UpComing.UpcomingData;
import com.userdoctor.ui.common.utils.APIClient;
import com.userdoctor.ui.common.utils.SharedPrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDRUpcomingList extends Fragment {

    private Context context;
    private View view;
    Button btn_booking_cancel;
    SharedPrefManager sharedPrefManager;
    int user_id;
    String id;
    RecyclerView recycler_user_list;
    AdapterDRUpcomingList adapterDRUpcomingList;
    ArrayList<UpcomingData> upcomingDataArrayList=new ArrayList<>();
    String nurse_id;

    public FragmentDRUpcomingList() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_fragment_drupcoming_list, container, false);
        initView();

        sharedPrefManager = new SharedPrefManager(getActivity());
        if (sharedPrefManager != null) {
            user_id = sharedPrefManager.getUser().getId();
        }

        id = String.valueOf(user_id);
        System.out.println("check user3456 id"+id);
        getUpComingList();

        return view;
    }
    private void initView() {
        recycler_user_list=view.findViewById(R.id.recycler_user_list);
    }


    private void getUpComingList() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();
        Api_Call apiInterface = APIClient.getClient().create(Api_Call.class);
        Call<DrUpcomingModel> call = apiInterface.getDRUpComingHistory(id);
        call.enqueue(new Callback<DrUpcomingModel>() {
            @Override
            public void onResponse(Call<DrUpcomingModel> call, Response<DrUpcomingModel> response) {
                try{
                    if (response!=null){
                        Log.e("error_Dr_login",""+response.body().getResult());

                        System.out.println("FragmentDRUpcomingList--getUpComingList--     "+response.body().getDrUpcomingModelData());
                        if (!response.body().getResult().equalsIgnoreCase("false")) {
                            if(response.body().getDrUpcomingModelData()!=null){
                                adapterDRUpcomingList= new AdapterDRUpcomingList(getActivity(), response.body().getDrUpcomingModelData());
                                RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                                recycler_user_list.setLayoutManager(mLayoutManger);
                                recycler_user_list.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                                recycler_user_list.setItemAnimator(new DefaultItemAnimator());
                                recycler_user_list.setAdapter(adapterDRUpcomingList);

                            }else {
                                Toast.makeText(getActivity(), "list not found", Toast.LENGTH_SHORT).show();
                            }

                        }


                        else {
                            Toast.makeText(getActivity(), ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }





                    }
                }catch (Exception e){
                    Log.e("error_Dr_login", e.getMessage());
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<DrUpcomingModel> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("error_Dr_login",t.getMessage());
            }
        });


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

}
