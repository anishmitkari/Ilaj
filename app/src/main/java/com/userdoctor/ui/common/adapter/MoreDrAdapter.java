package com.userdoctor.ui.common.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.userdoctor.R;
import com.userdoctor.ui.common.activity.Specialist_Doctors.ActivityDoctorTopSpecList;
import com.userdoctor.ui.common.model.TopHomeSpeciallist;
import com.userdoctor.ui.common.utils.URLs;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MoreDrAdapter extends ArrayAdapter<TopHomeSpeciallist> {
    List<TopHomeSpeciallist> listdata;
    Context context;
    int resource;
    List<TopHomeSpeciallist> filterArrayList;
    public MoreDrAdapter(@NonNull Context context, int resource, @NonNull List<TopHomeSpeciallist> listdata) {
        super(context, resource, listdata);
        this.listdata=listdata;
        this.context=context;
        this.resource=resource;


        this.filterArrayList = new ArrayList<>();//initiate filter list
        this.filterArrayList.addAll(listdata);



    }


  /*  public void setFilter(List<TopHomeSpeciallist> drList1) {
        listdata = drList1;
        notifyDataSetChanged();
    }*/
  public void setFilter( String charText, boolean isSearchWithPrefix) {
      charText = charText.toLowerCase(Locale.getDefault());
      listdata.clear();
      if (charText.length() == 0) {
          listdata.addAll(filterArrayList);
      } else {

          for (TopHomeSpeciallist model : filterArrayList) {

              //Now check the type of search filter
              if (isSearchWithPrefix) {
                  if (model.getCategoryname().toLowerCase(Locale.getDefault()).startsWith(charText))
                      listdata.add(model);
              } else {
                  if (model.getCategoryname().toLowerCase(Locale.getDefault()).contains(charText))
                      listdata.add(model);
              }


              }

          }
      notifyDataSetChanged();
      }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_view_more, null, true);
        }
        final TopHomeSpeciallist listdata = getItem(position);
        ImageView img = (ImageView) convertView.findViewById(R.id.mImageDrIcon);
        Picasso.with(context)
                .load(URLs.IMAGE_HOMEICON_SPECIALIST+listdata.getImageicon())
                .into(img);
        TextView textView=(TextView)convertView.findViewById(R.id.mTextDrName);
        textView.setText(listdata.getCategoryname());

        LinearLayout mLinearLayout=(LinearLayout)convertView.findViewById(R.id.mLinearLayout);
        mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ActivityDoctorTopSpecList.class);
                intent.putExtra("id",listdata.getId());
                context.startActivity(intent);




            }
        });
        return convertView;
    }}




