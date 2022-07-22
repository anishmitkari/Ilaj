package com.userdoctor.ui.common.adapter;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.userdoctor.R;
import com.userdoctor.ui.common.model.DrawerItem;

import java.util.ArrayList;

/**
 * Created by Raghvendra on 19-07-2019.
 */

public class LeftDrawerAdapter extends BaseAdapter {


    ArrayList<DrawerItem> Drawerlist = new ArrayList<>();
    Context mContext;
    private int selectedIndex;
    DrawerItem drawerItem;
    ImageView iconimg;
    ImageView icon_profile;

    public LeftDrawerAdapter(Context context, ArrayList<DrawerItem> listArray) {
        mContext = context;
        Drawerlist = listArray;
        //fragmentManager = fm;
    }

    @Override
    public int getCount() {
        return Drawerlist.size();    // total number of elements in the list
    }

    @Override
    public Object getItem(int i) {
        return Drawerlist.get(i);    // single item in the list
    }

    @Override
    public long getItemId(int i) {
        return i;                   // index number
    }

    @Override
    public View getView(int index, View view, final ViewGroup parent) {

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            view = inflater.inflate(R.layout.list_item_drawer, parent, false);
        }

        drawerItem = Drawerlist.get(index);

        TextView itemtxt = view.findViewById(R.id.list_item_id);
        iconimg = view.findViewById(R.id.icon_id);
        icon_profile = view.findViewById(R.id.icon_profile);
        LinearLayout linearLayout = view.findViewById(R.id.layout_id);


        //set name and icon on listview
        itemtxt.setText(drawerItem.getItemName());
        icon_profile.setImageResource((int) drawerItem.getIconImg());

        //Selected Item  Highlighted
        if (selectedIndex != -1 && index == selectedIndex) {

            //Log.e("test123455",""+drawerItem.getItemName());

            //PP call selectedIcon method
            //setSelectedIconImage();

            linearLayout.setBackgroundResource(R.drawable.listselection_bg);
            itemtxt.setTextColor(Color.parseColor("#000000"));

            //TextStyle Bold
            itemtxt.setTypeface(Typeface.DEFAULT_BOLD);

        } else {

            itemtxt.setTextColor(Color.parseColor("#000000"));
            //linearLayout.setBackground(null);
            linearLayout.setBackgroundResource(R.drawable.listdeselection_bg);
            itemtxt.setTypeface(Typeface.DEFAULT);

        }


        return view;
    }


    //PP method to make selected icon white
    public void setSelectedIconImage() {


        switch (drawerItem.getItemName()) {
            case "AppointMents":
                iconimg.setImageResource(R.drawable.ic_next_gray);
                break;

            case "Test Bookings":
                iconimg.setImageResource(R.drawable.ic_next_gray);

                break;
            case "Orders":
                iconimg.setImageResource(R.drawable.ic_next_gray);

                break;
            /*case "My Doctors":
                iconimg.setImageResource(R.drawable.ic_next_gray);

                break;*/
            case "Medical Records":
                iconimg.setImageResource(R.drawable.ic_next_gray);
                break;
            case "Reminder":
                iconimg.setImageResource(R.drawable.ic_next_gray);
                break;
            case "Payment":
                iconimg.setImageResource(R.drawable.ic_next_gray);
                break;
            /*case "Blogs":
                iconimg.setImageResource(R.drawable.ic_next_gray);
                break;*/
            case "Setting":
                iconimg.setImageResource(R.drawable.ic_next_gray);
                break;
            case "Help":
                iconimg.setImageResource(R.drawable.ic_next_gray);
                break;
            case "Share App":
                iconimg.setImageResource(R.drawable.ic_next_gray);
                break;
            case "LogOut":
                iconimg.setImageResource(R.drawable.ic_next_gray);

                break;
        }
    }

    public void setSelectedIndex(int ind) {
        selectedIndex = ind;
        notifyDataSetChanged();
    }
}
