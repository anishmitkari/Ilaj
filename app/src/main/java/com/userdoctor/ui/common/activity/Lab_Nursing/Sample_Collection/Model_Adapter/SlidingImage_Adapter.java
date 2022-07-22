package com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Adapter;

import android.content.Context;
import android.os.Parcelable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.userdoctor.R;
import com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Activity.Item_ClickOnRecycleviwAdapterTest;
import com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Sample.Banner;
import com.userdoctor.ui.common.utils.URLs;
import com.squareup.picasso.Picasso;

import java.util.List;


public class SlidingImage_Adapter extends PagerAdapter {


    private List<Banner> bannerlist;
    private LayoutInflater inflater;
    private Context context;


   /* public SlidingImage_Adapter() {
        this.context = context;
        this.IMAGES=IMAGES;
        inflater = LayoutInflater.from(context);
    }*/


   /* public SlidingImage_Adapter(List<Banner> IMAGES, LayoutInflater inflater, Context context) {
        this.bannerlist = IMAGES;
        this.inflater = inflater;
        this.context = context;
    }*/

    public SlidingImage_Adapter(List<Banner> banners, Item_ClickOnRecycleviwAdapterTest item_clickOnRecycleviwAdapterTest) {
        this.bannerlist = banners;
        this.context = item_clickOnRecycleviwAdapterTest;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return bannerlist.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);


       // System.out.println(bannerlist.get(position).getBanner());

        Picasso.with(context).load(URLs.BANNER + bannerlist.get(position).getBanner()).into(imageView);



        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}
