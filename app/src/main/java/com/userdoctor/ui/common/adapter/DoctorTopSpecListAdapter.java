package com.userdoctor.ui.common.adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.userdoctor.R;
import com.userdoctor.ui.common.activity.Doctors.Home_Doctor_list.Activity.ActivityDrDetail;
import com.userdoctor.ui.common.model.ModelDoctorTopSpecList;
import com.userdoctor.ui.common.utils.URLs;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

    public class DoctorTopSpecListAdapter extends RecyclerView.Adapter<DoctorTopSpecListAdapter.ViewHolder> {

        Context context;

        List<ModelDoctorTopSpecList> getDataAdapter;

        public DoctorTopSpecListAdapter(List<ModelDoctorTopSpecList> getDataAdapter, Context context){
            this.getDataAdapter = getDataAdapter;
            this.context = context;
        }



    public void setFilter(List<ModelDoctorTopSpecList> drList1) {
        getDataAdapter = drList1;
        notifyDataSetChanged();
    }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor_top_spec, parent, false);

            ViewHolder viewHolder = new ViewHolder(v);

            return viewHolder;
        }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelDoctorTopSpecList getDataAdapter1 =  getDataAdapter.get(position);
        holder.tv_name.setText(getDataAdapter1.getDr_name());
        holder.mTextAddress.setText(getDataAdapter1.getAddress());
        holder.mTextSpecialist.setText(getDataAdapter1.getCat_name());
        holder.mTextExeperience.setText("Exepeience: "+getDataAdapter1.getExperience());
        holder.mTextCunsultationFee.setText("Cunsultation Fee: "+getDataAdapter1.getHome_fee()+" Rs");
        holder.mTextAvailability.setText("Availability at Home: "+getDataAdapter1.getHome_availability());

       if (getDataAdapter1.getProfile_img()!=null) {
           Picasso.with(context).load(URLs.IMAGE_DOCTORLIST+getDataAdapter1.getProfile_img()).into(holder.profile_imageview);
       }
       holder.btn_dr_spec_book_now.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(context, ActivityDrDetail.class);
               intent.putExtra("id",getDataAdapter.get(position).getId());
               intent.putExtra("INTENT","HOME");
               context.startActivity(intent);

           }
       });

       holder.btn_dr_spec_profile.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
             /*  Intent intent = new Intent(context, ActivityDrDetail.class);
               context.startActivity(intent);*/

               Intent intent = new Intent(context, ActivityDrDetail.class);
               intent.putExtra("id",getDataAdapter.get(position).getId());
               intent.putExtra("INTENT","HOME");
               context.startActivity(intent);


           }
       });

    }

        @Override
        public int getItemCount() {
            return getDataAdapter.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{

            public TextView tv_name,mTextAddress,mTextSpecialist,mTextExeperience,
                    mTextCunsultationFee,mTextAvailability;

            CircleImageView profile_imageview;

            Button btn_dr_spec_profile,btn_dr_spec_book_now;
            public ViewHolder(View itemView) {

                super(itemView);
                btn_dr_spec_profile=itemView.findViewById(R.id.btn_dr_spec_profile);
                mTextAddress = (TextView) itemView.findViewById(R.id.mTextAddress) ;
                mTextSpecialist = (TextView) itemView.findViewById(R.id.mTextSpecialist) ;
                mTextExeperience = (TextView) itemView.findViewById(R.id.mTextExeperience) ;
                mTextCunsultationFee = (TextView) itemView.findViewById(R.id.mTextCunsultationFee) ;
                mTextAvailability=itemView.findViewById(R.id.mTextAvailability);
                tv_name=itemView.findViewById(R.id.tv_name);
                profile_imageview=itemView.findViewById(R.id.img_profile);
                btn_dr_spec_book_now=itemView.findViewById(R.id.btn_dr_spec_book_now);
            }
        }
    }



