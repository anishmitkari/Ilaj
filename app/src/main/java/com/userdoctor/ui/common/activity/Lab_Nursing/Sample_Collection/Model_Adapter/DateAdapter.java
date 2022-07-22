package com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.userdoctor.R;
import com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Activity.Activity_Sample_Book_Time;
import com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Sample.Current_Date_Model.Date;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.ViewHolder> {
    private List<Date> contactModels = new ArrayList<>();
    Context context;
    Date contactModel;
    Activity fx;
    String mStrDateSelect;

    public static LinkedHashSet<String> dateset = new LinkedHashSet<>();


    public DateAdapter(List<Date> drListdata, Activity activity) {
        this.contactModels = drListdata;
        this.fx = activity;


    }

    @NonNull
    @Override
    public DateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date_formate, parent, false);
        return new DateAdapter.ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull DateAdapter.ViewHolder holder, final int position) {


        if (contactModels.size() > 0) {

            contactModel = contactModels.get(position);
            holder.next_date.setText(contactModel.getFormated());


            if (dateset.contains(contactModels.get(position).getDate())) {
                holder.next_date.setTextColor(Color.parseColor("#ffffff"));
                holder.cardView.setCardBackgroundColor(Color.parseColor("#00c8d7"));
            } else {
                holder.next_date.setTextColor(Color.parseColor("#00c8d7"));
                holder.cardView.setCardBackgroundColor(Color.WHITE);
            }

            //  notifyItemChanged(position);

            holder.cardView.setOnClickListener(new View.OnClickListener() {

                boolean clicked = false;

                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {

                    Context context = v.getContext();
                     mStrDateSelect = contactModels.get(position).getDate();

                    HashMap<String, String> map = new HashMap<>();
                    map.put("date", contactModels.get(position).getDate());


                    try {
                        if (context instanceof Activity_Sample_Book_Time) {
                            ((Activity_Sample_Book_Time) context).yourDesiredMethod(mStrDateSelect);
                        }
                    } catch (Exception e) {
                        System.out.println("check the exception " + e);
                    }


                    if (clicked == false) {

                        clicked = true;
                        dateset.clear();
                        holder.next_date.setTextColor(Color.parseColor("#ffffff"));
                        holder.cardView.setCardBackgroundColor(Color.parseColor("#00c8d7"));
                        dateset.add(mStrDateSelect);
                     //   Toast.makeText(context, " " + dateset + "", Toast.LENGTH_SHORT).show();

                        notifyDataSetChanged();

                    } else if (clicked == true) {
                        clicked = false;
                        dateset.remove(mStrDateSelect);
                     //   Toast.makeText(context, " " + dateset + "", Toast.LENGTH_SHORT).show();
                        holder.next_date.setTextColor(Color.parseColor("#00c8d7"));
                        holder.cardView.setCardBackgroundColor(Color.WHITE);
                        notifyDataSetChanged();
                    }


                }
            });

        } else {

            Toast.makeText(context, "no record found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return contactModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView next_date;
        LinearLayout mlinearlayout;
        CardView cardView;

        public ViewHolder(View parent) {
            super(parent);
            next_date = parent.findViewById(R.id.next_date);
            mlinearlayout = parent.findViewById(R.id.mLinearLayout);
            cardView = parent.findViewById(R.id.cardview);

        }
    }

}
