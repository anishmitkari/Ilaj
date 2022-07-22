package com.userdoctor.ui.common.activity.Doctors.Home_Doctor_list.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.userdoctor.R;
import com.userdoctor.ui.common.activity.Doctors.Home_Doctor_list.Activity.ActivityBookNow_drTopSpec;
import com.userdoctor.ui.common.activity.Doctors.Home_Doctor_list.Model.ScheduleTimeData;

import java.util.List;


public class ScheduyleTimeDataAdapter extends RecyclerView.Adapter<ScheduyleTimeDataAdapter.ViewHolder> {
    private List<ScheduleTimeData> drList;
    private ScheduleTimeData drData;
    private Context context;
    int index = -1;
    OnShareClickedListener mCallback;


    public ScheduyleTimeDataAdapter(List<ScheduleTimeData> scheduleTimeDataList, ActivityBookNow_drTopSpec context) {
        this.drList = scheduleTimeDataList;
        this.context = context;
    }

    public void setOnShareClickedListener(OnShareClickedListener mCallback) {
        this.mCallback = mCallback;
    }



    @NonNull
    @Override
    public ScheduyleTimeDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_schedule_day, parent, false);

        return new ScheduyleTimeDataAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduyleTimeDataAdapter.ViewHolder holder, final int position) {
        if (drList.size() > 0) {

            drData = drList.get(position);
            holder.mtext_schedule.setText(drData.getDay_shedule() + " shedule available");
            holder.day.setText(drData.getDay());

            if (index == position) {

                holder.day.setTextColor(Color.parseColor("#ffffff"));
                holder.mtext_schedule.setTextColor(Color.parseColor("#ffffff"));
                holder.card_schedule.setCardBackgroundColor(Color.parseColor("#00c8d7"));



                //holder.card_schedule.setBackgroundResource(R.drawable.button_green_color);

            } else {

                holder.day.setTextColor(Color.parseColor("#00c8d7"));
                holder.mtext_schedule.setTextColor(Color.parseColor("#00c8d7"));
                holder.card_schedule.setCardBackgroundColor(Color.parseColor("#ffffff"));
                //holder.card_schedule.setBackgroundResource(R.drawable.btn_cancel_new_bg);
            }

                holder.card_schedule.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        index = position;
                        notifyDataSetChanged();

                        mCallback.ShareClicked(drList.get(position).getDay_id());
                        System.out.println("check day "+drList.get(position).getDay());
                        Toast.makeText(context, " "+drList.get(position).getDay(), Toast.LENGTH_SHORT).show();


                    }
                });


        }
    }

    @Override
    public int getItemCount() {

        return drList.size();

    }


    public interface OnShareClickedListener {
        public void ShareClicked(String data);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView day, mtext_schedule;
        CardView card_schedule;


        public ViewHolder(View parent) {
            super(parent);
            card_schedule = parent.findViewById(R.id.card_schedule);
            mtext_schedule = parent.findViewById(R.id.mtext_schedule);
            day = parent.findViewById(R.id.day);


        }
    }
}








