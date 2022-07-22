package com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Adapter;

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
import com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Sample.TestList;

import java.util.ArrayList;
import java.util.List;

public class SampleAdapter extends RecyclerView.Adapter<SampleAdapter.ViewHolder> {
    private List<TestList> contactModels = new ArrayList<>();
    Context context;
    TestList contactModel;
    Activity fx;

    InsideSampleDataAdapter insideSampleDataAdapter;
    public SampleAdapter(List<TestList> drListdata,Activity activity) {
        this.contactModels = drListdata;
        this.fx=activity;

    }



    public void setFilter(List<TestList> drList1) {
        contactModels = drList1;
        notifyDataSetChanged();
    }







    @NonNull
    @Override
    public SampleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_sample_adapter, parent, false);
        return new SampleAdapter.ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull SampleAdapter.ViewHolder holder, final int position) {


        if (contactModels.size() > 0) {
            contactModel = contactModels.get(position);
            holder.test_title.setText(contactModel.getTestName());



            insideSampleDataAdapter = new InsideSampleDataAdapter(contactModel.getSubcategory(), context);
            LinearLayoutManager manager = new LinearLayoutManager(context,  RecyclerView.HORIZONTAL, false);
            holder.recycler_view.setLayoutManager(manager);
            holder.recycler_view.setAdapter(insideSampleDataAdapter);




        } else {
            Toast.makeText(context, "no record found", Toast.LENGTH_SHORT).show();
        } }

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
            recycler_view=parent.findViewById(R.id.recycler_view);
            mLinearLayout = parent.findViewById(R.id.mLinearLayout);
            imageview = parent.findViewById(R.id.image);
            test_title=parent.findViewById(R.id.title_test);


        }
    }


}
