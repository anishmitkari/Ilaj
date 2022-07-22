package com.userdoctor.ui.common.activity.Lab_Nursing.Home_Nursing.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.userdoctor.R;
import com.userdoctor.ui.common.utils.NetworkUtil;

public class Activity_Home_Nursing extends AppCompatActivity {
TextView book_now;
RecyclerView recyclerView;
ImageView iv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__home__nursing);
        init();


        NetworkUtil.isNetworkConnected(Activity_Home_Nursing.this);





        book_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Activity_Home_Nursing.this, Nursing_Detail_Time.class));
            }
        });

    }



    public void init()
    {

        book_now=findViewById(R.id.book_now);
        recyclerView=findViewById(R.id.recycler_view);
        iv_back=findViewById(R.id.iv_back);



    }


}
