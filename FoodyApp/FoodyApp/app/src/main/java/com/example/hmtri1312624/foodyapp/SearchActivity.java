package com.example.hmtri1312624.foodyapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dd.morphingbutton.MorphingButton;

/**
 * Created by M-Tae on 5/27/2016.
 */
public class SearchActivity extends Activity {
    Button btnSearch;
    EditText editFood;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        btnSearch = (Button)findViewById(R.id.btnSearchFood);

        editFood = (EditText)findViewById(R.id.editFood);
        editFood.setTypeface(FontManager.getTypeface(SearchActivity.this,FontManager.ROBOTO));
        btnSearch.setTypeface(FontManager.getTypeface(SearchActivity.this,FontManager.ROBOTO));

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SearchActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                String foodname = editFood.getText().toString();
                if(foodname.compareTo("") == 0)
                    //ShowDialog("Missing Type Your Food!!");
                    MyAlertDialog.ShowDialog("Missing Type Your Food!!",SearchActivity.this);
                else {
                    bundle.putString("FoodName", foodname);
                    i.putExtra("MyPackage", bundle);
                    startActivity(i);
                }
            }
        });
    }
}
