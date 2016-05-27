package com.example.hmtri1312624.foodyapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

        final MorphingButton btnShow = (MorphingButton)findViewById(R.id.btnShow);

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MorphingButton.Params circle = MorphingButton.Params.create()
                        .duration(500)
                        .cornerRadius(120) // 56 dp
                        .width(120) // 56 dp
                        .height(120) // 56 dp
                        .color(Color.parseColor("#4CAF50")) // normal state color
                        .colorPressed(Color.parseColor("#FF9800")); // pressed state color
                        //.icon(); // icon
                btnShow.morph(circle);
                ShowDialog("Done!!!");

            }
        });

        btnSearch = (Button)findViewById(R.id.btnSearchFood);
        btnSearch.setTypeface(FontManager.getTypeface(SearchActivity.this,FontManager.FONTAWESOME));

        editFood = (EditText)findViewById(R.id.editFood);
        editFood.setTypeface(FontManager.getTypeface(SearchActivity.this,FontManager.ROBOTO));

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SearchActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                String foodname = editFood.getText().toString();
                if(foodname == "") ShowDialog("Missing Type Your Food!!");
                else {
                    bundle.putString("FoodName", foodname);
                    i.putExtra("MyPackage", bundle);
                    startActivity(i);
                }
            }
        });
    }

    private void ShowDialog(String text){
        final AlertDialog alertDialog = new AlertDialog.Builder(SearchActivity.this).create();
        alertDialog.setTitle("Alert Dialog");
        alertDialog.setMessage(text);

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
