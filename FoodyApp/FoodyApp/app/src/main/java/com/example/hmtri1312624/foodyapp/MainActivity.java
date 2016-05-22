package com.example.hmtri1312624.foodyapp;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RVAdapter adapter;

    ArrayList<ItemsListSingleItem> data = new ArrayList<>();

    RecyclerView rv;
    LinearLayout layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);


        data.add(
                new ItemsListSingleItem(
                        1,
                        "Hoang Minh Tri",
                        "Nam"
                ));

        data.add(
                new ItemsListSingleItem(
                        2,
                        "Nguyen Thanh Tri",
                        "Nu"
                ));
        data.add(
                new ItemsListSingleItem(
                        3,
                        "Cao Thai Toai",
                        "Nu"
                ));
        data.add(
                new ItemsListSingleItem(
                        4,
                        "Nguyen Viet Tri",
                        "Nu"
                ));


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


}
