package com.userdoctor.ui.common.activity.TestBookings;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.userdoctor.R;
import com.userdoctor.ui.common.Interface.Api_Call;
import com.userdoctor.ui.common.activity.TestBookings.Adapter.UpComingAdapterList;
import com.userdoctor.ui.common.model.UpComing.UpcomingData;
import com.userdoctor.ui.common.model.UpComing.UpcomingModel;
import com.userdoctor.ui.common.utils.APIClient;
import com.userdoctor.ui.common.utils.SharedPrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentUpcomingList extends Fragment {
    private Context context;
    private View view;
    Button btn_booking_cancel;
    SharedPrefManager sharedPrefManager;
    int user_id;
    String id;
    RecyclerView recycler_user_list;
    UpComingAdapterList upComingAdapterList;
    ArrayList<UpcomingData> upcomingDataArrayList=new ArrayList<>();
    String nurse_id;
    public FragmentUpcomingList() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_upcoming_list_apoint, container, false);
        initView();

        sharedPrefManager = new SharedPrefManager(getActivity());
        if (sharedPrefManager != null) {
            user_id = sharedPrefManager.getUser().getId();
        }

        id = String.valueOf(user_id);

        System.out.println("check user id   "+id);

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

        Call<UpcomingModel> call = apiInterface.getUpComingHistory(id);
        call.enqueue(new Callback<UpcomingModel>() {
            @Override
            public void onResponse(Call<UpcomingModel> call, Response<UpcomingModel> response) {
                try{
                    if (response!=null){
                        Log.e("error_Dr_login",""+response.body().getResult());

                        System.out.println("Upcoming list------user-------         "+response.body().getResult());
                        if (!response.body().getResult().equalsIgnoreCase("false")) {
                            // Toast.makeText(context, "Successfull", Toast.LENGTH_SHORT).show();

                            for (int i = 0; i < response.body().getUpcomingDataList().size(); i++) {
                                upcomingDataArrayList.add(new UpcomingData(
                                                response.body().getUpcomingDataList().get(i).getUserBookingId(),
                                                response.body().getUpcomingDataList().get(i).getUserId(),
                                                response.body().getUpcomingDataList().get(i).getNurseId(),
                                                response.body().getUpcomingDataList().get(i).getImage(),
                                                response.body().getUpcomingDataList().get(i).getNsName(),
                                                response.body().getUpcomingDataList().get(i).getNsContactNo(),
                                                response.body().getUpcomingDataList().get(i).getHospitalName(),
                                                response.body().getUpcomingDataList().get(i).getAddress(),
                                                response.body().getUpcomingDataList().get(i).getHome_sample_collection(),
                                                response.body().getUpcomingDataList().get(i).getStatus(),
                                                response.body().getUpcomingDataList().get(i).getDate(),
                                                response.body().getUpcomingDataList().get(i).getType()
                                        )
                                );
                                 nurse_id=response.body().getUpcomingDataList().get(i).getNurseId();
                            }
                        } else {
                            Toast.makeText(getActivity(), ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }

                        if(response.body().getUpcomingDataList()!=null){
                            upComingAdapterList= new UpComingAdapterList(getActivity(),response.body().getUpcomingDataList(),  nurse_id);
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                            recycler_user_list.setLayoutManager(mLayoutManger);
                            recycler_user_list.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                            recycler_user_list.setItemAnimator(new DefaultItemAnimator());
                            recycler_user_list.setAdapter(upComingAdapterList);

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
            public void onFailure(Call<UpcomingModel> call, Throwable t) {
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
