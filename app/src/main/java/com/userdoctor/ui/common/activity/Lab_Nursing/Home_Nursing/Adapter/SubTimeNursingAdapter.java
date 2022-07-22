package com.userdoctor.ui.common.activity.Lab_Nursing.Home_Nursing.Adapter;

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
import com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Vaccination_Model.Vaccination_Time_Slots.TimeSlot_Vacc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SubTimeNursingAdapter extends RecyclerView.Adapter<SubTimeNursingAdapter.ViewHolder> {
    private List<TimeSlot_Vacc> contactModels = new ArrayList<>();
    Context context;
    TimeSlot_Vacc contactModel;
    public static HashSet<String> timeselect_nursing=new HashSet<>();




    public SubTimeNursingAdapter(List<TimeSlot_Vacc> timeSlots, Context context) {
        this.contactModels = timeSlots;
        this.context=context;

    }

    @NonNull
    @Override
    public SubTimeNursingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subtime_formate, parent, false);
        return new SubTimeNursingAdapter.ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull SubTimeNursingAdapter.ViewHolder holder, final int position) {


        if (contactModels.size() > 0) {
            contactModel = contactModels.get(position);

            holder.text_time_Schedule.setText(contactModel.getFromTime() +" - "+contactModel.getToTime());


            if (timeselect_nursing.contains(contactModels.get(position).getId())) {
                holder.cardView.setCardBackgroundColor(Color.parseColor("#FF3C3BF5"));
            } else {
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
                        timeselect_nursing.remove(mStrTimeSelect);
                        holder.cardView.setCardBackgroundColor(Color.WHITE);
                        clicked = true;
                    } else {
                        timeselect_nursing.add(mStrTimeSelect);
                     //   Toast.makeText(context, " "+timeselect_nursing, Toast.LENGTH_SHORT).show();
                        holder.cardView.setCardBackgroundColor(Color.parseColor("#FF3C3BF5"));
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


