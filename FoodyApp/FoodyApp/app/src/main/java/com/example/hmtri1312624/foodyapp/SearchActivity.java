package com.example.hmtri1312624.foodyapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hmtri1312624.foodyapp.Global.Global;

/**
 * Created by M-Tae on 5/27/2016.
 */
public class SearchActivity extends Activity {
    Button btnSearch;
    EditText editFood;
    Activity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        activity = this;

        btnSearch = (Button)findViewById(R.id.btnSearchFood);

        editFood = (EditText)findViewById(R.id.editFood);
        editFood.setTypeface(FontManager.getTypeface(SearchActivity.this,FontManager.ROBOTO));

        editFood.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH)
                {
                    checkAndSearch();
                    return true;
                }
                return false;
            }
        });

        btnSearch.setTypeface(FontManager.getTypeface(SearchActivity.this,FontManager.ROBOTO));

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndSearch();
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

    private void checkAndSearch()
    {
        Global.hideSoftInput(activity);
        Intent i = new Intent(SearchActivity.this, MainActivity.class);
        String foodname = editFood.getText().toString();
        if(foodname.compareTo("") == 0)
            ShowDialog("Missing Type Your Food!!");
        else {
            Global.CurrentQuery = foodname;
            startActivity(i);
        }
    }
}
