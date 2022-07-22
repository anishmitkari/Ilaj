package com.userdoctor.ui.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.userdoctor.R;
import com.userdoctor.ui.common.activity.Doctors.Home_Doctor_list.Model.DocotrRating.DoctorRatingModelData;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
//RattingReviewAdapter
//list_item_ratting_list
/**
 * Created by Ravindra Birla on 20/09/2019.
 */
public class RattingReviewAdapter extends RecyclerView.Adapter<RattingReviewAdapter.ViewHolder> {

    List<DoctorRatingModelData> speciality_data;
    Context context;
    View viewlike;



    public RattingReviewAdapter(Context activity, List<DoctorRatingModelData> nurseAppoiData) {
        this.context = activity;
        this.speciality_data=nurseAppoiData;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_name,date,review;
        RatingBar ratingBar;
        CircleImageView img_profile;

        public ViewHolder(View view) {
            super(view);
            viewlike = view;
            tv_name=viewlike.findViewById(R.id.tv_name);
            date=viewlike.findViewById(R.id.date);
            review=viewlike.findViewById(R.id.reviews);
            ratingBar=viewlike.findViewById(R.id.ratingBar);
            img_profile=viewlike.findViewById(R.id.img_profile);

        }
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_ratting_list, parent, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {


        final DoctorRatingModelData specialityData1 = speciality_data.get(position);
        viewHolder.tv_name.setText(speciality_data.get(position).getFname());
        String ratingString=speciality_data.get(position).getRating();
        viewHolder.ratingBar.setRating(Float.parseFloat(ratingString));
        viewHolder.review.setText(speciality_data.get(position).getReviws());
        viewHolder.date.setText(speciality_data.get(position).getDate());


       /* Glide.with(context)
                .load(USER_IMAGE+speciality_data.get(position).getImage())
                .into(viewHolder.img_profile);*/




    }

    @Override
    public int getItemCount() {
        return speciality_data.size();
    }


}

