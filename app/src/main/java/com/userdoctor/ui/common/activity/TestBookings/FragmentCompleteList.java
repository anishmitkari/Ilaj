package com.userdoctor.ui.common.activity.TestBookings;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.userdoctor.R;
import com.userdoctor.ui.common.Interface.Api_Call;
import com.userdoctor.ui.common.activity.TestBookings.Adapter.CompleteDataAdapterList;
import com.userdoctor.ui.common.model.CompleteAppointment.CompleteData;
import com.userdoctor.ui.common.model.CompleteAppointment.CompleteModel;
import com.userdoctor.ui.common.utils.APIClient;
import com.userdoctor.ui.common.utils.SharedPrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentCompleteList extends Fragment {
    private Context context;
    private View view;
    RecyclerView recycler_view;
    SharedPrefManager sharedPrefManager;
    int user_id;
    String id;
    CompleteDataAdapterList completeDataAdapterList;
    ArrayList<CompleteData> completeDataArrayList=new ArrayList<>();

    public FragmentCompleteList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_complete_apoint_list, container, false);
        initView();
        sharedPrefManager = new SharedPrefManager(getActivity());
        if (sharedPrefManager != null) {
            user_id = sharedPrefManager.getUser().getId();
        }

        id = String.valueOf(user_id);

        System.out.println("check user id"+id);
        getCompleteApi();

        return view;
    }

    private void initView() {
        recycler_view=view.findViewById(R.id.recycler_view);
    }


    public void getCompleteApi()
    {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();
        Api_Call apiInterface = APIClient.getClient().create(Api_Call.class);

        Call<CompleteModel> call = apiInterface.getCompleteBookings(id);
        call.enqueue(new Callback<CompleteModel>() {
            @Override
            public void onResponse(Call<CompleteModel> call, Response<CompleteModel> response) {
                try{
                    if (response!=null){
                        Log.e("error_Dr_login",""+response.body().getResult());
                        if (!response.body().getResult().equalsIgnoreCase("false")) {

                            for (int i = 0; i < response.body().getCompleteData().size(); i++) {
                                completeDataArrayList.add(new CompleteData(





                                                response.body().getCompleteData().get(i).getUserBookingId(),
                                                response.body().getCompleteData().get(i).getUserId(),
                                                response.body().getCompleteData().get(i).getNurseId(),
                                                response.body().getCompleteData().get(i).getImage(),
                                                response.body().getCompleteData().get(i).getNsName(),
                                                response.body().getCompleteData().get(i).getNsContactNo(),
                                                response.body().getCompleteData().get(i).getHospitalName(),
                                                response.body().getCompleteData().get(i).getAddress(),
                                                response.body().getCompleteData().get(i).getHomeSampleCollection(),
                                                response.body().getCompleteData().get(i).getStatus(),
                                                response.body().getCompleteData().get(i).getDate()




                                        )
                                );
                            }
                        } else {
                            Toast.makeText(getActivity(), ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }

                        if(response.body().getCompleteData()!=null){
                            completeDataAdapterList= new CompleteDataAdapterList(getActivity(),response.body().getCompleteData());
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                            recycler_view.setLayoutManager(mLayoutManger);
                            recycler_view.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                            recycler_view.setItemAnimator(new DefaultItemAnimator());
                            recycler_view.setAdapter(completeDataAdapterList);

                        }else {
                            Toast.makeText(getActivity(), "list not found", Toast.LENGTH_SHORT).show();

                        }



                    }
                }catch (Exception e){
                    Log.e("error_Dr_login", e.getMessage());
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<CompleteModel> call, Throwable t) {
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
