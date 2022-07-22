package com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Adapter;

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
import com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Activity.Item_ClickOnRecycleviwAdapterTest;
import com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Sample.Subcategory;

import java.util.ArrayList;
import java.util.List;

public class InsideSampleDataAdapter extends RecyclerView.Adapter<InsideSampleDataAdapter.ViewHolder> {
    private List<Subcategory> contactModels = new ArrayList<>();
  public Context context;
    Subcategory contactModel;



    public InsideSampleDataAdapter(List<Subcategory> drListdata, Context cx) {
        this.contactModels = drListdata;
        this.context=cx;

    }

    @NonNull
    @Override
    public InsideSampleDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_categroy_item, parent, false);
        return new InsideSampleDataAdapter.ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull InsideSampleDataAdapter.ViewHolder holder, final int position) {


        if (contactModels.size() > 0) {

            contactModel = contactModels.get(position);
            holder.title_name.setText(contactModel.getSubcatName());
            holder.description.setText(contactModel.getDescription());


            holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try
                    {
                        Context context1 = v.getContext();
                        Intent intent=new Intent(context1, Item_ClickOnRecycleviwAdapterTest.class);
                        intent.putExtra("id",contactModels.get(position).getId());
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
