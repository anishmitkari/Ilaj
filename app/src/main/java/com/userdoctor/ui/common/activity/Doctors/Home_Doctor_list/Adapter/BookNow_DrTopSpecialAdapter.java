package com.userdoctor.ui.common.activity.Doctors.Home_Doctor_list.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.userdoctor.R;
import com.userdoctor.ui.common.activity.Doctors.Home_Doctor_list.Activity.Activity_Dr_Time_booking;
import com.userdoctor.ui.common.activity.Doctors.Home_Doctor_list.Model.GetAvailableTimeData;
import java.util.List;


public class BookNow_DrTopSpecialAdapter extends RecyclerView.Adapter<BookNow_DrTopSpecialAdapter.ViewHolder> {
    private List<GetAvailableTimeData> drList;
    private GetAvailableTimeData drData;
    private Context context;
    int index = -1;
    String DR_Image,Dr_ID,DR_Name,DR_Specialist;


    public BookNow_DrTopSpecialAdapter(List<GetAvailableTimeData> drList, Context context, String dr_id, String dr_name, String specialist, String DR_Image) {
        this.drList = drList;
        this.context = context;
        this.Dr_ID=dr_id;
        this.DR_Name=dr_name;
        this.DR_Specialist=specialist;
        this.DR_Image=DR_Image;
    }

    @NonNull
    @Override
    public BookNow_DrTopSpecialAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_dr_booking_item, parent, false);

        return new BookNow_DrTopSpecialAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookNow_DrTopSpecialAdapter.ViewHolder holder, final int position) {
        if (drList.size() > 0) {

            drData = drList.get(position);

            holder.time.setText(drData.getAvail_start_tym() + " to " + drData.getAvail_end_tym());


            holder.card_schedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    index = position;
                    notifyDataSetChanged();



                    Intent intent = new Intent(context, Activity_Dr_Time_booking.class);
                    intent.putExtra("time_id",drList.get(position).getAvail_time_id());
                    intent.putExtra("start_time",drList.get(position).getAvail_start_tym());
                    intent.putExtra("end_time",drList.get(position).getAvail_end_tym());
                    intent.putExtra("dr_id",Dr_ID);
                    intent.putExtra("dr_name",DR_Name);
                    intent.putExtra("dr_specialist",DR_Specialist);
                    intent.putExtra("dr_image",DR_Image);


                    context.startActivity(intent);




                }
            });


            if (index == position) {
              //  holder.time.setTextColor(Color.parseColor("#ffffff"));
              //holder.card_schedule.setCardBackgroundColor(R.drawable.button_green_color);
              //holder.card_schedule.setBackgroundResource(R.drawable.button_green_color);
              // holder.card_schedule.setBackgroundColor(R.drawable.button_green_color);
                holder.time.setTextColor(Color.parseColor("#ffffff"));
                holder.card_schedule.setCardBackgroundColor(Color.parseColor("#00c8d7"));


            } else {
                holder.time.setTextColor(Color.parseColor("#00c8d7"));
                holder.card_schedule.setCardBackgroundColor(Color.parseColor("#ffffff"));

                //holder.card_schedule.setBackgroundResource(R.drawable.btn_cancel_new_bg);
            }






        }
    }

    @Override
    public int getItemCount() {

        return drList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView week_day, time;
        public LinearLayout ll_item;
        public CardView card_schedule;

        public ViewHolder(View parent) {
            super(parent);


            ll_item = parent.findViewById(R.id.ll_item);
            time = parent.findViewById(R.id.time);
            card_schedule=parent.findViewById(R.id.card_schedule);

        }
    }
}
