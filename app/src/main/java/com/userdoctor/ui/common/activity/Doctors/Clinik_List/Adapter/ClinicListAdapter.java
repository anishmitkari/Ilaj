package com.userdoctor.ui.common.activity.Doctors.Clinik_List.Adapter;

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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.userdoctor.R;
import com.userdoctor.ui.common.activity.Doctors.Home_Doctor_list.Activity.ActivityDrDetail;
import com.userdoctor.ui.common.model.ClinikListData;
import com.squareup.picasso.Picasso;
import com.userdoctor.ui.common.utils.Constant;

import java.text.DecimalFormat;
import java.util.List;

import static com.userdoctor.ui.common.utils.URLs.IMAGE_DOCTORLIST;
import static java.lang.Math.round;

/**
 * Created by Ravindra Birla on 20/09/2019.
 */
public class ClinicListAdapter extends RecyclerView.Adapter<ClinicListAdapter.ViewHolder> {
    private List<ClinikListData> clinikList;
    private ClinikListData drData;
    private Context context;


    public ClinicListAdapter(List<ClinikListData> clinikList, Context context) {
        this.clinikList = clinikList;
        this.context = context;
    }

    public void setFilter(List<ClinikListData> drList1) {
        clinikList = drList1;
        notifyDataSetChanged();
    }




    @NonNull
    @Override
    public ClinicListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_clinic_list, parent, false);
        /*session = new Session(context);
        user_id = session.getUser().user_id;*/
        return new ClinicListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ClinicListAdapter.ViewHolder holder, final int position) {
        if (clinikList.size() > 0) {
            drData = clinikList.get(position);
            holder.tv_name.setText(drData.getClinic_name());
            holder.mTextAddress.setText(drData.getClinic_address());
           // holder.mTextDistance.setText(drData.getDistance()+" km");
           // String add= round(drData.getClinic_address(), 2);
            float f1 = Float.parseFloat(drData.getDistance());
            System.out.println("====dou=="+f1);

            holder.mTextDistance.setText(new DecimalFormat("##.##").format(f1)+ "  Km");

            Constant.typ=drData.getType();
            if (drData.getAvg_rating().equals("null"))
            {
                holder.ratingBar.setRating(Float.parseFloat("0.0"));
            }
            else
            {
                holder.ratingBar.setRating(Float.parseFloat(drData.getAvg_rating()));
            }


            //System.out.println("check clinik name"+drData.getProfile_img());
           // Log.e("Clinik name",drData.getProfile_img());

            if (drData.getProfile_img()!=null&&!drData.getProfile_img().isEmpty()){
            Picasso.with(context).load(IMAGE_DOCTORLIST+drData.getProfile_img()).fit().centerCrop().placeholder(R.drawable.ic_clinic1).error(R.drawable.ic_clinic1).into(holder.img_profile);
            }

            else
            {
                Picasso.with(context).load(R.drawable.ic_clinic1).fit().centerCrop()
                        .placeholder(R.drawable.ic_clinic1)
                        .error(R.drawable.ic_clinic1)
                        .into(holder.img_profile);
            }

            holder.card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(context, ActivityDrDetail.class);
                    intent.putExtra("id",clinikList.get(position).getId());
                    intent.putExtra("INTENT","CLINIK");
                    context.startActivity(intent);
                }
            });



//            holder.ll_clinic_details.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    Intent intent =new Intent(context, Activity_Clinic_Details.class);
//                    context.startActivity(intent);
//
//                }
//            });





        }
    }

    @Override
    public int getItemCount() {
        //return 10;
        return clinikList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name, mTextAddress,mTextDistance;
        public ImageView img_profile;
        LinearLayout ll_clinic_details;
        CardView card_view;
        RatingBar ratingBar;


        public ViewHolder(View parent) {
            super(parent);
            tv_name = parent.findViewById(R.id.tv_name);
            img_profile = parent.findViewById(R.id.img_profile);
            mTextAddress = parent.findViewById(R.id.mTextAddress);
            mTextDistance = parent.findViewById(R.id.mTextDistance);
            ratingBar=parent.findViewById(R.id.ratingBar);
            ll_clinic_details = parent.findViewById(R.id.ll_clinic_details);
            card_view = parent.findViewById(R.id.card_view);



        }
    }
}
