package com.userdoctor.ui.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.userdoctor.R;
import com.userdoctor.ui.common.activity.OTHER_Activity.ActivityWelcome;

public class WelcomePagerAdapter extends PagerAdapter {
    private Context mContext;
    LayoutInflater mLayoutInflater;
    private int[] mResources;
    private ActivityWelcome activity;

    public WelcomePagerAdapter(ActivityWelcome activity, Context mContext, int[] mResources) {
        this.mContext = mContext;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mResources = mResources;
        this.activity = activity;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        View itemView = mLayoutInflater.inflate(R.layout.list_item_welcome, container, false);
        ImageView iv_icon = itemView.findViewById(R.id.iv_icon);
        // ImageView imageView =  itemView.findViewById(R.id.imageView);
        //TextView tv_title =  itemView.findViewById(R.id.tv_title);
        //TextView ctvText1 =  itemView.findViewById(R.id.ctvText1);
        //setDescText(position, tv_title, ctvText1, iv_icon);
        setDescText(position, iv_icon);


        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return mResources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    //public void setDescText(int pos, TextView ctvText, TextView txt, ImageView img) {
    public void setDescText(int pos, ImageView img) {
        switch (pos) {
            case 0:
                //ctvText.setText(mContext.getString(R.string.title1));
                //txt.setText(mContext.getString(R.string.msg1));
                img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.slider1));
                break;
            case 1:
                //ctvText.setText(mContext.getString(R.string.title2));
                //txt.setText(mContext.getString(R.string.msg2));
                img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.slider2));
                break;
            case 2:
                //ctvText.setText(mContext.getString(R.string.title3));
                //txt.setText(mContext.getString(R.string.msg3));
                img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.slider3));
                break;
        }
    }
}
