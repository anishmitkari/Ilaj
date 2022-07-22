package com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.userdoctor.R;
import com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Sample.GetDate_TimeModel.TimeSlot;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SubTimeAdapter extends RecyclerView.Adapter<com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Adapter.SubTimeAdapter.ViewHolder> {
    private List<com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Sample.GetDate_TimeModel.TimeSlot> contactModels = new ArrayList<>();
    Context context;
    TimeSlot contactModel;
    // public static List<String> timeselect=new ArrayList<>();
    public static HashSet<String> timeselect=new HashSet<>();




    public SubTimeAdapter(List<com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Sample.GetDate_TimeModel.TimeSlot> timeSlots, Context context) {
        this.contactModels = timeSlots;
        this.context=context;

    }

    @NonNull
    @Override
    public com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Adapter.SubTimeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subtime_formate, parent, false);
        return new com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Adapter.SubTimeAdapter.ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Adapter.SubTimeAdapter.ViewHolder holder, final int position) {


        if (contactModels.size() > 0) {
            contactModel = contactModels.get(position);

            holder.text_time_Schedule.setText(contactModel.getFromTime() +" - "+contactModel.getToTime());


            if (timeselect.contains(contactModels.get(position).getId())) {
                holder.text_time_Schedule.setTextColor(Color.parseColor("#ffffff"));
                holder.cardView.setCardBackgroundColor(Color.parseColor("#00c8d7"));
            } else {
                holder.text_time_Schedule.setTextColor(Color.parseColor("#00c8d7"));
                holder.cardView.setCardBackgroundColor(Color.WHITE);
            }


            holder.cardView.setOnClickListener(new View.OnClickListener() {

                boolean clicked = true;
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {

                    Context context=v.getContext();
                    String mStrTimeSelect=contactModels.get(position).getId();
                    if (!clicked) {
                        timeselect.remove(mStrTimeSelect);
                        holder.text_time_Schedule.setTextColor(Color.parseColor("#00c8d7"));
                        holder.cardView.setCardBackgroundColor(Color.WHITE);
                        clicked = true;
                    } else {
                        timeselect.add(mStrTimeSelect);
                     //   Toast.makeText(context, " "+timeselect, Toast.LENGTH_SHORT).show();
                        holder.text_time_Schedule.setTextColor(Color.parseColor("#ffffff"));
                        holder.cardView.setCardBackgroundColor(Color.parseColor("#00c8d7"));
                        clicked = false;
                    }
                }
            });

        } else {
            Toast.makeText(context, "no record found", Toast.LENGTH_SHORT).show();
        } }

    @Override
    public int getItemCount() {
        return contactModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text_time_Schedule;
        CardView cardView;
        ImageView imageView;



        public ViewHolder(View parent) {
            super(parent);
            text_time_Schedule=parent.findViewById(R.id.current_timeslots);
            cardView=parent.findViewById(R.id.cardview);




        }
    }


}




