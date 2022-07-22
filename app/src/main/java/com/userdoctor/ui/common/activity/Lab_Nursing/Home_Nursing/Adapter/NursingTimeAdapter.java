package com.userdoctor.ui.common.activity.Lab_Nursing.Home_Nursing.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.userdoctor.R;
import com.userdoctor.ui.common.activity.Lab_Nursing.Home_Nursing.Activity.Visit_Time_Activity;
import com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Vaccination_Model.Vaccination_Time_Slots.VaccinationTimeDatum;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class NursingTimeAdapter extends RecyclerView.Adapter<NursingTimeAdapter.ViewHolder> {
    private List<VaccinationTimeDatum> contactModels = new ArrayList<>();
    Context context;
    VaccinationTimeDatum contactModel;
    Activity fx;

    SubTimeNursingAdapter subTimeNursingAdapter;
    static List<String> dateselect=new ArrayList<>();





    public NursingTimeAdapter(List<VaccinationTimeDatum> datum, Visit_Time_Activity activity) {
        this.contactModels=datum;
        this.fx=activity;
    }


    @NonNull
    @Override
    public NursingTimeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vaccination_item_time_formate, parent, false);
        return new NursingTimeAdapter.ViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull NursingTimeAdapter.ViewHolder holder, int position) {


        if (contactModels.size() > 0) {
            contactModel = contactModels.get(position);

            holder.text_time_Schedule.setText(contactModel.getSentinel());
            if (position==0)
            {
                Picasso.with(context).load(R.drawable.sunny).error(R.drawable.sunny).into(holder.imageView);
            }
            if (position==1)
            {
                Picasso.with(context).load(R.drawable.sunrise).error(R.drawable.sunrise).into(holder.imageView);
            }
            if (position==2)
            {
                Picasso.with(context).load(R.drawable.evening).error(R.drawable.evening).into(holder.imageView);
            }
            if (position==3)
            {
                Picasso.with(context).load(R.drawable.moon).error(R.drawable.moon).into(holder.imageView);
            }




            subTimeNursingAdapter=new SubTimeNursingAdapter(contactModel.getTimeSlotVaccs(), context);
            @SuppressLint("WrongConstant") GridLayoutManager manager = new GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false);
            holder.recyclerView.setLayoutManager(manager);

            if (subTimeNursingAdapter.timeselect_nursing!=null)
            {
                subTimeNursingAdapter.timeselect_nursing.clear();
            }

            holder.recyclerView.setAdapter(subTimeNursingAdapter);







        }
        else {
            Toast.makeText(context, "no record found", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public int getItemCount() {
        return contactModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text_time_Schedule;
        RecyclerView recyclerView;
        ImageView imageView;


        public ViewHolder(View parent) {
            super(parent);
            text_time_Schedule=parent.findViewById(R.id.text_time_Schedule);
            recyclerView=parent.findViewById(R.id.recycler_view);
            imageView=parent.findViewById(R.id.image);

        }
    }


}
