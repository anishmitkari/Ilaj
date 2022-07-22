package com.userdoctor.ui.common.activity.Doctors.Home_Doctor_list.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.userdoctor.R;
import com.userdoctor.ui.common.activity.Doctors.Home_Doctor_list.Activity.ActivityDrDetail;
import com.userdoctor.ui.common.model.DoctorListData;
import com.userdoctor.ui.common.utils.Constant;
import com.userdoctor.ui.common.utils.URLs;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ravindra Birla on 20/09/2019.
 */
public class DrListAdapter extends RecyclerView.Adapter<DrListAdapter.ViewHolder> {
    private List<DoctorListData> drList;
    private DoctorListData drData;
    private Context context;



    public DrListAdapter(List<DoctorListData> drList, Context context) {
        this.drList = drList;
        this.context = context;
    }


    public void setFilter(List<DoctorListData> drList1) {
        drList = drList1;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public DrListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_dr_list, parent, false);
        return new DrListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DrListAdapter.ViewHolder holder, final int position) {
        if (drList.size() > 0) {
            drData = drList.get(position);
            holder.tv_name.setText(drData.getDr_name());
            holder.mTextAddress.setText(drData.getHospital_name());

         try {
             float f=Float.parseFloat(drData.getDistance());
             Float km=f/1000;
             System.out.println("KILOMETER-------       "+km);
         }catch (Exception e){
             e.printStackTrace();
         }
            holder.mTextDistance.setText(drData.getDistance()+" km");
            System.out.println("id  "+drData.getId());

            if (!drData.getProfile_img().equalsIgnoreCase(null)){
                Picasso.with(context).load(URLs.IMAGE_DOCTORLIST+drData.getProfile_img()).fit().centerCrop()
                        .placeholder(R.drawable.ic_home_doc)
                        .error(R.drawable.ic_home_doc)
                        .into(holder.img_profile);
            }

             String rating =drData.getAvg_rating();
            Constant.typ=drData.getType();
            if (drData.getAvg_rating().equals("null"))
            {
                holder.ratingBar.setRating(Float.parseFloat("0.0"));
            }
            else {
                holder.ratingBar.setRating(Float.parseFloat(rating));
            }


            holder.ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ActivityDrDetail.class);
                    intent.putExtra("id",drList.get(position).getId());
                    intent.putExtra("INTENT","HOME");
                    context.startActivity(intent);

                }
            });



        }
    }

    @Override
    public int getItemCount() {
        //return 1;
        return drList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name, mTextAddress,mTextDistance;
        public ImageView img_profile;
        public LinearLayout ll_item;
        RatingBar ratingBar;


        public ViewHolder(View parent) {
            super(parent);


            img_profile=parent.findViewById(R.id.img_profile);
            tv_name=parent.findViewById(R.id.tv_name);
            mTextAddress=parent.findViewById(R.id.mTextAddress);
            mTextDistance=parent.findViewById(R.id.mTextDistance);
            ll_item = parent.findViewById(R.id.ll_item);
            ratingBar=parent.findViewById(R.id.ratingBar);


        }
    }
}
