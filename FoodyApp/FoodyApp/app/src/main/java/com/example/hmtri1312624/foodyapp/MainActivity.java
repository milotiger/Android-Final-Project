package com.example.hmtri1312624.foodyapp;

import android.content.Context;
import android.os.Bundle;
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

import com.example.hmtri1312624.foodyapp.Global.Global;
import com.example.hmtri1312624.foodyapp.Model.FoodyItemInfo;
import com.example.hmtri1312624.foodyapp.Service.RestService;

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
    Button btnNext,btnSearchNext;
    EditText editSearch;
    RelativeLayout layoutSearch;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        //adjust-layout-in-full-screen-mode-when-softkeyboard-is-visible
        AndroidBug5497Workaround.assistActivity(MainActivity.this);

        layoutSearch = (RelativeLayout) findViewById(R.id.layout_search);
        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        //bar = (ProgressBar) this.findViewById(R.id.progressBar);
        //Global.showPreloader(this, "Loading Data....");
        editSearch = (EditText)findViewById(R.id.editSearchNext);
        editSearch.setTypeface(FontManager.getTypeface(MainActivity.this,FontManager.ROBOTO));

        btnSearchNext = (Button)findViewById(R.id.btnSearchNext);
        btnSearchNext.setTypeface(FontManager.getTypeface(MainActivity.this,FontManager.FONTAWESOME));

        //do search different food
        btnSearchNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.CurrentQuery = editSearch.getText().toString();
                getData(Global.CurrentQuery);
            }
        });

        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setTypeface(FontManager.getTypeface(MainActivity.this,FontManager.ROBOTO));

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(Global.CurrentQuery);
            }
        });


        getData(Global.CurrentQuery);

    }

    private void getData(String FoodName){

        RestService restService = new RestService();
        Call<List<FoodyItemInfo>> call = restService.getService().GetPlaces(FoodName);

        Global.showPreloader(context, "Loading data...");
        call.enqueue(new Callback<List<FoodyItemInfo>>() {
            @Override
            public void onResponse(Call<List<FoodyItemInfo>> call, Response<List<FoodyItemInfo>> response) {
                Global.hidePreloader();
                data = response.body();
                adapter = new RVAdapter(MainActivity.this, data, new CustomItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Log.d("", "clicked position:" + position);
                        long postId = data.get(position).getID();
                    }
                });

                rv.setAdapter(adapter);

                btnNext.setVisibility(View.VISIBLE);
                layoutSearch.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<List<FoodyItemInfo>> call, Throwable t) {

            }
        });
    }
}
