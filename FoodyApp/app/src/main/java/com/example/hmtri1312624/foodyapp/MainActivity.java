package com.example.hmtri1312624.foodyapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hmtri1312624.foodyapp.Global.Global;
import com.example.hmtri1312624.foodyapp.Model.FoodyItemInfo;
import com.example.hmtri1312624.foodyapp.Service.RestService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RVAdapter adapter;
    List<FoodyItemInfo> data;
    RecyclerView rv;
    Button btnNext,btnSearchNext;
    EditText editSearch;
    RelativeLayout layoutSearch;
    Context context;
    Activity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        activity = this;

        layoutSearch = (RelativeLayout) findViewById(R.id.layout_search);
        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);


        editSearch = (EditText)findViewById(R.id.editSearchNext);
        editSearch.setTypeface(FontManager.getTypeface(MainActivity.this,FontManager.ROBOTO));
        editSearch.setText(Global.CurrentQuery);
        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH)
                {
                    SearchOther();
                    return true;
                }
                return false;
            }
        });

        btnSearchNext = (Button)findViewById(R.id.btnSearchNext);
        btnSearchNext.setTypeface(FontManager.getTypeface(MainActivity.this,FontManager.FONTAWESOME));

        //do search different food
        btnSearchNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchOther();
            }
        });

        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setTypeface(FontManager.getTypeface(MainActivity.this,FontManager.ROBOTO));

        //do find next place of current food
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnNext.setVisibility(View.GONE);
                layoutSearch.setVisibility(View.GONE);
                String Next = editSearch.getText().toString();
                if (!Next.equals(""))
                    Global.CurrentQuery = Next;
                getData(Global.CurrentQuery);
                Global.hideSoftInput(activity);
            }
        });


        btnNext.setVisibility(View.GONE);
        layoutSearch.setVisibility(View.GONE);
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
                Global.hideSoftInput(activity);
                data = response.body();

                if (data == null)
                {
                    MyAlertDialog.ShowDialog("We did not found any places which has your food",activity);
                    btnNext.setVisibility(View.VISIBLE);
                    layoutSearch.setVisibility(View.VISIBLE);
                    return;
                }

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
                Global.hidePreloader();
            }
        });
    }

    private void SearchOther()
    {
        Global.CurrentQuery = editSearch.getText().toString();
        btnNext.setVisibility(View.GONE);
        layoutSearch.setVisibility(View.GONE);
        getData(Global.CurrentQuery);
        Global.hideSoftInput(activity);
    }
}
