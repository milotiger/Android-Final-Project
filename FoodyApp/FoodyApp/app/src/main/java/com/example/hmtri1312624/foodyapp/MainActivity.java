package com.example.hmtri1312624.foodyapp;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.hmtri1312624.foodyapp.Model.FoodyItemInfo;
import com.example.hmtri1312624.foodyapp.Service.RestService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RVAdapter adapter;

    List<FoodyItemInfo> data;
    RecyclerView rv;
    LinearLayout layout;
    String foodname = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        Intent i = getIntent();
        Bundle bundle = i.getBundleExtra("MyPackage");
        if(bundle != null) {
            foodname  = bundle.getString("FoodName");
            //foodname = foodname.replace(" ","%20");
            getData();
        }
    }

    private void getData(){

        RestService restService = new RestService();
        Call<List<FoodyItemInfo>> call = restService.getService().GetPlaces(foodname);
        call.enqueue(new Callback<List<FoodyItemInfo>>() {
            @Override
            public void onResponse(Call<List<FoodyItemInfo>> call, Response<List<FoodyItemInfo>> response) {
                data = response.body();
                adapter = new RVAdapter(MainActivity.this, data, new CustomItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Log.d("", "clicked position:" + position);
                        long postId = data.get(position).getID();
                        // do what ever you want to do with it
                    }
                });

                rv.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<FoodyItemInfo>> call, Throwable t) {

            }
        });
    }


}
