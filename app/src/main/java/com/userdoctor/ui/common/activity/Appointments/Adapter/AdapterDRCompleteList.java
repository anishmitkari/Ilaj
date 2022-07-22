package com.userdoctor.ui.common.activity.Appointments.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.userdoctor.R;
import com.userdoctor.ui.common.activity.Appointments.Activity.DRUpcomiing_Detail_Page;
import com.userdoctor.ui.common.activity.Home_Activity.Model.DRCompleteData;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.userdoctor.ui.common.utils.URLs.IMAGE_CLINIKLIST;

public class AdapterDRCompleteList extends RecyclerView.Adapter<AdapterDRCompleteList.ViewHolder> {

    List<DRCompleteData> speciality_data;
    Context context;
    View viewlike;
    String booking_id;


    public AdapterDRCompleteList(FragmentActivity activity, List<DRCompleteData> completeData) {
        this.context=activity;
        this.speciality_data=completeData;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name, address,date,viewdetail,booking_id;

        ImageView image;

        public ViewHolder(View view) {
            super(view);
            viewlike = view;
            name = viewlike.findViewById(R.id.name);
            address=viewlike.findViewById(R.id.address);
            date=viewlike.findViewById(R.id.date);
            image=viewlike.findViewById(R.id.image);
            viewdetail=viewlike.findViewById(R.id.viewdetail);
            booking_id=viewlike.findViewById(R.id.booking_id);

        }
    }


    @Override
    public AdapterDRCompleteList.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_complete_list, parent, false);
        return new AdapterDRCompleteList.ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final AdapterDRCompleteList.ViewHolder viewHolder, final int position) {


        final DRCompleteData specialityData1 = speciality_data.get(position);
        viewHolder.name.setText(specialityData1.getDrName());
        viewHolder.address.setText("Hospital Name -"+specialityData1.getHospitalName());
        viewHolder.date.setText("Date -"+specialityData1.getDate());
        viewHolder.booking_id.setText("Booking Id -"+specialityData1.getUserBookingId());


        if (specialityData1.getProfileImg()!=null&&specialityData1.getProfileImg().isEmpty()){

        Picasso.with(context).load(IMAGE_CLINIKLIST+specialityData1.getProfileImg()).fit().centerCrop().placeholder(R.drawable.ic_clinic1).error(R.drawable.ic_clinic1).into(viewHolder.image);

        }
        else
        {
        Picasso.with(context).load(R.drawable.ic_clinic1).fit().centerCrop().placeholder(R.drawable.ic_clinic1).error(R.drawable.ic_clinic1).into(viewHolder.image);
        }


        booking_id=speciality_data.get(position).getUserBookingId();


        System.out.println("booking id "+booking_id);




        viewHolder.viewdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DRUpcomiing_Detail_Page.class);
                intent.putExtra("booking_id",booking_id);
                context.startActivity(intent);
            }
        });




    }


    @Override
    public int getItemCount() {
        return speciality_data.size();
    }


}
