package com.userdoctor.ui.common.activity.Doctors.Home_Doctor_list.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.userdoctor.R;
import com.userdoctor.ui.common.Interface.Api_Call;
import com.userdoctor.ui.common.activity.Doctors.Home_Doctor_list.Model.DocotrRating.DoctorRatingModel;
import com.userdoctor.ui.common.adapter.RattingReviewAdapter;
import com.userdoctor.ui.common.model.RattingReviewData;
import com.userdoctor.ui.common.utils.APIClient;
import com.userdoctor.ui.common.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityRattingReview extends AppCompatActivity {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recycler_view;
    private LinearLayout ll_no_record;
    private RattingReviewAdapter mAdapter;
    private List<RattingReviewData> rattingList;
Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratting_review);
        NetworkUtil.isNetworkConnected(ActivityRattingReview.this);
        initView();
        if (getIntent()!=null)

        {   Bundle b = getIntent().getExtras();
            String dr_id = b.getString("id");
            mFunGetRatingApi(dr_id);
        }


    }




    public void mFunGetRatingApi(final String id) {
        final ProgressDialog progressDialog = new ProgressDialog(context, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();
        Api_Call apiInterface = APIClient.getClient().create(Api_Call.class);
        Call<DoctorRatingModel> call = apiInterface.DRRatingList(id);
        call.enqueue(new Callback<DoctorRatingModel>() {
            @Override
            public void onResponse(Call<DoctorRatingModel> call, Response<DoctorRatingModel> response) {
                try {
                    if (response != null) {
                        Log.e("rating ", "" + response.body().getResult());
                        if (!response.body().getResult().equalsIgnoreCase("false")) {
                            if (response.body().getDoctorRatingModelData() != null) {
                                mAdapter = new RattingReviewAdapter(context, response.body().getDoctorRatingModelData());
                                RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(context);
                                recycler_view.setLayoutManager(mLayoutManger);
                                recycler_view.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
                                recycler_view.setItemAnimator(new DefaultItemAnimator());
                                recycler_view.setAdapter(mAdapter);


                                
                            }

                            else { Toast.makeText(context, "list not found", Toast.LENGTH_SHORT).show(); } }

                        else {
                            Toast.makeText(context, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (Exception e) {
                    Log.e("error_Dr_login", e.getMessage());
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<DoctorRatingModel> call, Throwable t) {
                progressDialog.dismiss();

                Log.e("error_Dr_login", t.getMessage());


            }
        });


    }












    @SuppressLint("WrongConstant")
    private void initView() {

        rattingList = new ArrayList<>();
        recycler_view = findViewById(R.id.recycler_view);
        ll_no_record = findViewById(R.id.ll_no_record);
        context=ActivityRattingReview.this;

        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    /*    mAdapter = new RattingReviewAdapter(rattingList, this);
        RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(mLayoutManger);
        recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();*/




    }
}
