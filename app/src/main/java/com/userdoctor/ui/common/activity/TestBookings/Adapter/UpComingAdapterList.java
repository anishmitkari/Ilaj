package com.userdoctor.ui.common.activity.TestBookings.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.userdoctor.R;
import com.userdoctor.ui.common.activity.TestBookings.Upcomiing_Detail_Page;
import com.userdoctor.ui.common.model.UpComing.UpcomingData;
import com.userdoctor.ui.common.utils.SharedPrefManager;
import com.rxandroidnetworking.RxAndroidNetworking;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import org.json.JSONObject;

import java.io.File;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.userdoctor.ui.common.utils.PathUtils.bitmapToFile;
import static com.userdoctor.ui.common.utils.URLs.Upload_reciept;


public class UpComingAdapterList extends RecyclerView.Adapter<UpComingAdapterList.ViewHolder> {

    List<UpcomingData> speciality_data;
    Context context;
    View viewlike;
    String booking_id;
    File imgFile1;
    int user_id;
    String id;
    String nurse_id;
    SharedPrefManager sharedPrefManager;
    ProgressDialog mProgressDialog;
    public UpComingAdapterList(Context activity, List<UpcomingData> nurseAppoiData, String nurse_id) {
        this.context = activity;
        this.speciality_data = nurseAppoiData;
        this.nurse_id=nurse_id;

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView booking_id, address,viewdetail, dr_name, hospitalname, tv_date, sample, tv_name, tv_address, patient_id, doctor_id;

        Button upload_receipt, status;




        public ViewHolder(View view) {
            super(view);
            viewlike = view;

            dr_name = viewlike.findViewById(R.id.dr_name);
            hospitalname = viewlike.findViewById(R.id.hospitalname);
            tv_date = viewlike.findViewById(R.id.date);
            doctor_id = viewlike.findViewById(R.id.doctor_id);
            sample = viewlike.findViewById(R.id.sample);
            address = viewlike.findViewById(R.id.address);
           // call = viewlike.findViewById(R.id.call);
            status = viewlike.findViewById(R.id.status);
            viewdetail = viewlike.findViewById(R.id.viewdetail);
            booking_id = viewlike.findViewById(R.id.booking_id);
            upload_receipt=viewlike.findViewById(R.id.upload_receipt);


        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_upcoming_list, parent, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {


        UpcomingData specialityData1 = speciality_data.get(position);

        sharedPrefManager = new SharedPrefManager(context);
        if (sharedPrefManager != null) {
            user_id = sharedPrefManager.getUser().getId();
        }
        id = String.valueOf(user_id);

        System.out.println("check id test"+id);
        Log.e("booking id ", speciality_data.get(position).getType());


        viewHolder.dr_name.setText(specialityData1.getNsName());
        viewHolder.hospitalname.setText(specialityData1.getHospitalName());
        viewHolder.tv_date.setText(specialityData1.getDate());
        viewHolder.sample.setText(specialityData1.getType());




        viewHolder.upload_receipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage1( speciality_data.get(position).getUserBookingId());
            }
        });
        viewHolder.doctor_id.setText("user id " + specialityData1.getNurseId());
        viewHolder.address.setText(specialityData1.getAddress());
        viewHolder.status.setText(specialityData1.getStatus());

        booking_id = speciality_data.get(position).getUserBookingId();
        viewHolder.booking_id.setText("Booking Id " + booking_id);

        System.out.println("booking id " + booking_id);

               if (specialityData1.getStatus().equals("Accepted")||specialityData1.getStatus().equals("Confirm")||specialityData1.getStatus().equals("Confirmed"))
               {
                   viewHolder.upload_receipt.setVisibility(View.VISIBLE);
               }



        viewHolder.viewdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Upcomiing_Detail_Page.class);
                intent.putExtra("booking_id", speciality_data.get(position).getUserBookingId());
                intent.putExtra("nurse_id", speciality_data.get(position).getNurseId());
                context.startActivity(intent);

            }
        });


    }


    @Override
    public int getItemCount() {
        return speciality_data.size();
    }
    private void selectImage1(String userBookingId) {

        final PickImageDialog dialog = PickImageDialog.build(new PickSetup());
        dialog.setOnPickCancel(new IPickCancel() {
            @Override
            public void onCancelClick() {
                dialog.dismiss();
            }
        }).setOnPickResult(new IPickResult() {
            @Override
            public void onPickResult(PickResult r) {

                if (r.getError() == null) {

                    imgFile1 = bitmapToFile(context, r.getBitmap());
                    String filename = imgFile1.getName();

                    System.out.println("check id is "+"recipt "+imgFile1+" "+" "+id+" "+nurse_id+" "+userBookingId);

                    UPLOAD_RECIEPT(id,nurse_id,userBookingId);

                } else {

                    Toast.makeText(context, r.getError().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

        }).show((FragmentActivity) context);
    }






    public void UPLOAD_RECIEPT(final String User_Id,final String nurse_id,final String user_booking_id) {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
        RxAndroidNetworking.upload(Upload_reciept)
                .addMultipartParameter("user_id", User_Id)
                .addMultipartParameter("user_booking_id",user_booking_id )
                .addMultipartFile("recipt", imgFile1)
                .build()
                .getJSONObjectObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {
                        // do anything onComplete
                        mProgressDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // handle error
                        mProgressDialog.dismiss();
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("rx_error", e.getMessage());
                    }


                    @Override
                    public void onNext(JSONObject response) {
                        //do anything with response
                        try {
                            mProgressDialog.dismiss();
                            Log.e("response check ", response.toString());
                            String res = response.getString("result");
                            String msg = response.getString("msg");
                            {
                                if (res.equals("true")) {
                                    Toast.makeText(context, "Submitted Data Successfully", Toast.LENGTH_SHORT).show();

                                } else {
                                    System.out.println("check msg " + msg);
                                    Toast.makeText(context, " " + msg, Toast.LENGTH_SHORT).show();
                                }

                            }

                        } catch (Exception e) {
                            Toast.makeText(context, "error " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();

                        }

                    }
                });


    }






}


