package com.userdoctor.ui.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.userdoctor.R;

/**
 * Created by Ravindra Birla on 26/09/2019.
 */
public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private Context context;

    public MarkerInfoWindowAdapter(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public View getInfoWindow(Marker arg0) {
        return null;
    }

    @Override
    public View getInfoContents(Marker arg0) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.info_dialog_dr_list, null);

        LatLng latLng = arg0.getPosition();
        TextView tv_name = v.findViewById(R.id.tv_name);
        tv_name.setText("Lat:" + latLng.latitude + "" + "Long:" + latLng.longitude);

        return v;
    }
}
