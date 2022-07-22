package com.userdoctor.ui.common.activity.OTHER_Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.userdoctor.R;
import com.userdoctor.ui.common.adapter.MoreDrAdapter;
import com.userdoctor.ui.common.model.TopHomeSpeciallist;
import com.userdoctor.ui.common.utils.DialogUtil;
import com.userdoctor.ui.common.utils.NetworkUtil;
import com.userdoctor.ui.common.utils.URLs;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class Activity_More_Dr extends AppCompatActivity {

    LinearLayout ll_doctor, ll_item;
    ImageView iv_back;
    GridView gridView;
    MoreDrAdapter adapter;
    EditText search_hint;
    private boolean isSearchWithPrefix = false;

    private List<TopHomeSpeciallist> topHomeSpeciallists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__more__dr);
        initview();

        topHomeSpeciallists = new ArrayList<>();
        NetworkUtil.isNetworkConnected(Activity_More_Dr.this);

        loadHeroList();
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        search_hint.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.setFilter( charSequence.toString(), isSearchWithPrefix);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

    }


    public void initview() {
        gridView = findViewById(R.id.gridView);
        iv_back = findViewById(R.id.iv_back);
        search_hint=findViewById(R.id.search_hint);
    }


    private void loadHeroList() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.BASE_URL + "Get_Specialist",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        DialogUtil.show(Activity_More_Dr.this);
                        try {
                            JSONObject obj = new JSONObject(response);
                            System.out.println("check response  " + obj);
                            boolean responsedata = obj.getBoolean("result");
                            if (responsedata == true) {
                                DialogUtil.dismiss();
                                JSONArray jsonArray = obj.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    TopHomeSpeciallist toplist = new TopHomeSpeciallist(jsonObject.getString("id"),jsonObject.getString("cat_name"), jsonObject.getString("icon"));
                                    topHomeSpeciallists.add(toplist);
                                }
                                adapter = new MoreDrAdapter(Activity_More_Dr.this,R.layout.item_view_more,topHomeSpeciallists);
                                gridView.setAdapter(adapter);
                            }
                            else {
                                Toast.makeText(Activity_More_Dr.this, "data not found", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
