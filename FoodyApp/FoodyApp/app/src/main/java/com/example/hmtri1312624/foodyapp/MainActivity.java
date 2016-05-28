package com.example.hmtri1312624.foodyapp;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
    private ProgressBar bar;
    List<FoodyItemInfo> data;
    RecyclerView rv;
    LinearLayout layout;
    String currentFoodName = "", nextFoodName = "";
    Button btnNext,btnSearchNext;
    EditText editSearch;
    RelativeLayout layoutSearch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutSearch = (RelativeLayout) findViewById(R.id.layout_search);
        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        bar = (ProgressBar) this.findViewById(R.id.progressBar);
        editSearch = (EditText)findViewById(R.id.editSearchNext);
        editSearch.setTypeface(FontManager.getTypeface(MainActivity.this,FontManager.ROBOTO));

        btnSearchNext = (Button)findViewById(R.id.btnSearchNext);
        btnSearchNext.setTypeface(FontManager.getTypeface(MainActivity.this,FontManager.FONTAWESOME));

        //do search different food
        btnSearchNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextFoodName = editSearch.getText().toString();
                new ProgressTaskSearchNext().execute();
            }
        });

        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setTypeface(FontManager.getTypeface(MainActivity.this,FontManager.ROBOTO));

        //do search Food on SearchActivity
        new ProgressTask().execute();

        //do search current food
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ProgressTaskFindNext().execute();
            }
        });
    }

    private class ProgressTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute(){
            btnNext.setVisibility(View.GONE);
            bar.setVisibility(View.VISIBLE);
            layoutSearch.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Intent i = getIntent();
            Bundle bundle = i.getBundleExtra("MyPackage");
            if (bundle != null) {
                currentFoodName = bundle.getString("FoodName");
                //foodname = foodname.replace(" ","%20");
                getData(currentFoodName);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
//            final Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    bar.setVisibility(View.GONE);
//                }
//            }, 4000);

            //Retrofit work on another task so i will set hidden Progressbar in that task;
        }
    }

    private class ProgressTaskFindNext extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute(){
            bar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            getData(currentFoodName);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
        }
    }

    private class ProgressTaskSearchNext extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute(){
            bar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            getData(nextFoodName);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
        }
    }

    private void getData(String FoodName){

        RestService restService = new RestService();
        Call<List<FoodyItemInfo>> call = restService.getService().GetPlaces(FoodName);
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

                bar.setVisibility(View.GONE);
                btnNext.setVisibility(View.VISIBLE);
                layoutSearch.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<List<FoodyItemInfo>> call, Throwable t) {

            }
        });
    }
}
