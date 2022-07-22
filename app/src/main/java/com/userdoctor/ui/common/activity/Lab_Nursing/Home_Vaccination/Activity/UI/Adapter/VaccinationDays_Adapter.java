package com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.userdoctor.R;
import com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Activity.Vaccination_Next_Page;
import com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Vaccination_Model.Vaccination_Days_Model.Vaccination_Days_Datum;
import com.userdoctor.ui.common.utils.PrefrenceManager;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class VaccinationDays_Adapter extends RecyclerView.Adapter<VaccinationDays_Adapter.ViewHolder> {
    private List<Vaccination_Days_Datum> contactModels = new ArrayList<>();
    Context context;
    Vaccination_Days_Datum contactModel;
    Activity fx;
    int index = -1;

    public static String days_id;
    public static LinkedHashSet<String> days_id_list = new LinkedHashSet<>();



    public VaccinationDays_Adapter(List<Vaccination_Days_Datum> drListdata, Activity activity) {
        this.contactModels = drListdata;
        this.fx = activity;

    }


    @NonNull
    @Override
    public VaccinationDays_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vaccination_day, parent, false);
        return new VaccinationDays_Adapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        if (contactModels.size() > 0) {
            contactModel = contactModels.get(position);
            holder.mText_VaccinationDays.setText(contactModel.getName());

            holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {

                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    //  String mStrDateSelect = contactModels.get(position).getDate();
                    days_id = contactModels.get(position).getId();
                    PrefrenceManager.setVaccinationDaysId(context, days_id);

                   /* at a time one position select and change background color*/
                    index = position;
                    notifyDataSetChanged();

                    Intent intent = new Intent(context, Vaccination_Next_Page.class);
                    context.startActivity(intent);

                }
            });



            if(index==position){
                holder.mText_VaccinationDays.setTextColor(Color.parseColor("#ffffff"));
                  holder.mText_VaccinationDays.setBackgroundResource(R.drawable.button_green_color);

            }else{
                holder.mText_VaccinationDays.setTextColor(Color.parseColor("#00c8d7"));
                  holder.mText_VaccinationDays.setBackgroundResource(R.drawable.btn_cancel_new_bg);
            }




        } else {
            Toast.makeText(context, "no record found", Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public int getItemCount() {
        return contactModels.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mText_VaccinationDays;
        LinearLayout mLinearLayout;

        public ViewHolder(View parent) {
            super(parent);
            mText_VaccinationDays = parent.findViewById(R.id.mText_VaccinationDays);
            mLinearLayout = parent.findViewById(R.id.mLinearLayout);

        }
    }


}