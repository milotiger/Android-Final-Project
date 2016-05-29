package com.example.hmtri1312624.foodyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.hmtri1312624.foodyapp.Global.Global;
import com.example.hmtri1312624.foodyapp.Model.FoodyItemInfo;
import com.example.hmtri1312624.foodyapp.Service.RestService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    public void SearchClick(View view) {

        assert ((EditText) findViewById(R.id.txtSearch)) != null;
        Global.Query = ((EditText) findViewById(R.id.txtSearch)).getText().toString();

        Global.ShowPreloader(this);
        getData();


    }

    private void getData(){
        RestService restService = new RestService();
        Call<List<FoodyItemInfo>> call = restService.getService().GetPlaces(Global.Query);

        call.enqueue(new Callback<List<FoodyItemInfo>>() {
            @Override
            public void onResponse(Call<List<FoodyItemInfo>> call, Response<List<FoodyItemInfo>> response) {
                Global.HidePreloader();
                Global.data = response.body();
                Intent myIntent = new Intent(getApplicationContext(), MainList.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(myIntent);
//                adapter = new RVAdapter(MainList.this, data, new CustomItemClickListener() {
//                    @Override
//                    public void onItemClick(View v, int position) {
//                        Log.d("", "clicked position:" + position);
//                        long postId = data.get(position).getID();
//                        // do what ever you want to do with it
//                    }
//                });

//                rv.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<FoodyItemInfo>> call, Throwable t) {

            }
        });
    }
}
