package com.userdoctor.ui.common.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.userdoctor.R;
import com.userdoctor.ui.common.activity.Specialist_Doctors.ActivityDoctorTopSpecList;
import com.userdoctor.ui.common.model.TopHomeSpeciallist;
import com.userdoctor.ui.common.utils.URLs;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Object> itemList;
    private final static int TopHomeSpeciallist = 1;
    private final static int ADVERTISE = 2;

    public HomeAdapter(List<Object> itemList) {
        this.itemList = itemList;
    }


    @Override
    public int getItemViewType(int position) {

        if (itemList.get(position) instanceof TopHomeSpeciallist) {
            return TopHomeSpeciallist;
        } /*else if (itemList.get(position) instanceof Advertise){
            return ADVERTISE;
        }*/
        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = null;
        switch (viewType) {
            case TopHomeSpeciallist:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_top_specialist, viewGroup, false);
                return new TopSpecialViewHolder(view);
           /* case ADVERTISE:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ad_row, viewGroup, false);
                return new AdViewHolder(view);*/
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        int type = viewHolder.getItemViewType();
        switch (type) {
            case TopHomeSpeciallist:
                final TopSpecialViewHolder topSpecialViewHolder = (TopSpecialViewHolder) viewHolder;
                 final TopHomeSpeciallist topHomeSpeciallist = (TopHomeSpeciallist) itemList.get(i);


                final Context context = topSpecialViewHolder.top_image_icon.getContext(); //<----- Add this line
                Picasso.with(context).load(URLs.IMAGE_HOMEICON_SPECIALIST + topHomeSpeciallist.getImageicon()).into(topSpecialViewHolder.top_image_icon);
                topSpecialViewHolder.mTextSpecialist.setText(topHomeSpeciallist.getCategoryname());

                topSpecialViewHolder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    // int position=topSpecialViewHolder.getAdapterPosition();

                        Intent intent = new Intent(context, ActivityDoctorTopSpecList.class);
                        intent.putExtra("id",topHomeSpeciallist.getId());
                        intent.putExtra("SPECIAL",topHomeSpeciallist.getCategoryname());
                        context.startActivity(intent);



                    }
                });


                //   movieViewHolder.movie_cat_name.setText(movie.getMovieCategory());
                break;
           /* case ADVERTISE:
                AdViewHolder adViewHolder = (AdViewHolder) viewHolder;
                Advertise advertise = (Advertise) itemList.get(i);
                adViewHolder.ad_image.setImageResource(advertise.getImageAd());
                break;*/
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public class TopSpecialViewHolder extends RecyclerView.ViewHolder {
        private ImageView top_image_icon;
        private TextView mTextSpecialist;
        private LinearLayout mLinearLayout;

        public TopSpecialViewHolder(@NonNull View itemView) {
            super(itemView);
            mLinearLayout=itemView.findViewById(R.id.mLinearLayout);
            top_image_icon = itemView.findViewById(R.id.iv_sample1);
            mTextSpecialist = itemView.findViewById(R.id.mTextSpecialist);
        }


    }

   /* public class AdViewHolder extends RecyclerView.ViewHolder{
        private ImageView ad_image;
        public AdViewHolder(@NonNull View itemView) {
            super(itemView);
            ad_image = itemView.findViewById(R.id.ad_image);
        }
    }*/
}
