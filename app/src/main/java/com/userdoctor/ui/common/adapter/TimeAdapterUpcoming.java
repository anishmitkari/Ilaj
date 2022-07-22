package com.userdoctor.ui.common.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.userdoctor.R;
import com.userdoctor.ui.common.model.UpComing_Detail.Time_Date;

import java.util.List;

    public class TimeAdapterUpcoming extends RecyclerView.Adapter<TimeAdapterUpcoming.ViewHolder> {

        List<Time_Date> speciality_data;
        Context context;
        View viewlike;

        String selectedItem;
        private static final String JSON_STATUS ="http://logicalsofttech.com/doctor_app/NurseApi/nurse_accept_booking";
        private static final String JSON_CANCEL ="http://logicalsofttech.com/doctor_app/NurseApi/nurse_cancel_booking";


        public TimeAdapterUpcoming(List<Time_Date> list_data) {
            speciality_data=list_data;



        }


  /*  public TimeAdapter(Context activity, List<Apoint_User_Model_Data> speciality_data1) {
        context = activity;
        speciality_data = speciality_data1;

    }*/



   /* public void setFilter(List<Apoint_User_Model_Data>apointUserModelArrayList1){
        speciality_data=new ArrayList<>();
        speciality_data.addAll(apointUserModelArrayList1);
        notifyDataSetChanged();
    }*/


        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView time,sentinel;






            public ViewHolder(View view) {
                super(view);
                viewlike = view;
                time=viewlike.findViewById(R.id.time);
                sentinel=viewlike.findViewById(R.id.sentinel);



            }
        }



        @Override
        public TimeAdapterUpcoming.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_time_adapter, parent, false);

            return new TimeAdapterUpcoming.ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final TimeAdapterUpcoming.ViewHolder viewHolder, final int position) {


            final Time_Date specialityData1 = speciality_data.get(position);
            Log.e("jjj",speciality_data.get(position).getFrom_time());
            String from_time=specialityData1.getFrom_time();
            String to_time=specialityData1.getTo_time();

            viewHolder.time.setText(from_time+" to "+to_time);

            viewHolder.sentinel.setText(specialityData1.getSentinel());





        }



        @Override
        public int getItemCount() {
            return speciality_data.size();
        }





    }


