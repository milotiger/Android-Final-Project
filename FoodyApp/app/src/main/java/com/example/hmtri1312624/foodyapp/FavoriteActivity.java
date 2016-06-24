package com.example.hmtri1312624.foodyapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;

import com.example.hmtri1312624.foodyapp.Global.Global;
import com.example.hmtri1312624.foodyapp.Model.Account;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

/**
 * Created by M-Tae on 6/23/2016.
 */
public class FavoriteActivity extends Activity {
    RVAdapter adapter2;
    RecyclerView rv;
    TextView facebookname;
    Context context;
    Activity activity;
    CallbackManager callbackManager;
    LoginButton loginButton;
    Button btnBack;
    SharedPreferences sharedpreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_favorite);

        loginButton = (LoginButton)findViewById(R.id.login_button2);

        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday"));

        btnBack = (Button)findViewById(R.id.back_button2);
        btnBack.setTypeface(FontManager.getTypeface(this,FontManager.FONTAWESOME));
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FavoriteActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
        context = this;
        activity = this;

        rv = (RecyclerView) findViewById(R.id.favoriterv);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        //for facebook
        facebookname = (TextView)findViewById(R.id.txtfacebookname2);

        sharedpreferences = getSharedPreferences(Global.MyPref, Context.MODE_PRIVATE);

        //logout
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {

                if (currentAccessToken == null){
                    facebookname.setText("");
                    Global.currentAcc = new Account();
                    Intent i = new Intent(FavoriteActivity.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };


        facebookname.setText(sharedpreferences.getString("UserName","Default"));
        Global.currentAcc.userid = sharedpreferences.getString("UserID","xxx");

        //end for facebook
        GetFavorite();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void GetFavorite(){
        adapter2 = new RVAdapter(FavoriteActivity.this, Global.currentAcc.Favorite, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.d("", "clicked position:" + position);
                long postId = Global.currentAcc.Favorite.get(position).getID();
            }
        });
        rv.setAdapter(adapter2);
    }
}
