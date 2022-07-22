package com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.userdoctor.R;
import com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Activity.Vaccination_Detail;
import com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Vaccination_Model.VaccinationsType;
import com.userdoctor.ui.common.utils.PrefrenceManager;

import java.util.ArrayList;
import java.util.List;

public class InsideVaccinationDataAdapter extends RecyclerView.Adapter<InsideVaccinationDataAdapter.ViewHolder> {
    private List<VaccinationsType> contactModels = new ArrayList<>();
    public Context context;
    VaccinationsType contactModel;



    public InsideVaccinationDataAdapter(List<VaccinationsType> drListdata, Context cx) {
        this.contactModels = drListdata;
        this.context=cx;

    }

    @NonNull
    @Override
    public InsideVaccinationDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_categroy_item, parent, false);
        return new InsideVaccinationDataAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (contactModels.size() > 0) {
            contactModel = contactModels.get(position);
            holder.title_name.setText(contactModel.getName());
            holder.description.setText(contactModel.getDescription());
            holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try
                    {
                        Context context1 = v.getContext();
                        String Vaccination_Main_Category_ID=contactModels.get(position).getId();
                        PrefrenceManager.setVaccination_Main_Category_ID(context1,Vaccination_Main_Category_ID);
                        Intent intent=new Intent(context1, Vaccination_Detail.class);
                        context1.startActivity(intent);

                    }
                    catch (Exception e)
                    {
                        System.out.println("exception e "+e.toString());
                    }

                }
            });
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
        public TextView title_name,description;
        public ImageView imageview;
        public LinearLayout mLinearLayout;
        RecyclerView recycler_view;



        public ViewHolder(View parent) {
            super(parent);
            mLinearLayout=parent.findViewById(R.id.mLinearLayout);
            description=parent.findViewById(R.id.description);
            title_name=parent.findViewById(R.id.title_name);


        }
    }


}

