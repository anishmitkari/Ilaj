package com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class SubTimeVaccinationAdapter extends RecyclerView.Adapter<SubTimeVaccinationAdapter.ViewHolder>{

    private List<TimeSlot_Vacc> contactModels = new ArrayList<>();
    Context context;
    TimeSlot_Vacc contactModel;
    TextView current_timeslots;
    public static HashSet<String> timeselect_vacc=new HashSet<>();


    public SubTimeVaccinationAdapter(List<TimeSlot_Vacc> timeSlotVaccs, Context context) {

        this.contactModels = timeSlotVaccs;
        this.context=context;


    }

    @NonNull
    @Override
    public SubTimeVaccinationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subtime_formate, parent, false);
        return new SubTimeVaccinationAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SubTimeVaccinationAdapter.ViewHolder holder, int position) {
        if (contactModels.size() > 0) {
            contactModel = contactModels.get(position);
            holder.current_timeslots.setText(contactModel.getFromTime() +" - "+contactModel.getToTime());
            if (timeselect_vacc.contains(contactModels.get(position).getId())) {
                holder.current_timeslots.setTextColor(Color.parseColor("#ffffff"));
                holder.cardview.setCardBackgroundColor(Color.parseColor("#00c8d7"));
            } else {
                holder.current_timeslots.setTextColor(Color.parseColor("#00c8d7"));
                holder.cardview.setCardBackgroundColor(Color.WHITE);
            }


            holder.cardview.setOnClickListener(new View.OnClickListener() {

                boolean clicked = true;
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {

                    Context context=v.getContext();
                    String mStrTimeSelect=contactModels.get(position).getId();
                    if (!clicked) {
                        timeselect_vacc.remove(mStrTimeSelect);
                        holder.current_timeslots.setTextColor(Color.parseColor("#00c8d7"));
                        holder.cardview.setCardBackgroundColor(Color.WHITE);
                        clicked = true;
                    } else {
                        timeselect_vacc.add(mStrTimeSelect);
                     //   Toast.makeText(context, " "+timeselect_vacc, Toast.LENGTH_SHORT).show();
                        holder.current_timeslots.setTextColor(Color.parseColor("#ffffff"));
                        holder.cardview.setCardBackgroundColor(Color.parseColor("#00c8d7"));
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
        public TextView current_timeslots, count;
        public ImageView imageview;
        public LinearLayout mLinearLayout;

         CardView cardview;

        public ViewHolder(View parent) {
            super(parent);

            mLinearLayout = parent.findViewById(R.id.mLinearLayout);
            cardview=parent.findViewById(R.id.cardview);
            current_timeslots=parent.findViewById(R.id.current_timeslots);


        }
    }
}
