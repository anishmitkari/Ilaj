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
import com.userdoctor.ui.common.activity.Doctors.Home_Doctor_list.Activity.ActivityDrDetail;
import com.userdoctor.ui.common.model.CheckUpData;

import java.util.List;

/**
 * Created by Ravindra Birla on 20/09/2019.
 */
public class CheckUpListAdapter extends RecyclerView.Adapter<CheckUpListAdapter.ViewHolder> {
    private List<CheckUpData> checkList;
    private CheckUpData checkUpData;
    private Context context;
    //private Session session;
    private String user_id;

    public CheckUpListAdapter(List<CheckUpData> checkList, Context context) {
        this.checkList = checkList;
        this.context = context;
    }

    @NonNull
    @Override
    public CheckUpListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_checkup, parent, false);
        /*session = new Session(context);
        user_id = session.getUser().user_id;*/
        return new CheckUpListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckUpListAdapter.ViewHolder holder, final int position) {
        if (checkList.size() > 0) {
            //checkUpData = checkList.get(position);
            //holder.tv_degree.setText(checkUpData.Degree_Name);
            //holder.tv_yop.setText(checkUpData.Year_Of_Pass);


            /*if (!checkUpData.image.equalsIgnoreCase(null)){

                Picasso.with(context).load(checkUpData.image).fit().centerCrop()
                        .placeholder(R.drawable.doctor)
                        .error(R.drawable.doctor)
                        .into(holder.img_profile);
            }*/

        }
    }

    @Override
    public int getItemCount() {
        return checkList.size();
        //return checkList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_degree, tv_yop;
        public ImageView iv_delete;
        public LinearLayout ll_item;


        public ViewHolder(View parent) {
            super(parent);
            ll_item = parent.findViewById(R.id.ll_item);
            // tv_yop = parent.findViewById(R.id.tv_yop);
            //iv_delete = parent.findViewById(R.id.iv_delete);


            ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ActivityDrDetail.class);
                    context.startActivity(intent);
                }
            });

        }
    }
}
