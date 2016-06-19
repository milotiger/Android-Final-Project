package com.example.hmtri1312624.foodyapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.example.hmtri1312624.foodyapp.Global.Global;
import com.example.hmtri1312624.foodyapp.Model.FoodyMenuSet;


/**
 * Created by M-Tae on 6/19/2016.
 */
public class MenuActivity extends Activity {
    RVMAdapter adapter;
    RecyclerView rv;
    FoodyMenuSet data;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_rv_layout);

        context = this;
        rv = (RecyclerView) findViewById(R.id.menurv);
        rv.setHasFixedSize(true);

        StaggeredGridLayoutManager llm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        data = new FoodyMenuSet("",Global.currentMenuList);

        if(data.Dishes == null)
        {
            MyAlertDialog.ShowDialog("Menu set này không có hình ảnh hiển thị",this);
            return;
        }
        adapter = new RVMAdapter(MenuActivity.this, data.Dishes, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

            }
        });

        rv.setAdapter(adapter);

    }
}
