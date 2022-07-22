package com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.userdoctor.R;
import com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Vaccination_Model.Vaccination_Datum;

import java.util.ArrayList;
import java.util.List;

public class Vaccination_Adapter_Main extends RecyclerView.Adapter<Vaccination_Adapter_Main.ViewHolder> {
    private List<Vaccination_Datum> contactModels = new ArrayList<>();
    Context context;
    Vaccination_Datum contactModel;
    Activity fx;

    InsideVaccinationDataAdapter insideVaccinationDataAdapter;

    public Vaccination_Adapter_Main(List<Vaccination_Datum> drListdata, Activity activity) {
        this.contactModels = drListdata;
        this.fx = activity;
    }





    public void setFilter(List<Vaccination_Datum> drList1) {
        contactModels = drList1;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public Vaccination_Adapter_Main.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_vaccination_adapter, parent, false);
        return new Vaccination_Adapter_Main.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (contactModels.size() > 0) {
            contactModel = contactModels.get(position);
            holder.test_title.setText(contactModel.getName());

            insideVaccinationDataAdapter = new InsideVaccinationDataAdapter(contactModel.getVaccinationsTypes(), context);
            LinearLayoutManager manager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
            holder.recycler_view.setLayoutManager(manager);
            holder.recycler_view.setAdapter(insideVaccinationDataAdapter);

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
        public TextView test_title, count;
        public ImageView imageview;
        public LinearLayout mLinearLayout;
        RecyclerView recycler_view;


        public ViewHolder(View parent) {
            super(parent);
            recycler_view = parent.findViewById(R.id.recycler_view);
            mLinearLayout = parent.findViewById(R.id.mLinearLayout);
            imageview = parent.findViewById(R.id.image);
            test_title = parent.findViewById(R.id.title_test);
        }
    }


}