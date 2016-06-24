package com.example.hmtri1312624.foodyapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

        Global.needReload = false;
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

    private void checkAndSearch()
    {
        Global.hideSoftInput(activity);
        Intent i = new Intent(SearchActivity.this, MainActivity.class);
        String foodname = editFood.getText().toString();

        if (Global.CheckOnline(btnSearch) == false)
            return;

        if(foodname.compareTo("") == 0)
            MyAlertDialog.ShowDialog("Missing Type Your Food!!",activity);
        else {
            Global.CurrentQuery = foodname;
            startActivity(i);
        }
    }
}
