package com.example.hmtri1312624.foodyapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.hmtri1312624.foodyapp.Global.Global;

public class MainList extends AppCompatActivity {
    RVAdapter adapter;


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

        adapter = new RVAdapter(MainList.this, Global.data, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.d("", "clicked position:" + position);
                long postId = Global.data.get(position).getID();
            }
        });

        rv.setAdapter(adapter);
    }
}
