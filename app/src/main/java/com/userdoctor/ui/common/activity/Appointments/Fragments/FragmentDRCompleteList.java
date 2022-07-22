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
import com.userdoctor.ui.common.activity.Appointments.Adapter.AdapterDRCompleteList;
import com.userdoctor.ui.common.activity.Home_Activity.Model.DRCompleteModel;
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
public class FragmentDRCompleteList extends Fragment {
   View view;
    private Context context;
    Button btn_booking_cancel;
    SharedPrefManager sharedPrefManager;
    int user_id;
    String id;
    RecyclerView recycler_user_list;
    AdapterDRCompleteList adapterDRCompleteList;
    ArrayList<UpcomingData> upcomingDataArrayList=new ArrayList<>();
    String nurse_id;


    public FragmentDRCompleteList() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_fragment_drcomplete_list, container, false);
        initView();
        sharedPrefManager = new SharedPrefManager(getActivity());
        if (sharedPrefManager != null) {
            user_id = sharedPrefManager.getUser().getId();
        }

        id = String.valueOf(user_id);
        System.out.println("check kratika id"+id);
        getCompleteApi();
        return view;

    }
    private void initView() {
        recycler_user_list=view.findViewById(R.id.recycler_user_list);
    }



    public void getCompleteApi()
    {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();
        Api_Call apiInterface = APIClient.getClient().create(Api_Call.class);

        Call<DRCompleteModel> call = apiInterface.getDRCompleteList(id);
        call.enqueue(new Callback<DRCompleteModel>() {
            @Override
            public void onResponse(Call<DRCompleteModel> call, Response<DRCompleteModel> response) {
                try{
                    if (response!=null){
                        Log.e("error_Dr_login",""+response.body().getResult());
                        if (!response.body().getResult().equalsIgnoreCase("false")) {
                            if(response.body().getDrCompleteDataList()!=null){

                                adapterDRCompleteList= new AdapterDRCompleteList(getActivity(),response.body().getDrCompleteDataList());
                                RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                                recycler_user_list.setLayoutManager(mLayoutManger);
                                recycler_user_list.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                                recycler_user_list.setItemAnimator(new DefaultItemAnimator());
                                recycler_user_list.setAdapter(adapterDRCompleteList);


                            }else {
                                Toast.makeText(getActivity(), "list not found", Toast.LENGTH_SHORT).show();

                            }

                        }
                        } else {
                            Toast.makeText(getActivity(), ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }





                }catch (Exception e){
                    Log.e("error_Dr_login", e.getMessage());
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<DRCompleteModel> call, Throwable t) {
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
